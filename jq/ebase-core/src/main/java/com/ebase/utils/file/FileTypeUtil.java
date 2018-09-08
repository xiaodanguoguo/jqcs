package com.ebase.utils.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.ebase.utils.secret.HexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过文件或者文件流的前几个字节，判断文件的实际类型
 */
public class FileTypeUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(FileTypeUtil.class);

	// 记录各个文件头信息及对应的文件类型
	public static Map<String, String> mFileTypes = new HashMap<String, String>();

	static {
		// images
		mFileTypes.put("FFD8FFE0", "jpg");
		mFileTypes.put("89504E47", "png");
		mFileTypes.put("47494638", "gif");
		mFileTypes.put("49492A00", "tif");
		mFileTypes.put("424D", "bmp");

		// PS和CAD
		mFileTypes.put("38425053", "psd");
		mFileTypes.put("41433130", "dwg"); // CAD
		mFileTypes.put("252150532D41646F6265", "ps");

		// 办公文档类
		mFileTypes.put("D0CF11E0", "doc"); // ppt、doc、xls
		mFileTypes.put("504B0304", "docx");// pptx、docx、xls

		/** 注意由于文本文档录入内容过多，则读取文件头时较为多变-START **/
		mFileTypes.put("0D0A0D0A", "txt");// txt
		mFileTypes.put("0D0A2D2D", "txt");// txt
		mFileTypes.put("0D0AB4B4", "txt");// txt
		mFileTypes.put("B4B4BDA8", "txt");// 文件头部为汉字
		mFileTypes.put("73646673", "txt");// txt,文件头部为英文字母
		mFileTypes.put("32323232", "txt");// txt,文件头部内容为数字
		mFileTypes.put("0D0A09B4", "txt");// txt,文件头部内容为数字
		mFileTypes.put("3132330D", "txt");// txt,文件头部内容为数字
		/** 注意由于文本文档录入内容过多，则读取文件头时较为多变-END **/

		mFileTypes.put("7B5C727466", "rtf"); // 日记本

		mFileTypes.put("255044462D312E", "pdf");

		// 视频或音频类
		mFileTypes.put("3026B275", "wma");
		mFileTypes.put("57415645", "wav");
		mFileTypes.put("41564920", "avi");
		mFileTypes.put("4D546864", "mid");
		mFileTypes.put("2E524D46", "rm");
		mFileTypes.put("000001BA", "mpg");
		mFileTypes.put("000001B3", "mpg");
		mFileTypes.put("6D6F6F76", "mov");
		mFileTypes.put("3026B2758E66CF11", "asf");

		// 压缩包
		mFileTypes.put("52617221", "rar");
		mFileTypes.put("1F8B08", "gz");

		// 程序文件
		mFileTypes.put("3C3F786D6C", "xml");
		mFileTypes.put("68746D6C3E", "html");
		mFileTypes.put("7061636B", "java");
		mFileTypes.put("3C254020", "jsp");
		mFileTypes.put("4D5A9000", "exe");

		mFileTypes.put("44656C69766572792D646174653A", "eml"); // 邮件
		mFileTypes.put("5374616E64617264204A", "mdb");// Access数据库文件

		mFileTypes.put("46726F6D", "mht");
		mFileTypes.put("4D494D45", "mhtml");

	}
	
	public static String getFileType(String filePath) {
		File f = new File(filePath);
		return getFileType(f);
	}

	public static String getFileType(File file){
		try {
			if(file!=null&&file.exists()){
				return getFileType(new FileInputStream(file));
			}else{
				logger.info("{}不存在",file);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return "";
	}
	
	/**
	 * @param fileContent
	 * @return
	 */
	public static String getFileType(byte[] fileContent) {
		if(fileContent==null){
			return "";
		}else{
			InputStream inputStream = new ByteArrayInputStream(fileContent);  
			return getFileType(inputStream);
		}
	}
	
	/**
	 * 根据文件的输入流获取文件头信息
	 * @return 文件头信息
	 */
	public static String getFileType(InputStream is) {
		byte[] b = new byte[4];
		if (is != null) {
			try {
				is.read(b, 0, b.length);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mFileTypes.get(getFileHeader(b));
	}

	/**
	 * 根据文件转换成的字节数组获取文件头信息
	 */
	private static String getFileHeader(byte[] b) {
		String value = HexUtil.bytesToHexString(b).toUpperCase();
		return value;
	}
}