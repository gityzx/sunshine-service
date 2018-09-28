package com.sunshine.service.java.设计模式.六大原则.里氏替换原则;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Date: 2018/9/28 09:38
 * @Auther: yangzhaoxu
 */
public class Son extends Father {

    @Override
    public List invoke(HashMap map) {
        System.out.println("Son执行1!");
        return null;
    }



}
