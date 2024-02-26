package models;

public class Participations {
    private  int idP ;
    private int id_event ;

    public Participations(int idP, int id_event) {
        this.idP = idP;
        this.id_event = id_event;
    }

    public Participations(int id_event) {
        this.id_event = id_event;
    }
     public Participations(){

     }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getId_event() {
        return id_event;
    }

    @Override
    public String toString() {
        return "Participations{" +
                "idP=" + idP +
                ", id_event=" + id_event +
                '}';
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }
}
