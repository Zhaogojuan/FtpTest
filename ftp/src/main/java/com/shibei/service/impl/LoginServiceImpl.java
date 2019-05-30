package com.shibei.service.impl;

import com.shibei.service.LoginService;
import com.shibei.util.FtpUtils;
import com.shibei.util.WcsConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zwj
 * @Description:
 * @Date:Create：in 2019/5/28 11:35
 * @Modified By：
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Override
    public List<String> getPicture(String barCode, String cameraId, HttpServletResponse response) {
        log.info("传入参数：barCode:"+barCode+",cameraId:"+cameraId);
        List<String> list = new ArrayList();
        delAllFile(WcsConst.ftpLocalPath);
        Integer port = 21;
        if ("0001".equals(cameraId)){
            port = 31;
        }
        if ("0002".equals(cameraId)){
            port = 32;
        }
        if ("0003".equals(cameraId)){
            port = 33;
        }
        if ("0004".equals(cameraId)){
            port = 34;
        }
//        FtpUtils ftpUtils = new FtpUtils(port);
        FtpUtils ftpUtils = new FtpUtils();
        boolean b ;
        try {
            b = ftpUtils.downloadFile(barCode);
        }catch (Exception e){
            e.printStackTrace();
            return list;
        }
        if (!b){
            log.info("找不到面单号为："+barCode+"的图片");
            return list;
        }
        File file = new File(WcsConst.ftpLocalPath);
        File[] files = file.listFiles();
        String pathName = "";
        for (int i = 0; i < files.length; i++) {
            File file1 = files[i];
            String name = file1.getName();
            if (name.indexOf(barCode)!= -1){
                pathName = name;
                list.add(pathName);

            }
        }
        return list;
    }

    public boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                flag = true;
            }
        }
        return flag;
    }

    public static void main(String[] args) throws UnknownHostException {
        InetAddress localHost;
        localHost = InetAddress.getLocalHost();
        System.out.println(localHost.getHostAddress());
//        System.out.println(localHost.getAddress());

    }

}
