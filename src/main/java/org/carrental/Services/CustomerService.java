package org.carrental.Services;

import org.carrental.Repository.CustomerDao;
import org.carrental.Model.Customer;

import java.util.List;

public class CustomerService {
    private final CustomerDao dao = new CustomerDao();

    public String[][] searchByName(String name){
        List<Customer> customerList = dao.getByName(name);
        return transformToJTable(customerList,7);
    }
    public String[][] getAllActiveCustomerForJTable(){

        List<Customer> customerList = dao.getActiveCustomers();
        return transformToJTable(customerList,7);
    }
    private String[][] transformToJTable(List<Customer> customerList,int columnSize){
        String[][] data = new String[customerList.size()][columnSize];
        for (int i = 0; i < customerList.size(); i++){
            data[i][0] = customerList.get(i).getId().toString();
            data[i][1] = customerList.get(i).getName();
            data[i][2] = customerList.get(i).getPhoneNo().toString();
            data[i][3] = customerList.get(i).getNicNo().toString();
            data[i][4] = customerList.get(i).getAddress();
            data[i][5] = customerList.get(i).getReferencePhoneNo().toString();
            data[i][6] = customerList.get(i).getStatus();
        }
        return data;
    }

    public Customer save(String name, Long phoneNo, Long nicNo, String address, Long referencePhoneNo,String status) {
        Customer customer = Customer.builder()
                .name(name)
                .phoneNo(phoneNo)
                .nicNo(nicNo)
                .address(address)
                .referencePhoneNo(referencePhoneNo)
                .status(status)
                .build();
        return customer;
    }

}
