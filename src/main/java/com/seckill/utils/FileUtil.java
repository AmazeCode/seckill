package com.seckill.utils;

import java.io.*;
import java.util.ArrayList;

/**
 * 文件工具类
 */
public class FileUtil {

    /**
     * 创建多层文件夹
     * @param dirPath 需要创建的文件路径
     */
    public static void dirExists(String dirPath) {
        File file = new File(dirPath);
        if (file.exists() == false){
            file.mkdirs();//创建文件夹
        }
    }

    /**
     * 先根遍历序递归删除文件夹
     * @param dirFile
     * @return 删除成功返回true,失败返回false
     */
    public static boolean deleteDirOrFile(File dirFile) {

        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {
            for (File file : dirFile.listFiles()) {
                deleteDirOrFile(file);
            }
        }
        return dirFile.delete();
    }

    /**
     * 获取文件大小
     * @param file
     * @return
     */
    public static int getFileSize(String file){

        File f = new File(file);
        if (f.exists() && f.isFile()){
            return (int)f.length();
        }else{
            return 0;
        }
    }

    /**
     * 通过文件所在路径，修改文件名称
     * @param filePath 文件全路径，例子：G:/kdm/test/124.pem.smpteKDM.xml
     * @param newFileName 新的文件名称,仅指文件名称，不带后缀
     * @return
     */
    public static String modifyFileName(String filePath, String newFileName) {
        File f = new File(filePath);
        if (!f.exists()) { // 判断原文件是否存在（防止文件名冲突）
            return null;
        }
        newFileName = newFileName.trim();
        if ("".equals(newFileName) || newFileName == null) // 文件名不能为空
            return null;
        String newFilePath = null;
        if (f.isDirectory()) { // 判断是否为文件夹
            newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/" + newFileName;
        } else {
            newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/" + newFileName
                    + filePath.substring(filePath.lastIndexOf("."));
        }
        File nf = new File(newFilePath);
        try {
            f.renameTo(nf); // 修改文件名
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
        return newFilePath;
    }

    /**
     * 获取文件夹下所有文件路径和文件名
     * @param folderPath 文件路径字符串
     */
    public static void getFileList(String folderPath) {
        File file = new File(folderPath);
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                System.out.println("文件路径"+fileList[i].getPath());
                String fileName = fileList[i].getName();
                System.out.println("文件：" + fileName);
            }
            if (fileList[i].isDirectory()) {
                String fileName = fileList[i].getName();
                System.out.println("目录：" + fileName);
            }
        }
    }

    /**
     * 方式一：FileReader读取txt文件，把内容放到数组中
     * @param name
     * @return
     */
    public static String[] toArrayByFileReader(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(name);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            String s = arrayList.get(i);
            array[i] = s;
        }
        // 返回数组
        return array;
    }

    /**
     * 方式二:使用InputStreamReader方式,读取txt文件,把内容存放到数组中
     * @param name
     * @return
     */
    public static String[] toArrayByInputStreamReader(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File(name);
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            inputReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            String s = arrayList.get(i);
            array[i] = s;
        }
        // 返回数组
        return array;
    }

    /**
     * 方式三:使用RandomAccessFile方式,把读取内容放到数组中
     * @param name
     * @return
     */
    public static String[] toArrayByRandomAccessFile(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File(name);
            RandomAccessFile fileR = new RandomAccessFile(file,"r");
            // 按行读取字符串
            String str = null;
            while ((str = fileR.readLine())!= null) {
                arrayList.add(str);
            }
            fileR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            String s = arrayList.get(i);
            array[i] = s;
        }
        // 返回数组
        return array;
    }
}
