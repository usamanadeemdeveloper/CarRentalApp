package org.carrental.Repository;

import org.carrental.Model.User;
import org.carrental.Mappers.UserMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.carrental.Repository.SqlQueryConstant.*;

public class UserDao extends BaseDao implements ICrud<User>{

    private final static UserMapper userMapper = new UserMapper();

    public User getUserByUsernameAndPassword(String username,String password){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(GET_USER_BY_USERNAME_AND_PASSWORD);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet rs = preparedStatement.executeQuery();
            return userMapper.resultSetToObject(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long insert(User object) {
        return null;
    }

    @Override
    public List<User> getAll() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS);
            return userMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public void update(User obj, Long id) {

    }

    @Override
    public void deleteById(String status, Long id) {

    }


}
