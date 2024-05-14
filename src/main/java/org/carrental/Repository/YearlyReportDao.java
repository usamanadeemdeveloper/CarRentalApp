package org.carrental.Repository;

import org.carrental.Model.Booking;
import org.carrental.Mappers.BookingMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static org.carrental.Repository.SqlQueryConstant.GET_ALL_SELECTED_YEAR_BOOKING;
import static org.carrental.Repository.SqlQueryConstant.GET_ALL_SELECTED_YEAR_VEHICLE;

public class YearlyReportDao extends BaseDao{

    private final BookingMapper bookingMapper = new BookingMapper();

    public List<Booking> getAllYearlyReports(java.util.Date startDate, java.util.Date endDate,String ownerName) {
        try {
            PreparedStatement statement = conn.prepareStatement(GET_ALL_SELECTED_YEAR_BOOKING);
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            statement.setString(3,ownerName);
            ResultSet resultSet = statement.executeQuery();
            return bookingMapper.resultToYearlyBookings(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> getAllYearlyVehicleReports(java.util.Date startDate, java.util.Date endDate,String vehicleName) {
        try {
            PreparedStatement statement = conn.prepareStatement(GET_ALL_SELECTED_YEAR_VEHICLE);
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            statement.setString(3,vehicleName);
            ResultSet resultSet = statement.executeQuery();
            return bookingMapper.resultToYearlyActiveVehicles(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
