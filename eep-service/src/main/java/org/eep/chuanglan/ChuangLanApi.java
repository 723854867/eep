package org.eep.chuanglan;

import java.io.IOException;

import javax.annotation.Resource;

import org.eep.chuanglan.model.ChuangLanRequest;
import org.eep.chuanglan.model.HttpResonse;
import org.eep.chuanglan.model.SmsRequest;
import org.eep.chuanglan.model.SmsResponse;
import org.eep.chuanglan.model.SmsState;
import org.eep.chuanglan.model.VarSmsRequest;
import org.eep.chuanglan.model.VarSmsResponse;
import org.eep.common.bean.exception.RequestFailure;
import org.eep.common.bean.exception.ResponseFailure;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Response;

@Slf4j
@Component
@PropertySource("classpath:conf/chuanglan.properties")
public class ChuangLanApi {
	
	private Gson gson;
	@Value("${chuanglan.host}")
	private String host;
	@Value("${chuanglan.account}")
	private String account;
	@Value("${chuanglan.password}")
	private String password;
	@Resource
	private OkHttpClient okHttpClient;
	
	public ChuangLanApi() {
		this.gson = new GsonBuilder().registerTypeAdapter(SmsState.class, new SmsStateSerializer()).create();
	}
	
	public SmsResponse sendSms(SmsRequest request) throws ResponseFailure {
		return _request(request, "msg/send/json", SmsResponse.class);
	}
	
	public VarSmsResponse sendSms(VarSmsRequest request) throws ResponseFailure {
		return _request(request, "msg/variable/json", VarSmsResponse.class);
	}
	
	private <RESPONSE extends HttpResonse> RESPONSE _request(ChuangLanRequest request, String path, Class<RESPONSE> clazz) throws ResponseFailure {
		request.account(account);
		request.password(password);
		HttpUrl.Builder builder = new HttpUrl.Builder();
		builder.host(host);
		builder.scheme("https");
		builder.addPathSegments(path);
		String content = gson.toJson(request);
		log.info("创蓝请求：{}", content);
		Response response = okHttpClient.postJson(builder.build(), content);
		RESPONSE resp;
		try {
			resp = gson.fromJson(response.body().string(), clazz);
		} catch (JsonSyntaxException | IOException e) {
			throw new RequestFailure(e);
		}
		log.info("创蓝响应：{}", gson.toJson(resp));
		resp.verify();
		return resp;
	}
}
