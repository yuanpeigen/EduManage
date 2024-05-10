package edu.hitwh.homework.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {
    public static  String imgsrc = "E:/WebHomework/EduManage/WebHomework/image/";
    public static String uploadImg(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        //获取原文件的后缀名
        if (originalFilename != null) {
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 使用UUID和文件扩展名来创建一个新的文件名
            String newFilename = "img-" +UUID.randomUUID().toString()+ fileExtension;
            File uploadDir = new File(imgsrc);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            file.transferTo(new File(imgsrc + newFilename));

            // String url ="file:///"+imgsrc +newFilename;
            String url ="http://localhost:8080"+"/uploadimg/" +newFilename;
            return url;
        }
        return "上传失败";
    }
}
