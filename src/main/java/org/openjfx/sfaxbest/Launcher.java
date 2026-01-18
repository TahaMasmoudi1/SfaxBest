package org.openjfx.sfaxbest;

import javafx.application.Application;
import utils.ConxDB;

import java.sql.Connection;

public class Launcher {
    public static void main(String[] args) {
        try {
            Connection c = ConxDB.getInstance();

            if (c != null && !c.isClosed()) {
                System.out.println("✅ DATABASE CONNECTED SUCCESSFULLY");
                System.out.println("Connected to: " + c.getMetaData().getURL());
            } else {
                System.out.println("❌ CONNECTION IS NULL OR CLOSED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        //Application.launch(HelloApplication.class, args);
    }
}
