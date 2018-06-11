package com.hwj.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.MindMap;
import com.hwj.entity.MindNode;
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
	
	
	
	/**
	 * @author Ragty
	 * @param  根据两个属性获取MindMap
	 * @serialData 2018.6.9
	 * @param propertyName1
	 * @param value1
	 * @param propertyName2
	 * @param value2
	 * @return
	 */
	public MindMap getMindMap(String propertyName1,Object value1,String propertyName2,Object value2){
		
		MindMap mindMap = null;
		try {
			mindMap = iMindMapService.get(propertyName1, propertyName2, value1, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return mindMap;
	}
	
	
	/**
	 * @author Ragty
	 * @param  获取子节点
	 * @serialData 2018.6.10
	 * @param list
	 * @return
	 */
	public List<MindNode> judgeHaveChild(List<MindNode> list, String nodeid, List<MindNode> storage){
		
		String parentid = null;
		
		for(int i= 0; i<list.size(); i++){
			MindNode mindNode = list.get(i);
			if(mindNode.getParentid().equals(nodeid)){
				parentid = mindNode.getNodeid();            //repeat too many times
				storage.add(mindNode);
			}
		}
		
		try {
			if( !parentid.equals(null) ){
				judgeHaveChild(list, parentid, storage);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return storage;
	}
	
	
	
	
	
	
	
}
