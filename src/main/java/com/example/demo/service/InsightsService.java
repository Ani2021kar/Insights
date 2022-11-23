package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.DemoRepository;
import com.example.demo.entity.DemoModel;

@Service
public class InsightsService {

	@Autowired
	private DemoRepository repo;

	public Map<String, Map<String, Integer>> getInsights() {

		Map<String, Map<String, Integer>> dataMap = new HashMap<>();
		List<DemoModel> demoModelList = repo.findAll();
		
		for (DemoModel demoModel : demoModelList) {
			boolean flag = true;
			String input = demoModel.getBody();
			JSONArray jsonArray = new JSONArray(input);
			System.out.println(jsonArray);
			for (int i = 0; i < jsonArray.length(); i++) {
				String check = jsonArray.getJSONObject(i).toString();
				if (check.contains("country_name")) {
					String countryName = jsonArray.getJSONObject(i).getString("country_name");
					System.out.println(countryName);
					if (countryName != null && !countryName.isEmpty()) {
						if(dataMap.containsKey(countryName) && !flag){
							int count = dataMap.get(countryName).get("Cumulative");
							dataMap.get(countryName).put("Cumulative", count + 1);
						} 
						else if(dataMap.containsKey(countryName) && flag) {
							flag = false;
							int cumulativeCount = dataMap.get(countryName).get("Cumulative");
							int uniqueCount = dataMap.get(countryName).get("Unique");
							dataMap.get(countryName).put("Cumulative", cumulativeCount + 1);
							dataMap.get(countryName).put("Unique", uniqueCount + 1);
						} 
						else{
							flag = false;
							HashMap<String, Integer> hs = new HashMap<>();
							hs.put("Cumulative", 1);
							hs.put("Unique", 1);
							dataMap.put(countryName, hs);
						}
					}
				}
			}
		}
		return dataMap;
	}

}
