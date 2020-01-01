package com.gavin.asmdemo.db.base.upgrade.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @Describe:
 * @Author: wfy
 */
public class UpdateStep {
    private List<UpdateDb> updateDbs;
    private String versionFrom;
    private String versionTo;

    public UpdateStep(Element ele) {
        versionFrom = ele.getAttribute("versionFrom");
        versionTo = ele.getAttribute("versionTo");
        this.updateDbs = new ArrayList<>();
        NodeList dbs = ele.getElementsByTagName("updateDb");
        for (int i = 0; i < dbs.getLength(); i++) {
            Element element = (Element) dbs.item(i);
            UpdateDb updateDb = new UpdateDb(element);
            this.updateDbs.add(updateDb);
        }
    }

    public List<UpdateDb> getUpdateDbs() {
        return updateDbs;
    }

    public String getVersionFrom() {
        return versionFrom;
    }

    public String getVersionTo() {
        return versionTo;
    }

    @Override
    public String toString() {
        return "UpdateStep{" +
                "updateDbs=" + updateDbs +
                ", versionFrom='" + versionFrom + '\'' +
                ", versionTo='" + versionTo + '\'' +
                '}';
    }
}
