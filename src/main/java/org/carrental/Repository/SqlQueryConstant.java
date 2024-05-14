package org.carrental.Repository;

public class SqlQueryConstant {
    public final static String GET_ALL_CUSTOMERS = "select * from customer";
    public final static String GET_ALL_USERS = "select * from user";
    public final static String GET_ALL_BOOKINGS = "select * from booking";
    public final static String CHECK_IF_ID_IS_PRESENT = "SELECT * FROM booking WHERE v_id = ?";
    public final static String GET_ALL_SELECTED_MONTHS_BOOKING = "SELECT b.id, c.customer_name, v.vehicle_name, o.customer_name As owner_name, b.booking_date, b.complete_booking, b.price, b.booking_status, b.price * DATEDIFF(b.complete_booking, b.booking_date) AS total_amount, o.commission\n" +
            "            FROM booking b\n" +
            "            INNER JOIN customer c ON b.c_id = c.id\n" +
            "            INNER JOIN vehicle v ON b.v_id = v.id\n" +
            "            INNER JOIN vehicle_owner o ON v.owner_id = o.id\n" +
            "            WHERE b.booking_date BETWEEN ? AND ?";

    public final static String GET_ALL_SELECTED_YEAR_BOOKING = "SELECT o.id, o.customer_name As owner_name, v.vehicle_name, b.booking_date, b.complete_booking,\n" +
            "b.price, b.booking_status, b.price * DATEDIFF(b.complete_booking, b.booking_date) AS total_amount, o.commission\n" +
            "FROM booking b\n" +
            "INNER JOIN customer c ON b.c_id = c.id\n" +
            "INNER JOIN vehicle v ON b.v_id = v.id\n" +
            "INNER JOIN vehicle_owner o ON v.owner_id = o.id\n" +
            "WHERE YEAR(b.booking_date) BETWEEN ? AND ?\n" +
            "And o.customer_name = ?";
    public final static String GET_ALL_SELECTED_YEAR_VEHICLE = "SELECT v.id, v.vehicle_name, b.booking_date, b.complete_booking,\n" +
            "                 b.price, b.booking_status, b.price * DATEDIFF(b.complete_booking, b.booking_date) AS total_amount, o.commission\n" +
            "            FROM booking b\n" +
            "            INNER JOIN customer c ON b.c_id = c.id\n" +
            "            INNER JOIN vehicle v ON b.v_id = v.id\n" +
            "            INNER JOIN vehicle_owner o ON v.owner_id = o.id\n" +
            "            WHERE YEAR(b.booking_date) BETWEEN ? AND ?\n" +
            "            And v.vehicle_name = ?\n" ;

    public final static String GET_ALL_TOTAL_OF_TOTAL_AMOUNT = "SELECT SUM(subquery.total_amount) AS final_total\n" +
            "FROM (\n" +
            "  SELECT b.id, c.customer_name, v.vehicle_name, o.customer_name AS owner_name, b.booking_date, b.complete_booking, b.price, b.booking_status, b.price * DATEDIFF(b.complete_booking, b.booking_date) AS total_amount\n" +
            "  FROM booking b\n" +
            "  INNER JOIN customer c ON b.c_id = c.id\n" +
            "  INNER JOIN vehicle v ON b.v_id = v.id\n" +
            "  INNER JOIN vehicle_owner o ON v.owner_id = o.id\n" +
            "  WHERE b.booking_date BETWEEN '2023-05-01' AND '2024-05-23'\n" +
            ") AS subquery;";
    public final static String GET_COMMISSION = "select Sum(o.commission*(DATEDIFF(b.complete_booking,b.booking_date)*b.price)/100) as commission from booking b inner join vehicle v on v.id = b.v_id inner join vehicle_owner o on o.id = v.owner_id where (b.booking_date Between ? And ?)";

    public final static String GET_OWNER_COMMISSION = "select Sum(o.commission*(DATEDIFF(b.complete_booking,b.booking_date)*b.price)/100) as commission from booking b inner join vehicle v on v.id = b.v_id inner join vehicle_owner o on o.id = v.owner_id where (b.booking_date Between ? And ?) And o.customer_name = ?";

