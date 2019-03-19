package jq.steel.cs.webapps.cs.controller.question;

import com.raqsoft.report.model.ReportDefine;
import com.raqsoft.report.usermodel.Context;
import com.raqsoft.report.usermodel.Engine;
import com.raqsoft.report.usermodel.IReport;
import com.raqsoft.report.util.ReportUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatePdf {


    public String createPdf(Long qid,String reporturl,String pdfName,String model) {
        try {
            File file;
            String dirName = createDir(reporturl);
            String fileName = createDir(reporturl)+"/" + pdfName;
            file = new File(fileName);
            if (file.isFile() && file.exists()) {// 路径为文件且不为空则进行删除
                file.delete();// 文件删除
            }
                ReportDefine report;
                Context cxt = new Context();
                cxt.setParamValue("qid",qid);
                report = (ReportDefine) ReportUtils.read(reporturl + model + ".rpx");
                FileOutputStream fos = new FileOutputStream(fileName);
                Engine engine = new Engine(report, cxt);
                IReport iReport = engine.calc();
                ReportUtils.exportToPDF(fos, iReport);
            fos.flush();
            fos.close();
            file.setWritable(true, false);    //设置写权限，windows下不用此语句
            return fileName;
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    // 创建目录
    public String createDir(String reporturl) {
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
        String dirT = sdf.format(new Date());
        String dirName = reporturl + dirT;// 创建目录名称
        File dir = new File(dirName);
        dir.setWritable(true, false);    //设置写权限，windows下不用此语句
        if (dir.exists()) {// 判断目录是否存在
            System.out.println("创建目录失败，目标目录已存在！");
            System.out.println("111111111111111111111111");
        }else if (dir.mkdirs()) {// 创建目标目录
            System.out.println("创建目录成功！" + dirName);
            System.out.println("222222222222222222222222");
        } else {
            System.out.println("创建目录失败！");
            System.out.println("333333333333333333333333");
        }
        return dirName;
    }
}
