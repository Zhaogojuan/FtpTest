package com.shibei.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @ClassName ImgUtil
 * @Description
 * @Author zwj
 * @Date 2019-01-25 15:22
 * @Version 1.0
 */
public class ImgUtil {
    /**
     * 读数据库，获取图片输入流
     * @param
     * @return
     * @throws
     */
    public static FileInputStream query_getPhotoImageBlob(String adress) {
        FileInputStream is = null;
        File filePic = new File(adress);
        try {
            is = new FileInputStream(filePic);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return is;

    }
    /*
     * 获取图片并显示在页面
     * @return
     * @throws SQLException
     */
    public static void queryPic(String path, HttpServletResponse response) throws IOException {

        if (path != null){
            response.setContentType("image/jpeg");
            FileInputStream is =query_getPhotoImageBlob(path);
            if (is != null){
                int i = is.available(); // 得到文件大小
                byte data[] = new byte[i];
                is.read(data); // 读数据
                is.close();
                response.setContentType("image/jpeg");  // 设置返回的文件类型
                OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
                toClient.write(data); // 输出数据
                toClient.close();
            }
        }
    }
}
