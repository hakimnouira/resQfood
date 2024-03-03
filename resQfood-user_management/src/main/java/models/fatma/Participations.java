package models.fatma;

public class Participations {
    private  int idP ;
    private int id_event ;
    private String particip_name ;

    public String getParticip_name() {
        return particip_name;
    }

    public void setParticip_name(String particip_name) {
        this.particip_name = particip_name;
    }

    public Participations(int idP, int id_event) {
        this.idP = idP;
        this.id_event = id_event;
    }
    public Participations(int idP, int id_event, String particip_name) {
        this.idP = idP;
        this.id_event = id_event;
        this.particip_name = particip_name;
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
                ", particip_name=" + particip_name +
                '}';
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }
}
