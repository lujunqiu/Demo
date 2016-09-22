package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by Lujunqiu on 2016/9/6.
 * 实现基于NIO的简单网络通信聊天工具(多客户端互发消息，服务器中继)
 */
public class ChatServer {
    public static void main(String[] args){
        Hashtable<String,SocketChannel> clintList = new Hashtable<String, SocketChannel>();   //客户端用户列表，用于转发客户端消息
        Selector selector = null;
        ServerSocketChannel serverSocketChannel = null;

        try {
            selector = Selector.open();//创建一个选择器
            serverSocketChannel = ServerSocketChannel.open();//创建服务端Socket Channel
            serverSocketChannel.socket().bind(new InetSocketAddress(12345));//绑定监听端口
            serverSocketChannel.configureBlocking(false);//设置为非阻塞模式
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//注册事件类型为：SelectionKey.OP_ACCEPT

            while (true) {//监听注册的事件
                selector.select();//监听
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();//可处理事件列表

                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();//每次处理完一个事件要删除当前事件，以免多次处理

                    if (key.isAcceptable()) {//判断事件类型
                        ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();//获取当前事件的对应的Channel
                        SocketChannel socketChannel = serverSocketChannel1.accept();//通过套接字连接获取客户端的SocketChannel对象
                        socketChannel.configureBlocking(false);//设置为非阻塞模式
                        socketChannel.register(selector, SelectionKey.OP_READ);//注册事件类型：SelectionKey.OP_READ

                        System.out.println("客户端连接" + socketChannel.socket().getInetAddress().getHostName() + socketChannel.socket().getLocalPort());
                    }else if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();//获取当前事件的对应的Channel
                        ByteBuffer buffer = ByteBuffer.allocate(128);//创建缓存区来接受数据
                        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();//创建解码器来解码输出
                        socketChannel.read(buffer);//将Channel中的数据写入缓存区
                        buffer.flip();//将buffer从写模式切换至读模式
                        String msg = decoder.decode(buffer).toString();//解码

                        System.out.println("收到信息：" + msg);

                        if (msg.startsWith("username=")) {//分析消息，判断是否第一次连接服务器
                            String username = msg.replaceAll("username=", "");//得到用户名
                            clintList.put(username, socketChannel);//将用户名和该用户的SocketChannel通道保持到用户列表
                        }else {//服务器转发该消息给对应的用户
                            String[] temp = msg.split(":");
                            if (temp.length == 3) {
                                String from = temp[0];//获取消息的发送者
                                String to = temp[1];//获取消息的接受者
                                String content = temp[2];//获取消息的内容

                                if (clintList.containsKey(to)) {
                                    CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();//编码器，编码后进行传输
                                    clintList.get(to).write(encoder.encode(CharBuffer.wrap(from + ":" + content)));//将发送的数据写入该用户的SocketChannel
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocketChannel.close();
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}