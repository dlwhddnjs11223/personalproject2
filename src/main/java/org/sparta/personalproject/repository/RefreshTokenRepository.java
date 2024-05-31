package org.sparta.personalproject.repository;

import org.sparta.personalproject.entity.User;
import org.sparta.personalproject.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

     Optional<RefreshToken> findByRefreshToken(String refreshtokenValue);

    Optional<RefreshToken> findByUser(User user);
}
