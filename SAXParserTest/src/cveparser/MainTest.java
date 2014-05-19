package cveparser;

/*
    Clase que prueba el parser para obtener las CVEs de la pagina NVD
    http://nvd.nist.gov/download/nvdcve-2014.xml
*/

public class MainTest {
    
    public static void main(String[] args) {
        
        CVEParser mCVEParser = new CVEParser();
        mCVEParser.doParse();
        
    }
    
}
