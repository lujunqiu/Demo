package NIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lujunqiu on 2016/9/6.
 * 客户端需要读取键盘输入发送给服务器以及监听服务器的发送来的消息，有2个监听事件同时进行，需要采用2个线程来并行处理
 */
public class ChatClient {
    public static void main(String[] args) {
        String username = null;
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            username = buffer.readLine();//从键盘读取用户名
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientThread client = new ClientThread(username);//创建客户端线程连接服务器
        client.start();//启动对服务器的监听

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));//缓存键盘输入的数据
        try {
            String readLine =  null;
            while ((readLine = in.readLine()) != null) {//从键盘读入数据
                if (readLine.equals("bye")) {
                    client.close();
                    System.exit(0);
                }
                client.send(username + ":" + readLine);//按照服务器的要求格式输入数据到Channel中
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}