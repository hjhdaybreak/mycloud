package com.bee.test;

import com.bee.service.CloudResourceManagementService;
import com.bee.service.impl.CloudResourceManagementServiceImpl;

import java.util.List;

public class TestShow {
    public static void main(String[] args) {
        CloudResourceManagementService cloudResourceManagementService=new CloudResourceManagementServiceImpl();
        List<String> list = cloudResourceManagementService.showFileByUserIdAndType(24, "document");
        for (String s : list) {
            System.out.println(s);
        }
    }
}