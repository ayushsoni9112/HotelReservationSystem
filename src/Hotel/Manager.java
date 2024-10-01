package Hotel;

import java.util.Scanner;

public class Manager {
	public void manageHotel() {
		Database db = new Database();
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Press 1 for reserve a Room:- ");
			System.out.println("Press 2 for view Reservation:- ");
			System.out.println("Press 3 to get room number:- ");
			System.out.println("Press 4 to update Reservation:- ");
			System.out.println("Press 5 to delete Reservation:- ");
			System.out.println("Press 0 to exit:- ");
			System.out.println("Choose an option:- ");
			int choice = sc.nextInt();
			switch(choice) {
				case 1:
					db.reserveRoom();
					break;
				case 2:
					db.viewReservation();
					break;
				case 3:
					System.out.println("Enter guest id to get details:- ");
					db.getRoomNumber(sc.nextInt());
					break;
				case 4:
					System.out.println("Enter id to update record:- ");
					db.updateReservation(sc.nextInt());
					break;
				case 5:
					System.out.println("Enter id of guest who is going to checkout:- ");
					db.deleteReservation(sc.nextInt());
					break;
				case 0:
					exit();
					sc.close();
					return;
				default:
					System.out.println("Please enter a valid choice");
			}
		}
	}
	private static void exit() {
		int n = 5;
		System.out.print("Exiting System");
		while (n != 0) {
			System.out.print(".");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			n--;
		}
		System.out.println();
		System.out.println("Thank you for using Hotel Management Application");
	}
}
