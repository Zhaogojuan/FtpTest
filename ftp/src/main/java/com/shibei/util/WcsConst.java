package com.shibei.util;

public class WcsConst {

    //中通
    public static final String EXPRESS_COMPANY_ZTO = "0";
    //申通
    public static final String EXPRESS_COMPANY_STO = "1";
    //圆通
    public static final String EXPRESS_COMPANY_YTO = "2";
    //韵达
    public static final String EXPRESS_COMPANY_YDA = "3";

    //上传到服务器的图片路径
    public static  String EXPRESS_IMAGE_FILE = "D:/upload/image/";

    //中通面单规则
    public static  String ZTO_RULE = "";

    //申通面单规则
    public static  String STO_RULE = "";

    //顶扫图片类型
    public static  String TOP_SCAN_IMAGE_TYPE = "jpg";

    //ftp下载文件参数配置
    //远程文件路径
    public static  String ftpRemotePath = "testImages";

    //文件后缀名
    public static  String ftpFilename = "jpg";

    //下载后保存到本地的路径
    public static  String ftpLocalPath = "D:/ftpDown/";

    //ftp服务器地址
    public static  String ftpHostname = "10.10.10.34";

    //ftp服务器端口号默认为21
    public static Integer port = 21 ;

    //ftp登录账号
    public static String username = "FTP_Admin";

    //ftp登录密码
    public static String password = "Shibei1234567";

    //0-快手   1-顶扫
    public static String SCAN_TYPE = "1";

}
