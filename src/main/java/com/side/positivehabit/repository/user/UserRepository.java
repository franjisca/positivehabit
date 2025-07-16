package com.side.positivehabit.repository.user;

import com.side.positivehabit.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String email);


    User findUserByEmailUsingQuerydsl(String email);
}
