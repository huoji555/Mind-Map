package com.hwj.service;


import com.hwj.entity.MindMap;
import com.hwj.vo.MindNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface MindMapService {

    void save(MindMap mindMap);

    void deleteMap(String mapid);

    void updateMindMap(String mapList, Date updateDate, String mapname, Integer id);

    String openMind (List<MindNode> list, String fakeRoot);

    MindMap queryMindByMapid(String mapid);

    Page<MindMap> queryMindMapByPage(String userid, String mapname, Date firstDate, Date lastDate, Pageable pageable);

    List<MindNode> getChild(List<MindNode> list, String nodeid, List<MindNode> storage);

    List<MindNode> getNope(List<MindNode> less, List<MindNode> more);



}
