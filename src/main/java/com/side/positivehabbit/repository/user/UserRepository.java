package com.side.positivehabbit.repository.user;

import com.side.positivehabbit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
