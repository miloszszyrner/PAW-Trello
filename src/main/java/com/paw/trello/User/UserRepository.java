package com.paw.trello.User;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

import static java.util.Collections.singletonList;

public class UserRepository {

    private static class LazyHolder {

        static final UserRepository INSTANCE = new UserRepository();

    }

    public static UserRepository getInstance() {
        return UserRepository.LazyHolder.INSTANCE;
    }

    private static EntityManager getEntityManager() throws NamingException {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("Tix");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emf.createEntityManager();
    }

    public UserRepository() {
        super();
    }

    private EntityManager em;

    private List<UserData> listOfUsers;

    public List<UserData> getItems() throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        listOfUsers = em.createQuery("SELECT data FROM UserData data").getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfUsers;
    }

    public List<UserData> getItem(Long id) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        listOfUsers = singletonList(em.find(UserData.class, id));
        em.getTransaction().commit();
        em.close();
        return listOfUsers;
    }

    public void createItem(UserData data) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        em.persist(data);
        em.getTransaction().commit();
        em.close();
    }

    public void removeItem(Long id) throws SQLException, NamingException {
        UserData userData = new UserData();
        em = getEntityManager();
        em.getTransaction().begin();
        userData = em.find(UserData.class, id);
        em.remove(userData);
        em.getTransaction().commit();
        em.close();
    }

    public void updateItem(UserData data, Long id) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        UserData userData = em.find(UserData.class, id);
        userData.setPassword(data.getPassword());
        em.persist(userData);
        em.getTransaction().commit();
        em.close();
    }

    public boolean existItem(Long bId) throws NamingException {
        if (getItem(bId) == null || getItem(bId).isEmpty()) {
            return false;
        }
        return true;
    }

    public List<UserData> getByUsername(String username) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<UserData> query = em.createQuery("SELECT data FROM UserData data WHERE data.username = :username", UserData.class);
        query.setParameter("username", username);
        listOfUsers = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfUsers;
    }
}
