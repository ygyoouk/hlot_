package org.mt.mms.security.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.security.user.CustomUserDetails;
import org.mt.mms.security.user.mapper.UserMapper;
import org.mt.mms.security.user.vo.UserVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("loadByUsername Method ..........");

        UserVO user = userMapper.selectUser(userId);

        if(user == null){
            throw new UsernameNotFoundException("해당 ID로 가입된 회원이 없습니다.");
        }

        return new CustomUserDetails(user);
    }

    public UserVO selectUserById(String userId) throws Exception  {
        return userMapper.selectUser(userId);
    }

}
