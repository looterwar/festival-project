/**
 * @author Joshua Preece
 * @version 0.6
 * Defines default menu functionality
 */
package menus;

import java.util.Scanner;

import festival.ErrorLog;
import prices.PricesManager;
import tents.TentManager;
import accounts.AttendeeManager;
import accounts.ChildManager;
import booking.BookingManager;

public abstract class Menu {

	// Create all the managers that are needed in all the menu classes
	protected static AttendeeManager amg = new AttendeeManager();
	protected static BookingManager bmg = new BookingManager();
	protected static PricesManager pmg = new PricesManager();
	protected static TentManager tmg = new TentManager();
	protected static ChildManager cmg = new ChildManager();
	
	protected static int choice;
	protected static String input;
	protected static boolean exit_menu;

	protected static final int EXIT_MENU = 0;
	
	/**
	 * Main entry point to he menu
	 */
	protected static void display_menu() {
		
		// Because this method needs to be static it cannot be abstract
		System.out.print("MAIN");
		
	}
	
	/**
	 * Gets an option input from the user
	 * @return Integer option
	 */
	protected static int get_option() {
		
		try {
			
			Scanner scan = new Scanner(System.in);
			return scan.nextInt();
			
		} catch (Exception ex) {
			ErrorLog.printError("Please enter a integer number!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
			return 0;
		}
		
	}
	
	/**
	 * Gets input from the user
	 * @return String input
	 */
	protected static String get_input() {
		
		try {
			
			Scanner scan = new Scanner(System.in);
			return scan.next().toLowerCase().trim();
			
		} catch (Exception ex) {
			ErrorLog.printError("Please enter a valid string!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
			return "";
		}
		
	}
	
	/*
	protected static void clear_screen() {
		
		for (int i = 0; i < 20; i++) {
			System.out.println("");
		}
		
	}*/
	
	/**
	 * Sets exit menu to true to allow menus to exit from their do while loop
	 * and return to a previous menu
	 */
	protected static void menu_end() {
		exit_menu = true;
	}
	
	/**
	 * Sets the exit menu to false ensuring that when returning from a menu
	 * the returning menu is not exited immediately
	 */
	protected static void menu_reset() {
		exit_menu = false;
	}
	
}
