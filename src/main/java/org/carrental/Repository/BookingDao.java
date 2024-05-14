package org.carrental.Repository;

import org.carrental.Model.Booking;
import org.carrental.Model.Customer;
import org.carrental.Model.Vehicle;
import org.carrental.Model.VehicleOwner;
import org.carrental.Mappers.BookingMapper;
import org.carrental.Mappers.CustomerMapper;
import org.carrental.Mappers.VehicleMapper;
import java.sql.*;
import java.util.List;

import static org.carrental.Repository.SqlQueryConstant.*;


public class BookingDao extends BaseDao implements ICrud<Booking>{

    BookingMapper bookingMapper = new BookingMapper();
    CustomerMapper customerMapper=new CustomerMapper();
    VehicleMapper vehicleMapper = new VehicleMapper();
    public Long insert(Booking object) {
        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_INTO_BOOKING);
            ps.setString(1, object.getCustomerID());
            ps.setString(2, object.getVehicleId());
            ps.setDate(3, object.getDatePicker());
            ps.setDouble(4, object.getPrice());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Booking> getAll() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_BOOKINGS);
            return bookingMapper.resultToList(resultSet);
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

    public List<Vehicle> getBookedVehicles() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_BOOKED_VEHICLES);
            return vehicleMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<VehicleOwner> totalCommission(java.util.Date startDate, java.util.Date endDate) {
        try {
            PreparedStatement statement = conn.prepareStatement(GET_COMMISSION);
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            return bookingMapper.resultSetToListOfCommission(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<VehicleOwner> getCommission(java.util.Date startDate, java.util.Date endDate,String ownerName) {
        try {
            PreparedStatement statement = conn.prepareStatement(GET_OWNER_COMMISSION);
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            statement.setString(3,ownerName);
            ResultSet resultSet = statement.executeQuery();
            return bookingMapper.resultSetToListOfCommission(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Booking getById(Long id) {
        try {
            PreparedStatement ps = conn.prepareStatement(GET_BOOKING_BY_ID);
            ps.setInt(1,id.intValue());
            ResultSet resultSet = ps.executeQuery();
            return bookingMapper.resultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public Long getId(Long id) {
//        try {
//            Long bId = null;
//            PreparedStatement ps = conn.prepareStatement(GET_BOOKING_ID);
//            ps.setLong(1,id.intValue());
//            ps.executeQuery();
//            return bId;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void updateBookingStatus(Long id,Booking obj) {
        try {
            PreparedStatement ps = conn.prepareStatement(UPDATE_BOOKING_STATUS);
            ps.setString(1,obj.getBookingStatus());
            ps.setLong(2,id.intValue());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void completeBookingStatus(Long id,Booking obj) {
        try {
            PreparedStatement ps = conn.prepareStatement(COMPLETE_BOOKING_STATUS);
            ps.setDate(1,obj.getCompleteBooking());
            ps.setString(2,obj.getBookingStatus());
            ps.setLong(3,id.intValue());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Booking> getByStatus(String status) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from booking where booking_status like '%"+status+"%'");
            ResultSet resultSet = ps.executeQuery();
            return bookingMapper.resultToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Booking obj, Long id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_BOOKING_BY_ID);
            preparedStatement.setDate(1,obj.getDatePicker());
            preparedStatement.setDouble(2,obj.getPrice());
            preparedStatement.setInt(3,id.intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(String status,Long id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_BOOKING_BY_ID);
            preparedStatement.setString(1,status);
            preparedStatement.setInt(2,id.intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByOnlyId(Long id) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE c, v, o, b\n" +
                    "FROM booking b\n" +
                    "LEFT JOIN customer c ON b.c_id = c.id\n" +
                    "LEFT JOIN vehicle v ON b.v_id = v.id\n" +
                    "LEFT JOIN vehicle_owner o ON v.owner_id = o.id\n" +
                    "WHERE b.id = ?\n");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=1");
        } catch (SQLException ex) {
            // Handle the exception
        }

    }
}
