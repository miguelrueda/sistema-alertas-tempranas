package jsf.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CounterBean implements java.io.Serializable {

    private static final Logger LOG = Logger.getLogger(CounterBean.class.getName());

    private int count;
    private String temp;

    public CounterBean() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
    
    public void increment() {
        count++;
        LOG.log(Level.INFO, "Valor cambiado: ", count);
    }

    public void actualizar() {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\Servicio Social Mayo 2014\\poll.txt"))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                temp = currentLine;
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }
    
}