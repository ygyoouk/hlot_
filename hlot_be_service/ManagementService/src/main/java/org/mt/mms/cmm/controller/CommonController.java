package org.mt.mms.cmm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.cmm.dto.Result;
import org.mt.mms.cmm.service.AttachmentService;
import org.mt.mms.cmm.vo.AttachmentVO;
import org.mt.mms.topContr.vo.TopContrVO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Slf4j // log용도
@RequestMapping(value = "/common")
@RequiredArgsConstructor // autowired 대체자
public class CommonController {

    private final AttachmentService attachmentService;

    @PostMapping(value = "/upload" ,consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Result> upload(@RequestPart(value = "file", required = false) MultipartFile file) throws Exception{

        return ResponseEntity.ok()
                .body(Result.resSuccess(attachmentService.upload(file)));
    }



    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable String fileId ) throws  Exception{
        log.info("======download========");
        log.info("fileId ==>" + fileId);
        AttachmentVO result = attachmentService.selectAttachmentInfo(fileId);

        log.info("result : {}", result);

        String filePath = result.getFilePath();
        String uploadFileName = result.getOrignFileName();

//        UrlResource urlResource = new UrlResource("file:" + filePath);
//        UrlResource urlResource = new UrlResource("file:" + filePath + File.separator + result.getChangeFileName());

        Path p = Paths.get(filePath + File.separator + result.getChangeFileName());

        byte[] data = Files.readAllBytes(p);

        ByteArrayResource resource = new ByteArrayResource(data);

        log.info("urlResourse : {}", resource);

        String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);

        log.info("encodeUploadFileName : {}", encodeUploadFileName);

        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\"";

        return ResponseEntity.ok()
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(uploadFileName, "utf-8") + "\"")
                .body(resource);
    }

}
