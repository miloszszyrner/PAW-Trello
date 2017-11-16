package com.paw.trello.board;

import org.apache.commons.beanutils.BeanUtils;

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

    /*@Override
    public List<BoardData> getItems() {
        List<BoardData> boards = new ArrayList<>();
        String queryString = "SELECT * FROM BOARD";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryString);
            while(resultSet.next()) {
                BoardData board = new BoardData();
                board.setId(resultSet.getLong(1));
                board.setName(resultSet.getString(2));
                boards.add(board);
            }
        } catch (SQLException e) {
            e.prLongStackTrace();
        }
        return boards;
    }

    @Override
    public BoardData getItem(Long id) {
        BoardData board = new BoardData();
        String queryString = "SELECT * FROM BOARD WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                board.setId(resultSet.getLong(1));
                board.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.prLongStackTrace();
        }
        return board;
    }

    @Override
    public void createItem(BoardData item) {
        String queryString = "INSERT LongO BOARD (NAME) values (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setString(1, item.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.prLongStackTrace();
        }
    }

    @Override
    public void updateItem(BoardData item) {
        String queryString = "UPDATE BOARD SET NAME = ? WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setString(1,item.getName());
            statement.setLong(2,item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.prLongStackTrace();
        }
    }*/
    private EntityManager em;
    private List<BoardData> listOfBoards;
    public List<BoardData> getItems(Long userId) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<BoardData> query = em.createQuery("SELECT data FROM BoardData data where data.userId = :userId", BoardData.class);
        query.setParameter("userId", userId);
        listOfBoards = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return listOfBoards;
    }

    public List<BoardData> getItem(Long userId, Long id) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<BoardData> query = em.createQuery("SELECT data FROM BoardData data where data.userId = :userId and data.id = :id", BoardData.class);
        query.setParameter("userId", userId);
        query.setParameter("id", id);
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
        BeanUtils.copyProperties(boardData,data);
        em.persist(boardData);
        em.getTransaction().commit();
        em.close();
    }

    public boolean existItem(Long userId, Long bId) throws NamingException {
        if(getItem(userId, bId) == null || getItem(userId, bId).isEmpty()){
            return false;
        }
        return true;
    }
}
