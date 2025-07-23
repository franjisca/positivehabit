package com.side.positivehabit.repository.user;

import com.side.positivehabit.domain.user.Provider;
import com.side.positivehabit.domain.user.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findByEmailAndProvider(String email, Provider provider);

    Optional<User> findByProviderAndProviderId(Provider provider, String providerId);

    @Query("SELECT u FROM User u WHERE u.refreshToken = :refreshToken AND u.isActive = true")
    Optional<User> findByRefreshToken(@Param("refreshToken") String refreshToken);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userSettings WHERE u.id = :id")
    Optional<User> findByIdWithSettings(@Param("id") Long id);
}
