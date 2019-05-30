package com.shibei.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: zwj
 * @Description:
 * @Date:Create：in 2019/5/28 11:36
 * @Modified By：
 */
public interface LoginService {
    List<String> getPicture(String barCode, String cameraId, HttpServletResponse response);
}
