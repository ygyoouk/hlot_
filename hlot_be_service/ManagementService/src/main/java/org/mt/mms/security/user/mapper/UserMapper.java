package org.mt.mms.security.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mt.mms.security.user.vo.UserVO;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {

    UserVO selectUser(String userId) throws Exception;

    List<UserVO> selectUsers(HashMap params) throws Exception;

    int insertUser(UserVO userVO) throws Exception;

    int updateUser(UserVO userVO) throws Exception;

    int deleteUser(String userId) throws Exception;
}
