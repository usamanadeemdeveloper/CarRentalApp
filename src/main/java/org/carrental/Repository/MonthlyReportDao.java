package org.carrental.Repository;

import org.carrental.Model.Booking;
import org.carrental.Mappers.BookingMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import static org.carrental.Repository.SqlQueryConstant.GET_ALL_SELECTED_MONTHS_BOOKING;
import static org.carrental.Repository.SqlQueryConstant.GET_ALL_TOTAL_OF_TOTAL_AMOUNT;

public class MonthlyReportDao extends BaseDao{
    BookingMapper bookingMapper = new BookingMapper();

    public List<Booking> getAllMonthlyReports(java.util.Date startDate, java.util.Date endDate) {
        try {
            PreparedStatement statement = conn.prepareStatement(GET_ALL_SELECTED_MONTHS_BOOKING);
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            return bookingMapper.resultToMonthlyBookings(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Double getAllTotalInFinalTotal() {
        Double totalAmount = 0.00;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_TOTAL_OF_TOTAL_AMOUNT);
            if (rs.next()) {
                totalAmount = rs.getDouble("final_total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalAmount;
    }
}
