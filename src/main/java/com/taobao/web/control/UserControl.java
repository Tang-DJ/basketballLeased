package com.taobao.web.control;

import com.taobao.dao.databasesDaoImpl.RoleDaoImpl;
import com.taobao.dao.databasesDaoImpl.UserDaoImpl;
import com.taobao.dao.entity.Role;
import com.taobao.dao.entity.User;
import com.taobao.service.sms.SendSMS;
import com.taobao.utils.sign.MD5;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by zhangpe0312@qq.com on 2018/2/26.
 */
@Controller
public class UserControl {

    private static Logger logger = Logger.getLogger(UserControl.class);

    @Autowired
    private MD5 md5;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private RoleDaoImpl roleDao;

    @Autowired
    private SendSMS sendSMS;

    /**
     * 登陆采用Get方法
     *
     * @return 返回登陆结果
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public @ResponseBody
    Map<String, String> login(HttpServletRequest req) {
        Map<String, String> map = new HashMap<>();

        try {
            String user = req.getParameter("user");
            String password = req.getParameter("password");

            int resutl = userDao.findUserBySchoolID(user, password);

            map.put("data", resutl + "");

            logger.info("用户 " + user + " 登陆登陆代码位 " + resutl);


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("url格式错误 " + req.getRequestURL() + " " + e);
            map.put("data", "3");
        }
        return map;
    }

    /**
     * 注册账号接口
     * 注册的都是普通用户，所以默认添加为普通用户，有更改
     * 请通过后台管理员修改
     *
     * @param req
     * @return 返回请求结果
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> register(HttpServletRequest req) {
        Map<String, String> map = new HashMap<>();

        try {

            String password = req.getParameter("password");
            String phone = req.getParameter("password");
            String schoolID = req.getParameter("user");
            String roleName = req.getParameter("role");
            //生成用户信息
            User user = new User();
            //使用加密码
            user.setPassword(password);
            user.setRole(roleDao.findRoleByName(roleName));
            user.setCreateTime(new Date());
            user.setPhone(phone);
            user.setSchoolID(schoolID);
            user.setMoney(0);
            userDao.save(user);

            logger.info("用户 " + schoolID + " 注册成功，角色为 " + roleName);
            map.put("data", "0");
        } catch (Exception e) {
            logger.error("用户注册失败 " + e);
            map.put("data", "1");
        }
        return map;
    }

    /**
     * 更新密码
     *
     * @param req
     * @return 返回修改结果
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, String> updatePasswordByOldPassword(HttpServletRequest req) {
        Map<String, String> map = new HashMap<>();

        try {
            String user = req.getParameter("user");
            User haveUser = userDao.findUserBySchoolID(user);

            if (haveUser == null) {
                map.put("data", "2");
                logger.info("没有找到用户 " + user + " 更改密码失败");
                return map;
            }

            String newPassword = req.getParameter("password");

            haveUser.setPassword(newPassword);
            userDao.update(haveUser);

            map.put("data", "0");

            logger.info("用户 " + user + " 更改密码成功");
        } catch (Exception e) {
            logger.error("url格式错误 " + req.getRequestURL() + " " + e);
            map.put("data", "3");
        }
        return map;
    }


    /**
     * 发送短信验证码
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> sendSMS(HttpServletRequest req) {
        Map<String, String> map = new HashMap<>();

        try {
            String user = req.getParameter("user");
            //查找这个用户得到手机号码
            User haveUser = userDao.findUserBySchoolID(user);
            if (haveUser == null) {
                map.put("data", "2");
                logger.error("用户 " + user + "不存在，发送信息失败");
                return map;
            }
            String phone = haveUser.getPhone();
            //发送手机号码
            map = sendSMS.sendVerificationCode(phone);
            map.put("data", "0");
            logger.info("用户 " + user + " 申请验证码 " + map.get("code") + " 成功");
        } catch (Exception e) {
            logger.error("请求url异常 " + req.getRequestURL());
            map.put("data", "2");
            return map;
        }
        return map;
    }

}
