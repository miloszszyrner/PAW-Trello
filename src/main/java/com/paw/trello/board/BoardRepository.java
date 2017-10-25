package com.paw.trello.board;

import com.paw.trello.repositories.AbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardRepository extends AbstractRepository<BoardData>{

    private static class LazyHolder {
        static final BoardRepository INSTANCE = new BoardRepository();
    }

    public static BoardRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    public BoardRepository() {
        super();
    }

    @Override
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return board;
    }

    @Override
    public void createItem(BoardData item) {
        String queryString = "INSERT INTO BOARD (NAME) values (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setString(1, item.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
