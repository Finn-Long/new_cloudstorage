package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getAllFiles(Integer userId);

    @Select("SELECT FROM FILES WHERE filename = #{fileName}")
    File getFile(String fileName);

    @Select("SELECT FROM FILES WHERE fileId = #{file_id}")
    File getFileById(Integer file_id);

    @Select("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, " +
            "#{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{file_id}")
    int delete(Integer file_id);
}
