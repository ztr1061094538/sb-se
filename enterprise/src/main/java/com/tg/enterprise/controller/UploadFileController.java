package com.tg.enterprise.controller;

import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.vo.BmpResulter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-19
 **/
@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadFileController {

    @Value("${tg.upload.path}")
    private String savePath;

    private final ResourceLoader resourceLoader;

    @Autowired
    public UploadFileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

/*
    @PostMapping("/upload")
    @ResponseBody
    public BmpResulter upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            String saveFileName = file.getOriginalFilename();
            File saveFile = new File(savePath + saveFileName);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return BmpResulter.newinstance(ErrorCode.OK, "上传成功", saveFileName);
            } catch (IOException e) {
                e.printStackTrace();
                return BmpResulter.newinstance(ErrorCode.UPLOAD_ERROR, "上传失败", saveFileName);
            }
        } else {
            return BmpResulter.newinstance(ErrorCode.UPLOAD_ERROR, "上传失败,文件为空", "");
        }
    }*/

    @PostMapping("/picture")
    public BmpResulter uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        AlternativeJdkIdGenerator alternativeJdkIdGenerator = new AlternativeJdkIdGenerator();
        String filename = alternativeJdkIdGenerator.generateId().toString() +
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File filepath = new File(savePath, filename);
        if (!filepath.getParentFile().exists()) {
            boolean mkdirs = filepath.getParentFile().mkdirs();
            if (!mkdirs) {
                return BmpResulter.newinstance(ErrorCode.MKDIRS_FAIL, "创建文件夹失败", filename);
            }
        }
        file.transferTo(new File(filepath.getPath()));
        return BmpResulter.newinstance(ErrorCode.OK, "上传成功", filename);
    }

    @GetMapping("/res/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> preview(@PathVariable("filename") String filename) {
        try {
            Resource resource = resourceLoader.getResource("file:" + Paths.get(savePath, filename).toString());
            if (resource.exists()) {
                return ResponseEntity.ok(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> download(@PathVariable("filename") String filename) {
        try {
            Resource resource = resourceLoader.getResource("file:" + Paths.get(savePath, filename).toString());
            if (resource.exists()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment; filename=" + filename);
                return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream"))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}


















