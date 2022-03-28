package com.example.springbatch.reader;

import com.example.springbatch.reader.interfaces.ExcelStreamReader;
import com.sun.source.tree.LineMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import javax.sound.sampled.Line;
import java.io.IOException;
import java.util.Iterator;

public class AbstractExcelReader<T> extends AbstractExcelStreamReader<T> implements InitializingBean {
    private Resource resource;
    private LineMapper<T> lineMapper;
    private int currentRow = 0;
    private boolean endReached = false;
    private int maxRows;
    private Workbook workbook;
    private boolean updateContext = false;
    private int rowsToSkip = 0;
    private String nameSheet;

    public AbstractExcelReader() {

    }

    public AbstractExcelReader(Resource resource, LineMapper<T> lineMapper){
        this.resource = resource;
        this.lineMapper = lineMapper;
    }

    public AbstractExcelReader(Resource resource, LineMapper<T> lineMapper, String nameSheet){
        this.resource = resource;
        this.lineMapper = lineMapper;
        this.nameSheet = nameSheet;
    }

    public void setLineMapper(LineMapper<T> lineMapper){
        this.lineMapper = lineMapper;
    }

    public void setResource(Resource resource){
        this.resource = resource;
    }

    public void skipRows(int rowsToSkip){
        this.rowsToSkip = rowsToSkip;
    }

    public void setNameSheet(String nameSheet){
        this.nameSheet = nameSheet;
    }

    @Override
    public T doRead() throws Exception {
        String line = readLine();

        if(line == null){
            return null;
        }

        return this.lineMapper.mapLine(line, currentRow);
    }

    private String readLine(){
        String line = null;

        try{
            if(this.workbook == null){
                this.workbook = new XSSFWorkbook(this.resource.getInputStream());
            }

            Sheet sheet = this.workbook.getSheet(this.nameSheet);

            Row row = sheet.getRow(currentRow);

            if(row == null){
                return null;
            }

            StringBuilder lineBuilder = new StringBuilder();

            Iterator<Cell> cells = row.cellIterator();

            while(cells.hasNext()){
                Cell cell = cells.next();

                lineBuilder.append(cell.getStringCellValue())
                        .append(";");
            }

            line = lineBuilder.substring(0, lineBuilder.length()-1);

            this.currentRow++;
        }catch (IOException e){
            e.printStackTrace();
        }

        return line;
    }

    @Override
    public void doOpen() {
        Assert.notNull(resource, "You must set a Resource to parse !");
        Assert.notNull(lineMapper, "You must set a LineMapper !");
        //Assert.notNull(nameSheet, "You must provide a sheet name");
        //throw new IllegalStateException("You must implement this method !");
    }

    @Override
    public void doClose() {
        closeWorkbook();
        closeResource();
        resetStates();
    }

    @Override
    public void doUpdate() {

    }

    @Override
    public void close() throws ItemStreamException {
        closeResource();
        closeWorkbook();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(resource, "You must set a Resource to parse !");
        Assert.notNull(lineMapper, "You must set a LineMapper !");
        Assert.notNull(nameSheet, "You must provide a sheet name");
    }

    private void resetStates(){
        this.currentRow = 0;
        this.maxRows = 0;
        this.endReached = false;
        this.resource = null;
    }

    @Override
    public void closeResource() {
       this.resource = null;
    }

    @Override
    public void closeWorkbook() {
        if(this.workbook != null){
            try {
                this.workbook.close();
            }catch (IOException e){
                //nothing to do !
            }
        }
    }
}
