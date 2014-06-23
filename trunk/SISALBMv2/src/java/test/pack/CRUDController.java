package test.pack;

import javax.servlet.http.HttpServlet;

public class CRUDController extends HttpServlet implements java.io.Serializable {
/*
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(CRUDController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("action") != null) {
            String action = (String) request.getParameter("action");
            if (action.equals("list")) {
                LOG.log(Level.INFO, "Parametro List Recibido");
                try {
                    List<CVE> cveList = new ArrayList<>();
                    VulnerabilityDAO dao = new VulnerabilityDAO();
                    cveList = dao.retrieveAllCVEsFromFile();
                    LOG.log(Level.INFO, "Obteniendo los CVES");
                    Gson gson = new Gson();
                    JsonElement element = gson.toJsonTree(cveList, new TypeToken<List<CVE>>(){}.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();
                    listData = "{\"Result\":\"OK\",\"Records\":" + listData + "}";
                    response.setContentType("application/json");
                    response.getWriter().print(listData);
                    LOG.log(Level.INFO, listData);
                } catch (IOException e) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + e.getMessage() + "}";
                    LOG.log(Level.INFO, error);
                    response.getWriter().print(error);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
*/
}