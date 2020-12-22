package com.bee.service.impl;

import com.bee.dao.CloudResourceManageDao;
import com.bee.dao.impl.CloudResourceManageDaoImpl;
import com.bee.pojo.BinaryFile;
import com.bee.pojo.TextFile;
import com.bee.service.CloudResourceManagementService;

import java.io.InputStream;
import java.util.List;

public class CloudResourceManagementServiceImpl implements CloudResourceManagementService {
    @Override
    public List<String> showFileByUserIdAndType(int userId, String fileType) {
        CloudResourceManageDao cloudResourceManageDao = new CloudResourceManageDaoImpl();
        return cloudResourceManageDao.selectFileByUserIdAndType(userId, fileType);
    }

    @Override
    public TextFile findTextFileByUserIdAndFileName(int userId, String fileName) {
        CloudResourceManageDao cloudResourceManageDao = new CloudResourceManageDaoImpl();
        return cloudResourceManageDao.selectTextFileByUserIdAndFileName(userId, fileName);
    }

    @Override
    public void uploadFile(int userId, String fileName, String fileType, InputStream in, String content) {
        CloudResourceManageDao cloudResourceManageDao = new CloudResourceManageDaoImpl();
        cloudResourceManageDao.insertByFileNameAndTypeAndDetails(userId, fileName, fileType, in, content);
    }

    @Override
    public BinaryFile findBinaryFileByUserIdAndFileName(int userId, String fileName) {
        CloudResourceManageDao cloudResourceManageDao = new CloudResourceManageDaoImpl();
        return cloudResourceManageDao.selectBinaryFileByUserIdAndFileName(userId, fileName);
    }

    @Override
    public String onlyFindFileType(int userId, String fileName) {
        CloudResourceManageDao cloudResourceManageDao = new CloudResourceManageDaoImpl();
        return cloudResourceManageDao.onlySelectFileType(userId, fileName);
    }
}