package main.java.report;

import classes.Customer;
import classes.Customers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import main.java.control.ControllerDevices;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ExportExcel {

    public static String datFile;
    //TODO: si on a le temps: stats sur nb d'events lancés en retard

    /**
     * Créé le fichier contenant les rapports, ou ajoute une nouvelle feuille s'il existe déjà
     */
    public static void CreateFile(Customers customers) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM HH-mm-ss");
            Date date = new Date();
            File directory = new File(System.getProperty("user.home"), "SimulationKfet/Reports/");
            if (!directory.exists()) {
                directory.mkdir();
            }
            XSSFWorkbook workbook;
            File file=new File(System.getProperty("user.home"), "SimulationKfet/Reports/Report_"+datFile+".xlsx");
            if (!file.exists()) {        //Si le fichier n'existe pas, créer workbook
                workbook = new XSSFWorkbook();
            } else {                    //Si le fichier existe, récuperer le workbook existant
                String path = file.getAbsolutePath();
                FileInputStream in = new FileInputStream(path);
                workbook = new XSSFWorkbook(in);
                in.close();
            }



            XSSFSheet customersheet = workbook.createSheet("Customers_"+workbook.getNumberOfSheets()/2);
            XSSFSheet devicesheet = workbook.createSheet("Devices_"+workbook.getNumberOfSheets()/2);
            //Ensemble de méthodes pour remplir le rapport ici
            tableCustomer(workbook, customersheet, customers);
            tableDevice(workbook, devicesheet);
            //
            //

            //Écrit le fichier et ferme le stream
            boolean isFree=false;
            while(!isFree){
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    workbook.write(out);
                    out.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Statistiques créées");
                    alert.setHeaderText("Le fichier de Statistiques à été créé");
                    alert.setContentText("Vous pouvez le treouver à cet emplacement : ");
                    alert.setContentText(System.getProperty("user.home").toString()+"SimulationKfet/Reports/Report_"+datFile+".xlsx");
                    ButtonType dirButtonType = new ButtonType("Ouvrir le dossier");
                    ButtonType closeButtonType = new ButtonType("Fermer");
                    alert.getButtonTypes().setAll(dirButtonType, closeButtonType);
                    Desktop d = Desktop.getDesktop();
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == dirButtonType) {
                        System.out.println("Approve Button is clicked");
                        d.open(directory);
                    }

                    isFree=true;
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

    /**
     * Créé une table remplie avec les clients, leur heure d'arrivée, leur heure de départ et leur nb d'articles commandés
     *
     * @param sheet feuille à écrire
     */
    private static void tableCustomer(XSSFWorkbook workbook, XSSFSheet sheet, Customers customers) {
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
        cell1.setCellFormula("AVERAGE(E2:E" + (nbLine) + ")");

        CellStyle style = workbook.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 6, 5, 15, 25);
        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText("Temps d'attente selon le nombre d'article commandés");
        XDDFValueAxis bottomAxis = chart.createValueAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Nombre d'article commandés"); // https://stackoverflow.com/questions/32010765
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("Temps d'attente en seconde");
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

        XDDFDataSource<Double> xs = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, nbLine-1, 2, 2));
        XDDFNumericalDataSource<Double> ys1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, nbLine-1, 4, 4));

        XDDFScatterChartData data = (XDDFScatterChartData) chart.createData(ChartTypes.SCATTER, bottomAxis, leftAxis);
        XDDFScatterChartData.Series series1 = (XDDFScatterChartData.Series) data.addSeries(xs, ys1);
        series1.setSmooth(false); // https://stackoverflow.com/questions/39636138

        series1.setMarkerStyle(MarkerStyle.CIRCLE);
        series1.setMarkerSize((short)5);
        setLineNoFill(series1);

        //XDDFScatterChartData.Series series2 = (XDDFScatterChartData.Series) data.addSeries(xs, ys2);
        //series2.setTitle("3x", null);

        //series2.setMarkerStyle(MarkerStyle.CIRCLE);
        //series2.setMarkerSize((short)5);
        //setLineNoFill(series2);

        chart.plot(data);
    }

    /**
     * Créé une table remplie avec les appareils, leur taux d'occupation et leur nb d'utilisation
     *
     * @param sheet feuille à écrire
     */

    private static void tableDevice(XSSFWorkbook workbook, XSSFSheet sheet) {
        //On crée notre table
        XSSFTable table = sheet.createTable(null);
        CTTable cttable = table.getCTTable();
        cttable.setRef("A1:C15");           //14 Device + Ligne d'en tête

        //Table avec une ligne sur deux en couleur
        CTTableStyleInfo styleInfo = cttable.addNewTableStyleInfo();
        styleInfo.setName("TableStyleMedium6");
        styleInfo.setShowColumnStripes(false);
        styleInfo.setShowRowStripes(true);

        //On choisit le nb de colonnes et on remplit
        CTTableColumns columns = cttable.addNewTableColumns();
        columns.setCount(3);

        String[] name = {"Device", "Taux d'occupation", "Nombre d'utilisations"};

        //On prépare la table avec id et nom
        for (int i = 1; i < 3; i++) {
            CTTableColumn column = columns.addNewTableColumn();
            column.setId(i);
            column.setName(name[i]);
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
        int nbCocoa = instanceDevice.getCocoa().size();
        int nbDevice = nbCafetiere + nbKettle + nbOven + nbMicrowave + nbCocoa;

        int current;
        //On remplit la table
        for (int r = 1; r < nbDevice; r++) {
            XSSFRow row = sheet.createRow(r);
            for (int c = 0; c < 3; c++) {
                XSSFCell cell = row.createCell(c);
                if (r < nbMicrowave) {                                          //Micro ondes
                    switch (c) {
                        case 0 -> cell.setCellValue("Micro-Onde n°" + r);
                        case 1 -> cell.setCellValue(instanceDevice.getMicrowave().get(r).getOccupationTime()/7200);
                        case 2 -> cell.setCellValue(instanceDevice.getMicrowave().get(r).getNbUsed());
                    }
                } else if (r < nbMicrowave + nbOven) {                           //fours
                    current = r - nbMicrowave;
                    switch (c) {
                        case 0 -> cell.setCellValue("Four n°" + (current));
                        case 1 -> cell.setCellValue(instanceDevice.getOven().get(current).getOccupationTime()/7200);
                        case 2 -> cell.setCellValue(instanceDevice.getOven().get(current).getNbUsed());
                    }
                } else if (r < nbMicrowave + nbOven + nbKettle) {                 //Bouilloire
                    current = r - nbMicrowave - nbOven;
                    switch (c) {
                        case 0 -> cell.setCellValue("Bouilloire n°" + (current));
                        case 1 -> cell.setCellValue(instanceDevice.getKettle().get(current).getOccupationTime()/7200);
                        case 2 -> cell.setCellValue(instanceDevice.getKettle().get(current).getNbUsed());
                    }
                } else if (r < nbMicrowave + nbOven + nbKettle + nbCafetiere) {       //Cafetiere
                    current = r - nbMicrowave - nbOven - nbKettle;
                    switch (c) {
                        case 0 -> cell.setCellValue("Cafetière n°" + (current));
                        case 1 -> cell.setCellValue(instanceDevice.getCafetiere().get(current).getOccupationTime()/7200);
                        case 2 -> cell.setCellValue(instanceDevice.getCafetiere().get(current).getNbUsed());
                    }
                } else {                                                          //Chocolat
                    current = r - nbMicrowave - nbOven - nbKettle - nbCafetiere;
                    switch (c) {
                        case 0 -> cell.setCellValue("Machine à chocolat n°" + (current));
                        case 1 -> cell.setCellValue(instanceDevice.getCocoa().get(current).getOccupationTime()/7200);
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