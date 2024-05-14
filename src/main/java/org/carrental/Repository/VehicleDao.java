package org.carrental.Repository;

import org.carrental.Model.Vehicle;
import org.carrental.Mappers.VehicleMapper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.carrental.Repository.SqlQueryConstant.*;

public class VehicleDao extends BaseDao implements ICrud<Vehicle> {

    private final VehicleMapper vehicleMapper = new VehicleMapper();

    @Override
    public Long insert(Vehicle object) {
        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_INTO_VEHICLE);
            ps.setString(1, object.getVehicleName());
            ps.setInt(2, object.getModel());
            ps.setString(3, object.getBrand());
            ps.setString(4, object.getColor());
            ps.setString(5, object.getOwnerId());
            ps.setString(6,object.getBookingStatus());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void activate(String status,Long id) {
        try {
            PreparedStatement ps = conn.prepareStatement(ACTIVE_VEHICLE_STATUS);
            ps.setString(1,status);
            ps.setLong(2,id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicle> getAll() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_VEHICLE);
            return vehicleMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vehicle> getAllAvailableCars() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_AVAILABLE_CARS);
            return vehicleMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Vehicle> getAllTotalCarsBooking(java.util.Date startDate, java.util.Date endDate) {
        try {
            PreparedStatement statement = conn.prepareStatement(GET_ALL_CARS_TOTAL_BOOKING);
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2,new java.sql.Date(endDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            return vehicleMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getTotalAvailableCars() {
        int totalCars = 0;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_TOTAL_AVAILABLE_CARS);
            if (resultSet.next()) {
                totalCars = resultSet.getInt("total_available_cars");
                System.out.println("getAllAvailableCars : " + totalCars);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalCars;
    }

    public List<Vehicle> getMostBookedCars() {

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_MOST_BOOKED_CARS);
            return vehicleMapper.resultToMostBookedList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vehicle> getLowestBookedCars() {

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_LOWEST_BOOKED_CARS);
            return vehicleMapper.resultToLowestBookedCarList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vehicle> getHighestProfitGivenCar() {

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_HIGHEST_PROFIT_GIVEN_CAR);
            return vehicleMapper.resultToHighestProfitGivenCarList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Vehicle getById(Long id) {
        try {
            PreparedStatement ps = conn.prepareStatement(GET_OWNER_BY_ID);
            ps.setInt(1, id.intValue());
            ResultSet resultSet = ps.executeQuery();
            return vehicleMapper.resultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vehicle> getVehicleById(Long id) {
        try {
            List<Vehicle> vehicleList = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement(GET_VEHICLE_BY_ID);
            ps.setInt(1, id.intValue());
            ResultSet resultSet = ps.executeQuery();
            return vehicleMapper.resultToVehicle(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setVehicleStatusUnActive(String status,Long id) {
        try {
            PreparedStatement ps = conn.prepareStatement(CHANGE_VEHICLE_STATUS);
            ps.setString(1,status);
            ps.setLong(2,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getId(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement(GET_VEHICLE_BY_ID);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true if row exists, false otherwise
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public List<Vehicle> getByName(String name) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from vehicle where vehicle_name like '%" + name + "%'");
            ResultSet resultSet = ps.executeQuery();
            return vehicleMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean checkIfVehicleIdIsPresent(Long id) {
        Boolean isPresent = false;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM booking WHERE v_id = ?");
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

    public List<Vehicle> getBookedVehicles() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_BOOKED_VEHICLES);
            return vehicleMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Vehicle obj, Long id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_VEHICLE_BY_ID);
            preparedStatement.setString(1, obj.getVehicleName());
            preparedStatement.setInt(2,obj.getModel());
            preparedStatement.setString(3,obj.getBrand());
            preparedStatement.setString(4,obj.getColor());
            preparedStatement.setString(5,obj.getOwnerId());
            preparedStatement.setInt(6, id.intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(String status,Long id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_VEHICLE_BY_ID);
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
            PreparedStatement ps = conn.prepareStatement("DELETE FROM vehicle WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=1");
        } catch (SQLException ex) {
            // Handle the exception
        }
    }

}