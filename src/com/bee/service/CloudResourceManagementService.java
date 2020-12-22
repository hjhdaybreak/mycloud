package com.bee.service;

import com.bee.pojo.BinaryFile;
import com.bee.pojo.TextFile;

import java.io.InputStream;
import java.util.List;

public interface CloudResourceManagementService {

    List<String> showFileByUserIdAndType(int userId, String fileType);

    TextFile findTextFileByUserIdAndFileName(int userId, String fileName);

    void uploadFile(int userid, String filename, String filetype, InputStream in, String content);

    BinaryFile findBinaryFileByUserIdAndFileName(int userId, String fileName);

    String onlyFindFileType(int userId, String fileName);
}
