package test.share;

import com.ppsea.srmi.SharedContext;

public class SharedDefine {
	public static final SharedContext context = new SharedContext();
	static{
		//
		context.addClass(Header.class,Player.class);
		
		context.addClass(GameException.class);
		
		context.addInterface(PlayerInterface.class);
		
		context.init();
		
	}
}
