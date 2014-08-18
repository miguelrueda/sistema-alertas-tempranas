package org.banxico.ds.sisal.ejb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;
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
import org.banxico.ds.sisal.entities.Software;
import org.banxico.ds.sisal.scanner.Result;
import org.banxico.ds.sisal.scanner.ScannerBean;

/**
 * Bean que realiza la tarea del analisis de vulnerabilidades
 *
 * @author t41507
 * @version 07.08.2014
 */
@Stateless(name="AnalizarBean", mappedName = "ejb/ds/sisalbm/AnalizarBean")
public class AnalizarBean implements AnalizarBeanLocal {
    
    /**
     * Inyección del recurso del servicio de tiempo
     */
    @Resource
    TimerService timerService;
    /**
     * Atributo Logger
     */
    private static final Logger LOG = Logger.getLogger(AnalizarBean.class.getName());
    /**
     * Atributos que definen la hora de la actualización
     */
    private static final int START_HOUR = 11;
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
    private static final String to1  = "";
    private static final String asunto = "+ Resultados ";
    private static final String [] recipientsArray = {"jamaya@banxico.org.mx", "XX@XX.com"};

    /**
     * Método que se encarga de establecer el tiempo para el analisis
     */
    @Override
    public void setTimer() {
        stopTimer();
        Calendar initialExpiration = Calendar.getInstance();
        initialExpiration.set(Calendar.HOUR_OF_DAY, START_HOUR);
        initialExpiration.set(Calendar.MINUTE, START_MINUTES);
        initialExpiration.set(Calendar.SECOND, START_SECONDS);
        long duration = new Integer(INTERVAL_IN_MINUTES).longValue() * 60 * 1000;
        LOG.log(Level.INFO, "Timer de analisis creado: {0} con un intervalo de: {1}", new Object[]{initialExpiration.getTime(), INTERVAL_IN_MINUTES});
        timerService.createTimer(initialExpiration.getTime(), duration, descripcion);
    }

    /**
     * Método que se utiliza para detener los temporizadores
     */
    @Override
    public void stopTimer() {
        Collection<Timer> timers = timerService.getTimers();
        for (Timer timer : timers) {
            timer.cancel();
            LOG.log(Level.INFO, "Timer: {0} cancelado.", timer.getInfo());
        }
    }
    
    /**
     * Método que se encarga de realizar el escaneo de vulnerabilidades recientes
     *
     * @param timer objeto de tipo Timer
     */
    @Timeout
    public void doScan(Timer timer) {
        Date reg = new Date();
        LOG.log(Level.INFO, "Ejecutando el bean: {0} / {1}", new Object[]{timer.getInfo(), reg});
        this.setUltimaEjecucion(reg);
        scanner = new ScannerBean();
        Set<Result> resultados = scanner.doRecentScan(reg);
        LOG.log(Level.INFO, "Se encontraron: {0} Posibles amenzas", resultados.size());
        if (!resultados.isEmpty()) {
            LOG.log(Level.INFO, "Enviando resultados por correo . . .");
            enviarResultados(resultados);
        } else {
            LOG.log(Level.INFO, "Los resultados no fueron enviados. . . ");
        }
        LOG.log(Level.INFO, "El siguiente analisis se ejecutar\u00e1: {0}", timer.getNextTimeout());
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
     * Método que se encarga de retornar información de la siguiente ejecución de la tarea
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
     * Método que se encarga de enviar los resultados por correo, usando javaMail
     * 
     * @param resultados conjunto de resultados para generar el cuerpo del correo
     */
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
            //Establecer receptor
            //InternetAddress [] recipients = new InternetAddress[recipientsArray.length];
            //for (int i = 0; i < recipientsArray.length; i++) {
            //    recipients[i] = new InternetAddress(recipientsArray[i]);
            //}
            //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); -- JAMAYA
            //msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CCss)); -- Servicio Social
            //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            //msg.setRecipients(Message.RecipientType.TO, recipients);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(CCss));
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
            LOG.log(Level.INFO, "El mensaje fue enviado correctamente!");
        } catch (MessagingException e) {
            LOG.log(Level.INFO, "ocurrio un error al enviar el correo: {0}", e.getMessage());
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
    
    
}