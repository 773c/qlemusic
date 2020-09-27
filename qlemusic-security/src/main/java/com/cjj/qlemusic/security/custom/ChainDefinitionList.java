package com.cjj.qlemusic.security.custom;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;


@ConfigurationProperties(prefix = "shiro")
public class ChainDefinitionList {
    private List<Map<String,String>> lists;

    public List<Map<String, String>> getLists() {
        return lists;
    }

    public void setLists(List<Map<String, String>> lists) {
        this.lists = lists;
    }

}
