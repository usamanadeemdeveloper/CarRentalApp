package org.carrental.Services;

import org.carrental.Repository.VehicleOwnerDao;
import org.carrental.Model.VehicleOwner;

import java.util.ArrayList;
import java.util.List;

public class VehicleOwnerService {

    private final VehicleOwnerDao dao = new VehicleOwnerDao();

    public String[][] searchByName(String name){
        List<VehicleOwner> vehicleOwnerList = dao.getByName(name);
        return transformToJTable(vehicleOwnerList,6);
    }

    public String[][] getAllCustomerForJTable(){

        List<VehicleOwner> vehicleOwnerList = dao.getAll();
        return transformToJTable(vehicleOwnerList,6);
    }

    public String[] transformToDropdownOwners(List<VehicleOwner> vehicleOwnerList){
        String[] data = new String[vehicleOwnerList.size()];
        for (int i = 0; i < vehicleOwnerList.size(); i++) {
            data[i] = vehicleOwnerList.get(i).getId().toString();
            data[i] += "," + vehicleOwnerList.get(i).getOwnerName();
        }
        return data;
    }
    private String[][] transformToJTable(List<VehicleOwner> vehicleOwnerList,int columnSize){
        String[][] data = new String[vehicleOwnerList.size()][columnSize];
        for (int i = 0; i < vehicleOwnerList.size();i++){
            data[i][0] = vehicleOwnerList.get(i).getId().toString();
            data[i][1] = vehicleOwnerList.get(i).getOwnerName();
            data[i][2] = vehicleOwnerList.get(i).getPhoneNo().toString();
            data[i][3] = vehicleOwnerList.get(i).getNicNo().toString();
            data[i][4] = vehicleOwnerList.get(i).getAddress();
            data[i][5] = vehicleOwnerList.get(i).getCommission().toString();
        }
        return data;
    }

    public List<String> getAvailableOwnerNames() {
        List<VehicleOwner> vehicleOwnerList = dao.totalAvailableCarsOwners();
        List<String> ownerNames = new ArrayList<>();

        for (VehicleOwner owner : vehicleOwnerList) {
            String ownerName = owner.getId().toString() + " : " + owner.getOwnerName();
            ownerNames.add(ownerName);
        }

        return ownerNames;
    }

    public List<String> getHighestCommission() {
        List<VehicleOwner> vehicleOwnerList = dao.getHighestCommissionOfOwner();
        List<String> ownerNames = new ArrayList<>();

        for (VehicleOwner owner : vehicleOwnerList) {
            String ownerName = owner.getId().toString() + " : " + owner.getOwnerName() + " : " + owner.getCommission();
            ownerNames.add(ownerName);
        }

        return ownerNames;
    }

    public VehicleOwner save(String name, Long phoneNo, Long nicNo, String address, Float commission) {
        VehicleOwner vehicleOwner = VehicleOwner.builder()
                .ownerName(name)
                .phoneNo(phoneNo)
                .nicNo(nicNo)
                .address(address)
                .commission(commission)
                .build();
        return vehicleOwner;
    }
}
