package com.eports.hot;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.watchers.DelayWatcher;
import com.eports.fileListener.FileWatcher;

import java.io.File;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/19 11:17
 */
public class HotService {
    // 文件或文件夹监听路径
    private String path;
    // 延迟(毫秒)
    private long delay;

    public HotService(String path, long delay) {
        this.path = path;
        this.delay = delay;
    }

    public void execute(){
        File file = FileUtil.file(path);

        // 文件监听 延迟
        DelayWatcher delayWatcher = new DelayWatcher(new HotWatcher(), delay);
        WatchMonitor watchMonitor = WatchMonitor.createAll(file,delayWatcher);
        //设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
        watchMonitor.setMaxDepth(Integer.MAX_VALUE);
        //启动监听
        watchMonitor.start();
        System.out.println("监听已启动："+file.getPath());
    }
}
