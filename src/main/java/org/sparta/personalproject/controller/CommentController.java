package org.sparta.personalproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.personalproject.dto.CommentReqeustDto;
import org.sparta.personalproject.dto.CommentResponseDto;
import org.sparta.personalproject.service.CommentService;
import org.sparta.personalproject.security.entity.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/{todoId}/comment")
    public CommentResponseDto createComment(@PathVariable(value = "todoId", required = false) Long todoId,
                                            @RequestBody CommentReqeustDto commentReqeustDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws Exception {

        return commentService.createComment(todoId, commentReqeustDto, userDetails);
    }

    // 수정 작성은 pathvariable 이 좋음.
    // restful url 일관성과 가독성이 중요함.
    @PutMapping("/{todoId}/comment/{commentId}")
    public CommentResponseDto modifyComment(@PathVariable("todoId") Long todoId,
                                            @PathVariable("commentId") Long commentId,
                                            @RequestBody @Valid CommentReqeustDto commentReqeustDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        //@Validated 공부
        return commentService.modifyComment(todoId, commentId, commentReqeustDto, userDetails);
    }

    @DeleteMapping("/{todoId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("todoId") Long todoId,
                                                @PathVariable("commentId") Long commentId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return commentService.deleteComment(todoId, commentId, userDetails);
    }



}
