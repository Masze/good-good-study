package com.wj.netty;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ServerSocket {

    public static Charset charset = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(30000));
//            serverSocketChannel.configureBlocking(false);

            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            socketChannel.read(buffer);
            System.out.println(charset.decode(buffer));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
