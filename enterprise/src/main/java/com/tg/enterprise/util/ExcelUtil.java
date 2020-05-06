package com.tg.enterprise.util;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    public static List<List<String>> readXlsx(String path) throws IOException {
        InputStream input = new FileInputStream(path);
        return readXlsx(input);
    }

    public static List<List<String>> readXlsx(InputStream input) throws IOException {
        List<List<String>> result = new ArrayList<List<String>>();
        XSSFWorkbook workbook = new XSSFWorkbook(input);
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = workbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow row = xssfSheet.getRow(rowNum);
                int minCellNum = row.getFirstCellNum();
                int maxCellNum = row.getLastCellNum();
                List<String> rowList = new ArrayList<String>();
                for (int i = minCellNum; i < maxCellNum; i++) {
                    XSSFCell cell = row.getCell(i);
                    cell.setCellType(CellType.STRING);
                    String str = cell.getStringCellValue();
                    rowList.add(str.trim());
                }
                result.add(rowList);
            }
        }
        return result;
    }

}
