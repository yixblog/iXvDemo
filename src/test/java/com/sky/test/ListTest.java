package com.sky.test;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-12-4
 * Time: 上午9:38
 */
public class ListTest {

    @Test
    public void test() {
        List<String> listA = new LinkedList<String>();
        for (int i = 0; i < 1000000; i++) {
            listA.add(RandomStringUtils.random(5, true, true));
        }
        List<String> listB = new ArrayList<String>(listA);
        System.out.println("for..each test");
        long time1 = System.currentTimeMillis();
        for (String s : listA) {
        }
        long time2 = System.currentTimeMillis();
        for (String s : listB) {
        }
        long time3 = System.currentTimeMillis();
        System.out.println("LinkedList:" + (time2 - time1));
        System.out.println("ArrayList:" + (time3 - time2));
    }

    @Test
    public void testInsert() {
        List<String> listA = new LinkedList<String>();
        List<String> listB = new ArrayList<String>();
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            listA.add(listA.size()/2, "aaa");
        }
        long time2 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            listB.add(listB.size()/2, "aaa");
        }
        long time3 = System.currentTimeMillis();

        System.out.println("LinkedList:" + (time2 - time1));
        System.out.println("ArrayList:" + (time3 - time2));
    }
}
