package org.sparta.personalproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.personalproject.timestamped.Timestamped;
import org.sparta.personalproject.security.entity.UserDetailsImpl;
import org.sparta.personalproject.dto.TodoRequestDto;

import java.util.ArrayList;
import java.util.List;


@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "todo") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Todo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false, length = 500)
    private String content;
    @Column(name = "person", nullable = false, length = 500)
    private String person;
    @Column(name = "pw", nullable = false)
    private String pw;
    @OneToMany(mappedBy="todo") //얘를 어떤필드(필드명)랑 연결을 할거냐.
    private List<Comment> comments = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="user_id")//얘를 어떤필드(필드명)랑 연결을 할거냐.
    private User user;


    public Todo(TodoRequestDto todoRequestDto, UserDetailsImpl userDetails) {
        this.title = todoRequestDto.getTitle();
        this.content = todoRequestDto.getContent();
        this.person = todoRequestDto.getPerson();
        this.pw = todoRequestDto.getPw();
        this.user=userDetails.getUser();
    }

    public void update(TodoRequestDto todoRequestDto) {
        this.title = todoRequestDto.getTitle();
        this.content = todoRequestDto.getContent();
        this.person = todoRequestDto.getPerson();
    }


}
