package com.example.r1pple.service.impl;

import com.example.r1pple.DTO.response.UserResponse;
import com.example.r1pple.model.User;
import com.example.r1pple.repository.UserRepository;
import com.example.r1pple.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(this::convertToUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("用户不存在"));
    }

    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .createTime(user.getCreateTime())
                .build();
    }
}