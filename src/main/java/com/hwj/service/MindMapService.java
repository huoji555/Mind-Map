package com.hwj.service;


import com.hwj.entity.MindMap;
import com.hwj.vo.MindNode;

import java.util.List;

public interface MindMapService {

    void save(MindMap mindMap);

    String openMind (List<MindNode> list);

}
