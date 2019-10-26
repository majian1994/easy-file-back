package com.pjjlt.easyfile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试类
 * */
public class MyTest {
    public static void main(String[] args) {
        class A{
            String name;

            public String getName() {
                return name;
            }

            public A setName(String name) {
                this.name = name;
                return this;
            }
        }
        List<A> firstList = new ArrayList<>();
        A a1 = new A().setName("a1");
        A a2 = new A().setName("a2");
        A a3 = new A().setName("a3");
        A a4 = new A().setName("a1");
        firstList.add(a1);
        firstList.add(a2);
        firstList.add(a3);
        List<A> secondList = firstList;
        List<A> thirdList = new ArrayList<>();
        thirdList.add(a4);
        secondList.removeAll(thirdList);
        for (A aaa:secondList){
            System.out.println(aaa.name);
        }
        for (A aaa:firstList){
            System.out.println(aaa.name);
        }
        System.out.println(new BigDecimal(99).compareTo(new BigDecimal(10000)));
    }
}
