package com.hwj;

import com.hwj.entity.MindMap;
import com.hwj.entity.Zsd;
import com.hwj.repository.MindMapRepository;
import com.hwj.repository.ZsdRepository;
import com.hwj.service.MindMapService;
import com.hwj.util.JsonAnalyze;
import com.hwj.vo.MindNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.ListIterator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZsdDataTransfer {

    @Autowired
    private ZsdRepository zsdRepository;
    @Autowired
    private MindMapRepository mindMapRepository;
    @Autowired
    private JsonAnalyze jsonAnalyze;


    @Test
    public void zsdTransfer() throws Exception {

        List<MindMap> list = mindMapRepository.findAll();

        for(ListIterator<MindMap> it = list.listIterator(); it.hasNext();) {
            MindMap mindMap = it.next();
            System.out.println("开始操作："+mindMap.getMapname());

            List<MindNode> nodeList = jsonAnalyze.parseList(mindMap.getMapList());

            for(ListIterator<MindNode> it1 = nodeList.listIterator(); it1.hasNext();) {
                MindNode mindNode = it1.next();
                try {
                    Zsd zsd = zsdRepository.findZsdByNodeid(mindNode.getId());

                    if (zsd != null && !zsd.getUserid().equals("null")) {
                        zsd.setMapid(mindMap.getMapid());
                        zsdRepository.save(zsd);
                    }

                } catch (Exception e) {

                }


            }


        }



    }



}
