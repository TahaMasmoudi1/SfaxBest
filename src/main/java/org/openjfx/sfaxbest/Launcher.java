package org.openjfx.sfaxbest;

import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;

import java.sql.Connection;

public class Launcher {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.emf().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            User user = new User(
                    "taha",
                    "taha@email.com",
                    "$2a$10$hashedPasswordExample"
            );

            em.persist(user);

            tx.commit();

            System.out.println("✅ User inserted with ID = " + user.getId());

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            JPAUtil.shutdown();
        }




        //Application.launch(HelloApplication.class, args);
    }
}
