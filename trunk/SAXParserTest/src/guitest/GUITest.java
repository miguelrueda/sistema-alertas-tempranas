package guitest;

import cveparser.CVE;
import cveparser.CVEParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GUITest extends javax.swing.JFrame {

    private static final Logger LOG = Logger.getLogger(GUITest.class.getName());

    public GUITest() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        urlButton = new javax.swing.JButton();
        filterTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        fileButton.setText("Seleccionar Archivo . . .");
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Prueba por lectura del archivo");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Prueba por lectura de URL");

        urlButton.setText("Ingresar URL");
        urlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Filtro");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(urlButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(filterTextField))
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileButton)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(urlButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void urlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlButtonActionPerformed
        String url_entry = JOptionPane.showInputDialog(this, "Ingresar la URL del archivo: ", "URL", JOptionPane.PLAIN_MESSAGE);
        String filtro = filterTextField.getText().trim();
        System.out.println("Filtro: " + filtro);
        if ((url_entry != null) && (url_entry.length() > 0)) {
            System.out.println("***ANALIZANDO URL");
            analizarURL(url_entry, filtro);
        }
    }//GEN-LAST:event_urlButtonActionPerformed

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar un archivo: ");
        this.getContentPane().add(fileChooser);
        fileChooser.setVisible(true);
        int ret = fileChooser.showDialog(null, "Abrir archivo");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println(file.getAbsolutePath());
            try {
                parsearArchivo(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                LOG.log(Level.SEVERE, "El archivo seleccionado no esta disponible: {0}", e);
            }
        }
    }//GEN-LAST:event_fileButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUITest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUITest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUITest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUITest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUITest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fileButton;
    private javax.swing.JTextField filterTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton urlButton;
    // End of variables declaration//GEN-END:variables

    private void analizarURL(String url_entry, String filtro) {
        try {
            URL an_url = new URL(url_entry);
            System.out.println("URL ingresada: " + an_url);
            URLConnection urlConnection = an_url.openConnection();
            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                System.out.println("***Conexión Abierta");
                connection = (HttpURLConnection) urlConnection;
            } else {
                System.out.println("Ingresar una URL valida....");
                return;
            }
            InputStream is = connection.getInputStream();
            System.out.println("***IS: " + is.available());
            CVEParser cveParser = new CVEParser();;
            if (!(filtro.length() == 0)) {
                System.out.println("Con filtro");
                cveParser.setFiltro(filtro);
            } else {
                System.out.println("Normal");
            }
            List<CVE> cves = cveParser.getListCVE(is);
            for (CVE cve : cves) {
                System.out.println(cve);
            }
            JOptionPane.showMessageDialog(null, "Se encontraron: " + cves.size() + " entradas.");
        } catch (MalformedURLException e) {
            LOG.log(Level.SEVERE, "La URL ingresada no tiene un formato correcto: {0}", e);

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error de entrada/salida: {0}", ex);
        }
    }

    private void parsearArchivo(FileInputStream fileInputStream) {
        CVEParser cveParser = new CVEParser();
        List<CVE> lista = cveParser.getListCVE(fileInputStream);
        JOptionPane.showMessageDialog(null, "Se encontraron: " + lista.size() + " entradas.");
        for (CVE cve : lista) {
            System.out.println(cve);
        }
    }
}
