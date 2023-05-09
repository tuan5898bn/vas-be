package com.vaccineadminsystem.util;

import com.vaccineadminsystem.dto.VaccineDto;
import com.vaccineadminsystem.exception.ImportFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelUtil {

    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    /**
     * get data from excel with each row and cell and transfer to list vaccine dto
     *
     * @param inputStream get input stream from file
     * @return return a list dto if get data in excel success full
     * @throws ImportFileException throw if file is not excel or wrong format
     */
    public List<VaccineDto> excelToVaccineDTOS(InputStream inputStream) throws ImportFileException {
        List<VaccineDto> vaccineDtos = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            VaccineDto vc;
            boolean isHeader = true;

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                vc = rowToVaccineDto(currentRow);
                if (vc != null) {
                    vaccineDtos.add(vc);
                } else {
                    break;
                }
            }
            workbook.close();
        } catch (Exception e) {
            throw new ImportFileException(ErrorMess.INVALID_FILE_EXTENSION);
        }
        return vaccineDtos;
    }


    private VaccineDto rowToVaccineDto(Row row) {
        Iterator<Cell> cells = row.iterator();
        List<Cell> list = new ArrayList<>();
        VaccineDto vc = null;
        cells.forEachRemaining(list::add);
        if (!list.isEmpty()) {
            vc = new VaccineDto();
            vc.setVaccineID(list.get(0).getStringCellValue());
            vc.setActive(list.get(1).getBooleanCellValue());
            vc.setName(list.get(2).getStringCellValue());
            vc.setUsage(list.get(3).getStringCellValue());
            vc.setIndication(list.get(4).getStringCellValue());
            vc.setContraindication(list.get(5).getStringCellValue());
            vc.setNumberOfInjection((int) list.get(6).getNumericCellValue());
            vc.setNextTimeStart(list.get(7).getDateCellValue());
            vc.setNextTimeEnd(list.get(8).getDateCellValue());
            vc.setOrigin(list.get(9).getStringCellValue());
            vc.setVaccineTypeId(list.get(10).getStringCellValue());
        }
        return vc;
    }

}
