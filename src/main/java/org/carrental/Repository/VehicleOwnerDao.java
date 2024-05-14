package org.carrental.Repository;

import org.carrental.Model.Combined;
import org.carrental.Model.VehicleOwner;
import org.carrental.Mappers.VehicleOwnerMapper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.carrental.Repository.SqlQueryConstant.*;

public class VehicleOwnerDao extends BaseDao implements ICrud<VehicleOwner>{

    private final VehicleOwnerMapper vehicleOwnerMapper = new VehicleOwnerMapper();

    @Override
    public Long insert(VehicleOwner object) {
        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_INTO_VEHICLE_OWNER,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, object.getOwnerName());
            ps.setLong(2, object.getPhoneNo());
            ps.setLong(3, object.getNicNo());
            ps.setString(4, object.getAddress());
            ps.setFloat(5, object.getCommission());
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

    @Override
    public List<VehicleOwner> getAll() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_VEHICLE_OWNERS);
            return vehicleOwnerMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Combined> getAllCommissionHolders(java.util.Date startDate, java.util.Date endDate) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(GET_ALL_OWNERS_COMMISSION);
            preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();
            return vehicleOwnerMapper.resultToCombineList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<VehicleOwner> totalAvailableCarsOwners() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_AVAILABLE_CARS_OWNERS);
            return vehicleOwnerMapper.resultToAllAvailableCarOwners(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<VehicleOwner> getHighestCommissionOfOwner() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_HIGHEST_COMMISSION_OFF_OWNER);
            return vehicleOwnerMapper.resultToHighestCommissionHolderOwnerList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public VehicleOwner getById(Long id) {
        try {
            PreparedStatement ps = conn.prepareStatement(GET_VEHICLE_BY_ID);
            ps.setInt(1, id.intValue());
            ResultSet resultSet = ps.executeQuery();
            return vehicleOwnerMapper.resultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkOwnerIsAvailable(String id) {
        try {
            boolean isPresent = false;
            PreparedStatement ps = conn.prepareStatement(CHECK_OWNER_IS_AVAILABLE);
            ps.setString(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                isPresent = true;
            }
            return isPresent;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<VehicleOwner> getByName(String name) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from vehicle_owner where customer_name like '%" + name + "%'");
            ResultSet resultSet = ps.executeQuery();
            return vehicleOwnerMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void update(VehicleOwner obj, Long id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_OWNER_BY_ID);
            preparedStatement.setString(1, obj.getOwnerName());
            preparedStatement.setLong(2,obj.getPhoneNo());
            preparedStatement.setLong(3,obj.getNicNo());
            preparedStatement.setString(4,obj.getAddress());
            preparedStatement.setFloat(5,obj.getCommission());
            preparedStatement.setInt(6, id.intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(String status,Long id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(null);
            preparedStatement.setString(1,status);
            preparedStatement.setInt(2, id.intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteByOnlyId(Long id) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE o, v, b\n" +
                    "FROM vehicle_owner o\n" +
                    "LEFT JOIN vehicle v ON o.id = v.owner_id\n" +
                    "LEFT JOIN booking b ON v.id = b.v_id\n" +
                    "WHERE o.id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=1");
        } catch (SQLException ex) {
            // Handle the exception
        }

    }

    public void setForeignKeyTo1() {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1;");
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

