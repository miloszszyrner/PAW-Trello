package com.paw.trello.card;

import com.paw.trello.card.CardData;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.smartcardio.Card;
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


    public List<CardData> getItems(Long rollId) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<CardData> query = em.createQuery("SELECT data FROM CardData data WHERE data.rollId = :rollId", CardData.class);
        listOfCards = query.setParameter("rollId", rollId).getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfCards;
    }

    public List<CardData> getItem(Long rollId, Long cardId) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<CardData> query = em.createQuery("SELECT data FROM CardData data WHERE data.rollId = :rollId AND data.id = :cardId", CardData.class);
        query.setParameter("cardId", cardId);
        query.setParameter("rollId", rollId);
        listOfCards = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfCards;
    }

    public void createItem(CardData data) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        em.persist(data);
        em.getTransaction().commit();
        em.close();
    }

    public void removeItem(Long id) throws SQLException, NamingException {
        CardData cardData = new CardData();
        em = getEntityManager();
        em.getTransaction().begin();
        cardData = em.find(CardData.class, id);
        em.remove(cardData);
        em.getTransaction().commit();
        em.close();
    }

    public void updateItem(CardData data, Long id) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        CardData cardData = em.find(CardData.class, id);
        cardData.setTitle(data.getTitle());
        em.persist(cardData);
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
