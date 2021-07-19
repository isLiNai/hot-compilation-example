package com.eports.hot;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * 文件或者文件夹监听
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/19 11:21
 */
public class HotWatcher implements Watcher {
    @Override
    public void onCreate(WatchEvent<?> event, Path currentPath) {
        Object obj = event.context();
        if(ignoreFiles(obj.toString()) ){
            Console.log("创建：{}-> {}", currentPath, obj);
            File file = FileUtil.file(currentPath+"/" + obj.toString());
            HotCodeEngine hotCodeEngine = new HotCodeEngine(file);
            hotCodeEngine.execute();
        }
    }

    @Override
    public void onModify(WatchEvent<?> event, Path currentPath) {
        Object obj = event.context();
        // 忽略 以~ 为后缀的文件
        if(ignoreFiles(obj.toString()) ){
            Console.log("修改：{}-> {}", currentPath, obj);
            File file = FileUtil.file(currentPath+"/" + obj.toString());
            HotCodeEngine hotCodeEngine = new HotCodeEngine(file);
            hotCodeEngine.execute();
        }
    }

    @Override
    public void onDelete(WatchEvent<?> event, Path currentPath) {
        Object obj = event.context();
        if(ignoreFiles(obj.toString())){
            Console.log("删除：{}-> {}", currentPath, obj);
        }
    }

    @Override
    public void onOverflow(WatchEvent<?> event, Path currentPath) {
        Object obj = event.context();
        if(ignoreFiles(obj.toString())){
            Console.log("Overflow：{}-> {}", currentPath, obj);
        }
    }

    private static Boolean ignoreFiles(String fileName){
        int i = fileName.lastIndexOf('.');
        if(i != -1){
            String suffix = fileName.substring(i);
            if(suffix.equals(".java")){
                return true;
            }
            return false;
        }
        return false;
    }
}
