package org.mt.mms.security.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mt.mms.security.user.vo.UserVO;

@Mapper
public interface UserMapper {

    UserVO selectUser(String userId);
}
