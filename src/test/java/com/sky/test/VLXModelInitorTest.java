package com.sky.test;

import com.sky.vdk.vlx.generator.VLXModelInitor;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-28
 * Time: 下午7:27
 */
public class VLXModelInitorTest {

    @Test
    public void testInitor(){
        VLXModelInitor initor = VLXModelInitor.getInstance();
        System.out.println(initor.getBaseVLX());
    }
}
