package com.side.positivehabit.config.security.service;

import com.side.positivehabit.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public CustomUserDetails loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oauth2User);
        } catch (Exception ex) {
            log.error("OAuth2 사용자 처리 중 오류 발생", ex);
            throw new OAuth2AuthenticationException("OAuth2 사용자 처리 실패");
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oauth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oauth2User.getAttributes());

        if (oauth2UserInfo.getEmail() == null || oauth2UserInfo.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationException("이메일 정보를 찾을 수 없습니다.");
        }

        Optional<User> userOptional = userRepository.findByEmail(oauth2UserInfo.getEmail());
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            // 기존 사용자 정보 업데이트
            user = updateExistingUser(user, oauth2UserInfo);
        } else {
            // 새 사용자 생성
            user = registerNewUser(userRequest, oauth2UserInfo);
        }

        return UserPrincipal.create(user, oauth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest userRequest, OAuth2UserInfo oauth2UserInfo) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        User user = User.builder()
                .name(oauth2UserInfo.getName())
                .email(oauth2UserInfo.getEmail())
                .profileImageUrl(oauth2UserInfo.getImageUrl())
                .provider(AuthProvider.valueOf(registrationId.toUpperCase()))
                .providerId(oauth2UserInfo.getId())
                .role(UserRole.USER)
                .notificationEnabled(true)
                .createdAt(LocalDateTime.now())
                .build();

        log.info("새 OAuth2 사용자 등록: {}, Provider: {}", user.getEmail(), registrationId);
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oauth2UserInfo) {
        existingUser.setName(oauth2UserInfo.getName());
        existingUser.setProfileImageUrl(oauth2UserInfo.getImageUrl());
        existingUser.setUpdatedAt(LocalDateTime.now());

        log.info("기존 OAuth2 사용자 정보 업데이트: {}", existingUser.getEmail());
        return userRepository.save(existingUser);
    }
}