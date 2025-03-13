package com.example.r1pple.service;

import com.example.r1pple.DTO.response.UserListResponse;
import com.example.r1pple.DTO.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getUserById(Long userId);
    List<UserListResponse> getAllUsers();
}

