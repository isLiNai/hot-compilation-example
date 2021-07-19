package com.eports.hot;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/19 11:14
 */
public class HotEngine {

    public static void main(String[] args) {
        HotService hotService = new HotService(System.getProperty("user.dir"),5000);
        hotService.execute();
    }



}
