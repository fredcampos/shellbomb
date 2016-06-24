package com.shellbomb;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DirectoryTraverse {

    private static DirectoryTraverse instance;

    private DirectoryTraverse(){
    }

    public static DirectoryTraverse instance(){
        if (instance == null){
            instance = new DirectoryTraverse();
        }
        return instance;
    }


    public List<FileHolder> listFiles(String path, String context) {
        if (context == null){
            context = "";
        }

        File files[] = new File(path).listFiles();
        List<FileHolder> fileList = new ArrayList<FileHolder>();
        List<FileHolder> dirList = new ArrayList<FileHolder>();
        List<FileHolder> allFiles = new ArrayList<FileHolder>();
        if (!path.equals("")) {
            FileHolder up = new FileHolder("..", "directory", "", null);
            File current = new File(path);

            if (path.contains(File.separator)) {
                up.link = path.substring(0, path.lastIndexOf(File.separator));
            } else {
                up.link = "";
            }
            up.fullPath = current.getParentFile().getAbsolutePath();
            dirList.add(up);

        }
        for (File file : files) {
            try {
                String name = file.getName();
                FileHolder holder = new FileHolder();
                if (!name.startsWith(".")) {
                    holder.name = name;
                    holder.fullPath = file.getAbsolutePath();
                    if (!path.equals("")) {
                        holder.link = URLEncoder.encode(path + "/" + holder.name, "UTF-8");
                    } else {
                        holder.link = URLEncoder.encode(holder.name, "UTF-8");
                    }
                    if (file.isDirectory()) {
                        try {
                            holder.type = "directory";
                            dirList.add(holder);
                            holder.size = IOUtils.formatSize(FileUtils.sizeOfDirectory(file));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        holder.size = IOUtils.formatSize(file.length()) + "";
                        holder.type = "file";
                        holder.link = URLEncoder.encode(holder.link, "UTF-8");

                        holder.downloadLink = context + "/download?path=" + holder.link;

                        fileList.add(holder);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        allFiles.addAll(dirList);
        allFiles.addAll(fileList);
        return allFiles;
    }

}
