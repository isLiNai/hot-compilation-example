package com.eports.fileListener;

import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Console;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/19 11:07
 */
public class FileWatcher implements Watcher {
    @Override
    public void onCreate(WatchEvent<?> event, Path currentPath) {
        Object obj = event.context();
        if(ignoreFiles(obj.toString()) ){
            Console.log("创建：{}-> {}", currentPath, obj);
        }
    }

    @Override
    public void onModify(WatchEvent<?> event, Path currentPath) {
        Object obj = event.context();
        // 忽略 以~ 为后缀的文件
        if(ignoreFiles(obj.toString()) ){
            Console.log("修改：{}-> {}", currentPath, obj);
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
        if(fileName.lastIndexOf('~') != -1 ){
            return false;
        }
        return true;
    }
}
