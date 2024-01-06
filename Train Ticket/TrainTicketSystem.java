package interview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class TrainTicketSystem {

    private static final int TICKET_PRICE =25;
    private static final String TRAIN_FROM = "London";
    private static final String TRAIN_TO = "France";

    private int capacityPerSection = 10;
    private Map<String, List<User>> passengersBySection = new HashMap<>();
    private List<Receipt> receipts = new ArrayList<>();

    // API 1: Purchase ticket
    public Receipt purchaseTicket(String firstName, String lastName, String email) {
        String section = allocateSeat();
        User user = new User(firstName, lastName, email, section);
        Receipt receipt = new Receipt(TRAIN_FROM, TRAIN_TO, user, TICKET_PRICE);
        addPassenger(section, user);
        receipts.add(receipt);
        return receipt;
    }

    // API 2: Show receipt
    public Receipt showReceipt(int receiptId) {
        return receipts.get(receiptId-1);
    }

    // API 3: View passengers by section
    public List<User> viewPassengersBySection(String section) {
        return passengersBySection.getOrDefault(section, new ArrayList<>());
    }

    // API 4: Remove user
    public void removeUser(int receiptId) {
        Receipt receipt = receipts.get(receiptId - 1);
        User user = receipt.getUser();
        passengersBySection.get(user.getSection()).remove(user);
        receipts.remove(receipt);
    }

    // API 5: Modify user's seat
    public void modifySeat(int receiptId, String newSection) {
    	if(receiptId> receipts.size()|| receiptId<=0) {
    		throw new IllegalArgumentException("Invalid receipt Id");
    	}
        Receipt receipt = receipts.get(receiptId - 1);
        User user = receipt.getUser();
        removeUserFromSection(user.getSection(), user);
        user.setSection(newSection);
        addPassenger(newSection, user);
    }

 // Check for available seats in each section
    private String allocateSeat() {
    	
    	    if (passengersBySection.getOrDefault("A", new ArrayList<>()).size() < capacityPerSection) {
    	        return "A";
    	    } else if (passengersBySection.getOrDefault("B", new ArrayList<>()).size() < capacityPerSection) {
    	        return "B";
    	    } else {
    	       
    	        throw new IllegalStateException("Train is full");
    	    }
    	    
    }

    private void addPassenger(String section, User user) {
        passengersBySection.computeIfAbsent(section, k -> new ArrayList<>()).add(user);
    }

    private void removeUserFromSection(String section, User user) {
        passengersBySection.get(section).remove(user);
    }

    public static void main(String[] args) {
        TrainTicketSystem obj1 = new TrainTicketSystem();
 
        // Test the APIs here
        // 1. Purchase a ticket
        Receipt receipt1 = obj1.purchaseTicket("ram", "kumar", "ramlokesh809@gmail.com");
        User user1=receipt1.getUser();
        System.out.println("Receipt 1 details: ");
        System.out.println("first name:"+ user1.getFirstName()+" "+"Last name:"+user1.getLastName()+" "+"email:"+user1.getEmail());
        
        System.out.println("Depature: " +receipt1.getFrom() );
        System.out.println("Arrival " + receipt1.getTo());
        System.out.println("Train fare : " +receipt1.getPrice() );

        // 2. Show a receipt
        Receipt shownReceipt = obj1.showReceipt(1); 
        System.out.println("Shown receipt: " + shownReceipt.getFrom()+ "  to "+shownReceipt.getTo());

        // 3. View passengers in a section
        List<User> passengersInSectionA = obj1.viewPassengersBySection("A");
        System.out.println("Passengers in section A: " + passengersInSectionA);

        // 4. Remove a user
        obj1.removeUser(1); 

        // 5. Modify a user's seat
        obj1.modifySeat(1, "C"); 

    }
}

class User {
    private String firstName;
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", section=" + section
				+ "]";
	}
	private String lastName;
    private String email;
    private String section;
	

    public User(String firstName, String lastName, String email, String section) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.section = section;
	}

    public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
}

class Receipt {
    private String from;
    private String to;
	private User user;
	private int price;
	public Receipt(String from, String to, User user, int price) {
			super();
			this.from = from;
			this.to = to;
			this.user = user;
			this.price = price;
		}
    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
   
}