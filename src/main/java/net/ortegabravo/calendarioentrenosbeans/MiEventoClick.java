/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ortegabravo.calendarioentrenosbeans;

import java.awt.Component;
import java.awt.event.MouseEvent;



    public class MiEventoClick extends MouseEvent{

    public MiEventoClick(Component source, int id, long when, int modifiers, int x, int y, int xAbs, int yAbs, int clickCount, boolean popupTrigger, int button) {
        super(source, id, when, modifiers, x, y, xAbs, yAbs, clickCount, popupTrigger, button);
    }

    
    
    
}

