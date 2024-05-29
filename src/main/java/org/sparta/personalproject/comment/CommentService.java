package org.sparta.personalproject.comment;

import lombok.RequiredArgsConstructor;
import org.sparta.personalproject.User.User;
import org.sparta.personalproject.User.UserRepository;
import org.sparta.personalproject.security.entity.UserDetailsImpl;
import org.sparta.personalproject.todo.entity.Todo;
import org.sparta.personalproject.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TodoService todoService;

    /*
    댓글 등록 기능, 예외처리 2,3 적용 완료.
     */
    @Transactional
    public CommentResponseDto createComment(Long todoId,
                                            CommentReqeustDto commentReqeustDto,
                                            UserDetailsImpl userDetails) throws Exception {


        Todo todo = todoService.findTodo(todoId);
        User user = userDetails.getUser();
        User newUser = userRepository.findById(user.getId()); // TIL 작성

        Comment comment = new Comment(commentReqeustDto, todo, newUser);
        commentRepository.save(comment);
        isSavedComment(comment);
        return new CommentResponseDto(comment);

    }

    /*
    댓글 수정 기능, 예외처리 2,
     */
    @Transactional
    public CommentResponseDto modifyComment(Long todoId,
                                            Long commentId,
                                            CommentReqeustDto commentReqeustDto,
                                            UserDetailsImpl userDetails) {

        Todo todo = todoService.findTodo(todoId);
        List<Comment> commentList = todo.getComments();
        Comment comment = findComment(commentId);
        checkUserId(comment, commentReqeustDto.getUserId());
        if (isExistComment(commentList, commentId)) {
            comment.setContent(commentReqeustDto.getContent());
        }
        isSavedComment(comment);
        return new CommentResponseDto(comment);
    }

    public ResponseEntity<String> deleteComment(Long todoId,
                                                Long commentId,
                                                CommentReqeustDto commentReqeustDto) {
        Todo todo = todoService.findTodo(todoId);
        List<Comment> commentList = todo.getComments();
        Comment comment = findComment(commentId);
        checkUserId(comment, commentReqeustDto.getUserId());
        if (!isExistComment(commentList, commentId)) {
            commentRepository.delete(comment);
        }
        return new ResponseEntity<>("삭제 완료 " + HttpStatus.OK, HttpStatus.OK);
    }


    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("없음"));
    }


    public boolean isExistComment(List<Comment> commentList, Long commentId) {
        for (Comment comment : commentList) {
            if (comment.getId().equals(commentId)) {
                return true;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
    }

    public void isSavedComment(Comment comment) {
        if (!commentRepository.findById(comment.getId()).isPresent()) {
            throw new IllegalArgumentException("DB에 저장되지 않았습니다.");
        }

    }
    public void checkUserId(Comment comment, String userId ){
        if(!comment.getUser().equals(userId)) {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }
}