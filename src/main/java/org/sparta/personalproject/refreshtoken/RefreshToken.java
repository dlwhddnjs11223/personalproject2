package org.sparta.personalproject.refreshtoken;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sparta.personalproject.User.User;
@Entity
@Getter
@Setter
@Table(name = "refreshtoken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column
    private String refreshToken;


    public RefreshToken(String token, User user) {
        this.refreshToken = token;
        this.user = user;
        this.user.setRefreshToken(token);
    }

    public RefreshToken() {

    }
}
