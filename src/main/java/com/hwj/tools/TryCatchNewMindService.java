package com.hwj.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	 * @param  获取子节点(not cotain select_node)
	 * @serialData 2018.6.10
	 * @param list
	 * @return
	 */
	public List<MindNode> judgeHaveChild(List<MindNode> list, String nodeid, List<MindNode> storage){
		
		String parentid = null;
		
		for(Iterator it = list.iterator(); it.hasNext();){
			MindNode mindNode = (MindNode) it.next();
			if(mindNode.getParentid().equals(nodeid)){
				parentid = mindNode.getNodeid();            //repeat too many times
				
				try {
					if( !parentid.equals(null) ){
						System.out.println("还有下一层");
						judgeHaveChild(list, parentid, storage);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				storage.add(mindNode);
			}
		}
		
		return storage;
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取所有子节点
	 * @serialData 2018.6.11
	 * @param list
	 * @param nodeid
	 * @param storage
	 * @return
	 */
	public List<MindNode> getChild(List<MindNode> list, String nodeid, List<MindNode> storage){
		
		judgeHaveChild(list, nodeid, storage);
		
		for(Iterator it = list.iterator(); it.hasNext();){
			MindNode mindNode = (MindNode) it.next();
			if(mindNode.getNodeid().equals(nodeid)){
				storage.add(mindNode);
			}
		}
		
		return storage;
		
	}
	
	
	/**
	 * @author Ragty
	 * @param  获取删除后的节点(利用Set的不可重复性,Banner)  //利用list的contain也可去掉，所以我懒得写了
	 * @serialData  2018.6.11
	 * @param less
	 * @param more
	 * @param target
	 * @return
	 */
	public List<MindNode> getNope(List<MindNode> less, List<MindNode> more ){
		
		Set<MindNode> les = new HashSet<MindNode>(less);
		Set<MindNode> mor = new HashSet<MindNode>(more);
		
		List<MindNode> target = new ArrayList<MindNode>();
		
        Iterator<MindNode> it = mor.iterator();
		
		while (it.hasNext()) {  
		  MindNode mind = it.next();  
		  
		  if( les.add(mind) ){
			  target.add(mind);
		  }
		  
		}  
		return target;
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  根据一个属性获取当前页数的数据
	 * @param currentPage
	 * @param pageSize
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<MindMap> getMindMapByPage(Integer currentPage, Integer pageSize,
			String propertyName,Object value){
		
		List<MindMap> list = null;
		try {
			list = iMindMapService.getAllByPage(currentPage, pageSize, propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		return list;
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  根据两个值获取当前页数的数据
	 * @param currentPage
	 * @param pageSize
	 * @param propertyName1
	 * @param value1
	 * @param propertyName2
	 * @param value2
	 * @return
	 */
	public List<MindMap> getMindMapByPage(Integer currentPage, Integer pageSize,
			String propertyName1,Object value1,String propertyName2,Object value2){
		
		List<MindMap> list = null;
		try {
			list = iMindMapService.getAllByPage(currentPage, pageSize, propertyName1, value1, propertyName2, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		return list;
	}
	
	
	
	
	/**
	 * @author Ragty
	 * @param  根据一个条件获取到查询到的总数
	 * @serialData 2018.6.12
	 * @param propertyName
	 * @param value
	 * @return
	 */
    public Long countByOneMind(String propertyName,Object value){
		
		Long total=null;
		try {
			total=this.iMindMapService.countByOne(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return total;
	}
	
	
	
}
