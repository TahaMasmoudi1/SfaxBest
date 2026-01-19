package utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("sfaxbestPU");

    public static EntityManagerFactory emf() { return EMF; }

    public static void shutdown() { EMF.close(); }
}