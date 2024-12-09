
package net.ortegabravo.modelo;




public class Exercici {

    private int Id;
    private String nomExercici;
    private String descripcio;
    private String demoFoto;

    public Exercici(int id, String NomExercici, String Descripcio, String DemoFoto) {
        this.Id = id;
        this.nomExercici = NomExercici;
        this.descripcio = Descripcio;
        this.demoFoto = DemoFoto;
    }

    @Override
    public String toString() {
        return  Id + " - " + nomExercici+"  " + descripcio ;
    }

    public Exercici() {
    }
    
    

    public int getId() {
        return Id;
    }

    public String getNomExercici() {
        return nomExercici;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public String getDemoFoto() {
        return demoFoto;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public void setNomExercici(String NomExercici) {
        this.nomExercici = NomExercici;
    }

    public void setDescripcio(String Descripcio) {
        this.descripcio = Descripcio;
    }

    public void setDemoFoto(String DemoFoto) {
        this.demoFoto = DemoFoto;
    }
    
    

}
