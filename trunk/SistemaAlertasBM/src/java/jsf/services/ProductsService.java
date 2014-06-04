package jsf.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jpa.entities.Producto;

@ManagedBean
@SessionScoped
public class ProductsService implements java.io.Serializable {

    private List<Producto> productsList;
    private static final Logger LOG = Logger.getLogger(ProductsService.class.getName());
    private final static String[] vendors;
    private final static String[] products;
    private final static String[] versions;

    static {
        vendors = new String[5];
        vendors[0] = "Microsoft";
        vendors[1] = "Apache";
        vendors[2] = "Oracle";
        vendors[3] = "Adobe";
        vendors[4] = "Canonical";
        products = new String[50];
        /*
         products[0] = "Windows 7";
         products[1] = "Adobe Reader";
         products[2] = "MySQL";
         products[3] = "Tomcat";
         products[4] = "Ubuntu";
         */
        products[0] = "Terminal Services ActiveX Client 5.x";
        products[1] = "Microsoft Powerpoint 2008 for Mac";
        products[2] = "Microsoft Visual J# .NET Version 1.1 Redistributable Package";
        products[3] = "Microsoft Excel 2010";
        products[4] = "Microsoft Works Suite 2006";
        products[5] = "Microsoft Systems Management Server 2003 Tool Kit 2.x";
        products[6] = "Microsoft Disk Usage 1.x";
        products[7] = "RS Client Print ActiveX Control 9.x";
        products[8] = "Microsoft Visual Studio .NET 2002";
        products[9] = "Microsoft Office 2003 Small Business Edition";
        products[10] = "Expression Blend 2.x";
        products[11] = "Conferencing Add-in for Microsoft Office Outlook 8.x";
        products[12] = "Microsoft Safety Scanner 1.x";
        products[13] = "Microsoft Windows 2000 Server";
        products[14] = "Microsoft Windows Messenger 3.x";
        products[15] = "Microsoft Host Integration Server 2000";
        products[16] = "Microsoft Windows Server 2008";
        products[17] = "Windows Live Essentials 2011";
        products[18] = "Microsoft Business Solutions CRM 1.x";
        products[19] = "Microsoft Windows Essentials 2012";
        products[20] = "Windows Installer 3.x";
        products[21] = "Microsoft Streets &amp; Trips 2008";
        products[22] = "Microsoft Tweak UI 2.x";
        products[23] = "Microsoft Windows CE 6.0";
        products[24] = "Microsoft MSN Search Toolbar 2.x";
        products[25] = "Microsoft Word 97";
        products[26] = "Microsoft SMS Sender 1.x";
        products[27] = "Microsoft Visual Studio 2005";
        products[28] = "Microsoft Visual Studio 6 Professional";
        products[29] = "Microsoft Digital Image Pro 9.x";
        products[30] = "Microsoft PsService 2.x";
        products[31] = "MSN Chat ActiveX Control 4.x";
        products[32] = "Microsoft Encarta Encyclopedia 2004";
        products[33] = "Microsoft DirectX 8.x";
        products[34] = "Windows Live Movie Maker 2011 15.x";
        products[35] = "Microsoft SQL Server 2008";
        products[36] = "Microsoft Office Web Apps";
        products[37] = "Microsoft Help Viewer 1.0 SDK";
        products[38] = "Microsoft Windows SharePoint Services 3.x";
        products[39] = "Microsoft Expression Design 3";
        products[40] = "Microsoft Office Outlook Connector for IBM Lotus Domino 1.x";
        products[41] = "Microsoft eMbedded Visual C++ 4.x";
        products[42] = "ASP.NET Web Pages 2.x";
        products[43] = "Microsoft Windows Server Update Services (WSUS) 3.x";
        products[44] = "Windows Live Photo Gallery 16.x";
        products[45] = "Microsoft Game Voice 1.x";
        products[46] = "Microsoft Time Zone 2.x";
        products[47] = "Microsoft XML Parser 2.x";
        products[48] = "Microsoft Visio 2003 Viewer";
        products[49] = "Microsoft SQL Server Management Studio 9.x";

        versions = new String[11];
        versions[0] = "1.0";
        versions[1] = "4.0.1";
        versions[2] = "5.0";
        versions[3] = "1.3";
        versions[4] = "1.1.3";
        versions[5] = "8.x";
        versions[6] = "10.x";
        versions[7] = "3.x";
        versions[8] = "4.x";
        versions[9] = "5.x";
        versions[10] = "12.x";

    }

    public ProductsService() {
    }

    public List<Producto> crearListaProductos(int size) {
        productsList = new ArrayList<>();
        if (size == 50) {
            for (int i = 0; i < size; i++) {
                productsList.add(new Producto((i + 1), vendors[0], products[i], getRandomVersion(), getRandomType()));
            }
        } else {
            for (int i = 0; i < size; i++) {
                productsList.add(new Producto((i + 1), vendors[0], getRandomProduct(), getRandomVersion(), getRandomType()));
            }
        }

        return productsList;
    }

    public String getRandomVendor() {
        return vendors[(int) (Math.random() * 5)];
    }

    public String getRandomProduct() {
        return products[(int) (Math.random() * 50)];
    }

    public String getRandomVersion() {
        return versions[(int) (Math.random() * 11)];
    }

    public Integer getRandomType() {
        Random rd = new Random();
        int aux = rd.nextInt(10);
        if (aux > 5) {
            return 1;
        } else {
            return 2;
        }
    }

}
