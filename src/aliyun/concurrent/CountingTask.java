package com.aliyun.concurrent;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Fork-Join框架：模拟实现"du -s"命令的功能
 * Created by xingyu.pl on 2016/7/18.
 *
 * @since 1.7
 */

public class CountingTask extends RecursiveTask<Long> {
    private Path dir;

    public CountingTask(Path dir) {
        this.dir = dir;
    }

    @Override
    protected Long compute() {
        long size =0;
        List<CountingTask> subTasks = new ArrayList<CountingTask>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir)) {
            for (Path subPath : ds) {
                if (Files.isDirectory(subPath, LinkOption.NOFOLLOW_LINKS)) {
                    subTasks.add(new CountingTask(subPath));
                } else {
                    long s = Files.size(subPath);
                    size += s;
                }
            }
            if (!subTasks.isEmpty()) {
                for (CountingTask subTask : invokeAll(subTasks)) {
                    long s = subTask.join();
                    size += s;
                }
            }
        } catch (IOException ex) {
            return 0L;
        }
        return size;
    }
}
