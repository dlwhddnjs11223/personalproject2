package org.sparta.personalproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sparta.personalproject.dto.CommentReqeustDto;
import org.sparta.personalproject.timestamped.CommentTimeStamped;

@Getter
@Setter
@Entity
@Table(name = "Comment")
public class Comment extends CommentTimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="todo_id", nullable = false)
    private Todo todo;

    public Comment(CommentReqeustDto commentReqeustDto, Todo todo, User user) {
        this.content = commentReqeustDto.getContent();
        this.user = user;
        this.todo = todo;
        todo.getComments().add(this);
        user.getCommentList().add(this);
    }


    public Comment() {

    }
}
