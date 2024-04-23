package com.clx.controller;

import com.clx.common.R;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${panda-delivery.path}")
    private String basePath;

    /**
     * File Upload
     * @param file
     * @return
     */
    @PutMapping("/upload")
    public R<String> upload(MultipartFile file){
        // file is a temporary file and needs to be transferred to the specified location,
        // otherwise the temporary file will be deleted after this request is completed.
        log.info(file.toString());

        //ORIGINAL FILE NAME
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //Use UUID to regenerate file names to prevent file overwriting caused by duplicate file names.
        String fileName = UUID.randomUUID().toString() + suffix;

        //Create a directory object
        File dir = new File(basePath);

        //Determine whether the current directory exists
        if (!dir.exists()){
            //The directory does not exist and needs to be created
            dir.mkdirs();
        }

        try {
            //Transfer temporary files to a specified location
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    /**
     * Download Document
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){

        try {
            //Input stream, read the file content through the input stream
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //Output stream, write the file back to the browser through the output stream,
            // and display the image in the browser
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ( (len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            //Close resource
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
