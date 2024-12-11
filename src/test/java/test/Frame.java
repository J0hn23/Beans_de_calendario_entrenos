
package test;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import net.ortegabravo.modelo.EntrenosFechasUsuarios;


public class Frame extends javax.swing.JFrame {

   private javax.swing.JList<EntrenosFechasUsuarios> lstListaEjercicios1;
   private javax.swing.JScrollPane jScrollPane3= new javax.swing.JScrollPane();
   ArrayList<EntrenosFechasUsuarios>arrayLista=new ArrayList<>();
    
   @SuppressWarnings("OverridableMethodCallInConstructor")
    public Frame() {
        initComponents();
        colocaLista();
      
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        calendarioEntrenosBeans2 = new net.ortegabravo.calendarioentrenosbeans.CalendarioEntrenosBeans();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(calendarioEntrenosBeans2);
        calendarioEntrenosBeans2.setBounds(90, 10, 556, 309);

        pack();
    }// </editor-fold>//GEN-END:initComponents

 
    public static void main(String args[]) {
        
        Frame f=new Frame();
        f.setVisible(true);
        f.setSize(700,600);
        
        
    }
    
    
    
    public void colocaLista(){
    
        lstListaEjercicios1= new javax.swing.JList<>();
        jScrollPane3.setViewportView(lstListaEjercicios1);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(240, 360, 330, 130);
        
       
        
        //cargaListaConObjetos(calendarioEntrenosBeans1.getFechasFiltradas());
        
    
    }
    
    private void cargaListaConObjetos(ArrayList<EntrenosFechasUsuarios>a) {
        DefaultListModel<EntrenosFechasUsuarios> dlm = new DefaultListModel();
        if(a!=null){
        ArrayList<EntrenosFechasUsuarios> exercicis=new ArrayList<>();
        //exercicis = DataAccess.getExercicisPerWorkout((entrenamiento));
        //variableControlItemSeleccionadoEntreno = true;
        

        for (EntrenosFechasUsuarios e : a) {
            dlm.addElement(e);
        }
        }
       lstListaEjercicios1.setModel(dlm);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private net.ortegabravo.calendarioentrenosbeans.CalendarioEntrenosBeans calendarioEntrenosBeans2;
    // End of variables declaration//GEN-END:variables
}
