package org.mt.mms.userManage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.mt.mms.cmm.Util;
import org.mt.mms.cmm.dto.Result;
import org.mt.mms.security.user.vo.UserVO;
import org.mt.mms.userManage.service.UserManageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class UserManageController {

    private final UserManageService userManageService;

    @GetMapping("/users")
    public ResponseEntity<Result> all(HttpServletRequest request) throws Exception {
        HashMap<String, String> params = Util.requestToMap(request);
        return ResponseEntity.ok()
                        .body(Result.resSuccess(userManageService.getUsers(params)));

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Result> one(@PathVariable String userId) throws Exception {
        UserVO userVO = userManageService.getUser(userId);
        if(userVO != null) userVO.setUserPassword("");
        return ResponseEntity.ok()
                .body(Result.resSuccess(userVO));
    }

    @PostMapping("/user")
    public ResponseEntity<Result> create(@RequestBody UserVO userVO) throws Exception {
        return ResponseEntity.ok()
                .body(Result.resSuccess(userManageService.newUser(userVO), true));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Result> delete(@PathVariable String id) throws Exception {
        return ResponseEntity.ok()
                .body(Result.resSuccess(userManageService.deleteUser(id), true));
    }
}
