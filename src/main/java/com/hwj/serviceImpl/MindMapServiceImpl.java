package com.hwj.serviceImpl;

import com.google.common.collect.Maps;
import com.hwj.entity.MindMap;
import com.hwj.repository.MindMapRepository;
import com.hwj.service.MindMapService;
import com.hwj.util.JsonAnalyze;
import com.hwj.vo.MindNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MindMapServiceImpl implements MindMapService {

    @Autowired
    private MindMapRepository mindMapRepository;
    @Autowired
    private JsonAnalyze jsonAnalyze;


    //保存方法
    @Override
    public void save(MindMap mindMap) { mindMapRepository.save(mindMap); }



    /**
     * @auther: Ragty
     * @describe: 打开知识图谱(返回可供前端显示的JOSN数据)
     * @param: [list]
     * @return: java.lang.String
     * @date: 2018/11/12
     */
    @Override
    public String openMind(List<MindNode> list) {

        Map<String,Object> data = Maps.newHashMap();
        Map<String,Object> meta = Maps.newHashMap();

        meta.put("name", "jsMind remote");
        meta.put("author", "hizzgdev@163.com");
        meta.put("version", "0.2");

        HashMap nodeList = new HashMap();
        for (int i = 0; i<list.size(); i++) {
            MindNode mindNode = list.get(i);
            nodeList.put(mindNode.getId(),mindNode);
        }


        Set entrySet = nodeList.entrySet();
        MindNode root = null;
        for (Iterator it = entrySet.iterator();it.hasNext();){
            MindNode node = (MindNode) ( (Map.Entry) it.next()).getValue();

            if ( (node.getParentid() == null) || (node.getParentid() == "00100") ) {
                root = node;
            } else {
                try {
                    ( (MindNode) nodeList.get( node.getParentid() ) ).addChild(node);       //主节点后边加子节点
                } catch (Exception e){ e.printStackTrace(); }
            }

        }

        data.put("meta", meta);
        data.put("data",root.toString());
        data.put("format", "node_tree");

        String datas = null;
        try {
            datas = removeSymbol(data);
        } catch (Exception e){e.printStackTrace();}

        return datas;
    }



    /**
     * @auther: Ragty
     * @describe: 根据mapid查询实体
     * @param: [mapid]
     * @return: com.hwj.entity.MindMap
     * @date: 2018/11/16
     */
    @Override
    public MindMap queryMindByMapid(String mapid) { return mindMapRepository.queryMindMapByMapid(mapid); }



    /**
     * @auther: Ragty
     * @describe: 移除对象转换后的非法符号(工具方法)
     * @param: [datas]
     * @return: java.lang.String
     * @date: 2018/11/12
     */
    private String removeSymbol (Map<String,Object> data) throws Exception{

        String datas = jsonAnalyze.object2Json(data);

        datas = datas.replace("\"", "'");
        datas = datas.replace(" ", "");
        datas = datas.replace("'{", "{");
        datas = datas.replace("}'", "}");

        return datas;
    }



}
