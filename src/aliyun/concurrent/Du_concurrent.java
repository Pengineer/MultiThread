package com.aliyun.concurrent;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * 多线程模拟实现"du -s"命令的功能
 * Created by xingyu.pl on 2016/7/17.
 */
public class Du_concurrent {
    // 定义一个二元组，表示当前目录下的文件总大小和子目录列表
    static class Dir {
        final protected long size;
        final protected List<File> subDirs;
        public Dir(long size, List<File> subDirs) {
            this.size = size;
            this.subDirs = Collections.unmodifiableList(subDirs);
        }
    }
    // 获取当前目录的二元组
    public static Dir getTotalAndSubDir(File file) {
        long total = 0;
        final List<File> dirs = new ArrayList<File>();
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    if (f.isFile()) {
                        total += f.length();
                    } else {
                        dirs.add(f);
                    }
                }
            }
        }
        return new Dir(total, dirs);
    }

    public static long count(File dir) throws Exception {
        final ExecutorService pool =Executors.newFixedThreadPool(100);
        long total = 0;
        final List<File> dirs = new ArrayList<File>();
        dirs.add(dir);

        try {
            // 非递归方式的层级遍历
            while (!dirs.isEmpty()) {
                List<Future<Dir>> futureResults = new ArrayList<Future<Dir>>();
                for (final File f : dirs) {
                    futureResults.add(pool.submit(new Callable<Dir>() { // 向线程池中提交任务，任务加入队列后submit就返回。
                        public Dir call() {
                            return getTotalAndSubDir(f);
                        }
                    }));
                }
                dirs.clear();
                for (Future<Dir> futureResult : futureResults) {
                    Dir result = futureResult.get(100, TimeUnit.SECONDS); // 如果任务没有执行完，get方法将block
                    dirs.addAll(result.subDirs);
                    total += result.size;
                }
            }
        } finally {
            pool.shutdown();
        }
        return total;
    }
}
