package org.mt.mms.security.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.cmm.dto.Result;
import org.mt.mms.security.jwt.JwtService;
import org.mt.mms.security.user.CustomUserDetails;
import org.mt.mms.security.user.service.UserDetailServiceImpl;
import org.mt.mms.security.user.vo.UserVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailServiceImpl userDetailService;


    @PostMapping("/login")
    public ResponseEntity<Result> loginAndGetToken(@RequestBody UserVO userVO) throws Exception {
        Authentication authentication
                = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userVO.getUserId(), userVO.getUserPassword()));

        if(authentication.isAuthenticated()){
            String accessToken = jwtService.GenerateToken(userVO.getUserId(), userVO.getUserAuth(), userVO.getUserName());

            UserVO user = userDetailService.selectUserById(userVO.getUserId());
            user.setAccessToken(accessToken);;

            return ResponseEntity.ok()
                    .body(Result.resSuccess(user, "환영합니다."));
        } else{
            return ResponseEntity.ok()
                    .body(Result.resError("로그인에 실패하였습니다."));
        }

    }
}
