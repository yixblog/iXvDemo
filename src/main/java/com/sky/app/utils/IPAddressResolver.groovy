package com.sky.app.utils

/**
 * Created by dyb on 13-12-21.
 */
class IPAddressResolver {
    static String resolveIpAddress(int addressValue){
        String ipAddress = "";
        int mask = 0b1111_1111;
        for(int i in 0..<4){
            int addressPart = addressValue >>> (8*(3-i));
            ipAddress+="${addressPart & mask}."
        }
        ipAddress.substring(0,ipAddress.lastIndexOf('.'));
    }
}
