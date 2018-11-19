package com.hwj.service;


import com.hwj.entity.MindMap;
import com.hwj.vo.MindNode;

import java.util.List;

public interface MindMapService {

    void save(MindMap mindMap);

    void deleteMap(String mapid);

    String openMind (List<MindNode> list);

    MindMap queryMindByMapid(String mapid);

    List<MindNode> getChild(List<MindNode> list, String nodeid, List<MindNode> storage);

    List<MindNode> getNope(List<MindNode> less, List<MindNode> more);

}
