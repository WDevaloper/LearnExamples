package com.gavin.asmdemo.db.base.upgrade.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @Describe:xml根节点
 * @Author: wfy
 */
public class UpdateXml {
    private List<UpdateStep> updateSteps;

    public UpdateXml(Document document) {
        this.updateSteps = new ArrayList<>();
        //获取升级脚本，解析根节点
        NodeList updateSteps = document.getElementsByTagName("updateStep");
        for (int i = 0; i < updateSteps.getLength(); i++) {
            Element ele = (Element) updateSteps.item(i);
            UpdateStep step = new UpdateStep(ele);
            this.updateSteps.add(step);
        }
    }

    public List<UpdateStep> getUpdateSteps() {
        return updateSteps;
    }

    @Override
    public String toString() {
        return "UpdateXml{" +
                "updateSteps=" + updateSteps +
                '}';
    }
}
