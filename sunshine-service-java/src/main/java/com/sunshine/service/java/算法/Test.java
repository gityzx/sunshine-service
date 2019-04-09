package com.sunshine.service.java.算法;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 
 * @Date: 2019/3/27 17:51
 * @Auther: yangzhaoxu 
 */
public class Test {

    public static void main(String[] args) {

        List<Integer> arr = new ArrayList<>();
        arr.add(12);
        arr.add(12);

        arr.add(13);
        arr.add(13);

        arr.add(120);
        arr.add(13000009);




        int n = 0;
        for (Integer i : arr) {
            n ^= i;
        }
        int m=0;
        if ((n & 1) == 0) {
            m =  (int) (Math.log((double) (n ^ (n & (n - 1))))/ Math.log(2d));
        }
        int a = 0;
        int b = 0;
        for (Integer i : arr) {
            int p = i>>m;
            if ((p & 1) != 0) {
                a ^= i;
            } else {
                b ^= i;
            }
        }
        System.out.println("分别为：[" + a + "," + b + "]");
    }
}
