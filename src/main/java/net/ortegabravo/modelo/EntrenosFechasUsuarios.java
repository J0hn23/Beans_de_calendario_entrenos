/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ortegabravo.modelo;

import java.util.Date;

/**
 *
 * @author john
 */
public class EntrenosFechasUsuarios {
    private int id;
    
    private String fechaEntreno;

  
    
  
    
    public EntrenosFechasUsuarios(int id,  String fechaEntreno) {
        this.id = id;
        
        this.fechaEntreno = fechaEntreno;
    }

    public EntrenosFechasUsuarios() {
      
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaEntreno() {
        return fechaEntreno;
    }

    public void setFechaEntreno(String fechaEntreno) {
        this.fechaEntreno = fechaEntreno;
    }

   

    
}
