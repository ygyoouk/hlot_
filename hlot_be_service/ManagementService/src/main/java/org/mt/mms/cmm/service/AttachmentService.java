package org.mt.mms.cmm.service;

import org.mt.mms.cmm.vo.AttachmentVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface AttachmentService {

    AttachmentVO selectAttachmentInfo(String fileId) throws Exception;

    AttachmentVO upload(MultipartFile file) throws Exception;
}