    public final static String GET_ACTIVE_CUSTOMERS = "SELECT * FROM customer WHERE status = 'active'";
    public final static String GET_BOOKED_VEHICLES = "SELECT * FROM vehicle WHERE booking_status = 'Booked'";
    public final static String GET_ALL_VEHICLE = "select * from vehicle";
    public final static String GET_ALL_AVAILABLE_CARS = "SELECT *\n" +
            "FROM vehicle\n" +
            "WHERE booking_status = 'UnActive' OR booking_status = 'Available' ";
    public final static String GET_ALL_CARS_TOTAL_BOOKING = "SELECT v.* FROM vehicle v INNER JOIN booking b ON b.v_id = v.id WHERE b.complete_booking >= ? AND b.booking_date <= ?";
    public final static String GET_ALL_AVAILABLE_CARS_OWNERS = "SELECT o.id, o.customer_name\n" +
            "FROM vehicle_owner o\n" +
            "LEFT JOIN vehicle v ON v.owner_id = o.id\n" +
            "WHERE v.booking_status = 'UnActive' or v.booking_status = 'Available' ";
    public final static String GET_TOTAL_AVAILABLE_CARS = "SELECT COUNT(*) as total_available_cars FROM vehicle WHERE booking_status = 'UnActive' or booking_status = 'Available' ";
    public final static String GET_MOST_BOOKED_CARS = "SELECT v.id, v.vehicle_name, COUNT(*) as total_bookings\n" +
            "FROM vehicle v\n" +
            "INNER JOIN booking b ON b.v_id = v.id\n" +
            "GROUP BY v.id, v.vehicle_name\n" +
            "HAVING COUNT(*) = (SELECT MAX(total_bookings) FROM (SELECT COUNT(*) as total_bookings FROM booking GROUP BY v_id) AS subquery)";
    public final static String GET_LOWEST_BOOKED_CARS = "SELECT v.id, v.vehicle_name, COUNT(*) as total_bookings\n" +
            "FROM vehicle v\n" +
            "INNER JOIN booking b ON b.v_id = v.id\n" +
            "GROUP BY v.id, v.vehicle_name\n" +
            "HAVING COUNT(*) = (SELECT MIN(total_bookings) FROM (SELECT COUNT(*) as total_bookings FROM booking GROUP BY v_id) AS subquery)";
    public final static String GET_HIGHEST_PROFIT_GIVEN_CAR = "SELECT v.id, v.vehicle_name, SUM(b.price) AS profit\n" +
            "FROM vehicle v\n" +
            "INNER JOIN booking b ON v.id = b.v_id\n" +
            "GROUP BY v.id\n" +
            "HAVING SUM(b.price) = (SELECT MAX(profit) FROM (SELECT SUM(price) as profit FROM booking GROUP BY v_id) AS subquery)";
    public final static String GET_HIGHEST_COMMISSION_OFF_OWNER = "SELECT o.id, o.customer_name, (b.total_profit * o.commission) / 100 AS commission\n" +
            "FROM vehicle_owner o\n" +
            "INNER JOIN (\n" +
            "    SELECT v.owner_id, SUM(price) AS total_profit\n" +
            "    FROM vehicle v\n" +
            "    INNER JOIN booking b ON b.v_id = v.id\n" +
            "    GROUP BY v.owner_id\n" +
            ") b ON b.owner_id = o.id\n" +
            "WHERE (b.total_profit * o.commission) / 100 = (\n" +
            "    SELECT MAX((b.total_profit * o.commission) / 100)\n" +
            "    FROM vehicle_owner o\n" +
            "    INNER JOIN (\n" +
            "        SELECT v.owner_id, SUM(price) AS total_profit\n" +
            "        FROM vehicle v\n" +
            "        INNER JOIN booking b ON b.v_id = v.id\n" +
            "        GROUP BY v.owner_id\n" +
            "    ) b ON b.owner_id = o.id\n" +
            ")\n" +
            "GROUP BY o.id, o.customer_name, o.commission";

