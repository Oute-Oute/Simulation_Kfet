package main;

import classes.Customers;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author Lilian
 * @author Corentin
 *
 * Will Serialize data for multiple structures, like the pointeuse or the company class
 * all the file names and dirs are already preselected for now
 */
public class Serializer {

    /**
     * The input stream that will read from files
     */
    private ObjectInputStream iS;
    /**
     * The output stream that will write to files
     */
    private ObjectOutputStream oS;

    private String path;

    /**
     * Open a file and create it if it doesn't exist
     *
     * @return a File object with the file open
     */
    public File createOpenFile() {

        File nameFile = null;

        //create a file
        try {
            Date date = new Date() ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
            nameFile = new File(System.getProperty("user.home"), "SimulationKfet/Data/"+dateFormat.format(date)+".dat");
            if (nameFile.createNewFile()) {
                System.out.println("File created: " + nameFile.getName());
                path = nameFile.getAbsolutePath();
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
     * Save the Company's data, then access each dpt on the company's dpt list to save its data,
     * then access each employee on the dpt's list to save its data
     * every time it creates another file for each class (company-dpt-employee)
     *
     * @param customers the company to serialize
     */
    public void serializeCustomers(Customers customers) {
        File directory = new File(System.getProperty("user.home"), "SimulationKfet/Data/");
        if (!directory.exists()) {
            directory.mkdir();
        }

        File compFile = createOpenFile();
        try {
            oS = new ObjectOutputStream(new FileOutputStream(compFile));
            oS.writeObject(customers);
            oS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPath(){
        return path;
    }


}