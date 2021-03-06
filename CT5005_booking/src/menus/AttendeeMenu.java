/**
 * @author Joshua Preece
 * @version 1.2
 * Menus used to manipulate attendees
 */
package menus;

import accounts.Attendee;
import festival.ErrorLog;


public class AttendeeMenu extends Menu {
	
	/**
	 * Displays the delete attendee menu
	 */
	public static void display_delete_attendee() {
		
		do {
			
			System.out.println("\n-- Delete Attendee -- ");
			System.out.println("Attendee Ref : ");
			
			Attendee att = new Attendee();
			
			// Get the attendee ref
			input = get_input();
			assert input.isEmpty() : "Input empty";
			if (input.isEmpty() == false) {
				
				// Get the attendee details from the database
				att = amg.get_attendee(input);
				
				// Check if we were able to retrieve data
				if (att == null) {
					
					// Check if the attendee is a child and is stored in the childrens database
					ErrorLog.printInfo("Could not find attendee. Checking if attendee is a child");
					att = cmg.get_child(input);
					
					// If we're unable to indetify the attendee print an error
					if (att == null) {
						
						ErrorLog.printInfo("Could not find attendee. Please check ref");
						
					} else {
						
						// Remove the child
						cmg.remove_child(input);
						
					}
					
				} else {
					
					// Remove the attendee
					amg.remove_attendee(input);
					
				}
				
				Menu.menu_end();
				
			} else {
				
				ErrorLog.printInfo("Please enter an attendee ref");
				
			}
			
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	/**
	 * Displays the menu to create a new attendee
	 */
	public static void display_create_attendee() {
		
		do {
			
			Attendee att = new Attendee();
			
			System.out.println("-- Create new attendee --\n");
			System.out.println("First Name : ");
			
			// Get the attendee's first name
			att.setFirst_Name(get_input());
			if (att.getFirst_Name().isEmpty() == false) {
				
				// Get the attendee's last name
				System.out.println("Last Name : ");
				att.setLast_Name(get_input());
				if (att.getLast_Name().isEmpty() == false) {
					
					// Get the attendee's age
					System.out.println("Age : ");
					att.setAge(Integer.parseInt(get_input()));
					if (att.getAge() > 0 && att.getAge() < 100) {
						
						// Get the attendee's email address
						System.out.println("Email Address : ");
						att.setEmailAddress(get_input());
						if (att.getEmailAddress().isEmpty() == false && att.getEmailAddress().contains("@")) {
						
								att = amg.create_attendee(att);
							
							if (att != null) {
								
								ErrorLog.printInfo("You Ref Number is : " + att.getRef());
							
								display_add_booking_now(att);
								
							}
								
							Menu.menu_end();
							
						}
						
					} else {
						
						ErrorLog.printInfo("Please enter an age above 0 and lower than 100");
						
					}
					
				} else {
					
					ErrorLog.printInfo("Please enter a last name");
					
				}
				
			} else {
				
				ErrorLog.printInfo("Please enter a first name");
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	/**
	 * Displays the menu to edit attendees
	 */
	public static void display_edit_attendee() {
		
		final int EDIT_NAME = 1;
		final int EDIT_AGE = 2;
		final int EDIT_EMAIL = 3;
		final int EDIT_BOOKING = 4;
		
		Attendee att = new Attendee();
		
		String first_name;
		String last_name;
		
		do {
			
			System.out.println("\n-- Select Attendee --");
			System.out.println("Attendee Ref : ");
				
			String ref = get_input();
			// Check if we got a ref
			if (ref.isEmpty() == false) {
				
				assert ref.isEmpty() == false : "Ref was not inputted ";
				// Get the attendee's details
				att = amg.get_attendee(ref);
				if (att == null) {
					ErrorLog.printInfo("Checking if attendee is child");
					
					att = cmg.get_child(ref);
					
					if (att == null) {
						ErrorLog.printInfo("Could not find attendee. Please check ref");
						return;
					}
				}
			
				System.out.println("\n-- Edit Attendee --");
				System.out.println("Edit Name : " + EDIT_NAME);
				System.out.println("Edit Age : " + EDIT_AGE);
				System.out.println("Edit Email : " + EDIT_EMAIL);
				System.out.println("Edit Booking : " + EDIT_BOOKING);
				System.out.println("Exit Menu : " + Menu.EXIT_MENU);
				
				choice = get_option();
				if (choice >= 0) {
					
					switch(choice) {
					
					case EDIT_NAME :
						
						// Get attendee's first name
						System.out.println("First Name : ");
						first_name = get_input();
						if (first_name.isEmpty() == false) {
							
							// Get attendee's last name
							System.out.println("Last Name : ");
							last_name = get_input();
							if (last_name.isEmpty() == false) {
								
								att.setFirst_Name(first_name);
								att.setLast_Name(last_name);
								Menu.menu_end();
							}
							
						}
						
						break;
						
					case EDIT_AGE :
						
						// Get attendee's age
						System.out.println("Age : ");
						
						choice = get_option();
						if (choice > 0) {
							
							att.setAge(choice);
							Menu.menu_end();
						}
						
						break;
						
					case EDIT_EMAIL :
						
						// Get attendee's email address
						System.out.println("Email : ");
						input = get_input();
						
						if (input.isEmpty() == false && input.contains("@")) {
							
							att.setEmailAddress(input);
							Menu.menu_end();
						}
						
						break;
						
					case EDIT_BOOKING :
						
						// Get attendee's booking ref
						System.out.println("Booking Ref : ");
						input = get_input();
						
						if (input.isEmpty() == false) {
							
							if (bmg.does_booking_exist(input)) {
								
								att.setBooking(input);
								Menu.menu_end();
							}
							
						} else {
							
							ErrorLog.printInfo("Please enter a booking ref");
							
						}
						
						break;
					case Menu.EXIT_MENU :
						
						Menu.menu_end();
						break;
					}
				}
			
			} else {
				
				ErrorLog.printInfo("Please enter an attendee ref");
				
			}
			
			
		} while (exit_menu == false);
		
		Menu.menu_reset();

		if (att.getAge() <= 12) {
		
			cmg.update_child(att);
			
		} else {
			
			amg.update_attendee(att);
		
		}
		
	}
	
	/**
	 * Displays a list of attendees that are assigned to a booking ref
	 */
	public static void display_list_attendees() {
		
		do {
			
			System.out.println("\n-- Booking Attendees --");
			System.out.println("Booking Ref : ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				bmg.print_all_attendees_attached(input);
				
			} else {
				
				ErrorLog.printInfo("Please enter a booking ref");
				
			}
			
			Menu.menu_end();
			
		} while (exit_menu == false);
			
		Menu.menu_reset();
		
	}
	
	/**
	 * Displays the an attendee's details to the screen
	 */
	public static void display_attendee_details() {
		
		do {
			
			System.out.println("\n-- Attendee Details --");
			System.out.println("Attendee Ref : ");
			
			// Get the attendee ref
			input = get_input();
			if (input.isEmpty() == false) {
				
				// Get the attendee details
				Attendee att = amg.get_attendee(input);
				if (att == null) {
					ErrorLog.printInfo("Checking if attendee is child");
					att = cmg.get_child(input);
					
					if (att == null) {
						ErrorLog.printInfo("Could not find attendee. Please check ref");
						return;
					}
				}
					
				System.out.println("Ref : " + att.getRef());
				System.out.println("Name : " + att.getFirst_Name() + " " + att.getLast_Name());
				System.out.println("Age : " + att.getAge());
				System.out.println("Email : " + att.getEmailAddress());
				System.out.println("Booking : " + att.getBooking());
				System.out.println("-- End Attendee Details --\n");
				
				Menu.menu_end();
				
			} else {
				
				ErrorLog.printInfo("Please enter a attendee ref");
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	/**
	 * Ask the user if they want to set a booking to this attendee. This is best used
	 * when a booking has been created before this attendee. New bookings require an attendee/booker
	 * so the user should say no if they do not yet have a booking created
	 * @param att Attendee
	 */
	public static void display_add_booking_now(Attendee att) {
		
		final String YES = "yes";
		final String NO = "no";
		
		do {
		
			System.out.println("Would you like to assign a booking now? (yes or no)");
			ErrorLog.printInfo("If you have already created a booking then say yes if not then\n"
					+ "say no and create one. When you create a booking you will be assigned a booking automatically.");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				String response = input.toLowerCase().trim();
				if (response.equals(YES)) {
					
					System.out.println("Booking Ref : ");
					
					input = get_input();
					if (input.isEmpty() == false) {
						
						att.setBooking(input);
						
						// Check if the attendee is a child or not to update the correct table
						if (att.getAge() <= 12) {
							
							cmg.update_child(att);
							
						} else {
	
							amg.update_attendee(att);
							
						}
						
						Menu.menu_end();
						
					} else {
						
						ErrorLog.printInfo("Please enter a booking ref");
						
					}
					
					
				} else if (response.equals(NO)) {
					
					Menu.menu_end();
					
				} else {
					
					ErrorLog.printInfo("Please enter Yes or No");
					
				}
				
			} else {
				
				ErrorLog.printInfo("Please provide a response!");
				
			}
			
		} while(exit_menu == false);
		
		Menu.menu_reset();
		
	}
}
