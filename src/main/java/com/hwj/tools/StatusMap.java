package com.hwj.tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hwj.json.JsonAnalyze;



@Component
public class StatusMap {
	private JsonAnalyze jsonAnalyze = new JsonAnalyze();

	public String status(String status) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Status", status);
		String str = jsonAnalyze.map2Json(map);
		return str;
	}

	/*
	 * a方法代表一种返回值得状态，返回的是一个json数据，3和4 分别代表其他意思，自定义吧 1，代表一切正常，2，代表数据不存在。
	 */
	public String a(String a) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", a);
		String str = jsonAnalyze.map2Json(map);
		return str;
	}

	public String b(String b) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("b", b);
		String str = jsonAnalyze.map2Json(map);
		return str;
	}
}
