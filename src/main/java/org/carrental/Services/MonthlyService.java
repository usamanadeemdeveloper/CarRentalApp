package org.carrental.Services;

import org.carrental.Repository.BookingDao;
import org.carrental.Repository.MonthlyReportDao;
import org.carrental.Model.Booking;

import java.util.Date;
import java.util.List;

public class MonthlyService {
    private final BookingDao dao = new BookingDao();
    private final MonthlyReportDao monthlyReportDao = new MonthlyReportDao();


    public String[][] getAllMonthlyBookingsForJTable(Date startDate, Date endDate){
        List<Booking> bookingList = monthlyReportDao.getAllMonthlyReports(startDate,endDate);
        return transformToJTable(bookingList,10);
    }

    private String[][] transformToJTable(List<Booking> bookingList,int columnSize){
        String[][] data = new String[bookingList.size()][columnSize];
        for (int i = 0; i < bookingList.size(); i++){
            data[i][0] = bookingList.get(i).getId().toString();
            data[i][1] = bookingList.get(i).getCustomerName();
            data[i][2] = bookingList.get(i).getVehicleName();
            data[i][3] = bookingList.get(i).getOwnerName();
            data[i][4] = bookingList.get(i).getDatePicker().toString();
            data[i][5] = bookingList.get(i).getPrice().toString();
            data[i][6] = bookingList.get(i).getBookingStatus();
            data[i][7] = String.valueOf(bookingList.get(i).getCompleteBooking());
            data[i][8] = bookingList.get(i).getTotalAmount().toString();
            data[i][9] = String.valueOf(bookingList.get(i).getCommission());
        }
        return data;
    }
}
