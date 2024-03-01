package models.feriel;

public class Testimony {
    int t_id;
    String title;
    String txt;
    int userId;

    String status;


    public Testimony(  int userId,String title, String txt) {

        this.title = title;
        this.txt = txt;
        this.userId = userId;
        this.status= "processing";
    }
    public Testimony( ) {}
    public int getT_id() {
        return t_id;
    }

    @Override
    public String toString() {
        return "Testimony{" +
                "id=" + t_id +
                ", title='" + title + '\'' +
                ", txt='" + txt + '\'' +
                ", userId=" + userId +
                '}';
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}




}
