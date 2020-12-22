package com.bee.web.servlet;

import com.bee.commons.Constants;
import com.bee.pojo.BinaryFile;
import com.bee.pojo.TextFile;
import com.bee.pojo.User;
import com.bee.service.CloudResourceManagementService;
import com.bee.service.impl.CloudResourceManagementServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;


@WebServlet("/downloadOrView.do")
public class DownloadToLocalOrViewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(Constants.USER_SESSION_KEY);
            int vipFlag = user.getMember();
            String operate = req.getParameter("operate");
            String typeFlag = req.getParameter("typeFlag");
            boolean pdf = isPdf(req);
            if ("document".equals(typeFlag)) {
                if ("download".equals(operate)) {
                    if (vipFlag == 1) {
                        if (pdf) {
                            this.vipDownloadPictureOrPdf(req, resp);
                        } else {
                            this.vipDownloadText(req, resp);
                        }
                    } else {
                        if (pdf) {
                            this.commonDownloadPictureOrPdf(req, resp);
                        } else {
                            this.commonDownloadText(req, resp);
                        }
                    }
                } else {
                    if (pdf) {
                        this.preViewPictureOrPdf(req, resp);
                    } else {

                        this.preViewText(req, resp);
                    }
                }
            } else if ("picture".equals(typeFlag)) {
                if ("download".equals(operate)) {
                    if (vipFlag == 1) {
                        this.vipDownloadPictureOrPdf(req, resp);
                    } else {
                        this.commonDownloadPictureOrPdf(req, resp);
                    }
                } else {
                    this.preViewPictureOrPdf(req, resp);
                }
            } else {
                resp.sendRedirect("error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isPdf(HttpServletRequest req) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constants.USER_SESSION_KEY);
        int userId = user.getUserId();
        CloudResourceManagementService cloudResourceManagementService = new CloudResourceManagementServiceImpl();
        String fileType = cloudResourceManagementService.onlyFindFileType(userId, fileName);
        return "pdf".equals(fileType) || "PDF".equals(fileType);
    }

    private void preViewPictureOrPdf(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getRequestDispatcher("seePicture.do").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void commonDownloadPictureOrPdf(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String responseType = getResponseType(req, resp, false);
        this.AllTypesCommonDownloadPart(req, resp, responseType, 100);
    }

    private void vipDownloadPictureOrPdf(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String responseType = getResponseType(req, resp, false);
        this.AllTypesCommonDownloadPart(req, resp, responseType, 1000);
    }

    private void commonDownloadText(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String responseType = getResponseType(req, resp, true);
        this.AllTypesCommonDownloadPart(req, resp, responseType, 100);
    }

    private void vipDownloadText(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String responseType = getResponseType(req, resp, true);
        this.AllTypesCommonDownloadPart(req, resp, responseType, 1000);
    }

    public String getResponseType(HttpServletRequest req, HttpServletResponse resp, boolean downLoadText) throws ServletException, IOException {
        String fileType;
        TextFile textFile;
        BinaryFile binaryFile;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constants.USER_SESSION_KEY);
        int userId = user.getUserId();
        String responseType = null;
        try {
            String fileName = req.getParameter("fileName");
            CloudResourceManagementService cloudResourceManagementService = new CloudResourceManagementServiceImpl();
            if (downLoadText) {
                textFile = cloudResourceManagementService.findTextFileByUserIdAndFileName(userId, fileName);
                fileType = textFile.getFileType();
            } else {
                binaryFile = cloudResourceManagementService.findBinaryFileByUserIdAndFileName(userId, fileName);
                fileType = binaryFile.getFileType();
            }
            if ("txt".equals(fileType)) {
                responseType = "text/plain";
            } else if ("gif".equals(fileType) || "gif".toUpperCase().equals(fileType)) {
                responseType = "image/gif";
            } else if ("png".equals(fileType) || "png".toUpperCase().equals(fileType)) {
                responseType = "image/png";
            } else if ("jpeg".equals(fileType) || "jpeg".toUpperCase().equals(fileType) || "jpg".equals(fileType) || "jpg".toUpperCase().equals(fileType)) {
                responseType = "image/jpeg";
            } else if ("pdf".equals(fileType) || "PDF".equals(fileType)) {
                responseType = "application/pdf";
            } else {
                resp.sendRedirect("error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseType;
    }

    private void commonIndownToLocal(File recordFile, InputStream is, HttpSession session, String localPath, long speed) throws IOException {
        try {
            session.setAttribute(Constants.DOWN_OK_OR_NOT, false); //用来控制文件删除
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(recordFile));
            if (dataInputStream.available() != 0) {
                long haveRead = dataInputStream.readLong();
                is.skip(haveRead);
                dataInputStream.close();
            }
            boolean needStop;
            long current = 0;
            byte[] buff = new byte[1024];
            int len;
            FileOutputStream fos = new FileOutputStream(localPath, true);
            long haveRead = 0;
            long startTime = System.currentTimeMillis();
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(recordFile));
            while ((len = is.read(buff)) != -1) {
                current = current + len;
                fos.write(buff);
                haveRead += 1024;
                needStop = (boolean) session.getAttribute("needStop");
                while (needStop) {
                    dataOutputStream.writeLong(haveRead);
                    dataOutputStream.close();
                    is.close();
                    fos.close();
                    return;
                }
                if (current > speed) {
                    startPause(startTime + 1000);
                    current = 0;
                    startTime = System.currentTimeMillis();
                }
            }
            dataOutputStream.close();
            is.close();
            fos.close();
            dataInputStream.close();
            session.setAttribute(Constants.DOWN_OK_OR_NOT, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AllTypesCommonDownloadPart(HttpServletRequest req, HttpServletResponse resp, String responseType, long speedLimit) throws IOException {
        HttpSession session = req.getSession();
        ConcurrentSkipListSet<String> skipListSet = (ConcurrentSkipListSet<String>) session.getAttribute(Constants.DOWNLOAD_LIST);
        ConcurrentHashMap<String, Integer> hashMap = (ConcurrentHashMap<String, Integer>) session.getAttribute(Constants.SAME_FILE_INCREASE_ORDER);
        ArrayList<String> arrayList = (ArrayList<String>) session.getAttribute(Constants.ALL_HAVE_DOWN_FILE);
        User user = (User) session.getAttribute(Constants.USER_SESSION_KEY);
        int userId = user.getUserId();
        session.setAttribute("needStop", false);
        String fileName = req.getParameter("fileName");
        //持同时下载多个同名文件
        if (!skipListSet.contains(fileName) && !arrayList.contains(fileName)) {
            arrayList.add(fileName);
            skipListSet.add(fileName);
            hashMap.put(fileName, 0);
        } else {
            if ((boolean) session.getAttribute(Constants.DOWN_OK_OR_NOT)) {
                Integer integer = hashMap.get(fileName);
                hashMap.put(fileName, integer + 1);
                skipListSet.add(fileName);
            }
        }
        String path;
        Integer order = hashMap.get(fileName);
        CloudResourceManagementService cloudResourceManagementService = new CloudResourceManagementServiceImpl();
        String localPath;//下载到本地的文件名
        long speed = 1024 * speedLimit;//限制下载速度为1000k/s,
        if ("text/plain".equals(responseType)) {
            if (session.getAttribute(fileName) == null) {
                path = "D:\\textRecord" + UUID.randomUUID().toString() + userId + ".txt";
                session.setAttribute(fileName, path);
            } else {
                path = (String) session.getAttribute(fileName);
            }
            File recordFile = new File(path);
            if (!recordFile.exists()) {
                recordFile.createNewFile();
            }
            TextFile textFile = cloudResourceManagementService.findTextFileByUserIdAndFileName(userId, fileName);
            String fileType = textFile.getFileType();
            order = hashMap.get(fileName);
            if (order != 0)
                localPath = "D:\\" + fileName + "." + fileType;
            else
                localPath = "D:\\" + fileName + "(" + order + ")" + "." + fileType;
            String fileContent = textFile.getFileContent();
            InputStream is = new ByteArrayInputStream(fileContent.getBytes());
            this.commonIndownToLocal(recordFile, is, session, localPath, speed);
            boolean canDelete = (boolean) session.getAttribute(Constants.DOWN_OK_OR_NOT);//用来控制文件删除
            if (canDelete) {
                recordFile.delete();
                //下载完毕 把文件从skipListSet中移除
                skipListSet.remove(fileName);
                resp.sendRedirect("filemanage/documentDownFinish.jsp");
            }
        } else {
            if (session.getAttribute(fileName) == null) {
                path = "D:\\binRecord" + UUID.randomUUID().toString() + userId + ".txt";
                session.setAttribute(fileName, path);
            } else {
                path = (String) session.getAttribute(fileName);
            }
            File recordFile = new File(path);
            if (!recordFile.exists()) {
                recordFile.createNewFile();
            }
            BinaryFile binaryFile = cloudResourceManagementService.findBinaryFileByUserIdAndFileName(userId, fileName);
            String fileType = binaryFile.getFileType();
            order = hashMap.get(fileName);
            if (order == 0)
                localPath = "D:\\" + fileName + "." + fileType;
            else
                localPath = "D:\\" + fileName + "(" + order + ")" + "." + fileType;
            InputStream is = binaryFile.getIs();
            this.commonIndownToLocal(recordFile, is, session, localPath, speed);
            boolean canDelete = (boolean) session.getAttribute(Constants.DOWN_OK_OR_NOT);//用来控制文件删除
            if (canDelete) {
                recordFile.delete();
                //下载完毕 把文件从skipListSet中移除
                skipListSet.remove(fileName);
                if ("application/pdf".equals(responseType))
                    resp.sendRedirect("filemanage/documentDownFinish.jsp");
                else
                    resp.sendRedirect("filemanage/pictureDownFinish.jsp");
            }
        }
    }

    private void startPause(long time) {
        while (true) {
            if (System.currentTimeMillis() > time) {
                break;
            }
        }
    }

    private void preViewText(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constants.USER_SESSION_KEY);
        int userId = user.getUserId();
        String fileName = req.getParameter("fileName");
        CloudResourceManagementService cloudResourceManagementService = new CloudResourceManagementServiceImpl();
        TextFile textFile = cloudResourceManagementService.findTextFileByUserIdAndFileName(userId, fileName);
        String fileContent = textFile.getFileContent();
        session.setAttribute("documentContent", fileContent);
        resp.sendRedirect("filemanage/documentPreview.jsp");
    }
}