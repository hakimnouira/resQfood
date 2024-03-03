package test.feriel;

import models.feriel.User;
import services.feriel.TestimonyService;
import services.feriel.UserService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args){

        UserService ps = new UserService();
        TestimonyService ts= new TestimonyService();
        try {
          ps.create(new User("ines ","kt","blavbla","mail",234,"area","particip"));
         // ps.create(new User("lola ","harryson","pwd","lola.h@gmail.com",23434567,"tunis","donor"));
         //   ts.create(new Testimony(2,"title 1","hi i love u app"));
          //  ts.create(new Testimony(3,"title 2","hi i love u app"));


          //  ps.update(new User("fer ","bg","pwd","mail",234,"area","particip"));
          //  ps.delete(1);
           List<User> tot= ps.read();

            System.out.println(ps.read());
            System.out.println(ts.read());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    }

