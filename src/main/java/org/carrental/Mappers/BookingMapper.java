package org.carrental.Mappers;

import org.carrental.Model.Booking;
import org.carrental.Model.Vehicle;
import org.carrental.Model.VehicleOwner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingMapper implements IMapper<Booking>{
    private final static String ID = "id";
    private final static String CUSTOMER_ID = "c_id";
    private final static String CUSTOMER_NAME = "customer_name";
    private final static String VEHICLE_ID = "v_id";
    private final static String VEHICLE_NAME = "vehicle_name";
    private final static String OWNER_NAME = "owner_name";
    private final static String BOOKING_DATE = "booking_date";
    private final static String PRICE = "price";
    private final static String BOOKING_STATUS = "booking_status";
    private final static String COMPLETE_DATE = "complete_booking";
    private final static String COMMISSION = "commission";
    private final static String TOTAL_AMOUNT = "total_amount";

    @Override
    public List<Booking> resultToList(ResultSet rs) throws SQLException {
        List<Booking> bookingList = new ArrayList<>();
        while (rs.next()){
            Booking booking = Booking.builder()
                    .id((long) rs.getInt(ID))
                    .customerID(rs.getString(CUSTOMER_ID))
                    .vehicleId(rs.getString(VEHICLE_ID))
                    .datePicker(rs.getDate(BOOKING_DATE))
                    .price(rs.getDouble(PRICE))
                    .bookingStatus(rs.getString(BOOKING_STATUS))
                    .completeBooking(rs.getDate(COMPLETE_DATE))
                    .totalAmount(rs.getDouble(TOTAL_AMOUNT))
                    .build();
            bookingList.add(booking);
        }
        return bookingList;
    }

    public List<Booking> resultToMonthlyBookings(ResultSet rs) throws SQLException {
        List<Booking> bookingList = new ArrayList<>();
        while (rs.next()){
            Booking booking = Booking.builder()
                    .id((long) rs.getInt(ID))
                    .customerName(rs.getString(CUSTOMER_NAME))
                    .vehicleName(rs.getString(VEHICLE_NAME))
                    .OwnerName(rs.getString(OWNER_NAME))
                    .datePicker(rs.getDate(BOOKING_DATE))
                    .price(rs.getDouble(PRICE))
                    .bookingStatus(rs.getString(BOOKING_STATUS))
                    .completeBooking(rs.getDate(COMPLETE_DATE))
                    .totalAmount(rs.getDouble(TOTAL_AMOUNT))
                    .commission(rs.getFloat(COMMISSION))
                    .build();
            bookingList.add(booking);
        }
        return bookingList;
    }

    public List<Booking> resultToYearlyBookings(ResultSet rs) throws SQLException {
        List<Booking> bookingList = new ArrayList<>();
        while (rs.next()){
            Booking booking = Booking.builder()
                    .id((long) rs.getInt(ID))
                    .OwnerName(rs.getString(OWNER_NAME))
                    .vehicleName(rs.getString(VEHICLE_NAME))
                    .datePicker(rs.getDate(BOOKING_DATE))
                    .completeBooking(rs.getDate(COMPLETE_DATE))
                    .bookingStatus(rs.getString(BOOKING_STATUS))
                    .price(rs.getDouble(PRICE))
                    .totalAmount(rs.getDouble(TOTAL_AMOUNT))
                    .commission(rs.getFloat(COMMISSION))
                    .build();
            bookingList.add(booking);
        }
        return bookingList;
    }

    public List<Booking> resultToYearlyActiveVehicles(ResultSet rs) throws SQLException {
        List<Booking> bookingList = new ArrayList<>();
        while (rs.next()){
            Booking booking = Booking.builder()
                    .id((long) rs.getInt(ID))
                    .vehicleName(rs.getString(VEHICLE_NAME))
                    .datePicker(rs.getDate(BOOKING_DATE))
                    .completeBooking(rs.getDate(COMPLETE_DATE))
                    .price(rs.getDouble(PRICE))
                    .bookingStatus(rs.getString(BOOKING_STATUS))
                    .totalAmount(rs.getDouble(TOTAL_AMOUNT))
                    .commission(rs.getFloat(COMMISSION))
                    .build();
            bookingList.add(booking);
        }
        return bookingList;
    }

    public List<Vehicle> resultToCheck(ResultSet rs) throws SQLException {
        List<Vehicle> vehicleList = new ArrayList<>();
        while (rs.next()){
            Vehicle vehicle = Vehicle.builder()
                    .id(rs.getLong(ID))
                    .build();
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    @Override
    public Booking resultSetToObject(ResultSet rs) throws SQLException {
        if (rs.next()){
            return Booking.builder()
                    .id((long) rs.getInt(ID))
                    .customerID(rs.getString(CUSTOMER_ID))
                    .vehicleId(rs.getString(VEHICLE_ID))
                    .datePicker(rs.getDate(BOOKING_DATE))
                    .price(rs.getDouble(PRICE))
                    .bookingStatus(rs.getString(BOOKING_STATUS))
                    .build();
        }
        return null;
    }

    public List<VehicleOwner> resultSetToListOfCommission(ResultSet rs) throws SQLException {
        List<VehicleOwner> vehicleOwnerList = new ArrayList<>();
        while (rs.next()) {
            VehicleOwner vehicleOwner = VehicleOwner.builder()
                    .commission(rs.getFloat(COMMISSION))
                    .build();
            vehicleOwnerList.add(vehicleOwner);
        }
        return vehicleOwnerList;
    }
}
