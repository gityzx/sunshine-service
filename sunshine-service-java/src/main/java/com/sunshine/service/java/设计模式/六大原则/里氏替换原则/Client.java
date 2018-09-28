package com.sunshine.service.java.设计模式.六大原则.里氏替换原则;

import java.util.HashMap;

/**
 * @Description:
 * @Date: 2018/9/28 09:38
 * @Auther: yangzhaoxu
 */
public class Client {
    public static void main(String[] args) {
        {
            Father f = new Father();
            HashMap map = new HashMap();
            f.invoke(map);

        }

        {
            Son f = new Son();
            HashMap map = new HashMap();
            f.invoke(map);


        }


        {
            Father f = new Son();
            HashMap map = new HashMap();
            f.invoke(map);


        }

//        {
//            IFather s = new Son();
//            HashMap map = new HashMap();
//            ((Son) s).invoke(map);
//            s.invoke(map);
//            ((Son) s).invoke(null, null, null);
//            ((Son) s).invokefefe(null);
//
//
//        }


    }
}