    public final static String GET_ALL_VEHICLE_OWNERS = "select * from vehicle_owner";
    public final static String GET_ALL_OWNERS_COMMISSION = "SELECT o.id, o.customer_name,v.vehicle_name,o.phone_no, o.nic_no, o.address, o.commission\n" +
            "FROM vehicle_owner o\n" +
            "INNER JOIN vehicle v ON v.owner_id = o.id\n" +
            "INNER JOIN booking b ON b.v_id = v.id\n" +
            "WHERE b.booking_date BETWEEN ? AND ?\n" +
            "GROUP BY o.id\n" +
            "ORDER BY o.customer_name";
    public final static String GET_CUSTOMER_BY_ID = "select * from customer where id = ?";
    public final static String GET_BOOKING_BY_ID = "select * from booking where id = ?";
    public final static String GET_BOOKING_ID = "SELECT * \n" +
            "FROM customer c \n" +
            "INNER JOIN booking b \n" +
            "ON b.c_id = c.id\n" +
            "WHERE c.id = 8;";
    public final static String COMPLETE_BOOKING_STATUS = "update booking set complete_booking = ?, booking_status = ? where id =?";
    public final static String UPDATE_BOOKING_STATUS = "update booking set booking_status = ? where id =?";
    public final static String GET_OWNER_BY_ID = "select * from vehicle where owner_id = ?";
    public final static String GET_VEHICLE_BY_ID = "SELECT v.id ,v.vehicle_name FROM booking b INNER JOIN vehicle v ON b.v_id = v.id WHERE b.id = ?";
    public final static String CHECK_OWNER_IS_AVAILABLE = "SELECT v.*\n" +
            "FROM vehicle v\n" +
            "JOIN vehicle_owner vo ON v.owner_id = vo.id\n" +
            "WHERE vo.id = ?";
    public final static String GET_VEHICLE_ID = "SELECT COUNT(*) FROM booking WHERE car_id = \" + carId + \" AND end_date >= CURDATE()";
    public final static String UPDATE_CUSTOMER_BY_ID = "update customer set customer_name = ?,phone_no = ?,nic_no = ?,address = ?,reference_phone_no = ? where id = ?";
    public final static String UPDATE_BOOKING_BY_ID = "update booking set  booking_date = ?, price = ?  where id = ?";
    public final static String UPDATE_VEHICLE_BY_ID = "update vehicle set vehicle_name = ?, model = ?, brand = ?, color = ?, owner_id = ? where id = ?";
    public final static String UPDATE_OWNER_BY_ID = "update vehicle_owner set customer_name = ?, phone_no = ?, nic_no = ?, address = ?, commission = ? where id = ?";
    public final static String DELETE_CUSTOMER_BY_ID = "update customer set status = ? where id = ?";
    public final static String DELETE_BOOKING_BY_ID = "update booking set booking_status = ? where id = ?";
    public final static String DELETE_VEHICLE_BY_ID = "update vehicle set booking_status = ? where id = ?";
    public final static String INSERT_INTO_CUSTOMERS = "insert into customer(customer_name,phone_no,nic_no,address,reference_phone_no,status) " +
            "values(?,?,?,?,?,?)";
    public final static String INSERT_INTO_BOOKING = "insert into booking(c_id,v_id,booking_date,price) " +
            "values(?,?,?,?)";
    public final static String INSERT_INTO_VEHICLE = "insert into vehicle(vehicle_name,model,brand,color,owner_id,booking_status) " +
            "values(?,?,?,?,?,?)";
    public final static String ACTIVE_VEHICLE_STATUS = "update vehicle set booking_status = ? where id = ?";
    public final static String CHANGE_VEHICLE_STATUS = "update vehicle set booking_status = ? where id = (SELECT v_id FROM booking WHERE id = ?)";
    public final static String INSERT_INTO_VEHICLE_OWNER = "insert into vehicle_owner(customer_name,phone_no,nic_no,address,commission) " +
            "values(?,?,?,?,?)";

    //User
    public final static String GET_USER_BY_USERNAME_AND_PASSWORD = "select * from user where userName = ? And pass = ? ";
}
