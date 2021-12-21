package core.report;

import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportExcel {

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

            //On créé la nouvelle feuille
            XSSFSheet sheet = workbook.createSheet("Results_from_" + dateFormat.format(date));

            //On créé notre table
            XSSFTable table = sheet.createTable(null);
            CTTable cttable = table.getCTTable();
            cttable.setRef("A1:D11");

            //Table avec une ligne sur deux en couleur
            CTTableStyleInfo styleInfo = cttable.addNewTableStyleInfo();
            styleInfo.setName("TableStyleMedium6");
            styleInfo.setShowColumnStripes(false);
            styleInfo.setShowRowStripes(true);

            //On choisit le nb de colonnes et on remplit
            CTTableColumns columns = cttable.addNewTableColumns();
            columns.setCount(4);

            String [] name = {"Numéro du client", "Heure d'arrivée", "Nombre d'articles commandés", "Heure de Départ"};
            for (int i = 1; i <= 4; i++) {
                CTTableColumn column = columns.addNewTableColumn();
                column.setId(i);
                column.setName(name[i-1]);
            }

            for (int r = 0; r < 2; r++) {
                XSSFRow row = sheet.createRow(r);
                for(int c = 0; c < 4; c++) {
                    XSSFCell cell = row.createCell(c);
                    if(r == 0) { //first row is for column headers
                        cell.setCellValue(name[c]); //content **must** be here for table column names
                    } else {
                        //cell.setCellValue("Data R"+ (r+1) + "C" + (c+1));
                    }
                    sheet.autoSizeColumn(c);
                }
            }

            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
            System.out.println("Created successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}