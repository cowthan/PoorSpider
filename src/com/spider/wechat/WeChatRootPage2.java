package com.spider.wechat;

import java.util.ArrayList;
import java.util.List;

import org.ayo.lang.JsonUtilsUseFast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ayo.spider.JsoupPage;

public class WeChatRootPage2 extends JsoupPage{

	public WeChatRootPage2(String url) {
		super(url);
		
		///每页搜10条，以li呈现，id是li_id，index是0到9
	}

	private static final String li_id_tmpl = "sogou_vr_11002601_box_{index}";
	private static final String title_a_id_tmpl = "sogou_vr_11002601_title_{index}";
	private static final String desc_p_id_tmpl = "sogou_vr_11002601_summary_{index}";
	private static final String cover_img_a_tmpl = "sogou_vr_11002601_img_";
	private static final String account_a_tmpl = "sogou_vr_11002601_account_{index}";
	
	
	private List<WeChatTop> tops = new ArrayList<>();
	
	public List<WeChatTop> getCrawedTops(){
		return tops;
	}
	
	@Override
	protected void tryToRead(Document doc) {

		for(int i = 0; i < 10; i++){
			String id = li_id_tmpl.replace("{index}", i + "");
			Element li = doc.getElementById(id);
			if(li != null){
				try {
					readLi(li, i);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
		System.out.println(JsonUtilsUseFast.toJson(tops));
	}
	
	private void readLi(Element li, int index){
		/*
		<li id="sogou_vr_11002601_box_1" d="ab735a258a90e8e1-6bee54fcbd896b2a-90f28c0387d5449381b323d994ce7275"> 
		   <div class="img-box"> 
		    <a data-z="art" target="_blank" id="sogou_vr_11002601_img_1" 
		    	href="http://mp.weixin.qq.com/s?src=3&amp;timestamp=1484458048&amp;ver=1&amp;signature=hQ7wOk13cgijd33scmqH5T7LUGbkXEVPR0rjBRsSmxkQlWOO67Ved*j-sV7-Lzlno-38K0s-ly55fA-RaGenhXak6JRy6aGzHRj2y5-YJZk4CivGzEL4qLY1wv9K9FchwmxP*UfQtyfZ0IV*qTJy-aEXAFqaSr0dZpQ90SnRI-s=" 
		    	uigs="main_toweixin_article_single_image_1">
		    	<img src="http://img01.sogoucdn.com/net/a/04/link?appid=100520033&amp;url=http://mmbiz.qpic.cn/mmbiz_png/fTA1TdjIdZCqwqwbQytMH2QDEjuE4dBUXe653DNlDH4LEIKIbaeF4MwvK4gBxMTz3RHUke9L85oXa5jibht5icEw/0?wx_fmt=png" onload="resizeImage(this,140,105)" onerror="this.parentNode.parentNode.style.display=&quot;none&quot;;this.onerror=null;" />
		    </a> 
		   </div> 
		   <div class="txt-box"> 
		    <h3> <a target="_blank" href="http://mp.weixin.qq.com/s?src=3&amp;timestamp=1484458048&amp;ver=1&amp;signature=hQ7wOk13cgijd33scmqH5T7LUGbkXEVPR0rjBRsSmxkQlWOO67Ved*j-sV7-Lzlno-38K0s-ly55fA-RaGenhXak6JRy6aGzHRj2y5-YJZk4CivGzEL4qLY1wv9K9FchwmxP*UfQtyfZ0IV*qTJy-aEXAFqaSr0dZpQ90SnRI-s=" 
		    		id="sogou_vr_11002601_title_1" 
		    		uigs="main_toweixin_article_title_1">
		    			<em><!--red_beg-->美女<!--red_end--></em>裙子下的秘密,看完震精了【小黄猫私藏】
		    	</a> 
		    </h3> 
		    <p class="txt-info" id="sogou_vr_11002601_summary_1">NO.1小黄猫神吐槽裙子底下竟然还有一张脸我想知道你的胖次在哪里腿太细了下边的毛看起来比较短啊NO.2小黄猫神吐槽你这样会没...</p> 
		    <div class="s-p" t="1484450511"> 
		     <a class="account" target="_blank" id="sogou_vr_11002601_account_1" i="oIWsFt-MhOyA-fFifnYGTXmdxyyU" href="http://mp.weixin.qq.com/profile?src=3&amp;timestamp=1484458048&amp;ver=1&amp;signature=TSCKBqyBiIuHQlFfDU4okkQsb0r5ASR3S1hoYRkrhDDSN5DPX05pMOEGHek9DP0AFisUuLSZCoeNpyShKhEh5g==" data-headimage="http://wx.qlogo.cn/mmhead/Q3auHgzwzM4jVrScic6DTlLhtvEeMFZQH0hMhgyfW2VOOyWepLPRcxw/0" data-isv="0" uigs="main_toweixin_article_account_1">有意思的事情</a>
		     <span class="s2"><script>document.write(timeConvert('1484450511'))</script></span> 
		     <div class="moe-box"> 
		      <span style="display:none;" class="sc"><a uigs="other_share" data-except="1" class="sc-a" href="javascript:void(0)"></a></span>
		      <span style="display:none;" class="fx"><a uigs="other_collect" data-except="1" class="fx-a" href="javascript:void(0)"></a></span> 
		     </div> 
		    </div> 
		   </div> 
		</li>
		 */
		WeChatTop top = new WeChatTop();
		
		///title
		//<a target="_blank" href="" id="sogou_vr_11002601_title_1" uigs="main_toweixin_article_title_1">TTTTT</a>
		String title_a_id = title_a_id_tmpl.replace("{index}", index + "");
		Element title_a = li.getElementById(title_a_id);
		if(title_a != null){
			top.title = title_a.text();
			top.topUrl = title_a.attr("href");
			//System.out.println(top.title + "--" + top.topUrl);
		}else{
			throw new RuntimeException("read title时出错了，没有找到元素，序列" + index + "");
		}
		
		
		///desc
		String desc_p_id = desc_p_id_tmpl.replace("{index}", index + "");
		Element desc_p = li.getElementById(desc_p_id);
		if(desc_p != null){
			top.shortIntro = desc_p.text();
		}else{
		}
		
		///取出封面图
		//单图：sogou_vr_11002601_img_{index 0-9}   a包img
		//三图：sogou_vr_11002601_img_1_1   a包三个（span包img）
		Elements covers= li.select("a[id^={a}]".replace("{a}", cover_img_a_tmpl));
		if(covers != null){
			if(covers.size() == 1){
				//单图  <a><img src>
				Element img = covers.first().child(0);
				if(img != null){
					String imgUrl = img.attr("src");
					if(imgUrl != null){
						top.coverImages.add(parseCoverImageUrl(imgUrl));
					}else{
						///图没了
					}
				}
				
				
			}else if(covers.size() == 3){
				//三图
				for(int i = 0; i < 3; i++){
					Element img = covers.get(i).child(0).child(0);
					if(img != null){
						String imgUrl = img.attr("src");
						if(imgUrl != null){
							top.coverImages.add(parseCoverImageUrl(imgUrl));
						}else{
							///图没了
						}
					}
				}
			}
		}else{
			//没封面
		}
		
		///作者
		String author_id = account_a_tmpl.replace("{index}", index + "");
		Element author_a = li.getElementById(author_id);
		if(author_a != null){
			top.authorName = author_a.text();
			top.authorUrl = author_a.attr("href");
			top.authorHeadImg= author_a.attr("data-headimage");
		}else{
			System.out.println("没找到 id = " + author_id);
		}
		
		///创建时间
		//<script>document.write(timeConvert('1483781404'))</script></span>
		Elements date_scripts = li.getElementsByClass("s2");
		if(date_scripts != null && date_scripts.size() > 0){
			String date_js = date_scripts.get(0).toString();
			//System.out.println(date_js);
			if(date_js != null){
				date_js = date_js.replace("<span class=\"s2\"><script>document.write(timeConvert('", "").replace("'))</script></span>", "");
				top.createAt = date_js;
			}
		}
		
		tops.add(top);
		
	}
	
	private String parseCoverImageUrl(String imgUrl){
		///http://img01.sogoucdn.com/net/a/04/link?appid=100520033&amp;url=http://mmbiz.qpic.cn/mmbiz_jpg/iaJ2Y8kZ6cicXMFduems3xrmmhZB51icFtKKOArYfNiaWlOgZXxy3Z0CNnXsUOpa69AbrrNUgialXnVPssia6PCfwpicg/640?wx_fmt=jpeg
			//这种形式打不开，必须从搜狗站内打开
		///http://mmbiz.qpic.cn/mmbiz_jpg/iaJ2Y8kZ6cicXMFduems3xrmmhZB51icFtKKOArYfNiaWlOgZXxy3Z0CNnXsUOpa69AbrrNUgialXnVPssia6PCfwpicg/640?wx_fmt=jpeg
			//必须把url=后面的截出来
		int pos = imgUrl.indexOf("url=");
		if(pos != -1){
			return imgUrl.substring(pos + 4, imgUrl.length()); 
		}else{
			return imgUrl; 
		}
	}

}
