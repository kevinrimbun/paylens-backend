package net.backend.paylens.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import net.backend.paylens.model.entity.FileUpload;

public interface FileService {
    FileUpload store(MultipartFile file) throws IOException, Exception;

    FileUpload getFile(String id);

    Stream<FileUpload> getFiles();

    // ResponseData<Object> changePassword(long id, ChangePasswordDto request) throws Exception;
    FileUpload changeFileUpload(String id, Long userId, MultipartFile file) throws IOException, Exception;


}