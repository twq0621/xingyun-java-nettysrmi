package test.client;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;

import com.ppsea.srmi.InvokConnector;
import com.ppsea.srmi.RemoteInvoker;
import com.ppsea.srmi.Serializer;
import com.ppsea.srmi.SharedContext;
import com.ppsea.srmi.http.URLConnector;
import com.ppsea.srmi.serialization.BinarySerializer;

public class HttpClient {
	
	SharedContext context;
	
	Serializer serializer;
	
	InvokConnector connector;
	
	RemoteInvoker invoker;
	String host;
	int port;
	public HttpClient(SharedContext context, Serializer serializer,String host, int port) {
		this.context = context;
		this.serializer = serializer;
		this.host = host;
		this.port = port;
		try {
			this.connector = new URLConnector(new URL("http://"+host+":"+port), context);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		invoker = new RemoteInvoker(context, serializer, connector);
	}
	public HttpClient(SharedContext context ,String host, int port){
		this(context, new BinarySerializer(), host, port);
	}
	@SuppressWarnings("unchecked")
	public <T> T getServerInterface(Class<T> clazz){
		return (T) Proxy.newProxyInstance(RemoteInvoker.class.getClassLoader(), new Class[]{clazz}, 
				invoker);
	}
	
	
	
	
	
	
}
