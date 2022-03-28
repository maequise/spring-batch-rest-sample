package com.example.springbatch.reader;

import com.example.springbatch.domain.Person;
import com.example.springbatch.linemappers.ExcelLineMapper;
import com.example.springbatch.tokenizers.ExcelTokenizer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

import java.util.Iterator;

public class ExcelReader implements ItemReader<Person>, ItemStreamReader<Person> {
    private LineMapper<Person> lineMapper;
    private LineTokenizer lineTokenizer;
    private Resource resource;
    private Workbook workbook;
    private int maxLines = 0;
    private boolean endReached = false;
    private int currentLine = 0;

    public ExcelReader(){
        this.resource = new ClassPathResource("person.xlsx");
        this.lineMapper = new ExcelLineMapper();
    }

    public ExcelReader(boolean containsHeader){

        this.lineMapper = new ExcelLineMapper();

        if(containsHeader){
            this.currentLine = 1;
        }
    }

    @Override
    public Person read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String lineRead = readLine();

        if(lineRead == null){
            return null;
        }

        return lineMapper.mapLine(lineRead, currentLine);
    }

    @Nullable
    private String readLine() {
        String line = "";

        try{
            if(workbook == null) {
                workbook = new XSSFWorkbook(resource.getInputStream());
            }

            Sheet sheet = workbook.getSheetAt(0);
            maxLines = sheet.getLastRowNum();

            Row row = sheet.getRow(currentLine);

            if(row == null){
                doClose();
                return null;
            }

            Iterator<Cell> cells = row.cellIterator();

            StringBuilder lineBuilder = new StringBuilder();

            while(cells.hasNext()){
                Cell cell = cells.next();

                lineBuilder.append(cell.getStringCellValue());
                lineBuilder.append(";");
            }

            line = lineBuilder.substring(0, lineBuilder.length()-1);

            currentLine++;

        }catch (Exception e){
            e.printStackTrace();
        }

        return line;
    }

    private void doClose() throws Exception {
        if(this.workbook != null){
            this.workbook.close();
            this.endReached = true;
        }
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.resource = new ClassPathResource("person.xlsx");
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {
        currentLine = 0;
        workbook = null;
        resource = null;
        endReached = false;
    }
}
