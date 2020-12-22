package com.bee.dao;

import com.bee.pojo.BinaryFile;
import com.bee.pojo.TextFile;

import java.io.InputStream;
import java.util.List;

public interface CloudResourceManageDao {

    List<String> selectFileByUserIdAndType(int userid, String type);

    TextFile selectTextFileByUserIdAndFileName(int userId, String fileName);

    void insertByFileNameAndTypeAndDetails(int userid, String fileName, String fileType, InputStream in, String fileContent);

    BinaryFile selectBinaryFileByUserIdAndFileName(int userId, String fileName);

    String  onlySelectFileType(int userId, String fileName);
}
