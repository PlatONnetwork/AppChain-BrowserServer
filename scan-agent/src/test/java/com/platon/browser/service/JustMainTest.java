package com.platon.browser.service;

import com.platon.bech32.Bech32;

public class JustMainTest {

    public static void main(String[] args){
       System.out.println(Bech32.addressEncode("LAT", "0x8e816EfcFB5306b8E974A59055c60121e7ad4AC6"));
    }
}
