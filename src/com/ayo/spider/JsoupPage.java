package com.ayo.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public abstract class JsoupPage extends OkHttp3Page{

	public JsoupPage(String url) {
		super(url);
	}

	@Override
	protected void anylise(String content) {
		Document document = Jsoup.parse(content, url.toString());
		try {
			tryToRead(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		for (Element element : document.getAllElements()){
//			try {
//				eachElement(element);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	protected abstract void tryToRead(Document document);

}
