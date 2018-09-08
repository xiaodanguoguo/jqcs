package com.ebase.core.sensitive;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Build {
	
	public static void main(String[] args) throws Exception {
		
		Set<String> sets = new LinkedHashSet<>();
		//将sets 写入到文件中
		sets.addAll(readLineSep("e:/file/key.txt","\\|"));
		sets.addAll(readExcel("e:/file/敏感词库表统计.xls"));
		sets.addAll(readLine("e:/file/暴恐词库.txt"));
		sets.addAll(readLine("e:/file/反动词库.txt"));
		sets.addAll(readLine("e:/file/民生词库.txt"));
		sets.addAll(readLine("e:/file/其他词库.txt"));
		sets.addAll(readLine("e:/file/贪腐词库.txt"));
		sets.addAll(readLine("e:/file/基本词库.txt"));
		
		FileOutputStream output = new FileOutputStream("e:/sensitiveWord.txt");
		IOUtils.writeLines(sets,null, output,"UTF-8");
		
	}
	
	/**
	 * 读取 一行行的数据
	 * @param filePath
	 * @return
	 */
	public static Set<String> readLine(String filePath) throws Exception{
		Set<String> sets = new HashSet<>();
		FileInputStream input = new FileInputStream(filePath);
		List<String> list =  IOUtils.readLines(input,"UTF-8");
		sets.addAll(list);
		return sets;
	}
	
	
	/**
	 * 读取每行的数据，用分隔符号间隔开
	 * @param filePath
	 * @return
	 */
	public static Set<String> readLineSep(String filePath,String sep) throws Exception{
		Set<String> sets = new HashSet<>();
		FileInputStream input = new FileInputStream(filePath);
		List<String> list =  IOUtils.readLines(input,"UTF-8");
		for (String str : list) {
		 	String[] values = StringUtils.split(str,sep);
			if(values!=null){
				for (String value : values) {
					if(StringUtils.isNotEmpty(value)){
						sets.add(value);
					}
				}
			}
		}
		return sets;
	}
	
	
	/**
	 * 读取 xlxs
	 * @param filePath
	 * @param sep
	 * @return
	 * @throws Exception
	 */
	public static Set<String> readExcel(String filePath) throws Exception{
		Set<String> sets = new HashSet<>();
		
		FileInputStream fis = null;
		// 去读Excel
		Workbook wb = null;
		try {
			fis = new FileInputStream(filePath);
			if(StringUtils.endsWithIgnoreCase(filePath, "xls")){
				wb = new XSSFWorkbook(fis);
			}else{
				wb = new HSSFWorkbook(fis);
			}
			Sheet sheet = wb.getSheetAt(0);
			// 获取最后行号
			int lastRowNum = sheet.getLastRowNum();
			
			for (int i = 1; i < lastRowNum-1; i++) {
				Row row = sheet.getRow(i);
				Cell cell =  row.getCell(3);
				if(cell!=null){
					String str = getStringCellValue(cell);
					if(StringUtils.isNotEmpty(str)){
						sets.add(str);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			fis.close();
			wb.close();
		}
		return sets;
	}
	
	/** 数字格式化 */
	private static NumberFormat format = NumberFormat.getInstance();
	
	private static String getStringCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		String strCell = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(format.format(cell.getNumericCellValue())).replace(",", "");
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (StringUtils.isBlank(strCell)) {
			return "";
		}

		return strCell;
	}
}
