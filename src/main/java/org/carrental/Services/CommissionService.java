package org.carrental.Services;

import org.carrental.Repository.VehicleOwnerDao;
import org.carrental.Model.Combined;

import java.util.Date;
import java.util.List;

public class CommissionService {
    private final VehicleOwnerDao dao = new VehicleOwnerDao();
//    public String[][] searchByName(String name) {
//        List<Combined> combinedList = dao.getByName(name);
//        return transformToJTable(combinedList, 7);
//    }

    public String[][] getAllOwnersCommissionForJTable(Date startDate,Date endDate) {

        List<Combined> combinedList = dao.getAllCommissionHolders(startDate,endDate);
        return transformToJTable(combinedList, 8);
    }

    private String[][] transformToJTable(List<Combined> vehicleOwnerList, int columnSize) {
        String[][] data = new String[vehicleOwnerList.size()][columnSize];
        for (int i = 0; i < vehicleOwnerList.size(); i++) {
            data[i][0] = vehicleOwnerList.get(i).getId().toString();
            data[i][1] = vehicleOwnerList.get(i).getOwnerName();
            data[i][2] = vehicleOwnerList.get(i).getVehicleName();
            data[i][3] = vehicleOwnerList.get(i).getPhoneNo().toString();
            data[i][4] = vehicleOwnerList.get(i).getNicNo().toString();
            data[i][5] = vehicleOwnerList.get(i).getAddress();
            data[i][6] = vehicleOwnerList.get(i).getCommission().toString();
            data[i][7] = vehicleOwnerList.get(i).getVehicleName();
        }
        return data;
    }

}
