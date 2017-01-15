package com.spider.wechat;

public class Main {

	/**

微信公众号的抓取:
- 主页是：http://weixin.sogou.com/weixin?type=2&query=%E9%9B%A8%E5%AE%AB%E7%90%B4%E9%9F%B3&ie=utf8&_sug_=y&_sug_type_=&w=01019900&sut=5109&sst0=1484409344685&lkt=1%2C1484409344577%2C1484409344577
	- type=1：公众号  type=2：文章
	- query是公众号或者文章关键字

	 */
	
	private static final String rootUrl = "http://weixin.sogou.com/weixin?type={type}&query={query}&ie=utf8&_sug_=y&_sug_type_=&w=01019900&sut=5109&sst0=1484409344685&lkt=1%2C1484409344577%2C1484409344577";
	private static final int TYPE_ARTICAL = 2;
	private static final int TYPE_ACCOUT = 1;
	
	public static void main(String[] args) {
		String[] keywords =  {"美女"}; //{"美女", "美乳", "嫩模"};
		for(String keyword: keywords){
			String url = rootUrl.replace("{query}", keyword).replace("{type}", TYPE_ARTICAL + "");
			WeChatRootPage page = new WeChatRootPage(url);
			page.run();
		}
	}
	
}
