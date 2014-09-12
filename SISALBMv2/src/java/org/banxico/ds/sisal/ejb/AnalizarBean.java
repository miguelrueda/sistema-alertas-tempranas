package org.banxico.ds.sisal.ejb;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.banxico.ds.sisal.dao.VulnerabilityDAO;
import org.banxico.ds.sisal.entities.Software;
import org.banxico.ds.sisal.scanner.Result;
import org.banxico.ds.sisal.scanner.ScannerBean;

/**
 * Bean que realiza la tarea del analisis de vulnerabilidades
 *
 * @author t41507
 * @version 07.08.2014
 */
@Stateless(name = "AnalizarBean", mappedName = "ejb/ds/sisalbm/AnalizarBean")
public class AnalizarBean implements AnalizarBeanLocal {

    /**
     * Inyección del recurso del servicio de tiempo
     */
    @Resource
    TimerService timerService;
    @Resource
    SessionContext context;
    /**
     * Atributo Logger
     */
    private static final Logger LOG = Logger.getLogger(AnalizarBean.class.getName());
    /**
     * Atributos que definen la hora de la actualización
     */
    private static final int START_HOUR = 14;
    private static final int START_MINUTES = 0;
    private static final int START_SECONDS = 0;
    private static final int INTERVAL_IN_MINUTES = 1440;
    /**
     * Atributos del Bean
     */
    private static final String descripcion = "Analisis";
    private ScannerBean scanner;
    private Date ultimaEjecucion;
    /**
     * Atributos de Envio de correo
     */
    private static final String host = "bmmail.banxico.org.mx";
    private static final String port = "25";
    private static final String from = "termometro_OSI@correobm.org.mx";
    private static final String to = "jamaya@banxico.org.mx";
    private static final String CCss = "T41507@correobm.org.mx";
    private static final String to1 = "";
    private static final String asunto = "+ Resultados ";
    private static final String[] recipientsArray = {"jamaya@banxico.org.mx", "XX@XX.com"};

    /**
     * Método que se encarga de establecer el tiempo para el analisis
     */
    @Override
    public void setTimer() {
        try {
            stopTimer();
            Calendar initialExpiration = Calendar.getInstance();
            initialExpiration.set(Calendar.HOUR_OF_DAY, START_HOUR);
            initialExpiration.set(Calendar.MINUTE, START_MINUTES);
            initialExpiration.set(Calendar.SECOND, START_SECONDS);
            long duration = new Integer(INTERVAL_IN_MINUTES).longValue() * 60 * 1000;
            LOG.log(Level.INFO, "AnalizarBean#setTimer() - Timer de analisis creado: {0} con un intervalo de: {1}", new Object[]{initialExpiration.getTime(), INTERVAL_IN_MINUTES});
            timerService.createTimer(initialExpiration.getTime(), duration, descripcion);
        } catch (IllegalArgumentException e) {
            LOG.log(Level.INFO, "AnalizarBean#setTimer() - Ocurrio un error al establecer los argumentos del Timer: {0}", e.getMessage());
        } catch (IllegalStateException e) {
            LOG.log(Level.INFO, "AnalizarBean#setTimer() - Ocurrio un error, estado ilegal del Timer: {0}", e.getMessage());
        } catch (EJBException e) {
            LOG.log(Level.INFO, "AnalizarBean#setTimer() - Ocurrio un error con el EJB: {0}", e.getMessage());
        }
        
    }

    /**
     * Método que se utiliza para detener los temporizadores
     */
    @Override
    public void stopTimer() {
        try {
            Collection<Timer> timers = timerService.getTimers();
            for (Timer timer : timers) {
                timer.cancel();
                LOG.log(Level.INFO, "AnalizarBean#stopTimer() - Timer: {0} cancelado.", timer.getInfo());
            }
        } catch (IllegalStateException e) {
            LOG.log(Level.INFO, "AnalizarBean#stopTimer() - Ocurrio un error, estado ilegal del Timer: {0}", e.getMessage());
        } catch (EJBException e) {
            LOG.log(Level.INFO, "AnalizarBean#stopTimer() - Ocurrio un error con el EJB: {0}", e.getMessage());
        }
    }

