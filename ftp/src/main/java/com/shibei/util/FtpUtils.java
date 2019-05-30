package com.shibei.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

@Slf4j
public class FtpUtils {

    //ftp服务器地址
    String hostname ;
    //ftp服务器端口号默认为21
    Integer port ;
    //ftp登录账号
    String username ;
    //ftp登录密码
    String password ;

    public FtpUtils()
    {
        //ftp服务器地址
//        hostname = "10.10.10.13";
        try {
            hostname = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            hostname = "10.10.10.13";
        }
//        InetAddress localHost = InetAddress.getLocalHost();
        //ftp服务器端口号默认为21
        port = 2020 ;
        //ftp登录账号
        username = "zwj";
        //ftp登录密码
        password = "6065007z";
    }

    public FtpUtils(Integer cameraPort)
    {
        //ftp服务器地址
        hostname = "172.31.58.28";
        //ftp服务器端口号默认为21
        port = cameraPort ;
        //ftp登录账号
        username = "Administrator";
        //ftp登录密码
        password = "abcABC123";
    }

    /**
     * 初始化ftp服务器
     */
    public static FTPClient initFtpClient(FTPClient ftpClient, String hostname, int port, String username, String password) {
        ftpClient = new FTPClient();
        //ftpClient.setControlEncoding("utf-8");
        try {
            log.info("connecting...ftp服务器..start:"+hostname+":"+port);
            ftpClient.connect(hostname, port); //连接ftp服务器
            ftpClient.login(username, password); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
                log.info("connect failed...ftp服务器:"+hostname+":"+port);
            }
            log.info("connect successful...ftp服务器:"+hostname+":"+port);

            return ftpClient ;
        }catch (MalformedURLException e) {
            e.printStackTrace();
            return null ;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** * 下载文件 *
     *
     * @return */
    public  boolean downloadFile(){
        boolean flag = false;
        OutputStream os=null;
        FTPClient ftpClient = new FTPClient();
        try {
            log.info("开始下载文件");
            //判断是否下载 downFlag 0: 下载  1:下载
            int downFlag = 0;
            ftpClient = initFtpClient(ftpClient, hostname, port,username, password);
            //切换FTP目录
            String ftpRemotePath = "";
            ftpClient.changeWorkingDirectory(ftpRemotePath);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                if(file.getName().endsWith(WcsConst.ftpFilename)){
                    log.info("*****"+file.getName());
                    File localFile = new File(WcsConst.ftpLocalPath + "/" + file.getName());
                    File fileParent = localFile.getParentFile();
                    if(!fileParent.exists()){
                        fileParent.mkdirs();
                    }
                    os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                    downFlag = 1 ;
                }
            }
            ftpClient.logout();
            if (downFlag == 1)
            {
                flag = true;
                log.info("下载文件成功");
            }
            else
            {
                log.info("下载文件失败");
            }

        } catch (Exception e) {
            log.info("下载文件失败");
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


    /** * 根据条码下载文件 *
     *
     * @return */
    public   boolean downloadFile(String barcode){
        boolean flag = false;
        OutputStream os=null;
        FTPClient ftpClient = new FTPClient();
        try {
            //判断是否下载 downFlag 0: 下载  1:下载
            int downFlag = 0;
            log.info("开始下载文件");
            ftpClient = FtpUtils.initFtpClient(ftpClient, hostname, port,username, password);
            String ftpRemotePath = WcsConst.ftpRemotePath;
            if (31 == port){
                ftpRemotePath = "0001";
            }
            if (32 == port){
                ftpRemotePath = "0002";
            }
            if (33 == port){
                ftpRemotePath = "0003";
            }
            if (34 == port){
                ftpRemotePath = "0004";
            }
            //切换FTP目录
            log.info("ftpRemotePath==="+ftpRemotePath);
            ftpClient.changeWorkingDirectory(ftpRemotePath);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                if(file.getName().endsWith(WcsConst.ftpFilename) && file.getName().indexOf(barcode)!=-1){
                    log.info("*****"+file.getName());
                    File localFile = new File(WcsConst.ftpLocalPath + "/" + file.getName());
                    File fileParent = localFile.getParentFile();
                    if(!fileParent.exists()){
                        fileParent.mkdirs();
                    }
                    os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                    downFlag = 1 ;
                    String name = localFile.getName();
                    log.info("下载的文件为："+name);
                }
            }
            ftpClient.logout();
            if (downFlag == 1)
            {
                flag = true;
                log.info("下载文件成功");
            }
            else
            {
                log.info("下载文件失败");
            }

        } catch (Exception e) {
            log.info("下载文件失败");
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /** * 根据条码和时间下载文件 *
     *
     * @return */
    public   boolean downloadFile(String barcode,String shotTime){
        boolean flag = false;
        OutputStream os=null;
        FTPClient ftpClient = new FTPClient();
        try {

            //判断是否下载 downFlag 0: 下载  1:下载
            int downFlag = 0;

            log.info("开始下载文件");
            ftpClient = FtpUtils.initFtpClient(ftpClient, hostname, port,username, password);
            String ftpRemotePath = WcsConst.ftpRemotePath;
            if (31 == port){
                ftpRemotePath = "0001";
            }
            if (32 == port){
                ftpRemotePath = "0002";
            }
            if (33 == port){
                ftpRemotePath = "0003";
            }
            if (34 == port){
                ftpRemotePath = "0004";
            }
            //切换FTP目录
            ftpClient.changeWorkingDirectory(ftpRemotePath);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                if(file.getName().endsWith(WcsConst.ftpFilename) && file.getName().indexOf(barcode + shotTime)!=-1){
                    log.info("*****");
                    log.info(file.getName());

                    File localFile = new File(WcsConst.ftpLocalPath + "/" + file.getName());
                    File fileParent = localFile.getParentFile();
                    if(!fileParent.exists()){
                        fileParent.mkdirs();
                    }
                    os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                    downFlag = 1 ;
                }
            }
            ftpClient.logout();
            if (downFlag == 1)
            {
                flag = true;
                log.info("下载文件成功");
            }
            else
            {
                log.info("下载文件失败");
            }

        } catch (Exception e) {
            log.info("下载文件失败");
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        FtpUtils ftpUtils = new FtpUtils();
        boolean b = ftpUtils.downloadFile();
        log.info("b===="+b);

    }
}
