package test;


import java.awt.HeadlessException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import net.ortegabravo.calendarioentrenosbeans.CalendarioEntrenosBeans;
import net.ortegabravo.calendarioentrenosbeans.MiEventoDiaSeleccionado;
import net.ortegabravo.calendarioentrenosbeans.MiEventoInterfaceRecogerArrayEntrenosListener;

public class Frame extends javax.swing.JFrame implements MiEventoInterfaceRecogerArrayEntrenosListener {

    private javax.swing.JList<String> lstListaEjercicios1;
    private javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Frame() {
        initComponents();
        colocaLista();
       
        calendarioEntrenosBeans1.addRecogerArrayEntrenosListener(this);

    }

    public Frame(JList<String> lstListaEjercicios1, CalendarioEntrenosBeans calendarioEntrenosBeans1) throws HeadlessException {
        this.lstListaEjercicios1 = lstListaEjercicios1;
        this.calendarioEntrenosBeans1 = calendarioEntrenosBeans1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        calendarioEntrenosBeans1 = new net.ortegabravo.calendarioentrenosbeans.CalendarioEntrenosBeans();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(calendarioEntrenosBeans1);
        calendarioEntrenosBeans1.setBounds(110, 10, 556, 309);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        Frame f = new Frame();
        f.setVisible(true);
        f.setSize(700, 600);

    }

    public void colocaLista() {

        lstListaEjercicios1 = new javax.swing.JList<>();
        jScrollPane3.setViewportView(lstListaEjercicios1);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(220, 360, 390, 130);

    }

    private void cargaListaConObjetos(ArrayList<String> a) {
         
        System.out.println("esta en cargarlistaobjetos tiene de size="+ a.size());            
         
        DefaultListModel<String> dlm = new DefaultListModel();
        
        if (a != null) {
            
            if(!a.isEmpty()){

                for (String e : a) {
                    dlm.addElement(e);
                }
            }else {dlm.addElement("Sin entrenos");}
        }
        lstListaEjercicios1.setModel(dlm);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private net.ortegabravo.calendarioentrenosbeans.CalendarioEntrenosBeans calendarioEntrenosBeans1;
    // End of variables declaration//GEN-END:variables

   
  
    

    @Override
    public void recogerArrayEntrenos(MiEventoDiaSeleccionado  e) {
        System.out.println("estoy en recogerArrayEntrenos el metodo de la interface");
          cargaListaConObjetos(e.getEfu());
        
       
    }
}
