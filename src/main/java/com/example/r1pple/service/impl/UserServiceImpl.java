package com.example.r1pple.service.impl;

import com.example.r1pple.DTO.response.UserListResponse;
import com.example.r1pple.DTO.response.UserResponse;
import com.example.r1pple.model.User;
import com.example.r1pple.repository.UserRepository;
import com.example.r1pple.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    // 根据ID获取用户信息
    @Override
    public UserResponse getUserById(Integer userId) {
        return userRepository.findById(userId)
                .map(this::convertToUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("用户不存在"));
    }

    // 将User对象转换为UserResponse对象
    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .createTime(user.getCreateTime())
                .build();
    }

    // 获取所有用户信息
    @Override
    @Transactional(readOnly = true)
    public List<UserListResponse> getAllUsers() {
        return userRepository.findAllUsersWithRelations().stream()
                .map(this::convertToUserListResponse)
                .collect(Collectors.toList());
    }

    // 将User对象转换为UserResponse对象
    private UserListResponse convertToUserListResponse(User user) {
        return UserListResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .createTime(user.getCreateTime())
                .articlesCount(user.getArticles().size())
                .commentCount(user.getComments().size())
                .build();
    }
}