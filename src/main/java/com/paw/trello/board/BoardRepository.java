package com.paw.trello.board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardRepository {

    private static class LazyHolder {
        static final BoardRepository INSTANCE = new BoardRepository();
    }

    public static BoardRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    Connection connection = null;

    public BoardRepository() {
        String url = "jdbc:mysql://mysql8.db4free.net:3307/paw_trello";
        String username = "miloszszyrner";
        String password = "zawszespoko1";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<BoardData> getBoards() {
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
            e.printStackTrace();
        }
        return boards;
    }
    public BoardData getBoard(Long id) {
        BoardData board = new BoardData();
        String queryString = "SELECT * FROM BOARD WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery(queryString);

            while(resultSet.next()) {
                board.setId(resultSet.getLong(1));
                board.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return board;
    }

    public void createBoard(BoardData board) {
        String queryString = "INSERT INTO BOARD (NAME) values (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setString(1, board.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBoard(BoardData board) {
        String queryString = "UPDATE BOARD SET NAME = ? WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setString(1,board.getName());
            statement.setLong(2,board.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean existItem(Long id) {
        String queryString = "SELECT * FROM BOARD WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void removeBoard(Long id) {
        String queryString = "DELETE FROM BOARD WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
