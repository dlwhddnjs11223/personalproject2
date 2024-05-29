package org.sparta.personalproject.refreshtoken;

import org.sparta.personalproject.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByUser(User user);
}
