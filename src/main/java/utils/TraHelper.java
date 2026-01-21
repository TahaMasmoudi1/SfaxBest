package utils;

import jakarta.persistence.EntityManager;

public final class TraHelper {
    private TraHelper() {}

    public static void write(Work work) {
        EntityManager em = JPAUtil.emf().createEntityManager();
        var tx = em.getTransaction();
        try {
            tx.begin();
            work.run(em);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    public static <T> T read(readWork<T> work) {
        EntityManager em = JPAUtil.emf().createEntityManager();
        try{
            return work.run(em);
    }finally {
            em.close();
        }
        }

        @FunctionalInterface
        public interface Work {
            void run(EntityManager em);
        }
        @FunctionalInterface
        public interface readWork<T> {
             T run(EntityManager em);
        }

}
