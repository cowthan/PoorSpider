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
		client = new OkHttpClient.Builder().build();
	}

	private String fetchFromUrl(String url){
		Request request = new Request.Builder().url(url).build();
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
