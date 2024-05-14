package org.carrental.Mappers;

import org.carrental.Model.Combined;
import org.carrental.Model.VehicleOwner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleOwnerMapper implements IMapper<VehicleOwner>{

    private final static String ID = "id";
    private final static String NAME = "customer_name";
    private final static String VEHICLE_NAME = "vehicle_name";
    private final static String PHONE_NO = "phone_no";
    private final static String NIC_NO = "nic_no";
    private final static String ADDRESS = "address";
    private final static String COMMISSION = "commission";

    @Override
    public List<VehicleOwner> resultToList(ResultSet rs) throws SQLException {
        List<VehicleOwner> vehicleOwnerList = new ArrayList<>();
        while (rs.next()){
            VehicleOwner vehicleOwner = VehicleOwner.builder()
                    .id(rs.getLong(ID))
                    .ownerName(rs.getString(NAME))
                    .phoneNo(rs.getLong(PHONE_NO))
                    .nicNo(rs.getLong(NIC_NO))
                    .address(rs.getString(ADDRESS))
                    .commission(rs.getFloat(COMMISSION))
                    .build();
            vehicleOwnerList.add(vehicleOwner);
        }
        return vehicleOwnerList;
    }

    public List<VehicleOwner> resultToAllAvailableCarOwners(ResultSet rs) throws SQLException {
        List<VehicleOwner> vehicleOwnerList = new ArrayList<>();
        while (rs.next()){
            VehicleOwner vehicleOwner = VehicleOwner.builder()
                    .id((long) rs.getInt(ID))
                    .ownerName(rs.getString(NAME))
                    .build();
            vehicleOwnerList.add(vehicleOwner);
        }
        return vehicleOwnerList;
    }

    public List<Combined> resultToCombineList(ResultSet rs) throws SQLException {
        List<Combined> combinedList = new ArrayList<>();
        while (rs.next()){
            Combined combined = Combined.builder()
                    .id(rs.getLong(ID))
                    .ownerName(rs.getString(NAME))
                    .vehicleName(rs.getString(VEHICLE_NAME))
                    .phoneNo(rs.getLong(PHONE_NO))
                    .nicNo(rs.getLong(NIC_NO))
                    .address(rs.getString(ADDRESS))
                    .commission(rs.getFloat(COMMISSION))
                    .build();
            combinedList.add(combined);
        }
        return combinedList;
    }


    public List<VehicleOwner> resultToHighestCommissionHolderOwnerList(ResultSet rs) throws SQLException {
        List<VehicleOwner> vehicleOwnerList = new ArrayList<>();
        while (rs.next()){
            VehicleOwner vehicleOwner = VehicleOwner.builder()
                    .id(rs.getLong(ID))
                    .ownerName(rs.getString(NAME))
                    .commission(rs.getFloat(COMMISSION))
                    .build();
            vehicleOwnerList.add(vehicleOwner);
        }
        return vehicleOwnerList;
    }

    @Override
    public VehicleOwner resultSetToObject(ResultSet rs) throws SQLException {
        if (rs.next()){
            return VehicleOwner.builder()
                    .id((long) rs.getInt(ID))
                    .ownerName(rs.getString(NAME))
                    .phoneNo(rs.getLong(PHONE_NO))
                    .nicNo(rs.getLong(NIC_NO))
                    .address(rs.getString(ADDRESS))
                    .commission(rs.getFloat(COMMISSION))
                    .build();
        }
        return null;
    }
}
