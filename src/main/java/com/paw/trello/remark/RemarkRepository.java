package com.paw.trello.remark;

import org.apache.commons.beanutils.BeanUtils;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class RemarkRepository {

    private static class LazyHolder {

        static final RemarkRepository INSTANCE = new RemarkRepository();

    }
    public static RemarkRepository getInstance() {
        return RemarkRepository.LazyHolder.INSTANCE;
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

    public RemarkRepository() {
        super();
    }

    private EntityManager em;

    private List<RemarkData> listOfCards;


    public List<RemarkData> getItems(Long cardId) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<RemarkData> query = em.createQuery("SELECT data FROM RemarkData data WHERE data.cardId = :cardId", RemarkData.class);
        listOfCards = query.setParameter("cardId", cardId).getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfCards;
    }

    public List<RemarkData> getItem(Long cardId, Long remarkId) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<RemarkData> query = em.createQuery("SELECT data FROM RemarkData data WHERE data.cardId= :cardId AND data.id = :remarkId", RemarkData.class);
        query.setParameter("cardId", cardId);
        query.setParameter("remarkId", remarkId);
        listOfCards = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfCards;
    }

    public void createItem(RemarkData data) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        em.persist(data);
        em.getTransaction().commit();
        em.close();
    }

    public void removeItem(Long id) throws SQLException, NamingException {
        em = getEntityManager();
        RemarkData remarkData = em.find(RemarkData.class, id);
        em.getTransaction().begin();
        em.remove(remarkData);
        em.getTransaction().commit();
        em.close();
    }

    public void updateItem(RemarkData data, Long id) throws SQLException, NamingException, InvocationTargetException, IllegalAccessException {
        em = getEntityManager();
        em.getTransaction().begin();
        RemarkData remarkData = em.find(RemarkData.class, id);
        BeanUtils.copyProperties(remarkData,data);
        em.persist(remarkData);
        em.getTransaction().commit();
        em.close();
    }

    public Boolean existItem(Long cId, Long rId) throws NamingException {
        if(getItem(cId, rId) == null || getItem(cId, rId).isEmpty()){
            return false;
        }
        return true;
    }
}
