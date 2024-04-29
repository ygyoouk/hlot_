package org.mt.company.controller;

import lombok.extern.slf4j.Slf4j;
import org.mt.company.service.FileService;
import org.mt.company.service.impl.FileServiceImpl;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/hlot/company/file")
@Slf4j
public class FileController {

    private final FileService fileService;

    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> download(){
      log.info("##### /hlot/company/file/download #####");
      try{
          byte[] data = fileService.download();
          ByteArrayResource resource = new ByteArrayResource(data);

          return ResponseEntity
                  .ok()
                  .contentLength(data.length)
                  .header("Content-type", "application/octet-stream")
                  .header("Content-disposition", "attachment; filename=\"" + URLEncoder.encode("test.pdf", "utf-8") + "\"")
                  .body(resource);
      } catch(IOException e){
          log.info(e.getMessage());
          return ResponseEntity.badRequest().contentLength(0).body(null);
      }
    }
}
