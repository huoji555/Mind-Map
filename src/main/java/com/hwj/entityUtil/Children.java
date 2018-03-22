package com.hwj.entityUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class Children {
	private List list = new ArrayList();

	public int getSize() {
		return list.size();
	}

	public void addChild(Node2 node) {
		list.add(node);
	}

	// 拼接孩子节点的JSON字符串
	public String toString() {
		String result = "[";
		for (Iterator it = list.iterator(); it.hasNext();) {
			result += ((Node2) it.next()).toString();
			result += ",";
		}
		result = result.substring(0, result.length() - 1);
		result += "]";
		return result;
	}

	// 孩子节点排序
	public void sortChildren() {
		// 对本层节点进行排序
		// 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器
		Collections.sort(list, new NodeIDComparator());
		// 对每个节点的下一层节点进行排序
		for (Iterator it = list.iterator(); it.hasNext();) {
			((Node2) it.next()).sortChildren();
		}
	}
}

/**
 * 节点比较器
 */
class NodeIDComparator implements Comparator {
	// 按照节点编号比较
	public int compare(Object o1, Object o2) {
		int j1 = Integer.parseInt(((Node2) o1).id);
		int j2 = Integer.parseInt(((Node2) o2).id);
		return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
	}
}

class VirtualDataGenerator {
	// 构造无序的结果集列表，实际应用中，该数据应该从数据库中查询获得；
	public static List getVirtualResult() {
		List dataList = new ArrayList();

		HashMap dataRecord1 = new HashMap();
		dataRecord1.put("id", "112000");
		dataRecord1.put("topic", "廊坊银行解放道支行");
		dataRecord1.put("parentid", "110000");

		HashMap dataRecord2 = new HashMap();
		dataRecord2.put("id", "112200");
		dataRecord2.put("topic", "廊坊银行三大街支行");
		dataRecord2.put("parentid", "112000");

		HashMap dataRecord3 = new HashMap();
		dataRecord3.put("id", "112100");
		dataRecord3.put("topic", "廊坊银行广阳道支行");
		dataRecord3.put("parentid", "112000");

		HashMap dataRecord4 = new HashMap();
		dataRecord4.put("id", "113000");
		dataRecord4.put("topic", "廊坊银行开发区支行");
		dataRecord4.put("parentid", "110000");

		HashMap dataRecord5 = new HashMap();
		dataRecord5.put("id", "100000");
		dataRecord5.put("topic", "廊坊银行总行");
		dataRecord5.put("parentid", "");

		HashMap dataRecord6 = new HashMap();
		dataRecord6.put("id", "110000");
		dataRecord6.put("topic", "廊坊分行");
		dataRecord6.put("parentid", "100000");

		HashMap dataRecord7 = new HashMap();
		dataRecord7.put("id", "111000");
		dataRecord7.put("topic", "廊坊银行金光道支行");
		dataRecord7.put("parentid", "110000");

		dataList.add(dataRecord1);
		dataList.add(dataRecord2);
		dataList.add(dataRecord3);
		dataList.add(dataRecord4);
		dataList.add(dataRecord5);
		dataList.add(dataRecord6);
		dataList.add(dataRecord7);

		return dataList;
	}
}