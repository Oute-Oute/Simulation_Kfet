package main.java.control;

import classes.Customers;
import java.io.*;

//TODO: commentaires

/**
 * 
 * @author Lilian
 * @author Corentin
 *
 * Will Serialize data for multiple structures, like the pointeuse or the company class
 * all the file names and dirs are already preselected for now
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
	 * Read from a file to create a company and all its data
	 * @return a new company with all the department, employee and data on them
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
