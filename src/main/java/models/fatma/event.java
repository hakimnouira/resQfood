package models.fatma;

import java.sql.Date;

public class event {
    private int id , capacity , users_joined ;
    private String name , location , status , description , time, image;
    private Date date ;


    public event(int id, int capacity, String name, String location, String status, String description, String time, Date date,String image,int users_joined) {
        this.id = id;
        this.capacity = capacity;
        this.name = name;
        this.location = location;
        this.status = status;
        this.description = description;
        this.time = time;
        this.date = date;
        this.image = image;
        this.users_joined = users_joined ;
    }
    public event(int id, int capacity, String name, String location, String status, String description, String time, Date date,String image) {
        this.id = id;
        this.capacity = capacity;
        this.name = name;
        this.location = location;
        this.status = status;
        this.description = description;
        this.time = time;
        this.date = date;
        this.image = image;
        this.users_joined = users_joined ;
    }

    public event(int capacity, String name, String location, String status, String description, Date date, String time,String image , int users_joined) {
        this.capacity = capacity;
        this.name = name;
        this.location = location;
        this.status = status;
        this.description = description;
        this.date = date;
        this.time = time;
        this.image = image;
        this.users_joined = users_joined ;
    }
    public event(int capacity, String name, String location, String status, String description, Date date, String time,String image ) {
        this.capacity = capacity;
        this.name = name;
        this.location = location;
        this.status = status;
        this.description = description;
        this.date = date;
        this.time = time;
        this.image = image;

    }

    public event() {

    }

    public event(int id, int capacity, int users_joined, String name, String location, String status, String description, String time, Date date) {
        this.id = id;
        this.capacity = capacity;
        this.users_joined = users_joined;
        this.name = name;
        this.location = location;
        this.status = status;
        this.description = description;
        this.time = time;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsers_joined() {
        return users_joined;
    }

    public void setUsers_joined(int users_joined) {
        this.users_joined = users_joined;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return (java.sql.Date) date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "event{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", image=" + image +
                ", users_joined=" + users_joined +
                '}';
    }
}
