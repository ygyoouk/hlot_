package org.mt.mms.cmm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachmentVO {

    private String fileId;
    private String filePath;
    private String orignFileName;
    private String changeFileName;
    private String fileSize;

}
