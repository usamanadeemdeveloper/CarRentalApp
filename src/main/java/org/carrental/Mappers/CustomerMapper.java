package org.carrental.Mappers;

import org.carrental.Model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerMapper implements IMapper<Customer>{
    private final static String ID = "id";
    private final static String NAME = "customer_name";
    private final static String PHONE_NO = "phone_no";
    private final static String NIC_NO = "nic_no";
    private final static String ADDRESS = "address";
    private final static String REFERENCE_PHONE_NO = "reference_phone_no";
    private final static String STATUS = "status";

    @Override
    public List<Customer> resultToList(ResultSet rs) throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        while (rs.next()){
            Customer customer = Customer.builder()
                    .id((long) rs.getInt(ID))
                    .name(rs.getString(NAME))
                    .phoneNo(rs.getLong(PHONE_NO))
                    .nicNo(rs.getLong(NIC_NO))
                    .address(rs.getString(ADDRESS))
                    .referencePhoneNo(rs.getLong(REFERENCE_PHONE_NO))
                    .status(rs.getString(STATUS))
                    .build();
            customerList.add(customer);
        }
        return customerList;
    }

    @Override
    public Customer resultSetToObject(ResultSet rs) throws SQLException {
        if (rs.next()){
            return Customer.builder()
                    .id((long) rs.getInt(ID))
                    .name(rs.getString(NAME))
                    .phoneNo(rs.getLong(PHONE_NO))
                    .nicNo(rs.getLong(NIC_NO))
                    .address(rs.getString(ADDRESS))
                    .referencePhoneNo(rs.getLong(REFERENCE_PHONE_NO))
                    .status(rs.getString(STATUS))
                    .build();
        }
        return null;
    }
}
