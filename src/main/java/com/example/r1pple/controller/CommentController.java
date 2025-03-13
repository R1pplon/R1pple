package com.example.r1pple.controller;

import com.example.r1pple.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "用户接口")
public class CommentController {
    private final CommentService commentService;
}
