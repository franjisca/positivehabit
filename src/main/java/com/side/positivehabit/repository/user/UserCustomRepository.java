package com.side.positivehabit.repository.user;

import com.side.positivehabit.domain.user.User;

public interface UserCustomRepository {
    User findUserByEmailUsingQuerydsl(String email);
}
