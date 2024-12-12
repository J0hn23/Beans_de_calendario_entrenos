
package net.ortegabravo.calendarioentrenosbeans;

import java.util.ArrayList;
import java.util.EventObject;
import net.ortegabravo.modelo.EntrenosFechasUsuarios;


public class MiEventoDiaSeleccionado extends EventObject{

    private final ArrayList<EntrenosFechasUsuarios> efu;
    


    public MiEventoDiaSeleccionado(Object source, ArrayList<EntrenosFechasUsuarios>efu) {
        super(source);
        this.efu=efu;
    }

    public ArrayList<EntrenosFechasUsuarios> getEfu() {
        return efu;
    }

    @Override
    public Object getSource() {
        return source;
    }
    
    
    
}

 