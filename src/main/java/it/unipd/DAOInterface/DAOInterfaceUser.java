package it.unipd.DAOInterface;

import it.unipd.models.User;

import java.util.Collection;

public interface DAOInterfaceUser {
    User findByUsername(String usrnm);
    Collection<User> findAllUsers();

    User save(User entity);

    void delete(User entity);

}
