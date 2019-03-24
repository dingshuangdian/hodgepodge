package com.lsqidsd.hodgepodge.utils;

import java.io.File;

public class FileUtil {

    /**
     * 判断文件是否存在
     * @param fileName
     * @return
     */
    public static boolean fileIsExists(String fileName){
        try{
            File f=new File(fileName);
            if(!f.exists()){
                return false;
            }
        }catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }


    /**
     * 删除某个文件夹下所有文件 递归
     * @param root
     */
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }




}
