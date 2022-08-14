package com.udacity.jwdnd.course1.cloudstorage.Service;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int storeFile(File file) {
        return fileMapper.insert(file);
    }

    public List<File> getAllFiles() {
        return fileMapper.getAllFiles();
    }

    public File getFile(String fileName) {
        return fileMapper.getFile(fileName);
    }

    public int deleteFile(String fileName) {
        return fileMapper.delete(fileName);
    }
}
