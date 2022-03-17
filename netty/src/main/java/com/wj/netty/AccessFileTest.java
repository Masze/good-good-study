package com.wj.netty;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class AccessFileTest {

    /**
     * 顺序写
     */
    public static long fileWrite(String filePath, String content, int index) {
        File file = new File(filePath);
        //  操作系统提供的一个内存映射的机制的类
        MappedByteBuffer map;
        try (RandomAccessFile randomAccessTargetFile = new RandomAccessFile(file, "rw")) {

            FileChannel channel = randomAccessTargetFile.getChannel();
            map = channel.map(FileChannel.MapMode.READ_WRITE, 0, (long) 1024 * 1024 * 1024);
            map.position(index);
            map.put(content.getBytes());
            return map.position();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 顺序读
     */
    public static String fileRead(String filePath, long index) {
        File file = new File(filePath);
        //  操作系统提供的一个内存映射的机制的类
        MappedByteBuffer map;
        try (RandomAccessFile randomAccessTargetFile = new RandomAccessFile(file, "rw")) {
            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, index);
            byte[] byteArr = new byte[10 * 1024];
            map.get(byteArr, 0, (int) index);
            return new String(byteArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
