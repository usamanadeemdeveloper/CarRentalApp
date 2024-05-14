package org.carrental.Mappers;

import org.carrental.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements IMapper<User>{

    public static final String ID = "id";
    public static final String USERNAME = "userName";
    public static final String PASSWORD = "pass";

    @Override
    public List<User> resultToList(ResultSet rs) throws SQLException {
        List<User> userList = new ArrayList<>();
        while (rs.next()){
            User user = User.builder()
                    .id(rs.getLong(ID))
                    .username(rs.getString(USERNAME))
                    .build();
            userList.add(user);
        }
        return userList;
    }

    @Override
    public User resultSetToObject(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return User.builder()
                    .id(rs.getLong(ID))
                    .username(rs.getString(USERNAME))
                    .password(rs.getString(PASSWORD))
                    .build();
        }
        return null;
    }
}
