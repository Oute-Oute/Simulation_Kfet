package main.java.report;

import classes.Customer;
import classes.Customers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import main.java.WaitingList;
import main.java.control.ControllerDevices;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.*;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

import java.awt.Desktop;

import java.io.*;
import java.util.Optional;

public class ExportExcel {

    public static String datFile;
    //TODO: si on a le temps: stats sur nb d'events lancés en retard

    /**
     * Créé le fichier contenant les rapports, ou ajoute une nouvelle feuille s'il existe déjà
     */
    public static void CreateFile(Customers customers) {
        try {
            File directory = new File(System.getProperty("user.home"), "SimulationKfet/Reports/");
            if (!directory.exists()) {
                directory.mkdir();
            }
            XSSFWorkbook workbook;
            File file = new File(System.getProperty("user.home"), "SimulationKfet/Reports/Report_" + datFile + ".xlsx");
            if (!file.exists()) {        //Si le fichier n'existe pas, créer workbook
                workbook = new XSSFWorkbook();
            } else {                    //Si le fichier existe, récuperer le workbook existant
                String path = file.getAbsolutePath();
                FileInputStream in = new FileInputStream(path);
                workbook = new XSSFWorkbook(in);
                in.close();
            }

            XSSFSheet customersheet = workbook.createSheet("Customers_" + workbook.getNumberOfSheets() / 3);
            XSSFSheet devicesheet = workbook.createSheet("Devices_" + workbook.getNumberOfSheets() / 3);
            XSSFSheet waitingSheet = workbook.createSheet("WaitingList_" + workbook.getNumberOfSheets()/3);

            //Ensemble de méthodes pour remplir le rapport ici
            tableCustomer(workbook, customersheet, customers);
            tableWaitingList(workbook, waitingSheet);
            tableDevice(workbook, devicesheet);
            //
            //

            //Écrit le fichier et ferme le stream
            boolean isFree = false;
            while (!isFree) {
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    workbook.write(out);
                    out.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Statistiques créées");
                    alert.setHeaderText("Le fichier de Statistiques à été créé");
                    alert.setContentText("Vous pouvez le trouver à cet emplacement : ");
                    alert.setContentText(System.getProperty("user.home") + "SimulationKfet/Reports/Report_" + datFile + ".xlsx");
                    ButtonType dirButtonType = new ButtonType("Ouvrir le dossier");
                    ButtonType closeButtonType = new ButtonType("Fermer");
                    alert.getButtonTypes().setAll(dirButtonType, closeButtonType);
                    Desktop d = Desktop.getDesktop();
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == dirButtonType) {
                        System.out.println("Approve Button is clicked");
                        d.open(directory);
                    }

                    isFree = true;
                } catch (FileNotFoundException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    alert.setTitle("Fichier Ouvert");
                    alert.setHeaderText("Le Fichier Excel est ouvert et ne peut être modifié");
                    alert.setContentText("Le processus ne peut pas accéder au fichier car ce fichier est utilisé par un autre processus \nFermez le fichier et réessayez");
                    alert.showAndWait();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void tableWaitingList(XSSFWorkbook workbook, XSSFSheet sheet) {
        //Table WaitingList
        XSSFTable table = sheet.createTable(null);
        CTTable ctTable = table.getCTTable();
        ctTable.setRef("A1:C721");

        CTTableStyleInfo styleInfo = ctTable.addNewTableStyleInfo();
        styleInfo.setName("TableStyleMedium3");
        styleInfo.setShowColumnStripes(false);
        styleInfo.setShowRowStripes(true);

        CTTableColumns columns = ctTable.addNewTableColumns();
        columns.setCount(2);

        String[] name = {"Temps", "Taille PréOrder", "Taille PostOrder"};

        for (int i = 1; i <= 3; i++) {
            CTTableColumn column = columns.addNewTableColumn();
            column.setId(i);
            column.setName(name[i - 1]);
        }

        for (int r = 0; r < 721; r++) {
            XSSFRow row = sheet.createRow(r);
            for (int c = 0; c < 3; c++) {
                XSSFCell cell = row.createCell(c);
                if (r == 0) {
                    cell.setCellValue(name[c]);
                    sheet.autoSizeColumn(c);
                } else {
                    switch (c) {
                        case 0 : cell.setCellValue((r-1) * 10);
                        case 1 :
                            if(WaitingList.getInstance().getSizePre().size()>0) {
                                cell.setCellValue(WaitingList.getInstance().getSizePre().get(r));
                            }
                            break;
                        case 2 :
                            if(WaitingList.getInstance().getSizePost().size()>0) {
                                cell.setCellValue(WaitingList.getInstance().getSizePost().get(r));
                            }
                            break;
                    }
                }
            }
        }

        sheet.getRow(0).createCell(4).setCellValue("Temps d'attente moyen:");
        sheet.getRow(1).createCell(4).setCellValue("Caisse");
        sheet.getRow(1).createCell(5).setCellFormula("ROUND(AVERAGE(B2:B721),0)");
        sheet.getRow(2).createCell(4).setCellValue("Commande");
        sheet.getRow(2).createCell(5).setCellFormula("ROUND(AVERAGE(C2:C721),0)");

        CellStyle style = workbook.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    }

    /**
     * Créé une table remplie avec les clients, leur heure d'arrivée, leur heure de départ et leur nb d'articles commandés
     *
     * @param sheet feuille à écrire
     */
    private static void tableCustomer(XSSFWorkbook workbook, XSSFSheet sheet, Customers customers) {
        //Table customers
        int nbLine = customers.getCustomers().size() + 1;

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

        String[] name = {"Numéro du client", "Heure d'arrivée", "Nombre d'articles commandés", "Heure de Départ", "Temps d'attente total"};

        //On prépare la table avec id et nom
        for (int i = 1; i <= 5; i++) {
            CTTableColumn column = columns.addNewTableColumn();
            column.setId(i);
            column.setName(name[i - 1]);
        }

        //On remplit la table
        for (int r = 0; r < nbLine; r++) {
            XSSFRow row = sheet.createRow(r);
            for (int c = 0; c < 5; c++) {
                XSSFCell cell = row.createCell(c);
                if (r == 0) {                            //Si ligne header
                    cell.setCellValue(name[c]);         //Nom des colonnes
                    sheet.autoSizeColumn(c);
                } else {
                    Customer current = customers.getCustomers().get(r - 1);
                    switch (c) {
                        case 0 -> cell.setCellValue(r);
                        case 1 -> cell.setCellValue(current.getArrivalTime());
                        case 2 -> cell.setCellValue(current.getOrder().getNbArticles());
                        case 3 -> cell.setCellValue(current.getDepartureTime());
                        case 4 -> cell.setCellFormula("D" + (r + 1) + " - B" + (r + 1));
                    }
                }
            }
        }

        XSSFRow row = sheet.createRow(nbLine);
        XSSFCell cell = row.createCell(3);
        cell.setCellValue("Temps d'attente moyen:");
        sheet.autoSizeColumn(3);
        XSSFCell cell1 = row.createCell(4);
        cell1.setCellFormula("ROUND(AVERAGE(E2:E" + (nbLine) + "),0)");
    }

    /**
     * Créé une table remplie avec les appareils, leur taux d'occupation et leur nb d'utilisation
     *
     * @param sheet feuille à écrire
     */

    private static void tableDevice(XSSFWorkbook workbook, XSSFSheet sheet) {
        int nbLine = 17;

        //On crée notre table
        XSSFTable table = sheet.createTable(null);
        CTTable cttable = table.getCTTable();
        cttable.setRef("A1:C" + nbLine);           //14 Device + Ligne d'en tête

        //Table avec une ligne sur deux en couleur
        CTTableStyleInfo styleInfo = cttable.addNewTableStyleInfo();
        styleInfo.setName("TableStyleMedium6");
        styleInfo.setShowColumnStripes(false);
        styleInfo.setShowRowStripes(true);

        //On choisit le nb de colonnes et on remplit
        CTTableColumns columns = cttable.addNewTableColumns();
        columns.setCount(2);

        String[] name = {"Device", "Taux d'occupation (%)", "Nombre d'utilisations"};

        //On prépare la table avec id et nom
        for (int i = 1; i <= 3; i++) {
            CTTableColumn column = columns.addNewTableColumn();
            column.setId(i);
            column.setName(name[i - 1]);
        }

        XSSFRow firstRow = sheet.createRow(0);
        for (int c = 0; c < 3; c++) {
            XSSFCell cell = firstRow.createCell(c);
            cell.setCellValue(name[c]);
        }

        ControllerDevices instanceDevice = ControllerDevices.getInstance();
        int nbCafetiere = instanceDevice.getCafetiere().size();
        int nbKettle = instanceDevice.getKettle().size();
        int nbOven = instanceDevice.getOven().size();
        int nbMicrowave = instanceDevice.getMicrowave().size();

        int current;
        //On remplit la table
        for (int r = 0; r < nbLine - 1; r++) {
            XSSFRow row = sheet.createRow(r + 1);
            for (int c = 0; c < 3; c++) {
                XSSFCell cell = row.createCell(c);
                if (r < nbMicrowave) { // rmax = 2                                         //Micro ondes
                    switch (c) {
                        case 0 -> cell.setCellValue("Micro-Onde n°" + (r + 1));
                        case 1 -> cell.setCellFormula("ROUND(" + ((double) instanceDevice.getMicrowave().get(r).getOccupationTime() / 72) + ",1)");
                        case 2 -> cell.setCellValue(instanceDevice.getMicrowave().get(r).getNbUsed());
                    }
                } else if (r < nbMicrowave + nbOven) { //rmax = 3 + 8 -1 = 10                                        //fours
                    current = r - nbMicrowave; // currentmax = 10 - 3 = 7
                    switch (c) {
                        case 0 -> cell.setCellValue("Four n°" + (current + 1));
                        case 1 -> cell.setCellFormula("ROUND(" + ((double) instanceDevice.getOven().get(current).getOccupationTime() / 72) + ",1)");
                        case 2 -> cell.setCellValue(instanceDevice.getOven().get(current).getNbUsed());
                    }
                } else if (r < nbMicrowave + nbOven + nbKettle) { //rmax = 3 + 8 + 2 -1 = 12         //Bouilloire
                    current = r - nbMicrowave - nbOven; //currentmax = 12 - 3 - 8 = 1
                    switch (c) {
                        case 0 -> cell.setCellValue("Bouilloire n°" + (current + 1));
                        case 1 -> cell.setCellFormula("ROUND(" + ((double) instanceDevice.getKettle().get(current).getOccupationTime() / 72) + ",1)");
                        case 2 -> cell.setCellValue(instanceDevice.getKettle().get(current).getNbUsed());
                    }
                } else if (r < nbMicrowave + nbOven + nbKettle + nbCafetiere) { //rmax = 3 + 8 + 2 + 2 -1 = 14      //Cafetiere
                    current = r - nbMicrowave - nbOven - nbKettle; //currentmax = 14 - 3 - 8 - 2 = 1
                    switch (c) {
                        case 0 -> cell.setCellValue("Cafetière n°" + (current + 1));
                        case 1 -> cell.setCellFormula("ROUND(" + ((double) instanceDevice.getCafetiere().get(current).getOccupationTime() / 72) + ",1)");
                        case 2 -> cell.setCellValue(instanceDevice.getCafetiere().get(current).getNbUsed());
                    }
                } else { //rmax = 16                                           //Chocolat
                    current = r - nbMicrowave - nbOven - nbKettle - nbCafetiere;  //currentmax = 15 - 3 - 8 - 2 - 2 = 0
                    switch (c) {
                        case 0 -> cell.setCellValue("Machine à chocolat n°" + (current + 1));
                        case 1 -> cell.setCellFormula("ROUND(" + ((double) instanceDevice.getCocoa().get(current).getOccupationTime() / 72) + ",1)");
                        case 2 -> cell.setCellValue(instanceDevice.getCocoa().get(current).getNbUsed());
                    }
                }
                sheet.autoSizeColumn(c);
            }
        }
    }

    private static void setLineNoFill(XDDFScatterChartData.Series series) {
        XDDFNoFillProperties noFillProperties = new XDDFNoFillProperties();
        XDDFLineProperties lineProperties = new XDDFLineProperties();
        lineProperties.setFillProperties(noFillProperties);
        XDDFShapeProperties shapeProperties = series.getShapeProperties();
        if (shapeProperties == null) shapeProperties = new XDDFShapeProperties();
        shapeProperties.setLineProperties(lineProperties);
        series.setShapeProperties(shapeProperties);
    }
}