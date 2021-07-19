package com.eports.fileListener;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.watchers.DelayWatcher;

import java.io.File;

/**
 * 文件监听示例
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/19 10:42
 */
public class FileListenerExample {


    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir") + "/src/main/java/com/eports/hot";
        File file = FileUtil.file(filePath);
        fileListener(file);
    }

    private static void fileListener(File file){
        DelayWatcher delayWatcher = new DelayWatcher(new FileWatcher(), 500);
        WatchMonitor watchMonitor = WatchMonitor.createAll(file,delayWatcher);
        //设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
        watchMonitor.setMaxDepth(Integer.MAX_VALUE);
        //启动监听
        watchMonitor.start();
        System.out.println("监听已启动："+file.getPath());
    }


}
