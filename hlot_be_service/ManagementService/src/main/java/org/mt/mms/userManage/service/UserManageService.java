package org.mt.mms.userManage.service;

import org.mt.mms.security.user.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface UserManageService {

    List<UserVO> getUsers(HashMap<String, String> params) throws Exception;

    UserVO getUser(String id) throws Exception;

    int newUser(UserVO userVO) throws Exception;

    int updateUser(UserVO userVO) throws Exception;

    int deleteUser(String id) throws Exception;
}
