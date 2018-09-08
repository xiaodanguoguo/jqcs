package com.ebase.utils.file;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * @Description : 文件类型转化工具类
 */
public class FileToOtherUtil {

	/**
	 * 将文件转为字节码
	 *
	 * @param filePath 文件路径
	 * @return byte[] 字节数组
	 */
	static byte[] file2byte(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return null;
		}
		byte[] buffer = null;

		File file = new File(filePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (null != fis) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			try {
				while ((n = fis.read(b)) != -1) {
					bos.write(b, 0, n);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				buffer = bos.toByteArray();
			}
		}
		try {
			if (null != fis) {
				fis.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

}
