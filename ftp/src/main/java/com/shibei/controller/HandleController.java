package com.shibei.controller;

import com.shibei.pojo.ResultInfo;
import com.shibei.service.LoginService;
import com.shibei.util.FtpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: zwj
 * @Description:
 * @Date:Create：in 2019/5/28 14:50
 * @Modified By：
 */
@RestController
@Slf4j
public class HandleController {

    @Autowired
    private LoginService loginService;
    /*
     * 获取图片
     * @return
     * @throws SQLException
     */
    @GetMapping(value = "/getPicture")
    public ResultInfo getPicture(String barCode, String cameraId,HttpServletResponse response) {
        log.info("barCode:"+barCode+",cameraId:"+cameraId);
        ResultInfo resultInfo = new ResultInfo();
        try {
            List<String> picture = loginService.getPicture(barCode,cameraId ,response);
            if (picture != null && picture.size() > 0){
                resultInfo.setCode(1);
                resultInfo.setData(picture);
                return resultInfo;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        resultInfo.setCode(0);
        resultInfo.setMsg("找不到面单号为："+barCode+"的图片");
        return resultInfo;

    }

    /*
     * 查询图片接口
     * @return  http://localhost:8080/findPic?barCode=888
     * @throws SQLException
     */
    @GetMapping(value = "/findPic")
    public String findPic(String barCode,Integer port ,String time) {
        log.info("面单号："+barCode+",时间："+time);
        try {
            FtpUtils ftpUtils = new FtpUtils(port);
            boolean b = false;
            if (StringUtils.isBlank(time)){
                if (StringUtils.isNotBlank(barCode)){
                   b = ftpUtils.downloadFile(barCode);
                }else {
                   b = ftpUtils.downloadFile();
                }
            }else {
                if (StringUtils.isNotBlank(barCode)){
                    b = ftpUtils.downloadFile(barCode, time);
                }else {
                    log.info("面单号为空");
                }
            }
            log.info("查询面单号返回："+b);
            if (b){
                return "download success !!!";
            }
        } catch (Exception e) {
            log.info("找不到图片");
            e.printStackTrace();
        }
        return "download failed !!!";
    }
}
