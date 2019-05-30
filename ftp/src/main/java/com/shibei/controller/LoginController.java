package com.shibei.controller;

import com.shibei.util.ImgUtil;
import com.shibei.util.WcsConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zwj
 * @Description:
 * @Date:Create：in 2019/5/28 9:11
 * @Modified By：
 */
@Slf4j
@Controller
public class LoginController {

    /**
     * 跳转搜索图片页面
     * @param model
     * @return
     */
    @RequestMapping("/searchPic")
    public String  searchPic(Model model) {
        return "/monitor/searchPic";
    }

    /**
     * 跳转搜索图片页面
     * @param model
     * @return
     */
    @RequestMapping("/test")
    public String  test(Model model) {
        return "/monitor/test";
    }


    /*
     * 获取图片
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/getPicture1")
    public void getPicture1(String pathName, HttpServletResponse response) {
        log.info("图片名："+pathName);
        if (!pathName.contains(".jpg") && !pathName.contains(".png")){
            pathName = pathName + ".jpg";
        }
        try {
            ImgUtil.queryPic(WcsConst.ftpLocalPath + pathName ,response);
        } catch (IOException e) {
            log.info("找不到图片");
            e.printStackTrace();
        }

    }


}
