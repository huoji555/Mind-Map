package com.hwj.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.MindMap;
import com.hwj.entity.MindNode;
import com.hwj.service.IMindMapService;
import com.hwj.service.IMindNodeService;

@Component
public class TryCatchMindMapService {
	@Autowired
	private IMindNodeService iMindNodeService;
	@Autowired
	private IMindMapService iMindMapService;

	public boolean saveMap(MindMap mindMap) {
		try {
			this.iMindMapService.save(mindMap);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean saveOrUpdata(MindMap mindMap) {
		try {
			this.iMindMapService.saveOrUpdate(mindMap);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public List<MindMap> getMindMap(String propertyName, Object value) {
		List<MindMap> list = null;
		try {
			list = this.iMindMapService.getAll(propertyName, value);
		} catch (Exception e) {
			return null;
		}
		return list;
	}

	public MindMap getMindMapObject(Serializable id) {
		MindMap mindMap = new MindMap();
		try {
			mindMap = (MindMap) this.iMindMapService.get(id);
		} catch (Exception e) {
			return null;
		}
		return mindMap;
	}

	/**
	 * @author Ragty
	 * @param 根据一对属性获取符合条件的节点
	 * @serialData 2018.3.6
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<MindNode> getMindNode(String propertyName, Object value) {
		List<MindNode> list = null;
		try {
			list = this.iMindNodeService.getAll(propertyName, value);
		} catch (Exception e) {
			return null;
		}
		return list;
	}

	
    /**
     * @author Ragty
     * @param 根据两对属性获取符合条件的节点	
     * @serialData 2018.3.6
     * @param propertyName1
     * @param value1
     * @param propertyName2
     * @param value2
     * @return
     */
	public List<MindNode> getMindNode(String propertyName1, Object value1,
			String propertyName2, Object value2) {
		List<MindNode> list = null;
		try {
			list = this.iMindNodeService.getAll(propertyName1, value1,
					propertyName2, value2);
		} catch (Exception e) {
			return null;
		}
		return list;
	}

	
	/**
	 * @author Ragty
	 * @param 保存节点
	 * @serialData 2018.3.6
	 * @param mindNode
	 * @return
	 */
	public boolean savaMindNodeObject(MindNode mindNode) {
		try {
			this.iMindNodeService.save(mindNode);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	/**
	 * @author Ragty	
	 * @param 修改节点
	 * @serialData 2018.3.6
	 * @param mindNode
	 * @return
	 */
	public boolean updateMindNodeObject(MindNode mindNode) {
		try {
			this.iMindNodeService.update(mindNode);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	/**
	 * @author Ragty
	 * @param 删除节点
	 * @serialData 2018.3.6
	 * @param mindNode
	 * @return
	 */
	public boolean deleteMindNodeObject(MindNode mindNode) {
		try {
			this.iMindNodeService.delete(mindNode);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	/**
	 * @author Ragty
	 * @param 根据两个条件获取特定的节点对象
	 * @serialData 2018.3.6
	 * @param propertyName1
	 * @param propertyName2
	 * @param value1
	 * @param value2
	 * @return
	 */
	public MindNode getMindNodeObject(String propertyName1,
			String propertyName2, Object value1, Object value2) {

		MindNode mindNode = new MindNode();

		try {
			mindNode = this.iMindNodeService.get(propertyName1, propertyName2,
					value1, value2);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mindNode;

	}

	
	/**
	 * @author Ragty
	 * @param 根据id获取节点对象
	 * @serialData 2018.3.6
	 * @param id
	 * @return
	 */
	public MindNode getMindNodeObject(Serializable id) {
		MindNode mindNode = new MindNode();
		try {
			mindNode = (MindNode) this.iMindNodeService.get(id);
		} catch (Exception e) {
			return null;
		}
		return mindNode;
	}
	
	
	/**
	 * @author Ragty
	 * @param 根据匹配条件查询分页取出list(一个条件)
	 * @serialData 2017.11.30
	 * @param pageSize
	 * @param propertyName
	 * @param value
	 * @return list
	 */
	public List<MindNode> getMindNodeByPage(Integer currentPage, Integer pageSize,
			String propertyName,Object value){
		
		List<MindNode> list=null;
		try {
			list=this.iMindNodeService.getAllByPage(currentPage, pageSize, propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}

	
	/**
	 * @author Ragty
	 * @param 根据匹配条件查询分页取出list(两个条件)
	 * @serialData 2017.11.30
	 * @param pageSize
	 * @param propertyName
	 * @param value
	 * @return list
	 */
	public List<MindNode> getMindNodeByPage(Integer currentPage, Integer pageSize,
			String propertyName1,Object value1,String propertyName2,Object value2){
		
		List<MindNode> list=null;
		try {
			list=this.iMindNodeService.getAllByPage(currentPage, pageSize, propertyName1, value1, propertyName2, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}
	
	
	/**
	 * @author Ragty
	 * @serialData 2017.12.1
	 * @param 获取根据条件查询到的数据的总数(一个条件)
	 * @param propertyName
	 * @param value
	 * @return total
	 */
	public Long countByOneMind(String propertyName,Object value){
		
		Long total=null;
		try {
			total=this.iMindNodeService.countByOne(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return total;
	}
	
	
	/**
	 * @author Ragty
	 * @param 获取根据条件查询到的数据的总数(两个条件)
	 * @param propertyName1
	 * @param propertyName2
	 * @param value1
	 * @param value2
	 * @return total
	 */
	public Long countByTwoMind(String propertyName1,
			String propertyName2, Object value1, Object value2){
		
		Long total=null;
		try {
			total=this.iMindNodeService.countByTwo(propertyName1, value1, propertyName2, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return total;
	}
	
	/**
	 * @author Ragty
	 * @param  模糊匹配多个字段
	 * @serialData 2018.4.16
	 * @param value1
	 * @return
	 */
	public List<MindNode> queryMindNode(Object value1, Integer currentPage, Integer pageSize){
		
		List<MindNode> list = null;
		try {
			list = iMindNodeService.selectMindNode(value1, currentPage, pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}
	
	
	/**
	 * @author Ragty
	 * @param  获得查询后获取到的总页数
	 * @serialData 2018.4.17
	 * @param value1
	 * @return
	 */
	public Long queryMindPage(Object value1){

		Long total = null;
		try {
			total = iMindNodeService.searchMindPage(value1);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return total;
	}
	
	
	
	/**
	 * @author Ragty
	 * @param 获取到点击节点之后的所有子节点
	 * @serialData 2018.3.6
	 * @param nodeid
	 * @param userid
	 * @return List
	 */
	public List<Map<String, String>> getzijiedian(String nodeid, String userid) { // 获取到点击的节点
		List<Map<String, String>> list100 = new ArrayList<Map<String, String>>();
		MindNode ma = (MindNode) this.iMindNodeService.get(nodeid);
		Map<String, String> map10 = new HashMap<String, String>();
		map10.put("id", ma.getNodeid());
		map10.put("topic", ma.getNodename());
		map10.put("parentid", ma.getParentid());
		map10.put("color", ma.getColor());
		list100.add(map10);
		if (this.iMindNodeService.getAll("parentid", nodeid, "userid", userid) != null) {
			// 获取到该节点下的子节点(之后一步步获取各级的节点,层层下推，共9层)
			List<MindNode> list = this.iMindNodeService.getAll("parentid",
					nodeid, "userid", userid);
			System.out.println(list + "@@@@@@@@@@@@@@获取到的子节点第一层" + list.size());
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				MindNode mindNode = (MindNode) list.get(i);
				map.put("id", mindNode.getNodeid());
				map.put("topic", mindNode.getNodename());
				map.put("parentid", mindNode.getParentid());
				map.put("color", mindNode.getColor());
				list100.add(map);
				if (this.iMindNodeService.getAll("parentid",
						mindNode.getNodeid(), "userid", userid) != null) {
					List<MindNode> list2 = this.iMindNodeService.getAll(
							"parentid", mindNode.getNodeid(), "userid", userid);
					System.out.println(list2 + "@@@@@@@@@@@@获取到的子节点第二层"
							+ list2.size());
					for (int j = 0; j < list2.size(); j++) {
						Map<String, String> map2 = new HashMap<String, String>();
						MindNode mindNode2 = (MindNode) list2.get(j);
						map2.put("id", mindNode2.getNodeid());
						map2.put("topic", mindNode2.getNodename());
						map2.put("parentid", mindNode2.getParentid());
						map2.put("color", mindNode2.getColor());
						list100.add(map2);
						if (this.iMindNodeService.getAll("parentid",
								mindNode2.getNodeid(), "userid", userid) != null) {
							List<MindNode> list3 = this.iMindNodeService
									.getAll("parentid", mindNode2.getNodeid(),
											"userid", userid);
							System.out.println(list3
									+ "@@@@@@@@@@@@@@@获取到的子节点第三层"
									+ list3.size());
							for (int k = 0; k < list3.size(); k++) {
								Map<String, String> map3 = new HashMap<String, String>();
								MindNode mindNode3 = (MindNode) list3.get(k);
								map3.put("id", mindNode3.getNodeid());
								map3.put("topic", mindNode3.getNodename());
								map3.put("parentid", mindNode3.getParentid());
								map3.put("color", mindNode3.getColor());
								list100.add(map3);
								if (this.iMindNodeService
										.getAll("parentid",
												mindNode3.getNodeid(),
												"userid", userid) != null) {
									List<MindNode> list4 = this.iMindNodeService
											.getAll("parentid",
													mindNode3.getNodeid(),
													"userid", userid);
									for (int l = 0; l < list4.size(); l++) {
										Map<String, String> map4 = new HashMap<String, String>();
										MindNode mindNode4 = (MindNode) list4
												.get(l);
										map4.put("id", mindNode4.getNodeid());
										map4.put("topic",
												mindNode4.getNodename());
										map4.put("parentid",
												mindNode4.getParentid());
										map4.put("color", 
												mindNode4.getColor());
										list100.add(map4);
										if (this.iMindNodeService.getAll(
												"parentid",
												mindNode4.getNodeid(),
												"userid", userid) != null) {
											List<MindNode> list5 = this.iMindNodeService
													.getAll("parentid",
															mindNode4
																	.getNodeid(),
															"userid", userid);
											for (int m = 0; m < list5.size(); m++) {
												Map<String, String> map5 = new HashMap<String, String>();
												MindNode mindNode5 = (MindNode) list5
														.get(m);
												map5.put("id",
														mindNode5.getNodeid());
												map5.put("topic",
														mindNode5.getNodename());
												map5.put("parentid",
														mindNode5.getParentid());
												map5.put("color", 
														mindNode5.getColor());
												list100.add(map5);
												if (this.iMindNodeService
														.getAll("parentid",
																mindNode5
																		.getNodeid(),
																"userid",
																userid) != null) {
													List<MindNode> list6 = this.iMindNodeService
															.getAll("parentid",
																	mindNode5
																			.getNodeid(),
																	"userid",
																	userid);
													for (int n = 0; n < list6
															.size(); n++) {
														Map<String, String> map6 = new HashMap<String, String>();
														MindNode mindNode6 = (MindNode) list6
																.get(n);
														map6.put(
																"id",
																mindNode6
																		.getNodeid());
														map6.put(
																"topic",
																mindNode6
																		.getNodename());
														map6.put(
																"parentid",
																mindNode6
																		.getParentid());
														map6.put("color", mindNode6.getColor());
														list100.add(map6);
														if (this.iMindNodeService
																.getAll("parentid",
																		mindNode6
																				.getNodeid(),
																		"userid",
																		userid) != null) {
															List<MindNode> list7 = this.iMindNodeService
																	.getAll("parentid",
																			mindNode6
																					.getNodeid(),
																			"userid",
																			userid);
															for (int o = 0; o < list7
																	.size(); o++) {
																Map<String, String> map7 = new HashMap<String, String>();
																MindNode mindNode7 = (MindNode) list7
																		.get(o);
																map7.put(
																		"id",
																		mindNode7
																				.getNodeid());
																map7.put(
																		"topic",
																		mindNode7
																				.getNodename());
																map7.put(
																		"parentid",
																		mindNode7
																				.getParentid());
																map7.put("color", mindNode7.getColor());
																list100.add(map7);
																if (this.iMindNodeService
																		.getAll("parentid",
																				mindNode7
																						.getNodeid(),
																				"userid",
																				userid) != null) {
																	List<MindNode> list8 = this.iMindNodeService
																			.getAll("parentid",
																					mindNode7
																							.getNodeid(),
																					"userid",
																					userid);
																	for (int p = 0; p < list8
																			.size(); p++) {
																		Map<String, String> map8 = new HashMap<String, String>();
																		MindNode mindNode8 = (MindNode) list8
																				.get(p);
																		map8.put(
																				"id",
																				mindNode8
																						.getNodeid());
																		map8.put(
																				"topic",
																				mindNode8
																						.getNodename());
																		map8.put(
																				"parentid",
																				mindNode8
																						.getParentid());
																		map8.put("color", mindNode8.getColor());
																		list100.add(map8);
																		if (this.iMindNodeService
																				.getAll("parentid",
																						mindNode8
																								.getNodeid(),
																						"userid",
																						userid) != null) {
																			List<MindNode> list9 = this.iMindNodeService
																					.getAll("parentid",
																							mindNode8
																									.getNodeid(),
																							"userid",
																							userid);
																			for (int q = 0; q < list9
																					.size(); q++) {
																				Map<String, String> map9 = new HashMap<String, String>();
																				MindNode mindNode9 = (MindNode) list9
																						.get(q);
																				map9.put(
																						"id",
																						mindNode9
																								.getNodeid());
																				map9.put(
																						"topic",
																						mindNode9
																								.getNodename());
																				map9.put(
																						"parentid",
																						mindNode9
																								.getParentid());
																				map9.put("color", mindNode9.getColor());
																				list100.add(map9);
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return list100;
	}
}
