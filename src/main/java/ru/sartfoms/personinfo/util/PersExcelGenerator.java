package ru.sartfoms.personinfo.util;

import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;

import ru.sartfoms.personinfo.exception.ExcelGeneratorException;
import ru.sartfoms.personinfo.model.PersRowData;

public class PersExcelGenerator extends ExcelGenerator {
	private Collection<PersRowData> data;
	private DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public PersExcelGenerator(Collection<PersRowData> rows) throws ExcelGeneratorException {
		super(rows);
		data = rows;

		createHeader();
		createBody();
	}

	@Override
	protected void createBody() {
		Font bodyFont = template.createFont();
		bodyFont.setFontName("Calibri");

		CellStyle bodyStyleAlignmentLeft = template.createCellStyle();
		bodyStyleAlignmentLeft.setFont(bodyFont);
		bodyStyleAlignmentLeft.setAlignment(HorizontalAlignment.LEFT);

		CellStyle bodyStyleAlignmentCenter = template.createCellStyle();
		bodyStyleAlignmentCenter.setFont(bodyFont);
		bodyStyleAlignmentCenter.setAlignment(HorizontalAlignment.CENTER);

		int rowNum = 2;// firstDataRow
		for (PersRowData rowData : data) {
			int column = 0;
			XSSFRow row = sheet.createRow(rowNum++);
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter), String.valueOf(rowNum - 2));
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter), rowData.getOip());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter), rowData.getEnp());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter),
					rowData.getPcyEffDt() != null ? rowData.getPcyEffDt().format(DATE_FORMATTER) : "");
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter), rowData.getPcyType());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter), rowData.getPcyStatus());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter), rowData.getDsourceType());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter), rowData.getGender());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft), rowData.getLastName());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft), rowData.getFirstName());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft), rowData.getPatronymic());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter),
					rowData.getBirthDay() != null ? rowData.getBirthDay().format(DATE_FORMATTER) : "");
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft), rowData.getBlankNum());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft), rowData.getDudlNum());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter), rowData.getDudlSer());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter),
					rowData.getDudlDateB() != null ? rowData.getDudlDateB().format(DATE_FORMATTER) : "");
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter),
					rowData.getDudlDateE() != null ? rowData.getDudlDateE().format(DATE_FORMATTER) : "");
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter), rowData.getDudlStatus());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter), rowData.getSnils());
			setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter),
					rowData.getPcyExpDt() != null ? rowData.getPcyExpDt().format(DATE_FORMATTER) : "");
		}
		for (int i = 0; i < 22; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	@Override
	protected void createHeader() {
		Object[] columns = new String[] { "№", "OIP_RESP", "ENP", "PCYDATEB", "PCYTYPE", "PCYSTATUS", "DSOURCETYPE",
				"SEX", "FAM", "IM", "OT", "DR", "BLANKNUM", "DUDLNUM", "DUDLSER", "DUDLDATEB", "DUDLDATEE",
				"DUDLSTATUS", "SNILS", "PCYDATEE" };

		CellStyle headerStyle = template.createCellStyle();
		Font headerFont = template.createFont();
		headerFont.setBold(true);
		headerFont.setFontName("Calibri");
		headerStyle.setFont(headerFont);
		headerStyle.setWrapText(true);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);

		XSSFRow row = sheet.createRow(0);
		for (int cn = 0; cn < columns.length; cn++) {
			setCellValue(createCellAndFormat(row, cn, headerStyle), columns[cn]);
		}
	}

}
