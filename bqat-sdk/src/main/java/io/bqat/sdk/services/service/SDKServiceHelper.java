package io.bqat.sdk.services.service;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import io.bqat.sdk.services.config.LoggerConfig;
import io.bqat.sdk.services.dto.BqatRequestDto;
import io.bqat.sdk.services.dto.SettingsDto;
import io.mosip.kernel.core.util.DateUtils;
import io.mosip.kernel.core.logger.spi.Logger;

public class SDKServiceHelper {
	private Logger logger = LoggerConfig.logConfig(SDKServiceHelper.class);
	private String REQ_TEMPLATE = "{ \"modality\": \"%s\", \"id\": \"%s\", \"type\": \"%s\", \"data\": \"%s\", \"requestTime\": \"%s\", \"version\": \"%s\"}";

	private SettingsDto settingsDto;
	public SDKServiceHelper(SettingsDto settingsDto)
	{
		this.settingsDto = settingsDto;
	}
	
	public JSONObject getQualityInfoWithJson(BqatRequestDto requestDto) {
		OkHttpClient client = new OkHttpClient();
		String requestBody = String.format(REQ_TEMPLATE, requestDto.getModality(), requestDto.getId(), requestDto.getType(), requestDto.getData(),
				DateUtils.getUTCCurrentDateTime(), "1.0.0");

		logger.debug("requestBody>>>>>>>>>>>>>>>>>" +  requestBody);
		
		MediaType mediaType = MediaType.parse(settingsDto.getContentType() + "; charset=" + settingsDto.getContentCharset());
		RequestBody body = RequestBody.create(mediaType, requestBody);
		Request request = new Request.Builder()
				.url("http://" + settingsDto.getServerIpAddress() + "" + settingsDto.getServerPort() + "" + settingsDto.getServerPath()).post(body).build();
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				String jsonResponse = response.body().string();
				logger.debug("response>>>>>>>>>>>>>>>>>" +  jsonResponse);
				JSONObject jsonObject = new JSONObject(jsonResponse);
				jsonObject = jsonObject.getJSONObject(settingsDto.getJsonResults());
				return jsonObject;
			}
		} catch (IOException | JSONException e) {
			logger.error("getQualityInfoWithJson", e);
		}
		return null;
	}
}