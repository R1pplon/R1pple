package com.example.r1pple.service;

import com.example.r1pple.DTO.response.UserResponse;

public interface UserService {
    UserResponse getUserById(Long userId);
}

