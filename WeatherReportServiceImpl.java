package com.pactero.weatherreport.service.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.pactero.weatherreport.model.City;
import com.pactero.weatherreport.service.WeatherReportService;

public class WeatherReportServiceImpl implements WeatherReportService {

	@Value("${uriWithApiKey}")
	private String uriWithApiKey;
	
	public City getWeatherReportForGivenCity(City city) {
		System.out.println("In getWeatherReportForGivenCity");
		
		try {
			String weatherInformationResponse = invokeWeatherApi(city.getCityName());
			return mapResponseJsonFieldsToObj(city, weatherInformationResponse); 
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return city;
	}

	/**
	 * @param city
	 * @param weatherInformationResponse
	 * @throws JSONException
	 */
	private City mapResponseJsonFieldsToObj(City city, String weatherInformationResponse) throws JSONException {
		
		JSONObject jsonObj = new JSONObject(weatherInformationResponse);
		
		JSONArray weatherObj = (JSONArray)jsonObj.get("weather");
		JSONObject desc = (JSONObject)weatherObj.get(weatherObj.length()-1);
		city.setWeather(desc.get("description").toString());
		
		
		JSONObject tempInfo = (JSONObject)jsonObj.get("main");
		city.setTemperature(tempInfo.get("temp")+"\u00B0".toString());
		
		JSONObject speedInfo = (JSONObject)jsonObj.get("wind");
		city.setWindSpeed(String.valueOf(milesTokm(Double.parseDouble(speedInfo.get("speed").toString()))));
		
		return city;
	}
	
	private String invokeWeatherApi(String cityName) {
		RestTemplate restTemplate = new RestTemplate();
		String url = uriWithApiKey+cityName;
		return restTemplate.getForObject(url, String.class);
	}

	private double milesTokm(double distanceInMiles) {
        return distanceInMiles * 1.60934;
    }
}