    /**
     * Método que se encarga de realizar el escaneo de vulnerabilidades
     * recientes
     *
     * @param timer objeto de tipo Timer
     */
    @Timeout
    public void doScan(Timer timer) {
        Date reg = new Date();
        LOG.log(Level.INFO, "AnalizarBean#doScan() - Ejecutando el bean: {0} / {1}", new Object[]{timer.getInfo(), reg});
        this.setUltimaEjecucion(reg);
        scanner = new ScannerBean();
        Set<Result> resultados = scanner.doRecentScan(reg);
        LOG.log(Level.INFO, "AnalizarBean#doScan() - Se encontraron: {0} Posibles amenzas", resultados.size());
        if (!resultados.isEmpty()) {
            LOG.log(Level.INFO, "AnalizarBean#doScan() - Enviando resultados por correo . . .");
            doPersist(resultados);
            long delay = 60000L;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                LOG.log(Level.INFO, "AnalizarBean#doScan() - Ocurrio un error al ejecutar la espera!!!");
            }
            enviarResultados(resultados);
        } else {
            LOG.log(Level.INFO, "AnalizarBean#doScan() - No se encontraron incidencias; los resultados no fueron enviados. . . ");
        }
        LOG.log(Level.INFO, "AnalizarBean#doScan() - El siguiente analisis se ejecutar\u00e1: {0}", timer.getNextTimeout());
    }

    /**
     * Método que se encarga de retornar la descripción de la tarea
     *
     * @return cadena con la descripción
     */
    @Override
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método que se encarga de retornar información de la siguiente ejecución
     * de la tarea
     *
     * @return fecha de la siguiente ejecución
     */
    @Override
    public Date getNextFireTime() {
        Collection<Timer> timers = timerService.getTimers();
        Date next = null;
        for (Timer timer : timers) {
            next = timer.getNextTimeout();
        }
        return next;
    }

    /**
     * Método que se encarga de enviar los resultados por correo, usando
     * javaMail
     *
     * @param resultados conjunto de resultados para generar el cuerpo del
     * correo
     */
    private static void enviarResultados(Set<Result> resultados) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        try {
            Session session = Session.getInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));  //JAMAYA
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CCss)); //Servicio Social
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            Date regdate = new Date();
            msg.setSubject(asunto + fmt.format(regdate));
            StringBuilder cuerpo = new StringBuilder();
            cuerpo.append("<h2 style='color=#FFF;background:#FF0'>")
                    .append("Se encontraron: ")
                    .append(resultados.size())
                    .append(" posibles amenazas.")
                    .append("</h2>");
            cuerpo.append("<table style='width:100%;border-collapse:collapse;' >");
            cuerpo.append("<thead style='border:1px solid #000;background:#797ca3;color:#FFF'>")
                    .append("<td>Vulnerabilidad</td>")
                    .append("<td>Publicación</td>")
                    .append("<td>Gravedad</td>")
                    .append("<td>Software Afectado</td>")
                    .append("<td>Grupos Afectados</td>")
                    .append("<td>Vector de Ataque</td>")
                    .append("</thead>");
            cuerpo.append("<tbody>");
            for (Result result : resultados) {
                String sev = result.getVulnerabilidad().getSeverity();
                String es_sev = "";
                if (sev.equalsIgnoreCase("high")) {
                    es_sev = "Alta";
                } else if (sev.equalsIgnoreCase("medium")) {
                    es_sev = "Medium";
                } else if (sev.equalsIgnoreCase("low")) {
                    es_sev = "Baja";
                } else {
                    es_sev = "ND";
                }
                cuerpo.append("<tr>").append("<td style='border:1px solid #000'>").append(result.getVulnerabilidad().getName())
                        .append("</td>").append("<td style='border:1px solid #000'>")
                        .append(fmt.format(result.getVulnerabilidad().getPublished()))
                        .append("</td>")
                        .append("<td style='border:1px solid #000'>")
                        .append(es_sev)
                        .append("</td>")
                        .append("<td style='border:1px solid #000'>")
                        .append("<table style='border:none'>")
                        .append("<tbody>");
                //SOFTWARE
                for (Software sw : result.getSwList()) {
                    cuerpo.append("<tr>")
                            .append("<td>")
                            .append(sw.getNombre())
                            .append("</td>")
                            .append("</tr>");
                }
                cuerpo.append("</tbody>")
                        .append("</table>")
                        .append("</td>")
                        .append("<td style='border:1px solid #000'>")
                        .append("<table style='border:none'>")
                        .append("<tbody>");
                for (String group : result.getGruposList()) {
                    cuerpo.append("<tr>").append("<td>").append(group).append("</td>").append("</tr>");
                }
                VulnerabilityDAO vulndao = new VulnerabilityDAO();
                cuerpo.append("</tbody>").append("</table>").append("</td>")
                        .append("<td style='border:1px solid #000'>");
                String vector = result.getVulnerabilidad().getCVSS().getVector();
                if (!vector.equals("") && !vector.equalsIgnoreCase("ND")) {
                    cuerpo.append(vulndao.describirVector(vector));
                } else {
                    cuerpo.append("Vector No Disponible");
                }
                cuerpo.append("</td>")
                        .append("</tr>");
                cuerpo.append("<tr>")
                        .append("<td colspan='6' style='border:1px solid #000;text-align:left !important'>")
                        .append(result.getVulnerabilidad().getDescription())
                        .append("</td>")
                        .append("</tr>");
            }
            cuerpo.append("</tbody>").append("</table>");
            msg.setContent(cuerpo.toString(), "text/html;charset=utf-8");
            msg.setSentDate(new Date());
            Transport.send(msg);
            LOG.log(Level.INFO, "AnalizarBean#enviarResultados() - El mensaje fue enviado correctamente!");
        } catch (MessagingException e) {
            LOG.log(Level.INFO, "AnalizarBean#enviarResultados() - Ocurrio un error al enviar el correo: {0}", e.getMessage());
        }
    }

    /**
     * Getter
     *
     * @return fecha con la ultima ejecucion
     */
    @Override
    public Date getUltimaEjecucion() {
        if (ultimaEjecucion != null) {
            return this.ultimaEjecucion;
        }
        return null;
    }

    /**
     * Setter
     *
     * @param ultimaEjecucion fecha con la ultima ejecucion
     */
    public void setUltimaEjecucion(Date ultimaEjecucion) {
        this.ultimaEjecucion = ultimaEjecucion;
    }

    /**
     * 
     * @param resultados
     * @return 
     */
    private boolean doPersist(Set<Result> resultados) {
        boolean flag = false;
        int res = 0;
        VulnerabilityDAO vdao = new VulnerabilityDAO();
        for (Result result : resultados) {
            try {
                res = vdao.comprobarExistenciaVulnerabilidad(result.getVulnerabilidad().getName());
                if (res == 0) { //No existe
                    vdao.crearVulnerabilidad(result);
                }
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, "AnalizarBean#doPersist() - Ocurrio un error al registrar la vulnerabilidad: {0}", result.getVulnerabilidad().getName());
            }
        }
        return flag;
    }

}

 /*
    private void enviarResultados(Set<Result> resultados) {
        //Instanciar un objeto de propiedades
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        //props.put("mail.debug", "true");
        try {
            //Obtener una sesión de correo
            Session session = Session.getInstance(props, null);
            //Crear una instancia del mensaje
            Message msg = new MimeMessage(session);
            //Establecer emisor
            msg.setFrom(new InternetAddress(from));
            //Establecer receptor o lista de receptores
            //>
            InternetAddress [] recipients = new InternetAddress[recipientsArray.length];
            for (int i = 0; i < recipientsArray.length; i++) {
                recipients[i] = new InternetAddress(recipientsArray[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, recipients);
            <//
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));  //JAMAYA
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CCss)); //Servicio Social
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            Date regdate = new Date();
            //Asunto del correo
            msg.setSubject(asunto + fmt.format(regdate));
            //Buffer para el cuerpo del correo
            StringBuilder cuerpo = new StringBuilder();
            cuerpo.append("Se encontraron: ").append(resultados.size()).append(" posibles amenazas.");
            //Para cada resultado
            for (Result result : resultados) {
                cuerpo.append("<p>");
                String sev = result.getVulnerabilidad().getSeverity();
                //Traducir gravedad
                String es_sev = "";
                if (sev.equalsIgnoreCase("high")) {
                    es_sev = "Alta";
                } else if (sev.equalsIgnoreCase("medium")) {
                    es_sev = "Medium";
                } else if (sev.equalsIgnoreCase("low")) {
                    es_sev = "Baja";
                } else {
                    es_sev = "ND";
                }
                //Cuerpo del mensaje
                cuerpo.append("La vulnerabilidad <u style='background: #FD0; color: #02F; text-decoration:none; font-size:18px'>")
                        .append(result.getVulnerabilidad().getName())
                        .append("</u> publicada el: ")
                        .append(fmt.format(result.getVulnerabilidad().getPublished()))
                        .append(" de gravedad: ")
                        .append(es_sev)
                        .append(" puede afectar al Software: ");
                cuerpo.append("<ul>");
                for (Software sw : result.getSwList()) {
                    cuerpo.append("<li>")
                            .append(sw.getNombre())
                            .append("</li>");
                }
                cuerpo.append("</ul>");
                cuerpo.append("<br /> Descripción: ")
                        .append(result.getVulnerabilidad().getDescription());
                cuerpo.append("<br /> Los grupos afectados son: ");
                cuerpo.append("<ul>");
                for (String grupo : result.getGruposList()) {
                    cuerpo.append("<li>")
                            .append(grupo)
                            .append("</li>");
                }
                cuerpo.append("</ul>");
                cuerpo.append("</p>");
            }
            //cuerpo.append("Correo generado por Java - ").append(fmt.format(new Date()));
            //msg.setText(cuerpo.toString());
            msg.setContent(cuerpo.toString(), "text/html; charset=utf-8");
            msg.setSentDate(new Date());
            Transport.send(msg);
            LOG.log(Level.INFO, "AnalizarBean#enviarResultados() - El mensaje fue enviado correctamente!");
        } catch (MessagingException e) {
            LOG.log(Level.INFO, "AnalizarBean#enviarResultados() - Ocurrio un error al enviar el correo: {0}", e.getMessage());
        }
    }*/
