package it.unipd.DAOImplementation;

import it.unipd.DAOInterface.DAOInterfaceUser;
import it.unipd.config.HibernateUtil;
import it.unipd.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;


public class DAOImplementationUser implements DAOInterfaceUser {

    @Override
    public User findByUsername(String usrnm) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.get(User.class, usrnm);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("select user from User user where user.username = " + usrnm, User.class).getSingleResult();
    }

    @Override
    public Collection<User> findAllUsers() {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery("FROM User", User.class).list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("select user from User user", User.class).getResultList();
    }


    @Override
    public User save(User entity) {
//        Transaction transaction = null;
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            System.out.println(session.isOpen());
//            transaction = session.beginTransaction();
//            System.out.println(transaction.isActive());
//            session.persist(entity);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//               transaction.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return entity;
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(entity);
        transaction.commit();
        return entity;
    }

    @Override
    public void delete(User entity) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()){
//            transaction = session.beginTransaction();
//            session.remove(entity);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null)
//                transaction.rollback();
//            e.printStackTrace();
//        }
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(entity);
        transaction.commit();
    }
}
