package com.side.positivehabit.repository.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.positivehabit.domain.user.Provider;
import com.side.positivehabit.domain.user.Role;
import com.side.positivehabit.domain.user.User;
import com.side.positivehabit.dto.user.UserSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

import static com.side.positivehabit.domain.user.QUser.user;
import static com.side.positivehabit.domain.usersettings.QUserSettings.userSettings;


@RequiredArgsConstructor
public class UserRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public User findUserByEmailUsingQuerydsl(String email) {
        return null;
    }

    @Override
    public Page<User> searchUsers(UserSearchCondition condition, Pageable pageable) {
        List<User> content = queryFactory
                .selectFrom(user)
                .leftJoin(user.userSettings, userSettings).fetchJoin()
                .where(
                        emailContains(condition.getEmail()),
                        nameContains(condition.getName()),
                        providerEq(condition.getProvider()),
                        roleEq(condition.getRole()),
                        isActiveEq(condition.getIsActive()),
                        createdDateBetween(condition.getCreatedFrom(), condition.getCreatedTo())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(user.count())
                .from(user)
                .where(
                        emailContains(condition.getEmail()),
                        nameContains(condition.getName()),
                        providerEq(condition.getProvider()),
                        roleEq(condition.getRole()),
                        isActiveEq(condition.getIsActive()),
                        createdDateBetween(condition.getCreatedFrom(), condition.getCreatedTo())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<User> findInactiveUsersForDeletion(LocalDateTime cutoffDate) {
        return queryFactory
                .selectFrom(user)
                .where(
                        user.isActive.eq(false),
                        user.updatedAt.before(cutoffDate),
                        user.deletedAt.isNull()
                )
                .fetch();
    }

    @Override
    public long countActiveUsers() {
        return queryFactory
                .select(user.count())
                .from(user)
                .where(
                        user.isActive.eq(true),
                        user.deletedAt.isNull()
                )
                .fetchOne();
    }

    @Override
    public List<User> findUsersWithBirthday(int month, int day) {
        // 생일 필드가 있다면 구현
        // 현재는 빈 리스트 반환
        return List.of();
    }

    // 동적 쿼리 조건 메서드들
    private BooleanExpression emailContains(String email) {
        return StringUtils.hasText(email) ? user.email.contains(email) : null;
    }

    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? user.nickname.contains(name) : null;
    }

    private BooleanExpression providerEq(Provider provider) {
        return provider != null ? user.provider.eq(provider) : null;
    }

    private BooleanExpression roleEq(Role role) {
        return role != null ? user.role.eq(role) : null;
    }

    private BooleanExpression isActiveEq(Boolean isActive) {
        return isActive != null ? user.isActive.eq(isActive) : null;
    }

    private BooleanExpression createdDateBetween(LocalDateTime from, LocalDateTime to) {
        if (from != null && to != null) {
            return user.createdAt.between(from, to);
        } else if (from != null) {
            return user.createdAt.goe(from);
        } else if (to != null) {
            return user.createdAt.loe(to);
        }
        return null;
    }
}