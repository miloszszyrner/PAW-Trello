package com.paw.trello.roll;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class RollRepository {

    private static class LazyHolder {

        static final RollRepository INSTANCE = new RollRepository();

    }
    public static RollRepository getInstance() {
        return LazyHolder.INSTANCE;
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

    public RollRepository() {
        super();
    }

    private EntityManager em;

    private List<RollData> listOfRolls;


    public List<RollData> getItems(Long boardId) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<RollData> query = em.createQuery("SELECT data FROM RollData data WHERE data.boardId = :boardId", RollData.class);
        listOfRolls = query.setParameter("boardId", boardId).getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfRolls;
    }

    public List<RollData> getItem(Long boardId, Long rollId) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<RollData> query = em.createQuery("SELECT data FROM RollData data WHERE data.boardId = :boardId AND data.id = :rollId", RollData.class);
        query.setParameter("rollId", rollId);
        query.setParameter("boardId", boardId);
        listOfRolls = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfRolls;
    }

    public void createItem(RollData data) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        em.persist(data);
        em.getTransaction().commit();
        em.close();
    }

    public void removeItem(Long id) throws SQLException, NamingException {
        RollData rollData = new RollData();
        em = getEntityManager();
        em.getTransaction().begin();
        rollData = em.find(RollData.class, id);
        em.remove(rollData);
        em.getTransaction().commit();
        em.close();
    }

    public void updateItem(RollData data, Long id) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        RollData rollData = em.find(RollData.class, id);
        rollData.setName(data.getName());
        em.persist(rollData);
        em.getTransaction().commit();
        em.close();
    }

    public Boolean existItem(Long bId, Long rId) throws NamingException {
        if(getItem(bId, rId) == null || getItem(bId, rId).isEmpty()){
            return false;
        }
        return true;
    }
}