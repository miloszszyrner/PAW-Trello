package com.paw.trello.repositories;

import java.sql.*;

public abstract class AbstractRepository<T> implements Repository<T>{

    protected Connection connection = null;

    public AbstractRepository() {
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
    @Override
    public boolean existItem(Long id, String tableName) {
        String queryString = "SELECT * FROM " + tableName + " WHERE ID = ?";
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

    @Override
    public void removeItem(Long id, String tableName) {
        String queryString = "DELETE FROM " + tableName + " WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
