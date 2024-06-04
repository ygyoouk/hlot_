package org.mt.mms.userManage.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.mt.mms.security.user.mapper.UserMapper;
import org.mt.mms.security.user.vo.UserVO;
import org.mt.mms.userManage.service.UserManageService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManageServiceImpl implements UserManageService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserVO> getUsers(HashMap params) throws Exception {
        return userMapper.selectUsers(params);
    }

    @Override
    public UserVO getUser(String id) throws Exception {
        return userMapper.selectUser(id);
    }

    @Override
    public int newUser(UserVO userVO) throws Exception {
        if(getUser(userVO.getUserId()) != null)
            return updateUser(userVO);

        String encodedPassword = passwordEncoder.encode(userVO.getUserPassword());
        userVO.setUserPassword(encodedPassword);

        return userMapper.insertUser(userVO);
    }

    @Override
    public int updateUser(UserVO userVO) throws Exception {
        String encodedPassword = passwordEncoder.encode(userVO.getUserPassword());
        userVO.setUserPassword(encodedPassword);

        return userMapper.updateUser(userVO);
    }

    @Override
    public int deleteUser(String id) throws Exception {
        return userMapper.deleteUser(id);
    }
}
