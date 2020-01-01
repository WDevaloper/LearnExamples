package com.gavin.asmdemo.db.base.upgrade.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @Describe:
 * @Author: wfy
 */
public class UpdateDb {

    private String sql_rename;
    private String sql_create;
    private String sql_insert;
    private String sql_delete;

    public UpdateDb(Element element) {
        NodeList sql_rename = element.getElementsByTagName("sql_rename");
        this.sql_rename = sql_rename.item(0).getTextContent();

        NodeList sql_create = element.getElementsByTagName("sql_create");
        this.sql_create = sql_create.item(0).getTextContent();

        NodeList sql_insert = element.getElementsByTagName("sql_insert");
        this.sql_insert = sql_insert.item(0).getTextContent();

        NodeList sql_delete = element.getElementsByTagName("sql_delete");
        this.sql_delete = sql_insert.item(0).getTextContent();
    }


    public String getSql_rename() {
        return sql_rename;
    }

    public String getSql_create() {
        return sql_create;
    }

    public String getSql_insert() {
        return sql_insert;
    }

    public String getSql_delete() {
        return sql_delete;
    }

    @Override
    public String toString() {
        return "UpdateDb{" +
                "sql_rename='" + sql_rename + '\'' +
                ", sql_create='" + sql_create + '\'' +
                ", sql_insert='" + sql_insert + '\'' +
                ", sql_delete='" + sql_delete + '\'' +
                '}';
    }
}
