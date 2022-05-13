package com.example.websocket.config;


import com.example.websocket.model.InMessage;
import com.example.websocket.model.OutMessage;
import com.example.websocket.tools.JSONChange;
import com.example.websocket.tools.TimeFormatter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/ws/{userId}")
@Component
public class WebSocketEndpoint {


    @OnOpen
    @ResponseBody
    public String onOpen(@PathParam("userId") String userId, Session session) throws IOException {

//        if (SessionPool.sessions.containsKey(userId)) {
//
//            Session outSession = SessionPool.sessions.get(userId);
//            outSession.getAsyncRemote().sendText("异地登录，你被挤下去啦");
//            SessionPool.close(outSession.getId());
//            outSession.close();
//        }

        SessionPool.sessions.put(userId, session);
        System.out.println(userId + " login");

        int online = SessionPool.sessions.size();
        OutMessage out = new OutMessage(TimeFormatter.formatTime(), online , userId, 1, "", 0, 0);
        String outJson = JSONChange.objToJson(out);
        SessionPool.sendMessage(outJson);
        return "succsss";
    }

   @OnClose
   public void onClose(Session session) throws IOException {
        String userId = null;
        for (String sId : SessionPool.sessions.keySet()) {
            Session ss = SessionPool.sessions.get(sId);
            if (session.equals(ss)) {
                userId = sId;
                break;
            }
        }

       SessionPool.close(session.getId());
       session.close();
       UserIdPool.User.remove(userId);

       System.out.println(userId + " have closed");

       int online = SessionPool.sessions.size();
        OutMessage out = new OutMessage(TimeFormatter.formatTime(),online , userId, 0, "", 0, 0);
        String outJson = JSONChange.objToJson(out);
        SessionPool.sendMessage(outJson);

   }

   @OnMessage
   public void onMessage(String inJson, Session session) throws JsonProcessingException {

        if (inJson.equals("ping")) {
            session.getAsyncRemote().sendText("pong");
            return;
        }
        InMessage in = (InMessage) JSONChange.jsonToObj(inJson, new InMessage());


        int online = SessionPool.sessions.size();
        OutMessage out = new OutMessage(TimeFormatter.formatTime(), online, in.from, 2, in.msg, 0, 0);


        String outJson = JSONChange.objToJson(out);
        SessionPool.sendMessage(outJson);
   }
}
