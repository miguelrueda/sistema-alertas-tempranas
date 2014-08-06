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

@Stateless
public class AnalizarBean implements AnalizarBeanLocal {
    
    @Resource
    TimerService timerService;
    private static final Logger LOG = Logger.getLogger(AnalizarBean.class.getName());
    private static final int START_HOUR = 11;
    private static final int START_MINUTES = 0;
    private static final int START_SECONDS = 0;
    private static final int INTERVAL_IN_MINUTES = 360;
    private static final String descripcion = "Analisis";
    private ScannerBean scanner;

    @Override
    public void setTimer() {
        LOG.log(Level.INFO, "Estableciendo el timer de analisis");
        stopTimer();
        Calendar initialExpiration = Calendar.getInstance();
        initialExpiration.set(Calendar.HOUR_OF_DAY, START_HOUR);
        initialExpiration.set(Calendar.MINUTE, START_MINUTES);
        initialExpiration.set(Calendar.SECOND, START_SECONDS);
        long duration = new Integer(INTERVAL_IN_MINUTES).longValue() * 60 * 1000;
        LOG.log(Level.INFO, "Timer de analisi creado: {0} con un intervalo de: {1}", new Object[]{initialExpiration.getTime(), INTERVAL_IN_MINUTES});
        timerService.createTimer(initialExpiration.getTime(), duration, descripcion);
    }

    @Override
    public void stopTimer() {
        Collection<Timer> timers = timerService.getTimers();
        for (Timer timer : timers) {
            timer.cancel();
            LOG.log(Level.INFO, "Timer: {0} cancelado.", timer.getInfo());
        }
    }
    
    @Timeout
    public void doScan(Timer timer) {
        LOG.log(Level.INFO, "Ejecutando el bean: {0} / {1}", new Object[]{timer.getInfo(), new Date()});
        scanner = new ScannerBean();
        Set<Result> resultados = scanner.doRecentScan();
        enviarResultados(resultados);
        LOG.log(Level.INFO, "Se encontraron: {0} posible amenazas.", resultados.size());
        LOG.log(Level.INFO, "El siguiente analisis se ejecutar\u00e1: {0}", timer.getNextTimeout());
    }
/*
        scanner = new ScannerBean();
        Set<Result> resultados = scanner.doRecentScan();
    */
    
    
    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public Date getNextFireTime() {
        Collection<Timer> timers = timerService.getTimers();
        Date next = null;
        for (Timer timer : timers) {
            next = timer.getNextTimeout();
        }
        return next;
    }
    
    private static final String host = "bmmail.banxico.org.mx";
    private static final String port = "25";
    private static final String from = "termometro_OSI@correobm.org.mx";
    private static final String to = "T41507@correobm.org.mx";
    private static final String asunto = "+ Resultados ";

    private void enviarResultados(Set<Result> resultados) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.debug", "true");
        try {
            Session session = Session.getInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            Date regdate = new Date();
            msg.setSubject(asunto + fmt.format(regdate));
            StringBuilder cuerpo = new StringBuilder();
            cuerpo.append("<h3>Se encontraron: ")
                    .append(resultados.size())
                    .append(" posibles amenazas - ")
                    .append(fmt.format(regdate))
                    .append("</h3>");
            for (Result result : resultados) {
                cuerpo.append("<p>");
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
            //msg.setText(cuerpo.toString());
            msg.setContent(cuerpo.toString(), "text/html; charset=utf-8");
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException e) {
            LOG.log(Level.INFO, "ocurrio un error al enviar el correo: {0}", e.getMessage());
        }
    }
}