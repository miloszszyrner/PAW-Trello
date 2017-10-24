package com.paw.trello.roll;

import com.paw.trello.repositories.AbstractRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RollRepository extends AbstractRepository<RollData>{

    private static class LazyHolder {
        static final RollRepository INSTANCE = new RollRepository();
    }

    public static RollRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    public RollRepository() {
        super();
    }

    @Override
    public List<RollData> getItems() {
        List<RollData> rolls = new ArrayList<>();
        String queryString = "SELECT * FROM ROLL";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryString);
            while(resultSet.next()) {
                RollData roll = new RollData();
                roll.setId(resultSet.getLong(1));
                roll.setName(resultSet.getString(2));
                roll.setBoardId(resultSet.getLong(3));
                rolls.add(roll);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rolls;
    }

    @Override
    public RollData getItem(Long id) {
        RollData roll = new RollData();
        String queryString = "SELECT * FROM ROLL WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery(queryString);

            while(resultSet.next()) {
                roll.setId(resultSet.getLong(1));
                roll.setName(resultSet.getString(2));
                roll.setBoardId(resultSet.getLong(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roll;
    }

    @Override
    public void createItem(RollData item) {
        String queryString = "INSERT INTO ROLL (NAME, BOARD_ID) values (?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setString(1, item.getName());
            statement.setLong(2, item.getBoardId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItem(RollData item) {
        String queryString = "UPDATE ROLL SET NAME = ?, BOARD_ID = ? WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setString(1,item.getName());
            statement.setLong(2,item.getBoardId());
            statement.setLong(3,item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
