package org.eep.chuanglan;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.eep.common.bean.enums.ContentType;
import org.eep.common.bean.exception.HttpStatusException;
import org.eep.common.bean.exception.RequestFailure;
import org.rubik.bean.core.enums.ColStyle;
import org.rubik.util.ConfigLoader;
import org.rubik.util.common.StringUtil;
import org.springframework.stereotype.Component;

import okhttp3.ConnectionPool;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class OkHttpClient {

	private okhttp3.OkHttpClient client;
	
	@PostConstruct
	private void init() {
		OkHttpConfig config = ConfigLoader.load("classpath*:conf/okhttp.properties").toBean(OkHttpConfig.class, ColStyle.camel2dot);
		okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();
		builder.connectTimeout(config.getConnTimeout(), TimeUnit.SECONDS);
		builder.readTimeout(config.getReadTimeout(), TimeUnit.SECONDS);
		builder.writeTimeout(config.getWriteTimeout(), TimeUnit.SECONDS);
		ConnectionPool pool = new ConnectionPool(config.getMaxIdleConns(), config.getConnKeepAlive(), TimeUnit.SECONDS);
		builder.connectionPool(pool);
		client = builder.build();
	}

	/**
	 * 同步json请求(post)
	 */
	public Response postJson(HttpUrl url, String content) {
		Request.Builder rb = new Request.Builder().url(url);
		RequestBody body = RequestBody.create(MediaType.parse(ContentType.APPLICATION_JSON_UTF_8.mark()), content);
		Request request = rb.post(body).build();
		return request(request);
	}
	
	/**
	 * 同步请求
	 */
	public Response request(Request request) {
		try {
			return _checkHttpStatus(this.client.newCall(request).execute());
		} catch (IOException e) {
			throw new RequestFailure(e);
		}
	}
	
	private Response _checkHttpStatus(Response response) {
		if (response.isSuccessful())
			return response;
		String msg = null;
		try {
			msg = response.body().string();
		} catch (Exception e) {}
		String error = response.message();
		if (StringUtil.hasText(msg))
			error += " - [" + msg + "]";
		throw new HttpStatusException(response.code(), error);
	}
}
