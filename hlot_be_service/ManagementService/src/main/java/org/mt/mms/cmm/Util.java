package org.mt.mms.cmm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.security.user.CustomUserDetails;
import org.mt.mms.security.user.vo.UserVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Util {

    public String getLoginUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public String getLoginUserName(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserVO user = (UserVO)auth.getPrincipal();

        return user.getUserName();
    }

}
