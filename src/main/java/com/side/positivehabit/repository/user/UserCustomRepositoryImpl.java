package com.side.positivehabit.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.positivehabit.domain.user.QUser;
import com.side.positivehabit.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public User findUserByEmailUsingQuerydsl(String email) {
        QUser user = QUser.user;

        User result = jpaQueryFactory
                .selectFrom(user)
                .where(user.email.eq(email))
                .fetchOne();

        if (result == null) {
            throw new IllegalArgumentException("해당 이메일의 사용자를 찾을 수 없습니다.");
        }

        return result;
    }
}


