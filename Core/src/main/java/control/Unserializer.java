package main.java.control;

import classes.Customers;
import java.io.*;

/**
 * 
 * @author Thomas Blumstein
 *
 * Will Unserialize data for the simulator (Customers)
 */
public class Unserializer {

	/**
	 * The input stream that will read from files
	 */
	private ObjectInputStream iS;
	/**
	 * The output stream that will write to files
	 */
	private ObjectOutputStream oS;

	/**
	 * Read from a file to create a Customers and all its data
	 * @return a new Customers with all the customer and order
	 */
	public Customers unserialiseCustomers(String filePath) {

		File directory = new File("GeneratorData");
		if (! directory.exists()){
			directory.mkdir();
		}

		File dataFile = new File(filePath);
		Customers customers = null;
		try {
			iS = new ObjectInputStream(new FileInputStream(dataFile));
			customers = (Customers) iS.readObject();
			iS.close();	
		}catch(EOFException e) {
			System.out.println("Empty file");

		}catch(ClassNotFoundException e) {
			//this could happen if the file has been modified, or if there was some difference between the classes version
			e.printStackTrace();

		}catch(IOException e) {
			e.printStackTrace();
		}

		return customers;

	}

}
