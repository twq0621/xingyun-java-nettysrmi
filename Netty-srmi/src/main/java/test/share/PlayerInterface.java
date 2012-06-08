package test.share;

public interface PlayerInterface {
	/**
	 * 登陆游戏
	 * @param id
	 * @param pwd
	 * @return
	 */
	Player login(String id,String pwd);
	/**
	 * 获取玩家信息
	 * @param id
	 * @return
	 */
	Player getInfo(String id);
	
	Player create(String name);
}
