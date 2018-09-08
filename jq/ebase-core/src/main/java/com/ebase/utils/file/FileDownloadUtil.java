package com.ebase.utils.file;

import com.ebase.utils.excel.ExportExcelUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class FileDownloadUtil {

	/**
	 * 下载服务器文件
	 *
	 * @param response response
	 * @param info     info 文件信息
	 */
	public static void exportToExcel(HttpServletResponse response, ExportFileInfo info) {
		if (null == response) {
			//只做简单非空验证
			return;
		}
		//生成的excel文件路径
		String excel2FilePath = null;
		try {
			excel2FilePath = ExportExcelUtils.createExcel2FilePath(
					info.getSheetName(),
					info.getTitle(),
					String.valueOf(System.currentTimeMillis()),
					info.getHeaders(),
					info.getDataList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//转换为字节码
		byte[] bytes = FileToOtherUtil.file2byte(excel2FilePath);
		try {
			response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(info.getExportFileName(), "UTF-8"));
			//response.addHeader("Content-Length", "" + 0)
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		OutputStream toClient = null;
		try {
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			if (null != bytes) {
				toClient.write(bytes);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != toClient) {
					toClient.flush();
					toClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 文件信息model
	 * 暂时放内部
	 *
	 * @param <T> T
	 */
	public static class ExportFileInfo<T> {

		/**
		 * 需要导出到本地的文件名
		 */
		private String exportFileName;

		/**
		 * sheetName
		 */
		private String sheetName;
		/**
		 * title
		 */
		private String title;

		/**
		 * 表头
		 * 格式 : 表头描述@propertyName
		 * 如: 姓名@name
		 */
		private String[] headers;
		/**
		 * 数据List
		 */
		private List<T> dataList;

		public ExportFileInfo(String exportFileName, String sheetName, String title, String[] headers, List<T> dataList) {
			this.exportFileName = exportFileName;
			this.sheetName = sheetName;
			this.title = title;
			this.headers = headers;
			this.dataList = dataList;
		}

		String getSheetName() {
			return sheetName;
		}

		String getTitle() {
			return title;
		}

		String getExportFileName() {
			return exportFileName;
		}

		String[] getHeaders() {
			return headers;
		}

		List<T> getDataList() {
			return dataList;
		}
	}

}


