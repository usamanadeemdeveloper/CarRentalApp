package org.carrental.Mappers;


import org.carrental.Model.Vehicle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleMapper implements IMapper<Vehicle>{
    private final static String ID = "id";
    private final static String NAME = "vehicle_name";
    private final static String MODEL = "model";
    private final static String BRAND = "brand";
    private final static String COLOR = "color";
    private final static String OWNER_ID = "owner_id";
    private final static String Status = "booking_status";
    private final static String TOTAL_BOOKINGS = "total_bookings";
    private final static String PROFIT = "profit";

    @Override
    public List<Vehicle> resultToList(ResultSet rs) throws SQLException {
        List<Vehicle> vehicleList = new ArrayList<>();
        while (rs.next()){
            Vehicle vehicle = Vehicle.builder()
                    .id((long) rs.getInt(ID))
                    .vehicleName(rs.getString(NAME))
                    .model(rs.getInt(MODEL))
                    .brand(rs.getString(BRAND))
                    .color(rs.getString(COLOR))
                    .ownerId(rs.getString(OWNER_ID))
                    .bookingStatus(rs.getString(Status))
                    .build();
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    public List<Vehicle> resultToVehicle(ResultSet rs) throws SQLException {
        List<Vehicle> vehicleList = new ArrayList<>();
        while (rs.next()){
            Vehicle vehicle = Vehicle.builder()
                    .id((long) rs.getInt(ID))
                    .vehicleName(rs.getString(NAME))
                    .build();
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    public List<Vehicle> resultToMostBookedList(ResultSet rs) throws SQLException {
        List<Vehicle> vehicleList = new ArrayList<>();
        while (rs.next()){
            Vehicle vehicle = Vehicle.builder()
                    .id(rs.getLong(ID))
                    .vehicleName(rs.getString(NAME))
                    .totalBookings(rs.getInt(TOTAL_BOOKINGS))
                    .build();
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    public List<Vehicle> resultToLowestBookedCarList(ResultSet rs) throws SQLException {
        List<Vehicle> vehicleList = new ArrayList<>();
        while (rs.next()){
            Vehicle vehicle = Vehicle.builder()
                    .id(rs.getLong(ID))
                    .vehicleName(rs.getString(NAME))
                    .totalBookings(rs.getInt(TOTAL_BOOKINGS))
                    .build();
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    public List<Vehicle> resultToHighestProfitGivenCarList(ResultSet rs) throws SQLException {
        List<Vehicle> vehicleList = new ArrayList<>();
        while (rs.next()){
            Vehicle vehicle = Vehicle.builder()
                    .id(rs.getLong(ID))
                    .vehicleName(rs.getString(NAME))
                    .profit(rs.getDouble(PROFIT))
                    .build();
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    @Override
    public Vehicle resultSetToObject(ResultSet rs) throws SQLException {
        if (rs.next()){
            return Vehicle.builder()
                    .id((long) rs.getInt(ID))
                    .vehicleName(rs.getString(NAME))
                    .model(rs.getInt(MODEL))
                    .brand(rs.getString(BRAND))
                    .color(rs.getString(COLOR))
                    .ownerId(rs.getString(OWNER_ID))
                    .bookingStatus(rs.getString(Status))
                    .build();
        }
        return null;
    }
}
