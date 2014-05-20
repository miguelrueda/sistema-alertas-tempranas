package cveparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class JFCTest extends JFrame {

    public JFCTest() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file");
        this.getContentPane().add(fileChooser);
        fileChooser.setVisible(true);
        
        int ret = fileChooser.showDialog(null, "Open File");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println(file);
            CVEParser cveParser = new CVEParser();
            try {
                cveParser.doParse(new FileInputStream(file));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JFCTest.class.getName()).log(Level.SEVERE, null, ex);
            }
             
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFCTest();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
    }
    
}