package it.unipd.DAOImplementation;

import it.unipd.DAOInterface.DAOInterfaceUser;
import it.unipd.config.HibernateUtil;
import it.unipd.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;


public class DAOImplementationUser implements DAOInterfaceUser {

    @Override
    public User findByUsername(String usrnm) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, usrnm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<User> findAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public User save(User entity) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println(session.isOpen());
            transaction = session.beginTransaction();
            System.out.println(transaction.isActive());
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public void delete(User entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }
}
