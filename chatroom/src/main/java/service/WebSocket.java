package service;

import eneity.MessageFromClient;
import eneity.MessageToClient;
import myUtil.CommUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description:
 * @Author:Anan
 * @Date:2019/8/19
 */

//@ServerEndpoint:把当前类标记为websocket类
@ServerEndpoint("/websocket")
public class WebSocket {
    //存储所有连接到后端的websocket
    private static CopyOnWriteArraySet<WebSocket> clients = new CopyOnWriteArraySet<>();
    //緩存所有的用戶列表
    private static Map<String,String> names = new ConcurrentHashMap<>();
    //绑定当前websocket会话
    private Session session;
    private String userName;//当前客户端用户名

    //@OnOpen：建立连接时调用
    @OnOpen
    public void onOpen(Session session){
        this.session =session;
        this.userName = session.getQueryString().split("=")[1];
        //将客户端聊天实体保存到clients
        clients.add(this);
        //将当前用户以及sessionID保存到用户列表
        names.put(session.getId(),userName);
        System.out.println("有新的连接，SessionId:"+session.getId()+",用户名为："+userName
                +",当前聊呗共有"+clients.size()+"人");
        //发送给所有用户一个上线通知
        MessageToClient messageToClient = new MessageToClient();
        messageToClient.setContent(userName+"上线了！");
        messageToClient.setNames(names);
        //发送消息
        String jsonStr = CommUtils.objectToJson(messageToClient);
        for(WebSocket webSocket : clients){
            webSocket.sendMsg(jsonStr);
        }
    }
    @OnError
    public void onError(Throwable e){
        System.out.println("websocket连接失败");
        e.printStackTrace();
    }
    @OnMessage
    //群聊:{"msg":"777","type":1}
    //私聊:{"to":"0-","msg":"33333","type":2}
    public void onMessage(String msg){
        MessageFromClient messageFromClient =
                (MessageFromClient) CommUtils.jsonToObject(msg,MessageFromClient.class);
        if(messageFromClient.getType().equals(1)){
            //群聊
            String content = messageFromClient.getMsg();
            MessageToClient messageToClient = new MessageToClient();
            messageToClient.setContent(content);
            messageToClient.setNames(names);
            //广播发送
            for(WebSocket webSocket : clients){
                webSocket.sendMsg(CommUtils.objectToJson(messageToClient));
            }
        }else if(messageFromClient.getType().equals(2)){
            //私聊
            //{"to":"0-","msg":"33333","type":2}
            String content = messageFromClient.getMsg();//私聊信息内容
            String[] tos = messageFromClient.getTo().substring(0,
                    messageFromClient.getTo().length()-1).split("-");
            List<String> list = Arrays.asList(tos);
            //给指定sessionID发送信息
            for(WebSocket webSocket : clients){
                if(list.contains(webSocket.session.getId())
                        && this.session.getId()!= webSocket.session.getId()){
                    MessageToClient messageToClient = new MessageToClient();
                    messageToClient.setContent(userName,content);
                    messageToClient.setNames(names);
                    webSocket.sendMsg(CommUtils.objectToJson(messageToClient));
                }
            }
        }



        System.out.println("浏览器发来的信息为："+msg);
        //群聊
        for(WebSocket webSocket : clients){
            webSocket.sendMsg(msg);
        }
    }
    @OnClose
    public void onClose(){
        System.out.println("有用户退出聊呗");
        clients.remove(this);
        names.remove(session.getId());
        System.out.println("有连接下线了，SessionId:"+session.getId()+",用户名为："+userName
                +",当前聊呗共有"+clients.size()+"人");

        //下线通知
        MessageToClient messageToClient = new MessageToClient();
        messageToClient.setContent(userName+"下线了！");
        messageToClient.setNames(names);
        //发送消息
        String jsonStr = CommUtils.objectToJson(messageToClient);
        for(WebSocket webSocket : clients){
            webSocket.sendMsg(jsonStr);
        }
    }


    public void sendMsg(String msg){
        try {
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
