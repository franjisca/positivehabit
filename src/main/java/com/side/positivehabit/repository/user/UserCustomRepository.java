package com.side.positivehabit.repository.user;

import com.side.positivehabit.domain.user.User;
import com.side.positivehabit.dto.user.UserSearchCondition;
import org.hibernate.query.Page;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface UserCustomRepository {
    User findUserByEmailUsingQuerydsl(String email);
    Page<User> searchUsers(UserSearchCondition condition, Pageable pageable);
    List<User> findInactiveUsersForDeletion(LocalDateTime cutoffDate);
    long countActiveUsers();
    List<User> findUsersWithBirthday(int month, int day);
}
