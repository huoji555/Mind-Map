package com.hwj.webSocket;

import java.io.IOException;  
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;  
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;  

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
  
/** 
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端, 
 *                 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端 
 */  
@ServerEndpoint(value = "/WebSocketManytoMany/{param}")
@Component
public class WebSocketManytoMany {
	
	 //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     private static int onlineCount = 0;
 
     //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
     private static CopyOnWriteArraySet<WebSocketManytoMany> webSocketSet = new CopyOnWriteArraySet<WebSocketManytoMany>();
 
     //使用map来收集session，key为roomName，value为同一个房间的用户集合
     private static final Map<String, Set<Session>> rooms = new ConcurrentHashMap();
     //与某个客户端的连接会话，需要通过它来给客户端发送数据
     private Session session;
     private String room;
     private String role;
    
  
     /**
      * 连接建立成功调用的方法
      * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
      */
     @OnOpen
     public void onOpen(@PathParam("param") String param,Session session){
    	 
         this.session = session;
         String[] arr = param.split(",");  
         this.role = arr[0];			//用户标识  
         this.room = arr[1];			//房间标识 

         addOnlineCount();				//在线数加1
         System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
         
         if (!rooms.containsKey(arr[1])) {
             // 创建房间不存在时，创建房间
             Set<Session> room = new HashSet<>();
             // 添加用户
             room.add(session);
             rooms.put(arr[1], room);
         } else {
             // 房间已存在，直接添加用户到相应的房间
             rooms.get(arr[1]).add(session);
         }
         
     }
     
  
     /**
      * 连接关闭调用的方法
      */
     @OnClose
     public void disConnect(@PathParam("param") String param, Session session) {
    	 
    	 String[] arr = param.split(",");  
    	 rooms.get(arr[1]).remove(session);
         subOnlineCount();           //在线数减1
         System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
     }  
  
     
     /**
      * 收到客户端消息后调用的方法
      * @param message 客户端发送过来的消息
      * @param session 可选的参数
      */
     @OnMessage
     public void onMessage(String message, Session session) {
         
         System.out.println("来自客户端的消息:" + message); 
         JSONObject json = JSONObject.parseObject(message);
         String string = (String) json.get("message");  //需要发送的信息  
         String role = (String) json.get("role");  //发送的用户
         String room = (String) json.get("room");  //发送的房间号(每个知识图谱的rootid)
         
         try {
			sendMessage(role, room, string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
     }
  
     
     
    /** 
     * 发生错误时调用 
     *  
     * @param session 
     * @param error 
     */  
    @OnError  
    public void onError(Session session, Throwable error) {  
        System.out.println("发生错误");  
        error.printStackTrace();  
    }  
  
    
    //向客户端发送消息   
    public void sendMessage(String role, String room, String string) throws IOException{
    	
    	System.out.println("====sendMessage=========");  
		for (Session session : rooms.get(room)) {
	        session.getBasicRemote().sendText(role+":"+string);
	    }
    	
     }
    
    public static synchronized int getOnlineCount() {  
        return onlineCount;  
    }  
  
    public static synchronized void addOnlineCount() {  
        WebSocketManytoMany.onlineCount++;  
    }  
  
    public static synchronized void subOnlineCount() {  
        WebSocketManytoMany.onlineCount--;  
    }  
}
