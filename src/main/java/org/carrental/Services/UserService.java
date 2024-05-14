package org.carrental.Services;
import org.carrental.Repository.UserDao;
import org.carrental.Model.User;

import java.util.List;

public class UserService {
    private final UserDao dao = new UserDao();

    public String[][] getAllForJTable(){
        List<User> userList = dao.getAll();
        return transformToJTable(userList,2);
    }

    private String[][] transformToJTable(List<User> userList, int columnSize){
        String[][] data = new String[userList.size()][columnSize];
        for (int i = 0; i < userList.size(); i++){
            data[i][0] = userList.get(i).getId().toString();
            data[i][1] = userList.get(i).getUsername();
        }
        return data;
    }

//    public Customer save(String name, Long phoneNo, Long nicNo, String address, Long referencePhoneNo,String status) {
//        Customer customer = Customer.builder()
//                .name(name)
//                .phoneNo(phoneNo)
//                .nicNo(nicNo)
//                .address(address)
//                .referencePhoneNo(referencePhoneNo)
//                .status(status)
//                .build();
//        return customer;
//    }
}
