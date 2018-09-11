package jq.steel.cs.webapps.cs.controller.file;

/**
 * @ClassName: FileUploadSringUtil
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/8/29 18:00
 */
public class FileUploadSringUtil {

    /**
     * @param:
     * @return:
     * @description: 添加访问路径
     * @author: lirunze
     * @Date: 2018/8/29
     */
    public static String addPath(String configPath, String rootPath) {
        return configPath + rootPath;
    }


    /**
     * @param:
     * @return:
     * @description:  截取路径
     * @author: lirunze
     * @Date: 2018/8/29
     */
    public static String subString(String configPath, String rootPath) {
        return rootPath = rootPath.substring(configPath.length());
    }
}
