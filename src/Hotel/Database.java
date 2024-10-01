package Hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/hotel";
    private static final String USER = "root";
    private static final String PASSWORD = "Tiger";

    private Connection connection;
    private Scanner sc = new Scanner(System.in);

    private Connection connect() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    
    private ResultSet getRecords(String query) {
    	ResultSet rst = null;
    	try {
    		PreparedStatement pstm = connect().prepareStatement(query);
    		rst = pstm.executeQuery();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return rst;
    }
    
    private int updateRecord(String query) {
    	int op = 0;
    	try {
    		PreparedStatement pstm = connect().prepareStatement(query);
    		op = pstm.executeUpdate();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return op;
    }

    public void viewReservation() {
        String query = "SELECT * FROM RESERVATIONS";
        printTable(getRecords(query));
    }
    
    private void printTable(ResultSet rst) {
    	System.out.println("+-------+----------------------+-----------+------------------+");
    	System.out.println("| ID    | Guest Name           | Room No   | Contact No       |");
    	System.out.println("+-------+----------------------+-----------+------------------+");
    	try {
            while (rst.next()) {
                int id = rst.getInt("ID");
                String name = rst.getString("GUEST_NAME");
                int room = rst.getInt("ROOM_No");
                String mobile = rst.getString("CONTACT");
                System.out.printf("| %-6s| %-21s| %-10s| %-17s|", id,name,room,mobile);
                System.out.println();
            }
            System.out.println("+-------+----------------------+-----------+------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public void getRoomNumber(int n) {
		String query = "SELECT * FROM RESERVATIONS WHERE ID = " + n;
		printTable(getRecords(query));
	}

	public void deleteReservation(int n) {
		String query = "DELETE FROM RESERVATIONS WHERE ID = " + n;
		if(updateRecord(query) > 0) {
			System.out.println("Guest has checkout Successfully.");
		} else {
			System.out.println("Guest has not checkout.");
		}
	}
	
	private boolean validateNameAndMobile(String name, String mobNo) {
		if(name.length() < 2 || mobNo.length() != 10) {
			System.out.println("Invaild name or mobile number. Please enter again correctally");
			return true;
		}
		return false;
	}

	public void reserveRoom() {
		System.out.println("Enter Guest name:- ");
		String name = sc.next().toUpperCase();
		System.out.println("Enter Room Number:- ");
		int roomNo = sc.nextInt();
		System.out.println("Enter Mobile Number:- ");
		String mobNo = sc.next();
		if(validateNameAndMobile(name, mobNo)) {
			reserveRoom();
			return;
		}
		String query = "INSERT INTO RESERVATIONS (GUEST_NAME, ROOM_NO, CONTACT) VALUES ('"+name+"',"+roomNo+","+mobNo+")";
	    if (updateRecord(query) > 0) {
	        System.out.println("Guest added successfully.");
	    } else {
	        System.out.println("Guest not added.");
	    }
	}

	public void updateReservation(int id) {
		System.out.println("Enter new name:- ");
		String name = sc.next().toUpperCase();
		System.out.println("Enter new Room Number:- ");
		int room = sc.nextInt();
		System.out.println("Enter new Mobile Number:- ");
		String mobNo = sc.next();
		if(validateNameAndMobile(name, mobNo)) {
			updateReservation(id);
			return;
		}
		String query = "UPDATE RESERVATIONS SET GUEST_NAME = '"+name+"',ROOM_NO = "+ room + ", CONTACT = " + mobNo + " WHERE ID = " + id;
		if (updateRecord(query) > 0)
			System.out.println("Updated Successfully.");
		else
			System.out.println("Not updated. Try again!");
	}
}