package net.backend.paylens.service;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.backend.paylens.model.entity.FileUpload;
import net.backend.paylens.model.entity.User;
import net.backend.paylens.repository.FileRepository;
import net.backend.paylens.repository.UserRepository;
import net.backend.paylens.validator.UserValidator;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserValidator userValidator;
    
    private User user;
    private FileUpload fileUpload;


    @Override
    public FileUpload store(MultipartFile file) throws IOException, Exception {
        // TODO Auto-generated method stub
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileUpload fileUpload = new FileUpload(fileName, file.getContentType(), file.getBytes());
    
        return fileRepository.save(fileUpload);
    }

    @Override
    public FileUpload getFile(String id) {
        // TODO Auto-generated method stub
        return fileRepository.findById(id).get();
    }

    @Override
    public Stream<FileUpload> getFiles() {
        // TODO Auto-generated method stub
        return fileRepository.findAll().stream();
    }

    @Override
    public FileUpload changeFileUpload(String id, Long userId, MultipartFile file) throws IOException, Exception {
        // TODO Auto-generated method stub
        Optional<User> userOpt = userRepository.findById(userId);
        userValidator.validateUserNotFound(userOpt);

        Optional<FileUpload> fileUploadOpt = fileRepository.findById(id);
        fileUpload = fileUploadOpt.get();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileUpload.setName(fileName);
        fileUpload.setType(file.getContentType());
        fileUpload.setData(file.getBytes());


        return fileRepository.save(fileUpload);
    }

    // @Override
    // public FileUpload store(Long id, MultipartFile file) throws IOException, Exception {
    //     // TODO Auto-generated method stub
    //     // Optional<User> userOpt = userRepository.findById(id);
    //     // String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    //     // FileUpload fileUpload = new FileUpload(fileName, file.getContentType(), file.getBytes());
    
    //     // return fileRepository.save(fileUpload);
    // }
}