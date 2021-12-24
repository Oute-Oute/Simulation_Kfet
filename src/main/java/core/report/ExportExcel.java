package core.report;

import core.Customer;
import core.Customers;
import core.control.ControllerDevices;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportExcel {

    /**
     * Créé le fichier contenant les rapports, ou ajoute une nouvelle feuille s'il existe déjà
     */
    public static void CreateFile() {
        try {
            File file = new File("Simulation_KFet_Reports.xlsx");
            XSSFWorkbook workbook;

            if (!file.exists()) {        //Si le fichier n'existe pas, créer workbook
                workbook = new XSSFWorkbook();
            } else {                    //Si le fichier existe, récuperer le workbook existant
                String path = file.getAbsolutePath();
                FileInputStream in = new FileInputStream(path);
                workbook = new XSSFWorkbook(in);
                in.close();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM HH-mm-ss");
            Date date = new Date();

            XSSFSheet sheet = workbook.createSheet("Results_from_" + dateFormat.format(date));

            //Ensemble de méthodes pour remplir le rapport ici
            tableCustomer(workbook, sheet);
            tableDevice(workbook, sheet);
            //
            //

            //Ecrit le fichier et ferme le stream
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
            System.out.println("Created successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Créé une table remplie avec les clients, leur heure d'arrivée, leur heure de départ et leur nb d'articles commandés
     * @param sheet feuille à écrire
     */
    public static void tableCustomer(XSSFWorkbook workbook, XSSFSheet sheet){
        int nbLine = Customers.getCustomers().size() + 1;

        //On créé notre table
        XSSFTable table = sheet.createTable(null);
        CTTable cttable = table.getCTTable();
        cttable.setRef("A1:E" + nbLine);

        //Table avec une ligne sur deux en couleur
        CTTableStyleInfo styleInfo = cttable.addNewTableStyleInfo();
        styleInfo.setName("TableStyleMedium6");
        styleInfo.setShowColumnStripes(false);
        styleInfo.setShowRowStripes(true);

        //On choisit le nb de colonnes et on remplit
        CTTableColumns columns = cttable.addNewTableColumns();
        columns.setCount(4);

        String [] name = {"Numéro du client", "Heure d'arrivée", "Nombre d'articles commandés", "Heure de Départ","Temps d'attente total"};

        //On prépare la table avec id et nom
        for (int i = 1; i <= 5; i++) {
            CTTableColumn column = columns.addNewTableColumn();
            column.setId(i);
            column.setName(name[i-1]);
        }

        //On remplit la table
        for (int r = 0; r < nbLine; r++) {
            XSSFRow row = sheet.createRow(r);
            for(int c = 0; c < 5; c++) {
                XSSFCell cell = row.createCell(c);
                if(r == 0) {                            //Si ligne header
                    cell.setCellValue(name[c]);         //Nom des colonnes
                }
                Customer current = Customers.getCustomers().get(r);
                switch (c) {
                    case 0 -> cell.setCellValue(r + 1);
                    case 1 -> cell.setCellValue(current.getArrivalTime());
                    case 2 -> cell.setCellValue(current.getOrder().getNbArticles());
                    case 3 -> cell.setCellValue(current.getDepartureTime());
                    case 4 -> cell.setCellFormula("D" + r + " - B" + r);
                }
                sheet.autoSizeColumn(c);
            }
        }

        XSSFRow row = sheet.createRow(nbLine);
        XSSFCell cell = row.createCell(4);
        cell.setCellValue("Temps d'attente moyen:");
        XSSFCell cell1 = row.createCell(5);
        cell1.setCellFormula("MOYENNE(E2:E"+(nbLine-1)+")");

        CellStyle style = workbook.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //TODO: faire graphique https://cdn.discordapp.com/attachments/513074388011188227/923220084523159602/unknown.png
    }

    /**
     * Créé une table remplie avec les appareils, leur taux d'occupation et leur nb d'utilisation
     * @param sheet feuille à écrire
     */
    public static void tableDevice(XSSFWorkbook workbook, XSSFSheet sheet){
        //On créé notre table
        XSSFTable table = sheet.createTable(null);
        CTTable cttable = table.getCTTable();
        cttable.setRef("F1:H15");           //14 Device + Ligne d'en tête

        //Table avec une ligne sur deux en couleur
        CTTableStyleInfo styleInfo = cttable.addNewTableStyleInfo();
        styleInfo.setName("TableStyleMedium6");
        styleInfo.setShowColumnStripes(false);
        styleInfo.setShowRowStripes(true);

        //On choisit le nb de colonnes et on remplit
        CTTableColumns columns = cttable.addNewTableColumns();
        columns.setCount(3);

        String [] name = {"Device", "Taux d'occupation", "Nombre d'utilisations"};

        //On prépare la table avec id et nom
        for (int i = 1; i <= 3; i++) {
            CTTableColumn column = columns.addNewTableColumn();
            column.setId(i);
            column.setName(name[i-1]);
        }

        XSSFRow firstRow = sheet.createRow(0);
        for (int c = 0; c < 3; c++){
            XSSFCell cell = firstRow.createCell(c);
            cell.setCellValue(name[c]);
        }

        ControllerDevices instanceDevice = ControllerDevices.getInstance();
        int nbCafetiere = instanceDevice.getCafetiere().size();
        int nbKettle = instanceDevice.getKettle().size();
        int nbOven = instanceDevice.getOven().size();
        int nbMicrowave = instanceDevice.getMicrowave().size();
        int nbCocoa = instanceDevice.getCocoa().size();
        int nbDevice = nbCafetiere + nbKettle + nbOven + nbMicrowave + nbCocoa;

        int current;
        //On remplit la table
        for (int r = 1; r < nbDevice+1; r++) {
            XSSFRow row = sheet.createRow(r);
            for(int c = 0; c < 4; c++) {
                XSSFCell cell = row.createCell(c);
                if (r< nbMicrowave+1){                                          //Micro ondes
                    switch (c) {
                        case 0 -> cell.setCellValue("Micro-Onde n°" + r);
                        case 1 -> cell.setCellValue(instanceDevice.getMicrowave().get(r).getOccupationTime()); //TODO: on a le temps d'occupation, on veut le taux donc à diviser par durée simulation
                        case 2 -> cell.setCellValue(instanceDevice.getMicrowave().get(r).getNbUsed());
                    }
                } else if (r< nbMicrowave+nbOven+1) {                           //fours
                    current = r-nbMicrowave;
                    switch (c) {
                        case 0 -> cell.setCellValue("Four n°" + (current));
                        case 1 -> cell.setCellValue(instanceDevice.getOven().get(current).getOccupationTime()); //TODO: on a le temps d'occupation, on veut le taux donc à diviser par durée simulation
                        case 2 -> cell.setCellValue(instanceDevice.getOven().get(current).getNbUsed());
                    }
                } else if (r < nbMicrowave+nbOven+nbKettle+1 ){                 //Bouilloire
                    current = r-nbMicrowave-nbOven;
                    switch (c) {
                        case 0 -> cell.setCellValue("Bouilloire n°" + (current));
                        case 1 -> cell.setCellValue(instanceDevice.getKettle().get(current).getOccupationTime()); //TODO: on a le temps d'occupation, on veut le taux donc à diviser par durée simulation
                        case 2 -> cell.setCellValue(instanceDevice.getKettle().get(current).getNbUsed());
                    }
                } else if (r< nbMicrowave+nbOven+nbKettle+nbCafetiere+1){       //Cafetiere
                    current = r-nbMicrowave-nbOven-nbKettle;
                    switch (c) {
                        case 0 -> cell.setCellValue("Cafetière n°" + (current));
                        case 1 -> cell.setCellValue(instanceDevice.getCafetiere().get(current).getOccupationTime()); //TODO: on a le temps d'occupation, on veut le taux donc à diviser par durée simulation
                        case 2 -> cell.setCellValue(instanceDevice.getCafetiere().get(current).getNbUsed());
                    }
                }else{                                                          //Chocolat
                    current = r-nbMicrowave-nbOven-nbKettle-nbCafetiere;
                    switch (c) {
                        case 0 -> cell.setCellValue("Machine à chocolat n°" + (current));
                        case 1 -> cell.setCellValue(instanceDevice.getCocoa().get(current).getOccupationTime()); //TODO: on a le temps d'occupation, on veut le taux donc à diviser par durée simulation
                        case 2 -> cell.setCellValue(instanceDevice.getCocoa().get(current).getNbUsed());
                    }
                }
                sheet.autoSizeColumn(c);
            }
        }
    }
}