package net.backend.paylens.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.dto.response.ResponseFile;
import net.backend.paylens.model.entity.FileUpload;
import net.backend.paylens.service.FileService;

@RestController
@RequestMapping("/files")
@CrossOrigin(value = "https://paylens.vercel.app")
public class FileController {
    @Autowired
    private FileService fileService;
  
    private ResponseData<Object> responseData;
  
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException, Exception {
      fileService.store(file);
      String message = "Uploaded the file successfully: " + file.getOriginalFilename();
      responseData = new ResponseData<Object>(200, message, null);
      return ResponseEntity.ok().body(responseData);
    }
  
    @GetMapping
    public ResponseEntity<Object> getFiles() {
      List<ResponseFile> files = fileService.getFiles().map(file -> {
        String fileDownloadUri = ServletUriComponentsBuilder
            .fromCurrentContextPath() // http://localhost:4000
            .path("/files/")
            .path(file.getId())
            .toUriString();
  
        // http://localhost:4000/v1/api/files/{idgambar}
  
        return new ResponseFile(file.getName(), fileDownloadUri, file.getType(), file.getData().length);
      }).collect(Collectors.toList());
  
      return ResponseEntity.ok().body(files);
    }
  
    @GetMapping("/{id}")
    public ResponseEntity<Object> getFile(@PathVariable String id) {
      FileUpload fileUpload = fileService.getFile(id);
  
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileUpload.getName() + "\"")
          .body(fileUpload.getData());
    }

    @PutMapping("/change-picture/{id}/{userId}")
    public ResponseEntity<Object> changeFileUpload(@PathVariable String id, @PathVariable Long userId, @RequestParam("file") MultipartFile file) throws IOException, Exception {
      fileService.changeFileUpload(id, userId, file);
      String message = "Uploaded the file successfully: " + file.getOriginalFilename();
      responseData = new ResponseData<Object>(200, message, null);
      return ResponseEntity.ok().body(responseData);
    }



}