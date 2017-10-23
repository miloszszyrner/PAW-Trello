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

    public List<Board> getBoards() {
        List<Board> boards = new ArrayList<>();
        String queryString = "SELECT * FROM BOARD";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryString);
            while(resultSet.next()) {
                Board board = new Board();
                board.setId(resultSet.getLong(1));
                board.setName(resultSet.getString(2));
                boards.add(board);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }
    public Board getBoard(Long id) {
        Board board = new Board();
        String queryString = "SELECT * FROM BOARD WHERE id =" + id;
        try {
            Statement statement = connection.createStatement();
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

    public void createBoard(Board board) {
        String queryString = "INSERT INTO BOARD (NAME) values (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setString(1, board.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
