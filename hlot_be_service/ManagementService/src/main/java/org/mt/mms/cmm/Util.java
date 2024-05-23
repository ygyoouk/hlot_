package org.mt.mms.cmm;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.security.user.vo.UserVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.HashMap;

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

    public String getUserAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserVO user = (UserVO)auth.getPrincipal();
        return user.getUserAuth();
    }

    public static HashMap<String, String> requestToMap(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<>();

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String value = request.getParameter(parameterName);
            map.put(parameterName, value);
        }
        return map;
    }

}
