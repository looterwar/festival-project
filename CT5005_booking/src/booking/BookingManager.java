package booking;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import accounts.Attendee;
import database.DatabaseManager;
import database.IDatabaseFunctions;
import festival.Festival;

public class BookingManager implements IDatabaseFunctions {

	public BookingManager() {
		
		
	}
	
	public boolean create_booking() {
		
		return false;
		
	}
	
	public boolean delete_booking() {

		return false;
		
	}

	@Override
	public void add_entry(Object data) throws SQLException {
		
		int count = count_items();
		if (count <= Festival.MAX_ATTENDEES) {
			
			Booking bok = (Booking)data;
			
			Statement stat = DatabaseManager.getConnection().createStatement();
				
			stat.executeUpdate("INSERT INTO booking (ref, booker) "
					+ "VALUES(ref_book_auto.nextval, '" + bok.getBooker() 
					+ "')");
			
			stat.close();
		}
		
		System.out.println("No available booking space");
	}

	@Override
	public void remove_entry(String ref) throws SQLException {

		Statement stat = DatabaseManager.getConnection().createStatement();
			
		stat.execute("DELETE FROM booking WHERE ref=" + ref);

		stat.close();
		
	}
	
	@Override
	public void update_entry(Object data) throws SQLException {
		
		Booking bok = (Booking)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.executeUpdate("UPDATE booking SET ref='" + bok.getRef() + "', booker='"
				+ bok.getBooker().getRef() + "' WHERE ref=" + bok.getRef());
		
		stat.close();
		
	}
	
	@Override
	public void  search_database(String column, String data) throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet rs = stat.executeQuery("SELECT * FROM booking WHERE " + column + "='" + data + "'");
		
		print_results(rs);
		
		stat.close();
		rs.close();
		
	}
	
	@Override
	public void create_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("CREATE TABLE booking "
				+ "(ref varchar(10), booker varchar(10),"
				+ "PRIMARY KEY (ref))");
		
		stat.execute("CREATE SEQUENCE ref_book_auto START WITH 1"
				+ " INCREMENT BY 1 NOMAXVALUE");
		
		stat.close();
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("DROP TABLE booking");
		
		stat.execute("DROP SEQUENCE ref_book_auto");
		
		stat.close();
	
	}
	
	@Override
	public int count_items() throws  SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet rs = stat.executeQuery("SELECT COUNT(*) FROM booking");
		
		if (rs.next()) {
			
			return rs.getInt(1);
			
		}
		
		return 0;
		
	}
	
	@Override
	public Object get_item(String ref) throws SQLException {
	
		Booking bok = new Booking(null);
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet result = stat.executeQuery("SELECT * FROM booking WHERE ref=" + ref);
		
		if (result.next()) {
			
			bok.setRef(result.getString("ref"));
			
			/*
			if (result.getString("booker") != null) {
				
				Attendee att = new Attendee();
				att.setRef(result.getString("booking"));
				att.setBooking(bok);
				
			}*/
		
			return bok;
			
		}
		
		return null;
	}
	
	@Override
	public void print_results(ResultSet result) throws SQLException {
		
		ResultSetMetaData meta = result.getMetaData();
		
		System.out.println("-- Results --");
		final String FORMAT = "%-20s";
		
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			
			System.out.format(FORMAT, meta.getColumnName(i));
		
		}
		
		while (result.next()) {
			
			System.out.println();
			
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				System.out.format(FORMAT, result.getString(i));
			}
			
		}
		
		System.out.println();
		
	}

	
}
