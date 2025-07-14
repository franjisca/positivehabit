package com.side.positivehabbit.repository.user;

import com.side.positivehabbit.domain.User;

public interface UserCustomRepository {
    User findUserByEmailUsingQuerydsl(String email);
}
