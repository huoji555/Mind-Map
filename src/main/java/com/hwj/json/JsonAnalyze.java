package com.hwj.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;








import org.springframework.stereotype.Component;






import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwj.entity.MindNode;

@Component
@SuppressWarnings("unchecked")
public class JsonAnalyze {
	private ObjectMapper objectMapper = new ObjectMapper();

	// 把从客户端得到的JSON数据转换成Map
	public Map<String, Object> json2Map(String json) throws IOException {
		Map<String, Object> map2 = new HashMap<String, Object>();
		try {
			Map<String, Object> map1 = objectMapper.readValue(json, Map.class);
			Set<String> key = map1.keySet();
			Iterator<String> iter = key.iterator();
			while (iter.hasNext()) {
				String str = iter.next();
				map2.put(str, map1.get(str));
			}
			return map2;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map2;

	}

	// 将JOSN数据转换为List数据
	public <T> List<T> json2List(String json) throws IOException {
		List<T> list1 = new ArrayList<T>();
		try {
			List<LinkedHashMap<String, Object>> list = objectMapper.readValue(
					json, List.class);
			list1 = (List<T>) list;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list1;
	}

	// 将json转换成Object
	public Object json2Object(String json) throws IOException {
		Object object = new Object();
		try {
			object = objectMapper.readValue(json, Object.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;

	}

	// 将Map数据转换成JSON数据

	public String map2Json(Map<String, Object> map) throws IOException {
		String json = new String();
		try {
			json = objectMapper.writeValueAsString(map);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return json;
	}

	// 将List<Map>数据转换成JSON数据

	public String Listmap2Json(List<Map<String, String>> maps)
			throws IOException {
		String json = new String();
		try {
			json = objectMapper.writeValueAsString(maps);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return json;
	}

	// 将从数据库取得的List类型的数据转换成JSON数据
	public <T> String list2Json(List<T> list) throws IOException {
		String json = new String();
		try {
			json = objectMapper.writeValueAsString(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	// Object转换为json
	public String string2Json(String str) throws IOException {
		String json = new String();
		try {
			json = objectMapper.writeValueAsString(str);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return json;
	}

	// 将object转换为json
	public String object2Json(Object obj) throws IOException {
		String json = new String();
		try {
			json = objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
	//转换为能取到的list（）
	/**
	 * @author Ragty
	 * @param  转换为能够取到的list(直接取的话，取出来是未转换的)
	 * @serialData 2018.6.10
	 * @param str
	 * @return
	 */
	public  List<MindNode>  parseList(String str){
		
		List<MindNode> mindList = null;
		try {
			 mindList =  
				    objectMapper.readValue(str, new TypeReference<List<MindNode>>(){}); 
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	
		return mindList;
	}
	
	
}

	// 将List<LoginUser>转换为json
	/*public String List2Json(List<LoginUser> usersData) throws IOException {
		String json = new String();
		try {
			json = objectMapper.writeValueAsString(usersData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

}*/
//
//
//
//
//
//
