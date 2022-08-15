package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.Service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/delete_file/{file_id}")
    public String delete_file(@PathVariable("file_id") Integer file_id, Model model) {
        try{
            fileService.deleteFile(file_id);
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMsg", "Successfully deleted the file");
        }catch(Exception e) {
            model.addAttribute("isError", true);
            model.addAttribute("errorMsg", "Error, please try delete again");
        }

        return "result";
    }

    @GetMapping(value = "/get_file/{file_id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> get_file(@PathVariable("file_id") Integer file_id) {
        File file = fileService.getFileById(file_id);
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + file.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(file.getContentType())).body(resource);
    }

    @PostMapping("/new_file")
    public String upload_file(Authentication auth, @RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {
        Integer userId = userService.getUser(auth.getName()).getUserId();
        List<File> file_List = fileService.getAllFiles(userId);
        String err_msg = null;

        if (file.getBytes().length == 0) {
            err_msg = "File is empty, please select file again";
        }

        String fileName = file.getOriginalFilename();
        for (File file_1: file_List
             ){
            if (file_1.getFileName().equals(fileName)) {
                err_msg = "File name \'" + fileName + "\' already exists";
                break;
            }
        }

        if (err_msg == null) {
            try {
                fileService.storeFile(file, userId);
                model.addAttribute("isSuccess", true);
                model.addAttribute("successMsg", "file " + file.getOriginalFilename() + " has been uploaded");
            }catch(IOException e) {
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "Please try again later");
            }
        }else {
            model.addAttribute("isError", true);
            model.addAttribute("errorMsg", err_msg);
        }
        System.out.println(err_msg);
        return "result";
    }
}
