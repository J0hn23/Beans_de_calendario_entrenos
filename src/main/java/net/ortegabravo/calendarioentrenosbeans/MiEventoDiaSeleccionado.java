
package net.ortegabravo.calendarioentrenosbeans;

import java.util.ArrayList;
import java.util.EventObject;


public class MiEventoDiaSeleccionado extends EventObject{

    private final ArrayList<String> efu;
    


    public MiEventoDiaSeleccionado(Object source, ArrayList<String>efu) {
        super(source);
        this.efu=efu;
    }

    public ArrayList<String> getEfu() {
        return efu;
    }

    

    @Override
    public Object getSource() {
        return source;
    }
    
    
    
}

 