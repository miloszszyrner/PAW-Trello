package com.paw.trello.card;

import org.apache.commons.beanutils.BeanUtils;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class CardRepository {

    private static class LazyHolder {

        static final CardRepository INSTANCE = new CardRepository();

    }

    public static CardRepository getInstance() {
        return CardRepository.LazyHolder.INSTANCE;
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

    public CardRepository() {
        super();
    }

    private EntityManager em;

    private List<CardData> listOfCards;


    public List<CardData> getItems(Long laneId) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<CardData> query = em.createQuery("SELECT data FROM CardData data WHERE data.laneId = :laneId and data.status = :status", CardData.class);
        query.setParameter("laneId", laneId);
        query.setParameter("status", CardData.Status.CREATED);
        listOfCards = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfCards;
    }

    public List<CardData> getItem(Long laneId, Long cardId) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<CardData> query = em.createQuery("SELECT data FROM CardData data WHERE data.laneId = :laneId AND data.id = :cardId and data.status = :status", CardData.class);
        query.setParameter("cardId", cardId);
        query.setParameter("laneId", laneId);
        query.setParameter("status", CardData.Status.CREATED);
        listOfCards = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfCards;
    }

    public void createItem(CardData data) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        data.setStatus(CardData.Status.CREATED);
        em.persist(data);
        em.getTransaction().commit();
        em.close();
    }

    public void removeItem(Long id) throws SQLException, NamingException {
        CardData cardData = new CardData();
        em = getEntityManager();
        em.getTransaction().begin();
        cardData = em.find(CardData.class, id);
        cardData.setStatus(CardData.Status.DELETED);
        em.persist(cardData);
        em.getTransaction().commit();
        em.close();
    }

    public void updateItem(CardData data, Long id) throws SQLException, NamingException, InvocationTargetException, IllegalAccessException {
        em = getEntityManager();
        em.getTransaction().begin();
        CardData cardData = em.find(CardData.class, id);
        BeanUtils.copyProperties(cardData, data);
        em.persist(cardData);
        em.getTransaction().commit();
        em.close();
    }

    public Boolean existItem(Long lId, Long cId) throws NamingException {
        if (getItem(lId, cId) == null || getItem(lId, cId).isEmpty()) {
            return false;
        }
        return true;
    }
}
