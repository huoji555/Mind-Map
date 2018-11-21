package com.hwj.serviceImpl;

import com.google.common.collect.Maps;
import com.hwj.entity.MindMap;
import com.hwj.repository.MindMapRepository;
import com.hwj.service.MindMapService;
import com.hwj.util.JsonAnalyze;
import com.hwj.vo.MindNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * @describe: 删除方法
     * @param: [mapid]
     * @return: void
     * @date: 2018/11/17
     */
    @Override
    public void deleteMap(String mapid) { mindMapRepository.deleteMindMapByMapid(mapid); }




    /**
     * @auther: Ragty
     * @describe: 打开知识图谱(返回可供前端显示的JOSN数据)
     * @param: [list]
     * @return: java.lang.String
     * @date: 2018/11/12
     */
    @Override
    public String openMind(List<MindNode> list, String fakeRoot) {

        Map<String,Object> data = Maps.newHashMap();
        Map<String,Object> meta = Maps.newHashMap();

        if ( fakeRoot == null ) {
            fakeRoot = "00100";
        }

        meta.put("name", "jsMind remote");
        meta.put("author", "hizzgdev@163.com");
        meta.put("version", "0.2");

        HashMap nodeList = new HashMap();
        for (int i = 0; i<list.size(); i++) {
            MindNode mindNode = new MindNode();
            mindNode = list.get(i);
            nodeList.put(mindNode.getId(),mindNode);
        }


        Set entrySet = nodeList.entrySet();
        MindNode root = null;
        for (Iterator it = entrySet.iterator();it.hasNext();){
            MindNode node = (MindNode) ( (Map.Entry) it.next()).getValue();

            if ( (node.getParentid().equals(null)) || (node.getParentid().equals("00100")) || (node.getParentid().equals(fakeRoot)) ) {
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
     * @describe:多条件分页查询
     * @param: [userid, mapname, firstDate, lastDate]
     * @return: org.springframework.data.domain.Page<com.hwj.entity.MindMap>
     * @date: 2018/11/20
     */
    @Override
    public Page<MindMap> queryMindMapByPage(String userid, String mapname, Date firstDate, Date lastDate, Pageable pageable) {
        return mindMapRepository.queryMindMapByPage(userid, mapname, firstDate, lastDate, pageable);
    }




    /**
     * @auther: Ragty
     * @describe: 获取所选子节点后的子节点
     * @param: [list, nodeid, storage]
     * @return: java.util.List<com.hwj.vo.MindNode>
     * @date: 2018/11/19
     */
    @Override
    public List<MindNode> getChild(List<MindNode> list, String nodeid, List<MindNode> storage) {

        judgeHaveChild(list, nodeid, storage);

        for (ListIterator<MindNode> it = list.listIterator(); it.hasNext();) {
            MindNode mindNode = it.next();
            if (mindNode.getId().equals(nodeid)) {
                storage.add(mindNode);
            }
        }

        return storage;
    }




    /**
     * @auther: Ragty
     * @describe: 获取子节点工具(not contain select_node)
     * @param: [list, nodeid, storage]
     * @return: java.util.List<com.hwj.vo.MindNode>
     * @date: 2018/11/19
     */
    private List<MindNode> judgeHaveChild(List<MindNode> list, String nodeid, List<MindNode> storage){

        String parentid = null;

        for(Iterator it = list.iterator(); it.hasNext();){
            MindNode mindNode = (MindNode) it.next();
            if(mindNode.getParentid().equals(nodeid)){
                parentid = mindNode.getId();            //repeat too many times

                try {
                    if( !parentid.equals(null) ){
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
     * @auther: Ragty
     * @describe: 获取删除后所剩的数据 O(1)
     * @param: [less, more]
     * @return: java.util.List<com.hwj.vo.MindNode>
     * @date: 2018/11/19
     */
    public List<MindNode> getNope(List<MindNode> less, List<MindNode> more ){

        Set<MindNode> les = new HashSet<MindNode>(less);
        Set<MindNode> mor = new HashSet<MindNode>(more);
        List<MindNode> target = new ArrayList<MindNode>();

        Iterator<MindNode> it = mor.iterator();

        while (it.hasNext()) {
            MindNode mind = it.next();
            if( les.add(mind) ) { target.add(mind); }
        }
        return target;
    }





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
