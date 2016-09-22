package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;

/**
 * Created by lujunqiu on 2016/9/6.
 * 客户端需要读取键盘输入发送给服务器以及监听服务器的发送来的消息，有2个监听事件同时进行，需要采用2个线程来并行处理
 */
public class ClientThread extends Thread{
    private CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
    private CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
    private Selector selector = null;
    private SocketChannel channel = null;
    private SelectionKey clientKey = null;
    private String username = null;

    public ClientThread(String name) {//构造函数中启动客户端与服务器的连接，并将用户名发送给服务器
        try {
            selector = Selector.open();
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            clientKey = channel.register(selector, SelectionKey.OP_CONNECT);//注册连接服务器事件
            channel.connect(new InetSocketAddress("localhost",12345));//绑定端口
            this.username = name;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {//处理与服务器的交互事件
        try {
            while (true) {
                selector.select();//监听事件
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();//事件驱动列表

                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    if (key.isConnectable()) {
                        SocketChannel channel1 = (SocketChannel) key.channel();
                        if (channel1.isConnectionPending()) {
                            channel1.finishConnect();//完成连接
                        }
                        channel1.register(selector, SelectionKey.OP_READ);//连接完成之后注册读取事件
                        System.out.println("连接服务器成功！");

                        try {//利用当前的Channel写入数据或者利用send函数来写入数据到Channel中
                            channel1.write(encoder.encode(CharBuffer.wrap(("username=" + this.username))));//发送消息
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                      send("username=" + this.username);//连接完成同时发送该客户端的用户名
                    }else if (key.isReadable()) {
                        SocketChannel channel1 = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(128);
                        channel1.read(buffer);//将Channel中的字节数据读入缓存区
                        buffer.flip();//缓存区从写模式切换到读模式
                        String msg = decoder.decode(buffer).toString();//解码输出数据
                        System.out.println("收到：" + msg);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                selector.close();
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String msg) {
        try {
            SocketChannel socketChannel = (SocketChannel) clientKey.channel();//获取当前客户端的SocketChannel
            socketChannel.write(encoder.encode(CharBuffer.wrap(msg)));//发送消息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            selector.close();
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}