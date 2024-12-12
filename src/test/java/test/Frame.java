package test;


import java.util.ArrayList;
import javax.swing.DefaultListModel;
import net.ortegabravo.modelo.EntrenosFechasUsuarios;
import net.ortegabravo.calendarioentrenosbeans.MiEventoInterfaceRecogerArrayEntrenosListener;

public class Frame extends javax.swing.JFrame implements MiEventoInterfaceRecogerArrayEntrenosListener {

    private javax.swing.JList<EntrenosFechasUsuarios> lstListaEjercicios1;
    private javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
    ArrayList<EntrenosFechasUsuarios> arrayLista = new ArrayList<>();
    MiEventoInterfaceRecogerArrayEntrenosListener listener;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Frame() {
        initComponents();
        colocaLista();
       //calendarioEntrenosBeans1.addRecogerArrayEntrenosListener(listener);
        calendarioEntrenosBeans1.addRecogerArrayEntrenosListener(this);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        calendarioEntrenosBeans1 = new net.ortegabravo.calendarioentrenosbeans.CalendarioEntrenosBeans();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(calendarioEntrenosBeans1);
        calendarioEntrenosBeans1.setBounds(100, 10, 556, 309);

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
        jScrollPane3.setBounds(240, 360, 330, 130);

    }

    private void cargaListaConObjetos(ArrayList<EntrenosFechasUsuarios> a) {
        
       
        System.out.println("esta en cargarlistaobjetos");
        DefaultListModel<EntrenosFechasUsuarios> dlm = new DefaultListModel();
        if (a != null) {

            
            
            ArrayList<EntrenosFechasUsuarios> exercicis = new ArrayList<>();
            //exercicis = DataAccess.getExercicisPerWorkout((entrenamiento));
            //variableControlItemSeleccionadoEntreno = true;

            for (EntrenosFechasUsuarios e : a) {
                dlm.addElement(e);
            }
        }
        lstListaEjercicios1.setModel(dlm);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private net.ortegabravo.calendarioentrenosbeans.CalendarioEntrenosBeans calendarioEntrenosBeans1;
    // End of variables declaration//GEN-END:variables

   
  
    

    @Override
    public void recogerArrayEntrenos(ArrayList<EntrenosFechasUsuarios>  e) {
        System.out.println("estoy en recogerArrayEntrenos el metodo de la interface");
          cargaListaConObjetos(e);
        
       
    }
}
