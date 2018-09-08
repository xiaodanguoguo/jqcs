package com.ebase.utils.file;

import com.ebase.core.DateHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class FileUtil {
    /**
     * 上传文件
     * @param file  文件对应的byte数组流   使用file.getBytes()方法可以获取
     * @param filePath  上传文件路径，不包含文件名
     * @param fileName 上传文件名
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+"/"+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 获取文件的存储目录（根据时间和类型）
     * @return
     */
    public static String getDirName(byte[] fileContent){
        // /2018/08/image/
        String fileType = FileTypeUtil.getFileType(fileContent);
        return "/"+DateHelper.getCurrYear()+"/"+DateHelper.getCurrMonth()+"/"+fileType+"/";
    }


    /**
     * 获取文件名
     * @param fileExt 扩展名
     * @return
     */
    public static String getFileName(String fileExt){
        //yyyyMMddHHmmss_xxx.xxx
        return DateHelper.formatDateToyyyyMMddHHmm2(new Date())+ "_" + new Random().nextInt(10000) + "." + fileExt;
    }


    public static String renameToUUID(String fileName) throws Exception {
        return UUID.randomUUID().toString();
    }

}
