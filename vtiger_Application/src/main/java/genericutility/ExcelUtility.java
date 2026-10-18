package genericutility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

    public String getDataFromExcel(
            String sheetName, int rowNum, int cellNum)
            throws IOException {

        String filePath =
                "./src/test/resources/Vtiger_TestData.xlsx";

        try (FileInputStream fis =
                     new FileInputStream(filePath);
             Workbook wb = WorkbookFactory.create(fis)) {

            if (wb.getSheet(sheetName) == null) {
                throw new IllegalArgumentException(
                    "Excel sheet not found: " + sheetName
                );
            }

            if (wb.getSheet(sheetName).getRow(rowNum) == null) {
                throw new IllegalArgumentException(
                    "Excel row not found: " + rowNum
                );
            }

            if (wb.getSheet(sheetName)
                    .getRow(rowNum).getCell(cellNum) == null) {
                throw new IllegalArgumentException(
                    "Excel cell not found at row " + rowNum
                    + ", column " + cellNum
                );
            }

            DataFormatter formatter = new DataFormatter();

            return formatter.formatCellValue(
                wb.getSheet(sheetName)
                  .getRow(rowNum)
                  .getCell(cellNum)
            );
        }
    }
}
