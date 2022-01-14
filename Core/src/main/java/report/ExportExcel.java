package main.java.report;

import classes.Customer;
import classes.Customers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import main.java.WaitingList;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
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

            XSSFSheet customerSheet = workbook.createSheet("Customers_" + workbook.getNumberOfSheets() / 4);
            XSSFSheet deviceSheet = workbook.createSheet("Devices_" + workbook.getNumberOfSheets() / 4);
            XSSFSheet waitingSheet = workbook.createSheet("WaitingList_" + workbook.getNumberOfSheets() / 4);
            XSSFSheet kfetierSheet = workbook.createSheet("K'Fetiers_" + workbook.getNumberOfSheets() / 4);

            //Ensemble de méthodes pour remplir le rapport ici
            tableCustomer(workbook, customerSheet, customers);
            tableWaitingList(workbook, waitingSheet);
            tableDevice(workbook, deviceSheet);
            tableKfetier(workbook, kfetierSheet);
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

    /**
     * Crée une table remplie avec la taille des listes d'attente toutes les 10 secondes
     *
     * @param sheet feuille à écrire
     */
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
                        case 0:
                            cell.setCellValue((r - 1) * 10);
                            break;
                        case 1:
                            if (WaitingList.getInstance().getSizePre().size() >= (r - 1) * 10) {
                                cell.setCellValue(WaitingList.getInstance().getSizePre().get((r - 1) * 10));
                            }
                            break;
                        case 2:
                            if (WaitingList.getInstance().getSizePost().size() >= (r - 1) * 10) {
                                cell.setCellValue(WaitingList.getInstance().getSizePost().get((r - 1) * 10));
                            }
                            break;
                    }
                }
            }
        }
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 6, 6, 15, 27);

        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText("Tailles de Pre et PostOrder dans le temps");
        chart.setTitleOverlay(false);

        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);


        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Temps en secondes");
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("Tailles de PreOrder et PostOrder");

        XDDFDataSource<String> time = XDDFDataSourcesFactory.fromStringCellRange(sheet,
                new CellRangeAddress(0, 720, 0, 0));

        XDDFNumericalDataSource<Double> preOrder = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
                new CellRangeAddress(0, 720, 1, 1));

        XDDFNumericalDataSource<Double> postOrder = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
                new CellRangeAddress(0, 720, 2, 2));

        XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);

        XDDFLineChartData.Series series1 = (XDDFLineChartData.Series) data.addSeries(time, preOrder);
        series1.setTitle("TaillePreOrder", null);
        series1.setSmooth(false);
        series1.setMarkerStyle(MarkerStyle.NONE);


        XDDFLineChartData.Series series2 = (XDDFLineChartData.Series) data.addSeries(time, postOrder);
        series2.setTitle("Taille PostOrder", null);
        series2.setSmooth(true);
        series2.setMarkerSize((short) 6);
        series2.setMarkerStyle(MarkerStyle.NONE);
        chart.plot(data);

        sheet.getRow(0).createCell(4).setCellValue("Taille moyenne:");
        sheet.getRow(1).createCell(4).setCellValue("Caisse");
        sheet.getRow(1).createCell(5).setCellFormula("ROUND(AVERAGE(B2:B721),0)");
        sheet.getRow(2).createCell(4).setCellValue("Commande");
        sheet.autoSizeColumn(4);
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
                        case 0 -> cell.setCellValue(r - 1);
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
        cell1.setCellFormula("ROUND(AVERAGE(E2:E" + nbLine + "),0)");

        XSSFRow row1 = sheet.createRow(nbLine + 1);
        XSSFCell cellServed = row1.createCell(0);
        cellServed.setCellValue("Clients non servis:");
        XSSFCell cellnumber = row1.createCell(1);
        cellnumber.setCellFormula("COUNTIF(D2:D" + nbLine + ",0)");

        XSSFRow row2 = sheet.createRow(nbLine + 2);
        XSSFCell cell2 = row2.createCell(3);
        cell2.setCellValue("Temps d'attente min:");
        XSSFCell cell3 = row2.createCell(4);
        cell3.setCellFormula("MIN(E2:E" + (nbLine) + ")");

        XSSFRow row3 = sheet.createRow(nbLine + 3);
        XSSFCell cell4 = row3.createCell(3);
        cell4.setCellValue("Temps d'attente max:");
        XSSFCell cell5 = row3.createCell(4);
        cell5.setCellFormula("MAX(E2:E" + (nbLine) + ")");

        CellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        cell1.setCellStyle(style);

        CellStyle style1 = workbook.createCellStyle();
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style1.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        cell3.setCellStyle(style1);

        CellStyle style2 = workbook.createCellStyle();
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style2.setFillForegroundColor(IndexedColors.RED1.getIndex());
        cell5.setCellStyle(style2);

        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 6, 5, 15, 25);
        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText("Temps d'attente selon le nombre d'article commandés");
        XDDFValueAxis bottomAxis = chart.createValueAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Nombre d'article commandés"); // https://stackoverflow.com/questions/32010765
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("Temps d'attente en seconde");
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

        XDDFDataSource<Double> xs = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, nbLine - 1, 2, 2));
        XDDFNumericalDataSource<Double> ys1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, nbLine - 1, 4, 4));

        XDDFScatterChartData data = (XDDFScatterChartData) chart.createData(ChartTypes.SCATTER, bottomAxis, leftAxis);
        XDDFScatterChartData.Series series1 = (XDDFScatterChartData.Series) data.addSeries(xs, ys1);
        series1.setSmooth(false); // https://stackoverflow.com/questions/39636138

        series1.setMarkerStyle(MarkerStyle.CIRCLE);
        series1.setMarkerSize((short) 5);
        setLineNoFill(series1);
        chart.plot(data);
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

    /**
     * Créé une table remplie avec les appareils, leur taux d'occupation et leur nb d'utilisation
     *
     * @param sheet feuille à écrire
     */
    private static void tableKfetier(XSSFWorkbook workbook, XSSFSheet sheet) {
        int nbLine = 17;

        int nbCook = ControllerHR.getInstance().getNbCooks();
        int nbCashier = ControllerHR.getInstance().getNbCashier();
        int nbKfetier = ControllerHR.getInstance().getNbKfetiers();

        //On crée nos tables
        XSSFTable tableCook = sheet.createTable(null);
        CTTable cttableCook = tableCook.getCTTable();
        cttableCook.setRef("A1:C" + (nbCook + 1));

        XSSFTable tableCashier = sheet.createTable(null);
        CTTable cttableCashier = tableCashier.getCTTable();
        cttableCashier.setRef("A" + (nbCook + 3) + ":C" + (nbCook + nbCashier + 3));

        XSSFTable tableKfetier = sheet.createTable(null);
        CTTable cttableKfetier = tableKfetier.getCTTable();
        cttableKfetier.setRef("A" + (nbCook + nbCashier + 5) + ":C" + (nbCook + nbCashier + nbKfetier + 5));

        //Table avec une ligne sur deux en couleur
        CTTableStyleInfo styleInfoCook = cttableCook.addNewTableStyleInfo();
        styleInfoCook.setName("TableStyleMedium6");
        styleInfoCook.setShowColumnStripes(false);
        styleInfoCook.setShowRowStripes(false);

        CTTableStyleInfo styleInfoCashier = cttableCashier.addNewTableStyleInfo();
        styleInfoCashier.setName("TableStyleMedium3");
        styleInfoCashier.setShowColumnStripes(false);
        styleInfoCashier.setShowRowStripes(false);

        CTTableStyleInfo styleInfoKfetier = cttableKfetier.addNewTableStyleInfo();
        styleInfoKfetier.setName("TableStyleMedium5");
        styleInfoKfetier.setShowColumnStripes(false);
        styleInfoKfetier.setShowRowStripes(false);

        //On choisit le nb de colonnes et on remplit
        CTTableColumns columnsCook = cttableCook.addNewTableColumns();
        columnsCook.setCount(2);
        CTTableColumns columnsCashier = cttableCashier.addNewTableColumns();
        columnsCashier.setCount(2);
        CTTableColumns columnsKfetier = cttableKfetier.addNewTableColumns();
        columnsKfetier.setCount(2);

        String[] name = {"Kfetier", "Taux d'occupation (%)", "Nombre d'interventions"};

        //On prépare la table avec id et nom
        for (int i = 1; i <= 3; i++) {
            CTTableColumn columnCook = columnsCook.addNewTableColumn();
            columnCook.setId(i);
            columnCook.setName(name[i - 1]);

            CTTableColumn columnCashier = columnsCashier.addNewTableColumn();
            columnCashier.setId(i);
            columnCashier.setName(name[i - 1]);

            CTTableColumn columnKfetier = columnsKfetier.addNewTableColumn();
            columnKfetier.setId(i);
            columnKfetier.setName(name[i - 1]);
        }

        XSSFRow rowNameCook = sheet.createRow(0);
        XSSFRow rowNameCashier = sheet.createRow(nbCook + 2);
        XSSFRow rowNameKfetier = sheet.createRow(nbCook + nbCashier + 4);

        for (int c = 0; c < 3; c++) {
            XSSFCell cell = rowNameCook.createCell(c);
            cell.setCellValue(name[c]);

            cell = rowNameCashier.createCell(c);
            cell.setCellValue(name[c]);

            cell = rowNameKfetier.createCell(c);
            cell.setCellValue(name[c]);

            sheet.autoSizeColumn(c);
        }

        for (int r = 1; r <= nbCook; r++) {
            XSSFRow row = sheet.createRow(r);
            for (int c = 0; c < 3; c++) {
                XSSFCell cell = row.createCell(c);
                switch (c) {
                    case 0 -> cell.setCellValue("Cook n°" + r);
                    case 1 -> cell.setCellFormula("ROUND(" + ((double) ControllerHR.getInstance().getCooks().get(r - 1).getOccupationTime() / 72) + ",1)");
                    case 2 -> cell.setCellValue(ControllerHR.getInstance().getCooks().get(r - 1).getNbUse());
                }
            }
        }

        for (int r = nbCook + 3; r < nbCook + nbCashier + 3; r++) {
            XSSFRow row = sheet.createRow(r);
            int current = r - nbCook - 3;
            for (int c = 0; c < 3; c++) {
                XSSFCell cell = row.createCell(c);
                switch (c) {
                    case 0 -> {
                        cell.setCellValue("Cashier n°" + (current + 1));
                        sheet.autoSizeColumn(c);
                    }
                    case 1 -> cell.setCellFormula("ROUND(" + ((double) ControllerHR.getInstance().getCashier().get(current).getOccupationTime() / 72) + ",1)");
                    case 2 -> cell.setCellValue(ControllerHR.getInstance().getCashier().get(current).getNbUse());
                }
            }
        }

        for (int r = nbCook + nbCashier + 5; r < nbCook + nbCashier + nbKfetier + 5; r++) {
            XSSFRow row = sheet.createRow(r);
            int current = r - nbCook - nbCashier - 5;
            for (int c = 0; c < 3; c++) {
                XSSFCell cell = row.createCell(c);
                switch (c) {
                    case 0 -> cell.setCellValue("Kfetier n°" + (current + 1));
                    case 1 -> cell.setCellFormula("ROUND(" + ((double) ControllerHR.getInstance().getKfetiers().get(current).getOccupationTime() / 72) + ",1)");
                    case 2 -> cell.setCellValue(ControllerHR.getInstance().getKfetiers().get(current).getNbUse());
                }
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