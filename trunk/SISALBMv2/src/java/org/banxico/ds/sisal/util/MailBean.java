package org.banxico.ds.sisal.util;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Set;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.banxico.ds.sisal.dao.VulnerabilityDAO;
import org.banxico.ds.sisal.entities.Software;
import org.banxico.ds.sisal.scanner.Result;

public class MailBean implements java.io.Serializable {

    /**
     * Atributos de serialización y logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(MailBean.class.getName());
    /**
     * Atributos de salida del correo
     */
    private static final String host = "bmmail.banxico.org.mx";
    private static final String port = "25";
    private static final String from = "termometro_OSI@correobm.org.mx";
    private static final String asunto = "+ Resultados ";
    /**
     * Recipientes del correo
     */
    private static final String analistaOSI = "martha.pio@banxico.org.mx";
    private static final String administrador = "jamaya@banxico.org.mx";
    private static final String serviciosocial = "T41507@correobm.org.mx";
    private static final String[] recipientsArray = {"jamaya@banxico.org.mx", "martha.pio@banxico.org.mx"};

    private String[] receptores;

    public MailBean() {
    }

    public String[] getReceptores() {
        return receptores;
    }

    public void setReceptores(String[] receptores) {
        this.receptores = receptores;
    }

    public boolean enviarCorreodePrueba(String asunto) {
        boolean enviado = false;
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        try {
            Session session = Session.getInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(serviciosocial));
            msg.setSubject("+ " + asunto);
            StringBuilder cuerpo = new StringBuilder();
            cuerpo.append("Contenido de prueba");
            msg.setContent(cuerpo.toString(), "text/html;charset=utf8");
            msg.setSentDate(new Date());
            Transport.send(msg);
            enviado = true;
        } catch (MessagingException e) {
            LOG.log(Level.INFO, "MailBean#enviarCorreodePrueba() - El correo no pudo ser enviado: {0}", e.getMessage());
        }
        return enviado;
    }

    public boolean enviarCorreodeResultados(String asunto, Set<Result> resultados) {
        boolean enviado = false;
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        try {
            Session session = Session.getInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(analistaOSI));
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(serviciosocial));
            msg.setSubject("+ " + asunto);
            StringBuilder cuerpo = new StringBuilder();
            cuerpo.append("<h3>")
                    .append("Se encontraron: ")
                    .append(resultados.size())
                    .append(" posible(s) amenaza(s).")
                    .append("</h3>");
            cuerpo.append("<div style='font-family:calibri !important;font-size:9pt !important'>");
            cuerpo.append("<table style='width:100%;border-collapse:collapse;font-size:9pt'>");
            cuerpo.append("<thead style='border:1px solid #000;background:#797ca3;color:#FFF'>")
                    .append("<td>Vulnerabilidad</td>")
                    .append("<td>Publicación</td>")
                    .append("<td>Gravedad</td>")
                    .append("<td>Software Afectado</td>")
                    .append("<td>Grupos Afectados</td>")
                    .append("<td>Vector de Ataque</td>")
                    .append("</thead>");
            cuerpo.append("<tbody>");
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            for (Result result : resultados) {
                String sev = result.getVulnerabilidad().getSeverity();
                String es_sev = "";
                if (sev.equalsIgnoreCase("high")) {
                    es_sev = "Alta";
                } else if (sev.equalsIgnoreCase("medium")) {
                    es_sev = "Media";
                } else if (sev.equalsIgnoreCase("low")) {
                    es_sev = "Baja";
                } else {
                    es_sev = "ND";
                }
                cuerpo.append("<tr>")
                        .append("<td style='border:1px solid #000;text-align:center'>")
                        .append(result.getVulnerabilidad().getName())
                        .append("</td>")
                        .append("<td style='border:1px solid #000;text-align:center'>")
                        .append(fmt.format(result.getVulnerabilidad().getPublished()))
                        .append("</td>")
                        .append("<td style='border:1px solid #000;text-align:center'>")
                        .append(es_sev)
                        .append("</td>")
                        .append("<td style='border:1px solid #000'>")
                        .append("<table style='border:none;font-size:9pt'>")
                        .append("<tbody>");
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
                        .append("<table style='border:none;font-size:9pt;'>")
                        .append("<tbody>");
                for (String group : result.getGruposList()) {
                    cuerpo.append("<tr>")
                            .append("<td>")
                            .append(group)
                            .append("</td>")
                            .append("</tr>");
                }
                VulnerabilityDAO vulndao = new VulnerabilityDAO();
                cuerpo.append("</tbody>")
                        .append("</table>")
                        .append("</td>")
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
            cuerpo.append("</tbody>")
                    .append("</table>");
            cuerpo.append("</div>");
            msg.setContent(cuerpo.toString(), "text/html;charset=utf8");
            msg.setSentDate(new Date());
            Transport.send(msg);
            enviado = true;
        } catch (MessagingException e) {
            LOG.log(Level.INFO, "MailBean#enviarCorreodeResultados() - El correo no pudo ser enviado: {0}", e.getMessage());
        }
        return enviado;
    }

}
