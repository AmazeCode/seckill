package com.seckill.utils;

import com.seckill.dto.FtpDto;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * FTP上传文件工具类
 */
public class FtpUtil {

    private static FTPClient ftp;

    /**
     * 获取ftp连接
     * @param ftpDto
     * @return
     * @throws Exception
     */
    public static boolean connectFtp(FtpDto ftpDto) throws Exception {
        ftp = new FTPClient();
        boolean flag = false;
        int reply;
        if (ftpDto.getPort() == null) {
            ftp.connect(ftpDto.getIpAddr(), 21);
        } else {
            ftp.connect(ftpDto.getIpAddr(), ftpDto.getPort());
        }
        ftp.login(ftpDto.getUserName(), ftpDto.getPwd());
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return flag;
        }
        ftp.changeWorkingDirectory(ftpDto.getPath());
        flag = true;
        return flag;
    }

    /**
     * 关闭ftp连接
     */
    public static void closeFtp() {
        if (ftp != null && ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ftp上传文件
     * @param file
     * @throws Exception
     */
    public static void upload(File file) throws Exception {
        if (file.isDirectory()) {
            ftp.makeDirectory(file.getName());
            ftp.changeWorkingDirectory(file.getName());
            String[] files = file.list();
            for (String fstr : files) {
                File file1 = new File(file.getPath() + "/" + fstr);
                if (file1.isDirectory()) {
                    upload(file1);
                    ftp.changeToParentDirectory();
                } else {
                    File file2 = new File(file.getPath() + "/" + fstr);
                    FileInputStream input = new FileInputStream(file2);
                    ftp.storeFile(file2.getName(), input);
                    input.close();
                }
            }
        } else {
            File file2 = new File(file.getPath());
            FileInputStream input = new FileInputStream(file2);
            ftp.storeFile(file2.getName(), input);
            input.close();
        }
    }

    /**
     * 测试
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        FtpDto f = new FtpDto();
        f.setIpAddr("your ip");
        f.setUserName("username");
        f.setPwd("password");
        FtpUtil.connectFtp(f);
        File file = new File("F:/commons-net-3.6.jar");
        FtpUtil.upload(file);//把文件上传在ftp上
        System.out.println("上传文件完成。。。。");
    }
}
