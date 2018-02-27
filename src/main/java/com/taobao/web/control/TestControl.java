package com.taobao.web.control;

import com.taobao.dao.databasesDaoImpl.SchoolCardDaoImpl;
import com.taobao.dao.databasesDaoImpl.UserDaoImpl;
import com.taobao.dao.entity.SchoolCard;
import com.taobao.dao.entity.User;
import com.taobao.utils.sign.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by zhangpe0312@qq.com on 2018/2/27.
 * <p>
 * 控制金额的扣除、充值、返回等操作
 */
@Controller
public class TestControl {

    @Autowired
    private SchoolCardDaoImpl schoolCardDao;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    MD5 md5;

    /**
     * 添加校园卡
     * @return
     */
    @RequestMapping(value = "/testAddSchool" , method = RequestMethod.GET)
    public @ResponseBody Map<String,String> testAddEntity() {

        Map<String,String> map = new HashMap<>();
        SchoolCard schoolCard = new SchoolCard();
        schoolCard.setMoney(10);
        schoolCard.setPassword(md5.encryption("123456"));
        schoolCard.setSchoolID("123456789111");
        schoolCardDao.save(schoolCard);

        map.put("data","添加成功");
        return map;
    }

    /**
     * 添加用户
     * @return
     */
    @RequestMapping(value = "/testAddUser" , method = RequestMethod.GET)
    public @ResponseBody Map<String,String> testAddUser() {

        Map<String,String> map = new HashMap<>();

        User user = new User();
        user.setSchoolID("123456789111");
        user.setPhone("18203085236");
        user.setCreateTime(new Date());
        user.setPassword(md5.encryption("123456"));
        userDao.save(user);

        map.put("data","添加成功");
        return map;
    }


}