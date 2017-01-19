package com.ayo.spider;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class OkHttp3Page extends Page{
	
	OkHttpClient client;

	public OkHttp3Page(String url) {
		super(url);
		client = new OkHttpClient.Builder()
				.build();
	}

	private String fetchFromUrl(String url){
		Request request = new Request.Builder()
		        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
		        .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		        .addHeader("Cookie", "CXID=3800ECF5F3A835AD8C8AA78197CE614E; ad=8lllllllll2YNWIylllllVPDyGylllll1LWlZklllxwlllll9Vxlw@@@@@@@@@@@; SUID=C28067D33965860A5875EA50000BFDE2; ABTEST=7|1484705111|v1; SNUID=793BDB68BBBEFDEF6371EC82BC07207C; IPLOC=CN1100; weixinIndexVisited=1; JSESSIONID=aaaKl_zWMhRfGbBEUpMMv; SUV=0048178AD36780C2587ECD59E05B8522; sct=4")
				.url(url).build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
			Page.raiseCannotContinue(e);
			return "";
		}
		String responseSource = response.networkResponse() != null
				? ("(network: " + response.networkResponse().code() + " over " + response.protocol() + ")") : "(cache)";
		int responseCode = response.code();

		System.out.printf("%03d: %s %s%n", responseCode, url, responseSource);

		String contentType = response.header("Content-Type");
		if (responseCode != 200 || contentType == null) {
			response.body().close();
			Page.raiseCannotContinue(new RuntimeException(responseSource));
			return "";
		}
		try {
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
			Page.raiseCannotContinue(e);
			return "";
		}
	}
	
	@Override
	protected String craw() {
		String s = fetchFromUrl(this.url);
		return s;
	}


}
