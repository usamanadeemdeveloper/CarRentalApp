package org.carrental.Repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class BaseDao {
    static final String DB_URL = "jdbc:mysql://localhost:3306/batch12";
    static final String USER = "root";
    static final String PASS = "usamaNadeem@123shanks";
    static Connection conn;

    BaseDao(){
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
