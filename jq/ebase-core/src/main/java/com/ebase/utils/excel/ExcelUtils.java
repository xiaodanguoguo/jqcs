package com.ebase.utils.excel;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: ExcelAttribute1
 * Description: excel 导出（依赖注解）
 * Author: wangyu
 * Date: 2018/4/11 21:05
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class ExcelUtils {

	private static Logger LOG = LoggerFactory.getLogger(ExcelUtils.class);

	private static void setFileName(HttpServletRequest request, HttpServletResponse response, String exportFileName)
			throws UnsupportedEncodingException {
		String agentLower = request.getHeader("User-Agent").toLowerCase(); // 获取浏览器
		if (agentLower.contains("firefox")) {// 火狐浏览器使用base64编码
			response.addHeader("Content-Disposition",
					"attachment;filename=\"" + new String(exportFileName.getBytes("UTF-8"), "iso-8859-1") + "\"");
		} else if (agentLower.contains("msie")) {// IE使用url编码
			response.addHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(exportFileName, "UTF-8"));
		} else {
			response.addHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(exportFileName, "UTF-8"));
		}
	}

	/**
	 * 导入到response 中
	 * @return
	 */
	public static <T> Boolean OutPutWorkBookResponse(List<T> list, String title,Class<T> clazz,String fileName){

		try{
			XSSFWorkbook workbook = exportExcel(list, title, clazz);
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getResponse();

			setFileName(request, response, fileName+".xls");
			response.setContentType("application/octet-stream");
			workbook.write(response.getOutputStream());
			workbook.close();
			return true;
		}catch (Exception e){
			LOG.error("文件导出error = {}", e);
		}
		return false;
	}


	/**
	 * 导出excel  单个sheet
	 * @param list  数据
	 * @param title excel工作表
	 * @return XSSFWorkbook
	 */
	public static <T> XSSFWorkbook exportExcel(List<T> list, String title,Class<T> clazz) {

		Field[] allFields = clazz.getDeclaredFields();
		List<Field> fields = new ArrayList<Field>();
		// 得到所有field并存放到一个list中.
		for (Field field : allFields) {
			// 如果被这个注解修饰
			if (field.isAnnotationPresent(ExcelAttribute.class)) {
				fields.add(field);
			}
		}
		XSSFWorkbook workbook = new XSSFWorkbook();// 产生工作薄对象

		try {
			//报表属性
			XSSFCellStyle createCellStyle = upXssfCellStyle(workbook);

			XSSFSheet sheet = workbook.createSheet(title);

			// 产生表格标题行
			XSSFRow createRow = sheet.createRow(0);
			
			// 列 ---
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				upXssFCellColumn(createCellStyle, sheet, createRow, i, field);
			}

			//垂直并居中样式
            XSSFCellStyle cellStyleMiddle = workbook.createCellStyle();
            cellStyleMiddle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            cellStyleMiddle.setAlignment(XSSFCellStyle.VERTICAL_CENTER);
            cellStyleMiddle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
            cellStyleMiddle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平

			// 数据
			for (int j = 0, size = list.size(); j < size; j++) {
				
				XSSFRow row = sheet.createRow(j + 1);

				for (int i = 0; i < fields.size(); i++) {
					Field field = fields.get(i);
					field.setAccessible(true);// class访问权限
					ExcelAttribute attribute = field.getAnnotation(ExcelAttribute.class);
					// 是否需要显示
					XSSFCell cell = row.createCell(i);// 单元格
                    cell.setCellStyle(cellStyleMiddle);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);

					// 如果有值 就 设置 没有值 就用默认值
					T t = (T) list.get(j);
					Object object = field.get(t);
					//添加数组逻辑
					if(object instanceof String[]){
                        //如果是数组时的style
                        cellStyleMiddle.setWrapText(true); //自动换行
                        cell.setCellStyle(cellStyleMiddle);
                        String [] str = (String[])object;
                        StringBuffer buffer = new StringBuffer();
                       for(int z = 0,sizz = str.length;z < sizz; z++){
                           if(z != (sizz-1)){
                               buffer.append(str[z] + "\r\n" );
                           }else{
                               buffer.append(str[z]);
                           }
                       }
                        cell.setCellValue(new XSSFRichTextString(buffer.toString()));

                    }else if(object instanceof Date){
						String format = DateFormatUtils.format((Date) object, "yyyy-MM-dd HH:mm:ss");
						cell.setCellValue(format);
					} else{
						String str = object == null ? attribute.isDefault() : String.valueOf(object);
						cell.setCellValue(str);
					}
				}
			}
		} catch (IllegalAccessException e) {
			LOG.error("excel表格生成异常", e);
		}
		return workbook;
	}

	private static void upXssFCellColumn(XSSFCellStyle createCellStyle, XSSFSheet sheet, XSSFRow createRow, int i, Field field) {
		ExcelAttribute attribute = field.getAnnotation(ExcelAttribute.class);
		// 列名
		String name = attribute.name();

		// 创建列
		XSSFCell cell = createRow.createCell(i);

		// 列数据
		cell.setCellValue(name);
		cell.setCellStyle(createCellStyle);
		sheet.setColumnWidth(i, 5000);
	}

	/**
	 * 设置workBook 样式
	 * @param workbook
	 * @return
	 */
	private static XSSFCellStyle upXssfCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle createCellStyle = workbook.createCellStyle();
		// 设置格式
		createCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		createCellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 设置颜色为红色
		createCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        createCellStyle.setWrapText(true); //自动换行
        createCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //设置居中

		return createCellStyle;
	}


}
