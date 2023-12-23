package project;

import java.util.ArrayList;
import java.util.Scanner;

class Bike {
	private String bikeId;
	private String brand;
	private String bikeModel;
	private double basePrice;
	private boolean isAvailable;

	public Bike(String bikeId, String brand, String bikeModel, double basePrice) {
		super();
		this.bikeId = bikeId;
		this.brand = brand;
		this.bikeModel = bikeModel;
		this.basePrice = basePrice;
		this.isAvailable = true;
	}

	
	public String getBikeId() {
		return bikeId;
	}
	

	public String getBrand() {
		return brand;
	}
	

	public String getbikeModel() {
		return bikeModel;
	}
	

	public double getBasePrice() {
		return basePrice;
	}
	

	public boolean isAvailable() {
		return isAvailable;
	}
	

	public double calculatePrice(double amount) {
		return basePrice * amount;
	}

	public boolean rent() {
		return isAvailable = false;
	}

	public boolean returnBike() {
		return isAvailable = true;
	}
}

class Customer {
	private String id;
	private String name;

	public Customer(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}

class Rental {
	private Bike bike;
	private Customer customer;
	private int days;

	public Rental(Bike bike, Customer customer, int days) {
		this.bike = bike;
		this.customer = customer;
		this.days = days;
	}

	public Bike getBike() {
		return bike;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getDays() {
		return days;
	}
}

class BikeRentalSystem {
	ArrayList<Bike> bikes;
	ArrayList<Customer> customers;
	ArrayList<Rental> rentals;

	public BikeRentalSystem() {
		   bikes = new ArrayList<>();
		   customers = new ArrayList<>();
		   rentals = new ArrayList<>();
	}

	public void addCar(Bike bike) {
		bikes.add(bike);
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public void rentCar(Bike bike, Customer customer, int days) {
		if (bike.isAvailable()) {
			bike.rent();
			rentals.add(new Rental(bike, customer, days));
		} else {
			System.out.println("Car is not available.");
		}
	}

	public void returnCar(Bike bike) {
		bike.returnBike();
		Rental rentalToRemove = null;
		for (Rental rental : rentals) {
			if (rental.getBike() == bike) {
				rentalToRemove = rental;
			}
		}
		if (rentalToRemove != null) {
			rentals.remove(rentalToRemove);
		} else {
			System.out.println("bike is not rented");
		}
	}

	public void menu() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("\n========Bike Rental System=======\n");
			System.out.println("1].rent a bike\n2].Return a Bike\n0].Exit");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Enter your name: ");
				String customerName = sc.next();

				System.out.println("<><><>Available Bikes<><><>");
				for (Bike bike : bikes) {
					if (bike.isAvailable()) {
						System.out.println(bike.getBikeId() + "\t" + bike.getbikeModel() + "\t" + bike.getBrand());
					}
				}

				System.out.println("Enter Bike ID you want to rent :");
				String bikeId = sc.next();

				System.out.println("Enter Number of days for rental: ");
				int days = sc.nextInt();

				Customer newCustomer = new Customer("CUS" + (customers.size() + 10), customerName);
				customers.add(newCustomer);

				Bike selectedBike = null;
				for (Bike bike : bikes) {
					if (bike.getBikeId().equals(bikeId) && bike.isAvailable()) {
						selectedBike = bike;
						break;
					}
				}

				if (selectedBike != null) {
					double totalPrice = selectedBike.calculatePrice(days);

					System.out.println("\n== Rental Information ==\n");
					System.out.println("Customer ID: " + newCustomer.getId());
					System.out.println("Customer Name: " + newCustomer.getName());
					System.out.println("Bike: " + selectedBike.getBrand() + " " + selectedBike.getbikeModel());
					System.out.println("Rental Days: " + days);
					System.out.printf("Total Price: RS%.2f%n", totalPrice);
					sc.nextLine();
					System.out.println("Confirm Rental: (Y/N)");
					String confirm = sc.next();

					if (confirm.equalsIgnoreCase("Y")) {
						rentCar(selectedBike, newCustomer, days);
						System.out.println("Bike Rent Successfully...");
					} else {
						System.out.println("Bike Renting cancel");
					}
				} else {
					System.out.println("Bike is not available for rent or id mismatch");
				}
				break;

			case 2:
				System.out.println("Enter Bike ID that you want to return: ");
				String bikeID = sc.next();

				Bike bikeToReturn = null;
				for (Bike bike : bikes) {
					if (bike.getBikeId().equals(bikeID)) {
						bikeToReturn = bike;
						break;
					}
				}
				if (bikeToReturn != null) {
					Customer customer = null;
					for (Rental rental : rentals) {
						if (rental.getBike().equals(bikeToReturn)) {
							customer = rental.getCustomer();
							break;
						}
					}
					if (customer != null) {
						returnCar(bikeToReturn);
						System.out.println("Bike returned successfully by " + customer.getName());
					} else {
						System.out.println("Bike was not rented or rental information is missing");
					}
				} else {
					System.out.println("Invalid car iD or bike is not rented");
				}
				break;

			case 0:
				System.out.println("Thank you for visiting Bike Rental System");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid choice. Please enter a valid option.");
				break;
			}
		}
	}
}

public class Main {
	public static void main(String[] args) {
		BikeRentalSystem rentalSystem = new BikeRentalSystem();

		Bike bike1 = new Bike("C001", "honda", "shine", 1000);
		Bike bike2 = new Bike("C002", "Tvs", "rtr160", 1500);
		Bike bike3 = new Bike("C003", "Activa", "scooty", 2000.0);
		rentalSystem.addCar(bike1);
		rentalSystem.addCar(bike2);
		rentalSystem.addCar(bike3);

		rentalSystem.menu();
	}
}