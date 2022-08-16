package com.udacity.jwdnd.course1.cloudstorage.Service;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer storeFile(MultipartFile file, Integer userId) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileSize = String.valueOf(file.getSize());
        String fileType = file.getContentType();
        return fileMapper.insert(new File(null, fileName, fileType, fileSize, userId, file.getBytes()));
    }

    public List<File> getAllFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }

    public File getFileByName(String fileName) {
        return fileMapper.getFile(fileName);
    }

    public File getFileById(Integer file_id) {
        return fileMapper.getFileById(file_id);
    }

    public int deleteFile(Integer file_id) {
        return fileMapper.delete(file_id);
    }
}
