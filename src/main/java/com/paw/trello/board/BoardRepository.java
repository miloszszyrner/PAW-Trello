package com.paw.trello.board;

import com.paw.trello.util.BeanUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class BoardRepository {

    private static class LazyHolder {

        static final BoardRepository INSTANCE = new BoardRepository();
    }

    public static BoardRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    public BoardRepository() {
        super();
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

    private EntityManager em;
    private List<BoardData> listOfBoards;

    public List<BoardData> getItems(Long userId) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<BoardData> query = em.createQuery("SELECT data FROM BoardData data where data.userId = :userId and data.status = :status", BoardData.class);
        query.setParameter("userId", userId);
        query.setParameter("status", BoardData.Status.CREATED);
        listOfBoards = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfBoards;
    }

    public List<BoardData> getItem(Long userId, Long id) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<BoardData> query = em.createQuery("SELECT data FROM BoardData data where data.userId = :userId and data.id = :id and data.status = :status", BoardData.class);
        query.setParameter("userId", userId);
        query.setParameter("id", id);
        query.setParameter("status", BoardData.Status.CREATED);
        listOfBoards = query.getResultList();
        em.close();
        return listOfBoards;
    }

    public void createItem(BoardData data) throws SQLException, NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        data.setStatus(BoardData.Status.CREATED);
        em.persist(data);
        em.getTransaction().commit();
        em.close();
    }

    public void removeItem(Long id) throws SQLException, NamingException {
        BoardData boardData = new BoardData();
        em = getEntityManager();
        em.getTransaction().begin();
        boardData = em.find(BoardData.class, id);
        boardData.setStatus(BoardData.Status.DELETED);
        em.persist(boardData);
        em.getTransaction().commit();
        em.close();
    }

    public void updateItem(BoardData data, Long id) throws SQLException, NamingException, InvocationTargetException, IllegalAccessException {
        em = getEntityManager();
        em.getTransaction().begin();
        BoardData boardData = em.find(BoardData.class, id);
        BeanUtil.getInstance().copyProperties(data, boardData);
        em.persist(boardData);
        em.getTransaction().commit();
        em.close();
    }

    public boolean existItem(Long userId, Long bId) throws NamingException {
        if (getItem(userId, bId) == null || getItem(userId, bId).isEmpty()) {
            return false;
        }
        return true;
    }
}
