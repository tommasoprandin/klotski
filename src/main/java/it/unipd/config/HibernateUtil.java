package it.unipd.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static EntityManagerFactory entityManagerFactory;

    private HibernateUtil() {
    }

    public static void init() {
        if (entityManagerFactory != null) return;
        try {
//            Configuration configuration = new Configuration().configure("META-INF/hibernate.cfg.xml");
//            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                    .applySettings(configuration.getProperties())
//                    .build();
            entityManagerFactory = Persistence.createEntityManagerFactory("klotski");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}

