package com.example.r1pple.service;

import com.example.r1pple.DTO.response.UserResponse;

import java.util.List;

public interface UserService {
    // 根据ID获取用户信息
    UserResponse getUserById(Integer userId);

    // 获取所有用户
    List<UserResponse> getAllUsers();
}

