package org.sparta.personalproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.personalproject.dto.UserReqeustDto;
import org.sparta.personalproject.timestamped.CommentTimeStamped;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends CommentTimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private List<Comment> commentList;

    @OneToMany(mappedBy="user")
    private List<Todo> TodoList;

    private String refreshToken;



    public User(String nickname, String username, String password, UserRoleEnum role) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(UserReqeustDto userReqeustDto) {
        this.username = userReqeustDto.getUsername();
        this.password = userReqeustDto.getPassword();
    }


    public User(String userName, String password) {
        this.username = userName;
        this.password = password;

    }
}
