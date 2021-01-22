package com.puwd.jwt.service;

import com.puwd.jwt.dto.JwtUserDto;

/**
 * @auther puwd
 * @Date 2021-1-21 15:06
 * @Description
 */
public interface IJwtUserService {

    JwtUserDto getJwtUserDtoByUserName(String userName, String password);

    JwtUserDto getJwtUserDtoByUserId(String userId);
}
