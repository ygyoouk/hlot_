package org.mt.mms.security.user.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class UserVO {

    private String userId;
    private String userPassword;
    private String userName;
    private String userAuth;
    private String userAuthNm;
    private String accessToken;

}
