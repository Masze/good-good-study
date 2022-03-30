package com.wj.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ClientSocket {
    public static Charset charset = StandardCharsets.UTF_8;

    public static void main(String[] args) throws IOException, InterruptedException {
        try (SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 30000))) {

            Socket socket = channel.socket();
            while (true){

            }
        }
    }
}
