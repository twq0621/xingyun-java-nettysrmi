package test.client;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import com.ppsea.srmi.HeaderProvider;
import com.ppsea.srmi.SharedContext;

import test.share.Player;
import test.share.PlayerInterface;
import test.share.SharedDefine;

public class Client {
	public static void main(String[] args) throws MalformedURLException {
//		URLConnector connector  = new URLConnector(new URL("http://127.0.0.1:8899"),SharedDefine.context);
		HttpClient client = new HttpClient(SharedDefine.context, "127.0.0.1", 8899);
		SharedDefine.context.setHeaderProvider(new HeaderProvider.Abstract() {
			@Override
			public Object provideRequestHeader(SharedContext context, Method method,
					Object responseHeader, Object[] args) {
				return responseHeader;
			}
		});
		PlayerInterface server = client.getServerInterface(PlayerInterface.class);
		Player create = server.create("cyg");
		Player ply = server.login("cyg", "123");
		Player ply1 = server.getInfo("123");
		System.out.println(ply1.getName());
		
		
		
	}
}	
