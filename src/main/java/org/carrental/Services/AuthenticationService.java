package org.carrental.Services;

import org.carrental.Repository.UserDao;
import org.carrental.Model.User;

public class AuthenticationService {
    UserDao userDao = new UserDao();
    public Boolean checkLogin(String username,String password){
        User user = userDao.getUserByUsernameAndPassword(username, password);
        if (user != null){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
