package com.hwj.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.MindMap;
import com.hwj.json.JsonAnalyze;
import com.hwj.service.IMindMapService;

@Component
public class TryCatchNewMindService {

	@Autowired
	private IMindMapService iMindMapService;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	
	
	/**
	 * @author Ragty
	 * @param  保存mindMap
	 * @serialData 2018.6.7
	 * @param mindMap
	 * @return
	 */
	public boolean saveMindMap(MindMap mindMap){
		
		try {
			this.iMindMapService.save(mindMap);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		return true;
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  修改知识图谱
	 * @serialData 2018.6.7
	 * @param mindMap
	 * @return
	 */
	public boolean updateMindMap(MindMap mindMap){
		
		try {
			this.iMindMapService.update(mindMap);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * @author Ragty
	 * @param  删除知识图谱
	 * @serialData 2018.6.7
	 * @param mindMap
	 * @return
	 */
	public boolean delMindMap(MindMap mindMap){
		
		try {
			this.iMindMapService.delete(mindMap);
		} catch (Exception e) {
			// TODO: handle exception
		    return false;
		}
		
		return true;
	}
	
	
	
}
