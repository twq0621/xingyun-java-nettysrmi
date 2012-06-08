package test.server;

import java.lang.reflect.Method;

import test.share.GameException;
import test.share.Header;
import test.share.Player;
import test.share.PlayerInterface;
import test.share.SharedDefine;

import com.ppsea.SrmiNettyServer;
import com.ppsea.srmi.Filter;
import com.ppsea.srmi.HeaderProvider;
import com.ppsea.srmi.Log;
import com.ppsea.srmi.NativeMethod;
import com.ppsea.srmi.ServiceException;
import com.ppsea.srmi.SharedContext;

public class Main {
	public static void main(String[] args) {
		//初始化一个Server
		SrmiNettyServer server = new SrmiNettyServer(SharedDefine.context, "127.0.0.1",8899);
		
		
		//设置返回头的Provider 
		SharedDefine.context.setHeaderProvider(new HeaderProvider.Abstract() {
			@Override
			public Object provideResponseHeader(SharedContext context,
					Method method, Object requestHeader, Object[] args,
					boolean isException, Object result) {
				//如果是登陆方调用成功则返回一个新的Header
				if(method.getName().equals("login")&&!isException){
					Player player = (Player) result;
					Log.info("new Header");
					return new Header("123",player.getId());
				}
				return requestHeader;
			}
		});
		
		server.getInvoker().setImplements(PlayerInterface.class, new PlayerInterface() {
			@Override
			public Player login(String id, String pwd) {
				if(pwd.equals("123")){
					return new Player(id,pwd);
				}
				throw new GameException();
			}
			
			@Override
			public Player getInfo(String id) {
				return new Player(id,"hepp");
			}
			@Override
			public Player create(String name) {
				return new Player("1",name);
			}
		});
		//为所有方法添加一个过滤器
		server.getInvoker().addFilter(new Filter.Abstract(){
			@Override
			public void readyInvok(int methodIndex, NativeMethod method) {
				Header header = (Header) method.getRequestHeader();
				
				if(!method.getMethod().getName().equals("login")&&header==null){
					throw new ServiceException("非法连接。。。");
				}
			}
		});

		server.startup();
		
		
		
	}
}
