package test;


import services.PostService;

import java.sql.SQLException;


public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        PostService ps = new PostService();


        try {
            System.out.println(ps.read());
        } catch (SQLException var3) {
            System.err.println(var3.getMessage());
        }

    }
}

