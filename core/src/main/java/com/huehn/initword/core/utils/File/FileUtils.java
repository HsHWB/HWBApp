package com.huehn.initword.core.utils.File;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.huehn.initword.core.app.App;
import com.huehn.initword.core.utils.Exception.AppException;
import com.huehn.initword.core.utils.Log.LogManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

public class FileUtils {

    public final static String TAG = "FileUtils";

    /**
     * 创建文件
     * @param fileName 文件名
     * @return
     */
    private static FileResult createFile(String fileName){
        String path = getAppDataDirectory(fileName, true);
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(fileName)){
            return new FileResult(FileResult.FILE_CREATE_FAIL, null);
        }
        if (!"/".equals(String.valueOf(path.charAt(path.length() - 1)))){
            path = path + "/";
        }
        File file = new File(path + fileName);
        boolean isCreate = false;
        if (file.exists()){
            LogManager.d("File is exits");
            return new FileResult(FileResult.FILE_HAS_EXITS, file);
        }else {
            try {
                isCreate = file.createNewFile();
                LogManager.d("File is create");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (isCreate) {
            return new FileResult(FileResult.FILE_CREATE_SUCCESS, file);
        }else {
            LogManager.d("File is error");
            return new FileResult(FileResult.FILE_CREATE_FAIL, file);
        }
    }

    /**
     * 保存数据至文件中
     * @param name 传入文件名
     * @param stringBuilder 传入要保存的字符
     * OutputStream(String fileName,boolean append)：使用给定的名称name创建一个FileOutputStream对象，
     * 如果第二个参数为 true，则将字节写入文件末尾处，而不是写入文件开始处。
     */
    public static void saveFile(String name, StringBuilder stringBuilder){

        if (TextUtils.isEmpty(name) || stringBuilder == null || TextUtils.isEmpty(stringBuilder.toString())){
            return;
        }
        try {
            FileResult result = createFile(name);
            if (result.isSuccessToGetFile()) {
                FileOutputStream fileOutputStream = new FileOutputStream(result.getFile(), true);
                fileOutputStream.write(stringBuilder.toString().getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * 获取手机外部储存的路径，如果没有外部储存或者不能访问，通过canUserAppStorage判断是否能获取内部储存路径
     *
     * https://blog.csdn.net/csdn_aiyang/article/details/80665185
     * 在外部储存中建立App专属文件，这些文件会随着应用卸载而被删除（内部储存和外部储存都会被删除）
     *
     * 内部储存路径，内部存储不是内存：data/data/< package name >/files/
     * 内置储存卡储存路径(机身储存，也是定义上的外部储存)：/storage/emulated/0/Android/data/com.huehn.initword.test/files/
     * 外部储存getExternalFilesDir(null)参数传入的为null，这样默认访问的是files文件夹，
     * 我们可以指定子文件夹File externalFilesDir = getExternalFilesDir("Caches");则访问
     * /storage/emulated/0/Android/data/com.huehn.initword.test/Caches
     *
     * 4.4以后外部存储就包含两部分了，其中通过getExternalStorageDirectory获取的是机身存储的外部存储，
     * 而外置SD卡我们则需要通过getExternalDirs遍历来获取了
     * @param canUserAppStorage  是否允许使用内部储存
     */
    private static String getAppDataDirectory(String fileName, boolean canUserAppStorage){
        String phoneStorageDirectory = null;
        //判断外部存储是否可用(是否接入了sd卡)
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
           try {
               phoneStorageDirectory = App.getApp().getExternalFilesDir(null).getAbsolutePath();
           }catch (NullPointerException e){
               e.printStackTrace();
           }
        }else if (canUserAppStorage){
            phoneStorageDirectory = App.getApp().getFilesDir() + File.separator;
        }else {
            //没有外部储存的使用权限，也不能使用内部储存时，抛出异常
            throw AppException.hasNotStoragePermission();
        }
        LogManager.d(TAG, phoneStorageDirectory);
        return phoneStorageDirectory;
    }

}
