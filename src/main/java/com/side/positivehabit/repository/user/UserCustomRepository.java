package com.side.positivehabit.repository.user;

import com.side.positivehabit.domain.User;

public interface UserCustomRepository {
    User findUserByEmailUsingQuerydsl(String email);
}
