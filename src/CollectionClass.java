import java.io.*;
import java.util.*;

enum AppStates {
	MENU,
	CREATE,
	DELETE,
	VIEW,
	VIEW_ALL,
	SAVE,
	EXIT
}

//TODO:ACCOUNT STORAGE LOGIC
class Account {
	static HashMap<String, String> accs = new HashMap<>();
	String accNo;
	String name;
	int balance;


	public Account(String name) {
		accNo = "A" + ((int) (Math.random() * 100));
		this.name = name;
		balance = 0;

		accs.put(accNo, this.accNo + this.name + this.balance); //putting upon creation.
	}


}

public class CollectionClass {
	static Scanner sc = new Scanner(System.in);
	static boolean running = true;
	static AppStates states = AppStates.MENU; //default.


	public static void main(String[] args) throws IOException {
		while (running) {
			switch (states) {
				case MENU:
					displayMenu();
					break;
				case CREATE:
					displayAccCreate();
					break;
				case DELETE:
					displayDelete();
					break;
				case VIEW:
					displayView();
					break;
				case VIEW_ALL:
					displayViewAll();
					break;
				case SAVE:
					displaySave();
					break;
				case EXIT:
					System.exit(0);
					break;
			}

		}


	}

	private static void displayMenu() {
		System.out.println("----Menu----");
		System.out.println("Press the number for navigating in Menu");
		System.out.println("1 : Create Account");
		System.out.println("2 : Delete Account");
		System.out.println("3 : View Account");
		System.out.println("4 : View All Account");
		System.out.println("5 : Save Account");
		System.out.println("6 : Exit");
		System.out.print("Enter your choice: ");
		int choice = sc.nextInt();


		//STATE CHANGER.
		switch (choice) {
			case 1:
				states = AppStates.CREATE;
				break;
			case 2:
				states = AppStates.DELETE;
				break;
			case 3:
				states = AppStates.VIEW;
				break;
			case 4:
				states = AppStates.VIEW_ALL;
				break;
			case 5:
				states = AppStates.SAVE;
				break;
			case 6:
				states = AppStates.EXIT;
				break;
		}
	}

	private static void displayAccCreate() {
		System.out.println("Creating an Account");
		System.out.print("Enter a Name: ");
		String name = sc.next();
		Account acc = new Account(name);
		String accNo = acc.accNo;
		System.out.println("Account is created. Your given accNo is: " + accNo);
		System.out.println("------------------------------");

		states = AppStates.MENU; //reback to menu
	}

	private static void displayDelete() {
		System.out.println("----Delete an Account----");
		System.out.println("Write account no to delete : ");
		String input = sc.next();
		Account.accs.remove(input);
		System.out.println("Successfully Deleted.");
		states = AppStates.MENU;
	}

	private static void displayView() {
		System.out.println("----View an Account----");
		System.out.println("Write your account no: ");
		String input = sc.next();
		System.out.println(Account.accs.get(input));

		states = AppStates.MENU;
	}

	private static void displayViewAll() {
		System.out.println("----ViewAll an Account----");

		for (Map.Entry<String, String> entry : Account.accs.entrySet()) {
			System.out.println("Account No: " + entry.getKey());
			System.out.print(" Info: " + entry.getValue());
		}

		System.out.println("-----------END-----------");
		states = AppStates.MENU;
	}

	private static void displaySave() {
		//TODO:SAVE ALL HASHMAP TO THE FILE. PROPERTIES OR STRINGTOKEN?
	}
}

