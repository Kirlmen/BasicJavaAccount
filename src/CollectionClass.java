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
class Account implements Serializable {
	String accNo;
	String name;
	int balance;

	public Account(String name) {
		accNo = "A" + ((int) (Math.random() * 1000));
		this.name = name;
		balance = 0;
	}

	@Override
	public String toString() {
		return "Account no: " + accNo + " Name: " + name + " Balance: " + balance;
	}

}

class AccountManager implements Serializable {
	HashMap<String, Account> accounts = new HashMap<>();

	public void addAccount(Account acc) {
		accounts.put(acc.accNo, acc);
	}

	public Account removeAccount(String accNo) {
		return accounts.remove(accNo);
	}

	public Account getAccount(String accNo) {
		return accounts.get(accNo);
	}

	public Collection<Account> getAllAccounts() {
		repopulate();
		return accounts.values();

	}

	@SuppressWarnings("unchecked")
	public void repopulate() {
		File file = new File("MyData.txt");
		if(file.length() == 0)
		{
			System.out.println("File is empty, no record loaded!");
			return;
		}

		try (FileInputStream fis = new FileInputStream("MyData.txt")) {
			ObjectInputStream ois = new ObjectInputStream(fis);

			//repopulating the hashmap
			HashMap<String, Account> load= (HashMap<String, Account>) ois.readObject();
			accounts = load; //overwriting.

		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public void saveAccounts() {
		try (FileOutputStream fos = new FileOutputStream("MyData.txt")) {
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(accounts);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		System.out.println("Successfully Saved.");
	}
}

public class CollectionClass {
	static AppStates states = AppStates.MENU; //default.
	static boolean running = true;
	Scanner sc = new Scanner(System.in);
	AccountManager accountManager = new AccountManager();

	public static void main(String[] args) {
		CollectionClass app = new CollectionClass();
		app.accountManager.repopulate();

		while (running) {
			switch (states) {
				case MENU:
					app.displayMenu();
					break;
				case CREATE:
					app.displayAccCreate();
					break;
				case DELETE:
					app.displayDelete();
					break;
				case VIEW:
					app.displayView();
					break;
				case VIEW_ALL:
					app.displayViewAll();
					break;
				case SAVE:
					app.displaySave();
					break;
				case EXIT:
					System.exit(0);
					break;
			}

		}


	}

	private void displayMenu() {
		System.out.println("-----------MENU-----------");
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
				running = false;
				break;
		}
	}

	private void displayViewAll() {
		System.out.println("----ViewAll an Account----");
		for (Account accs:accountManager.getAllAccounts()) {
			System.out.println(" Infos: " + accs);
		}

		System.out.println("-----------END-----------");
		states = AppStates.MENU;
	}

	private void displaySave() {
		System.out.println("----Saving----");
		accountManager.saveAccounts();
		System.out.println("-----------END-----------");
		states = AppStates.MENU;
	}

	private void displayView() {
		System.out.println("----View an Account----");
		System.out.println("Write your account no: ");
		String input = sc.next();
		Account displayed = accountManager.getAccount(input);
		if (displayed == null) {
			System.out.println("Account not found!");
		} else
			System.out.println(displayed);

		System.out.println("-----------END-----------");
		states = AppStates.MENU;
	}

	private void displayAccCreate() {
		System.out.println("Creating an Account");
		System.out.print("Enter a Name: ");
		String name = sc.nextLine().trim();
		Account acc = new Account(name);
		accountManager.addAccount(acc);
		accountManager.saveAccounts();
		System.out.println("Account is created. Your given accNo is: " + acc.accNo);
		System.out.println("-----------END-----------");

		states = AppStates.MENU; //reback to menu
	}

	private void displayDelete() {
		System.out.println("----Delete an Account----");
		System.out.println("Write account no to delete : ");
		String input = sc.next();
		Account deleted = accountManager.removeAccount(input);
		if (deleted != null) {
			System.out.println(" Successfully Deleted of the Account no: " + deleted.accNo);
			accountManager.saveAccounts();
		} else
			System.out.println("Account not found.");

		System.out.println("-----------END-----------");
		states = AppStates.MENU;
	}
}

