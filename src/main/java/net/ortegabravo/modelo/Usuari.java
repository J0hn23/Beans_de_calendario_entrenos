
package net.ortegabravo.modelo;



public class Usuari {

    private int Id;
    private String nom;
    private String email;
    private String passwordHash;
    private byte[] foto;
    private String fotoFilename;
    private boolean instructor;
    private int assignedInstructor;

    public Usuari(int Id, String nom, String email, String passwordHash, byte[] foto, String fotoFilename, boolean instructor, int assignedInstructor) {
        this.Id = Id;
        this.nom = nom;
        this.email = email;
        this.passwordHash = passwordHash;
        this.foto = foto;
        this.fotoFilename = fotoFilename;
        this.instructor = instructor;
        this.assignedInstructor = assignedInstructor;
    }

    public String getFotoFilename() {
        return fotoFilename;
    }

    public void setFotoFilename(String fotoFilename) {
        this.fotoFilename = fotoFilename;
    }

    

    public Usuari() {
    }

    
    public int getId() {
        return Id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public byte[] getFoto() {
        return foto;
    }
    
    
    
    public boolean getInstructor(){
        return instructor;    
    }
    

    public int getAssignedInstructor() {
        return assignedInstructor;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public void setInstructor(boolean instructor) {
        this.instructor = instructor;
    }

    
    
    public void setAssignedInstructor(int assignedInstructor) {
        this.assignedInstructor = assignedInstructor;
    }

   
 
    
    

    public boolean isInstructor() {
        return true;
        //esto no es asi
      
    }
   
    
   
   
    
}
