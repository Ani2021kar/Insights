package com.example.demo.controller;

import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.InsightsService;

@RestController
public class InsightController {

	@Autowired
	private InsightsService insightService;
	
	@GetMapping("/insights/countryName")
	public Map<String, Map<String, Integer>>  insightData(){
		return insightService.getInsights();
	}
}
