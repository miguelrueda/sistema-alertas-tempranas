package org.banxico.ds.sisal.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Clase que se encarga de generar el o los reportes PDf
 *
 * @author t41507
 */
public class GenerarPDF {
    
    /**
     * Atributo Logger
     */
    private static final Logger LOG = Logger.getLogger(GenerarPDF.class.getName());
    /**
     * Atributos de Fuentes
     */
    private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    
    
    /**
     * Método que se encarga de crear el objeto de tipo documento
     *
     * @param file nombre del archivo a generar
     * @return objeto de tipo documento
     */
    public static Document createPDF(String file) {
        //Instanciar variable de tipo Document
        Document document = null;
        try {
            //Inicializarla y asociarle un flujo de salida
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            //Abrir el documento y agregar contenido
            document.open();
            addMetaData(document);
            addTitlePage(document);
            createTable(document);
            //Cerrar documento
            document.close();
        } catch (DocumentException e) {
            LOG.log(Level.INFO, "ocurrio una excepci\u00f3n al crear el documento: {0}", e.getMessage());
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, "no se encontro el archivo: {0}", ex.getMessage());
        }
        return document;
    }

    /**
     * Método que se encarga de agregar metadatos al documento pdf
     * 
     * @param document documento al cual se le agregara el contenido
     */
    private static void addMetaData(Document document) {
        document.addTitle("Reporte de prueba");
        document.addSubject("Generación de reporte desde Java");
        document.addAuthor("Banco de México");
        document.addCreator("Banco de México");
    }

    /**
     * Método que se encarga de agregar la portada al documento
     * 
     * @param document documento al cual se le agregara el contenido
     * @throws DocumentException  cuando no se pueda agregar el contenido
     */
    private static void addTitlePage(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
        createEmptyLine(preface, 1);
        preface.add(new Paragraph("PDF_Report", TIME_ROMAN));
        
        createEmptyLine(preface, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        preface.add(new Paragraph("Reporte creado: " + simpleDateFormat.format(new Date()), TIME_ROMAN_SMALL));
        document.add(preface);
    }

    /**
     * Método que se encarga de crear una tabla en el documento
     * 
     * @param document referencía al documento donde se agrega la tabla
     * @throws DocumentException  cuando no se puede agregar el contenido
     */
    private static void createTable(Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        createEmptyLine(paragraph, 2);
        document.add(paragraph);
        //Crear el objeto de tipo tabla
        PdfPTable table = new PdfPTable(3);
        //Crear celdas de encabezado
        PdfPCell c1 = new PdfPCell(new Phrase("First Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Last Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Test"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);
        //Agregar contenido aleatorio a la tabla
        for (int i = 0; i < 5; i++) {
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell("Banco");
            table.addCell("de México");
            table.addCell("Bien");
        }
        //Agregar la tabla al documento
        document.add(table);
    }

    /**
     * Método que se encarga de agregar lineas en blanco
     * 
     * @param paragraph parrafo al cual se le agregará la linea
     * @param number  cantidad de lineas
     */
    private static void createEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
}