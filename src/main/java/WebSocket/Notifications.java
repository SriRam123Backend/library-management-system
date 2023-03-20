package WebSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/Notification")
public class Notifications {

	private static List<Session> Users = new ArrayList<Session>();
	@OnOpen
	public void open(Session notifications)
	{
		Users.add(notifications);
	}
	
	@OnClose
	public void close(Session notifications)
	{
		Users.remove(notifications);
	}
	
	@OnMessage
	public void notification(String value,Session session)
	{
		System.out.println(value+" "+Users.size());
		Users.forEach(n->{
			try {
				n.getBasicRemote().sendText(value);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
}
