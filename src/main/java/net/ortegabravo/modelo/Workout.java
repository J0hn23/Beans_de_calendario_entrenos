package net.ortegabravo.modelo;

import java.util.Date;

public class Workout {

    int Id;
    Date ForDate;
    int IdUsuari;
    String comments;

    
    public Workout(int Id, Date ForDate, int IdUsuari, String comments) {
        this.Id = Id;
        this.ForDate = ForDate;
        this.IdUsuari = IdUsuari;
        this.comments = comments;
    }

    public Workout() {
    }
    
    

    public int getId() {
        return Id;
    }

    public Date getForDate() {
        return ForDate;
    }

    public int getIdUsuari() {
        return IdUsuari;
    }

    public String getComments() {
        return comments;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setForDate(Date ForDate) {
        this.ForDate = ForDate;
    }

    public void setIdUsuari(int IdUsuari) {
        this.IdUsuari = IdUsuari;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    
    
    
    
}
