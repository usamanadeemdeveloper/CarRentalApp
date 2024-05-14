package org.carrental.Repository;

import org.carrental.Model.Customer;
import org.carrental.Mappers.CustomerMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.carrental.Repository.SqlQueryConstant.*;

public class CustomerDao extends BaseDao implements ICrud<Customer>{
    private final CustomerMapper customerMapper = new CustomerMapper();

    public Long insert(Customer object) {
        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_INTO_CUSTOMERS,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, object.getName());
            ps.setLong(2, object.getPhoneNo());
            ps.setLong(3, object.getNicNo());
            ps.setString(4, object.getAddress());
            ps.setLong(5, object.getReferencePhoneNo());
            ps.setString(6,object.getStatus());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new SQLException("Failed to get generated ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean checkIfCustomerIdIsPresent(Long id) {
        Boolean isPresent = false;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM booking WHERE c_id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isPresent = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isPresent;
    }

    public List<Customer> getAll() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_CUSTOMERS);
            return customerMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> getActiveCustomers() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ACTIVE_CUSTOMERS);
            return customerMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Customer getById(Long id) {
        try {
            PreparedStatement ps = conn.prepareStatement(GET_CUSTOMER_BY_ID);
            ps.setInt(1,id.intValue());
            ResultSet resultSet = ps.executeQuery();
            return customerMapper.resultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> getByName(String name) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM customer WHERE customer_name LIKE '%"+name+"%' AND status = 'active'");
            ResultSet resultSet = ps.executeQuery();
            return customerMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Customer obj, Long id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_CUSTOMER_BY_ID);
            preparedStatement.setString(1,obj.getName());
            preparedStatement.setString(2, String.valueOf(obj.getPhoneNo()));
            preparedStatement.setString(3, String.valueOf(obj.getNicNo()));
            preparedStatement.setString(4,obj.getAddress());
            preparedStatement.setString(5, String.valueOf(obj.getReferencePhoneNo()));
            preparedStatement.setInt(6,id.intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(String status,Long id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_CUSTOMER_BY_ID);
            preparedStatement.setString(1,status);
            preparedStatement.setInt(2,id.intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
