package org.banxico.ds.sisal.prueba;

import java.util.Date;
import java.text.SimpleDateFormat;
import org.banxico.ds.sisal.scanner.ScannerBean;
import java.util.Set;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.banxico.ds.sisal.dao.VulnerabilityDAO;
import org.banxico.ds.sisal.entities.Software;
import org.banxico.ds.sisal.scanner.Result;

public class SendMailTest {

    private static ScannerBean scanner = new ScannerBean();
    private static final String host = "bmmail.banxico.org.mx";
    private static final String port = "25";
    private static final String from = "termometro_OSI@correobm.org.mx";
    private static final String to = "T41507@correobm.org.mx";
    private static final String asunto = "+ Resultados ";

    public static void main(String[] args) {

        Set<Result> resultados = scanner.doCompleteScan("15/07/2014", "01/08/2014");
        if (!resultados.isEmpty()) {
            enviarResultados(resultados);
        } else {
            System.out.println("Sin Resultados");
        }
        

    }

    private static void enviarResultados(Set<Result> resultados) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        try {
            Session session = Session.getInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            Date regdate = new Date();
            msg.setSubject(asunto + fmt.format(regdate));
            StringBuilder cuerpo = new StringBuilder();
            cuerpo.append("<h3 style='color=#FFF;background:#FF0'>")
                    .append("Se encontraron: ")
                    .append(resultados.size())
                    .append(" posibles amenazas.")
                    .append("</h3>");
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
                        .append("<td style='border:1px solid #000'>").append(vulndao.describirVector(result.getVulnerabilidad().getCVSS().vector)).append("</td>").append("</tr>");
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
        } catch (MessagingException e) {
            System.out.println("Error de mensajeria: " + e.getMessage());
        }
    }
}
