package com.github.it18monkey.nio;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wanghaohao on 2018/8/10.
 */
public class FileChannelTest {
    public static void main(String[] args) throws Exception {
        FileChannelTest test = new FileChannelTest();
//        test.readFile();
//        test.writeFile();
        test.copyFile();
    }

    public void readFile() throws Exception {
        FileInputStream inputStream = new FileInputStream(getClass().getClassLoader().getResource("data.txt").getFile());
        FileChannel fileChannel = inputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        fileChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        inputStream.close();
    }

    public void writeFile() throws Exception {
        FileOutputStream outputStream = new FileOutputStream(getClass().getClassLoader().getResource("data.txt").getFile());
        FileChannel fileChannel = outputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("随机写入一些内容到 Buffer 中".getBytes());
        // Buffer 切换为读模式
        buffer.flip();
        while (buffer.hasRemaining()) {
            // 将 Buffer 中的内容写入文件
            fileChannel.write(buffer);
        }
        outputStream.close();
    }

    public void copyFile() throws Exception {
        FileInputStream inputStream = new FileInputStream(getClass().getClassLoader().getResource("in.txt").getFile());
        FileChannel inputStreamChannel = inputStream.getChannel();
        FileOutputStream outputStream = new FileOutputStream((getClass().getClassLoader().getResource("out.txt").getFile()));
        FileChannel outputStreamChannel = outputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        while (true) {
            int r = inputStreamChannel.read(byteBuffer);
            if (r == -1) {
                break;
            }
            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);
            byteBuffer.clear();
        }
    }
}
