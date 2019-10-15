package com.gmail.dmytrod8;

import java.util.ArrayList;
import java.util.List;

public class JsonUsers {
    private final List<String> list;


    public JsonUsers(List<String> sourceList) {
        this.list = new ArrayList<>();
        for (int i = 0; i < sourceList.size(); i++)
            list.add(sourceList.get(i));
    }


}