package core;

import java.io.EOFException;
import java.io.File;  // Import the File class
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;// Import the IOException class to handle errors
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import core.Customer;
import core.Customers;

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
	 * Open a file and create it if it doesn't exist
	 * @param fileName the name of the file to open or create and open
	 * @return a File object with the file open
	 */
	public File createOpenFile(String fileName) {

		File nameFile = null;

		//create a file
		try {
			nameFile = new File(fileName);
			if (nameFile.createNewFile()) {
				System.out.println("File created: " + nameFile.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		return nameFile;
	}

	/**
	 * Read from a file to create a company and all its data
	 * @return a new company with all the department, employee and data on them
	 */
	public Customers unserialiseCompagny() {

		File directory = new File("GeneratorData");
		if (! directory.exists()){
			directory.mkdir();
		}

		File dataFile = createOpenFile("GeneratorData" + File.separator + "CustomersFile.dat");
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
