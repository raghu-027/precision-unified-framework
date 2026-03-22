package com.precision.common.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {

    private static final String FILE_PATH = "testdata/testdata.xlsx";

    private ExcelUtils() {}

    public static Map<String, String> getTestData(String sheetName, String testCase) {
        Map<String, String> dataMap = new HashMap<>();

        InputStream input = ExcelUtils.class
                .getClassLoader()
                .getResourceAsStream(FILE_PATH);

        if (input == null) {
            throw new RuntimeException(
                    "testdata.xlsx not found in classpath: " + FILE_PATH);
        }

        try (Workbook workbook = new XSSFWorkbook(input)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            int colCount  = headerRow.getLastCellNum();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Cell firstCell = row.getCell(0);
                if (firstCell != null &&
                        getCellValue(firstCell).equalsIgnoreCase(testCase)) {
                    for (int col = 0; col < colCount; col++) {
                        String key   = getCellValue(headerRow.getCell(col));
                        String value = getCellValue(row.getCell(col));
                        dataMap.put(key, value);
                    }
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read Excel file: " + e.getMessage(), e);
        }

        if (dataMap.isEmpty()) {
            throw new RuntimeException(
                    "TestCase not found: " + testCase + " in sheet: " + sheetName);
        }

        return dataMap;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING  -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default      -> "";
        };
    }
}