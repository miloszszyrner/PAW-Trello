package com.paw.trello.lane;

import org.apache.commons.beanutils.BeanUtils;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class LaneRepository {

    private static class LazyHolder {

        static final LaneRepository INSTANCE = new LaneRepository();

    }
    public static LaneRepository getInstance() {
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

    public LaneRepository() {
        super();
    }

    private EntityManager em;

    private List<LaneData> listOfRolls;


    public List<LaneData> getItems(Long boardId) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<LaneData> query = em.createQuery("SELECT data FROM LaneData data WHERE data.boardId = :boardId and data.status = :status", LaneData.class);
        query.setParameter("boardId", boardId);
        query.setParameter("status", LaneData.Status.CREATED);
        listOfRolls = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfRolls;
    }

    public List<LaneData> getItem(Long boardId, Long rollId) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<LaneData> query = em.createQuery("SELECT data FROM LaneData data WHERE data.boardId = :boardId AND data.id = :rollId and data.status = :status", LaneData.class);
        query.setParameter("rollId", rollId);
        query.setParameter("boardId", boardId);
        query.setParameter("status", LaneData.Status.CREATED);
        listOfRolls = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfRolls;
    }

    public void createItem(LaneData data) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        data.setStatus(LaneData.Status.CREATED);
        em.persist(data);
        em.getTransaction().commit();
        em.close();
    }

    public void removeItem(Long id) throws SQLException, NamingException {
        LaneData laneData = new LaneData();
        em = getEntityManager();
        em.getTransaction().begin();
        laneData = em.find(LaneData.class, id);
        laneData.setStatus(LaneData.Status.DELETED);
        em.persist(laneData);
        em.getTransaction().commit();
        em.close();
    }

    public void updateItem(LaneData data, Long id) throws SQLException, NamingException, InvocationTargetException, IllegalAccessException {
        em = getEntityManager();
        em.getTransaction().begin();
        LaneData laneData = em.find(LaneData.class, id);
        BeanUtils.copyProperties(laneData,data);
        em.persist(laneData);
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
