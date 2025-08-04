🌟 PositiveHabit

긍정적인 변화를 만드는 스마트 습관 관리 플랫폼 (개발 진행 중)
Spring Boot 기반 백엔드 아키텍처 학습 및 실전 적용 프로젝트

🚧 프로젝트 상태: 개발 진행 중 (약 40% 완료)
📋 프로젝트 소개
PositiveHabit은 개인의 습관 관리를 돕는 웹 애플리케이션으로, Spring Boot 생태계의 다양한 기술들을 실전에 적용하며 학습하는 프로젝트입니다.
🎯 개발 목적

Spring Boot 심화 학습 및 실전 적용
Spring Batch를 활용한 배치 처리 시스템 구축
OAuth2 + JWT 기반 인증/인가 시스템 구현
도메인 중심 설계 및 계층형 아키텍처 적용
실무 중심의 코드 품질 및 테스트 코드 작성

✅ 현재 구현 완료된 기능
🔐 인증 시스템 ✅

 JWT 기반 토큰 인증
 OAuth2 소셜 로그인 (Google, Kakao, Naver)
 Spring Security 설정
 사용자 권한 관리

📊 도메인 모델링 ✅

 User, Habit, UserSettings 엔티티 설계
 JPA 연관관계 매핑
 소프트 삭제 구현
 BaseTimeEntity 상속 구조

⏰ Spring Batch 알림 시스템 ✅

 HabitReminderJob 배치 작업 구현
 Reader, Processor, Writer 패턴 적용
 NotificationMessage 엔티티 및 상태 관리
 알림 재시도 메커니즘

🚧 현재 개발 중인 기능
📱 REST API

 기본 CRUD API 구조 설계
 예외 처리 및 응답 표준화
 습관 완료 처리 API
 통계 API 구현
 API 문서화 (Swagger)

📈 데이터 분석

 기본 통계 쿼리 설계
 습관 달성률 계산 로직
 트렌드 분석 기능
 차트 데이터 API

🔔 알림 시스템

 배치 기반 알림 발송 구조
 이메일 발송 기능 구현
 알림 설정 관리
 알림 이력 조회

🛠 기술 스택 및 학습 포인트
Core Framework

Spring Boot 3.1 - 최신 버전 활용, Auto Configuration 학습
Spring Data JPA - N+1 문제 해결, 쿼리 최적화 경험
Spring Security 6 - OAuth2, JWT 구현을 통한 보안 이해

Batch Processing

Spring Batch - 대용량 데이터 처리, 청크 기반 처리 패턴 학습
Scheduler - 정시 작업 실행, 크론 표현식 활용

Database

MySQL 8.0 - 인덱스 설계, 쿼리 튜닝
JPA/Hibernate - 영속성 컨텍스트, 지연 로딩 최적화

개발 도구

Gradle - 멀티 모듈 구성, 의존성 관리
JUnit 5 - 단위 테스트, 통합 테스트
Mockito - 테스트 더블 활용

🏗 프로젝트 구조
src
    ├── main
    │   ├── generated
    │   ├── java
    │   │   └── com
    │   │       └── side
    │   │           └── positivehabit
    │   │               ├── batch
    │   │               ├── config
    │   │               │   ├── auth
    │   │               │   ├── batch
    │   │               │   │   ├── processor
    │   │               │   │   └── reader
    │   │               │   ├── querydsl
    │   │               │   ├── s3
    │   │               │   └── security
    │   │               │       ├── jwt
    │   │               │       ├── oauth
    │   │               │       │   └── user
    │   │               │       └── service
    │   │               ├── controller
    │   │               │   ├── User
    │   │               │   ├── habit
    │   │               │   └── habitlog
    │   │               ├── domain
    │   │               │   ├── common
    │   │               │   ├── dailyrecord
    │   │               │   ├── emotion
    │   │               │   ├── habit
    │   │               │   ├── user
    │   │               │   └── usersettings
    │   │               ├── dto
    │   │               │   ├── emotion
    │   │               │   ├── habit
    │   │               │   ├── habitlog
    │   │               │   ├── habitphoto
    │   │               │   └── user
    │   │               ├── exception
    │   │               ├── global
    │   │               ├── infrastructure
    │   │               │   ├── image
    │   │               │   └── storage
    │   │               ├── repository
    │   │               │   ├── dailyrecord
    │   │               │   ├── emotion
    │   │               │   ├── habit
    │   │               │   ├── habitphoto
    │   │               │   └── user
    │   │               ├── service
    │   │               │   ├── User
    │   │               │   ├── habit
    │   │               │   └── habitlog
    │   │               └── util
    │   └── resources
    │       ├── static
    │       └── templates
    └── test
        └── java
            └── com
                └── side
                    └── positivehabit



 
💡 주요 학습 내용 및 기술적 도전
1. Spring Batch 배치 처리 시스템
java@Component
public class HabitReminderProcessor implements ItemProcessor<Habit, NotificationMessage> {
    @Override
    public NotificationMessage process(Habit habit) throws Exception {
        // 개인화된 알림 메시지 생성 로직
        if (!shouldSendNotification(habit)) return null;
        return createPersonalizedNotification(habit);
    }
}
학습 포인트: 청크 기반 처리, 실패 처리, 재시작 메커니즘
2. OAuth2 소셜 로그인 구현
java@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // 제공자별 사용자 정보 표준화 처리
        return processOAuth2User(userRequest, oauth2User);
    }
}
학습 포인트: Factory Pattern 적용, 제공자별 데이터 표준화
3. 도메인 중심 설계
java@Entity
public class User extends BaseTimeEntity {
    // 연관관계 편의 메서드
    public void addHabit(Habit habit) {
        habits.add(habit);
        habit.setUser(this);
    }
    
    // 비즈니스 로직
    public void updateProfile(String name, String profileImageUrl) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }
}
학습 포인트: Rich Domain Model, 캡슐화, 비즈니스 로직 분리
🧪 테스트 전략
java@SpringBootTest
@Transactional
class HabitServiceTest {
    
    @Test
    @DisplayName("사용자는 새로운 습관을 생성할 수 있다")
    void createHabit_Success() {
        // Given, When, Then 패턴 적용
        // 테스트 더블을 활용한 단위 테스트
    }
}
현재 테스트 현황:

 도메인 엔티티 단위 테스트(진행중)
 Repository 계층 테스트(진행중)
 Service 계층 테스트 (진행 중)
 Controller 계층 테스트(진행중)
 통합 테스트

📚 개발 과정에서 해결한 문제들
N+1 문제 해결
문제: 습관 목록 조회 시 연관된 사용자 정보를 개별 쿼리로 조회
해결: @EntityGraph와 Fetch Join 활용
학습: JPA 성능 최적화, 쿼리 분석 능력 향상
동시성 문제 인식
상황: 동일 습관에 대한 동시 완료 처리 시나리오 발견
계획: @Version을 이용한 낙관적 락 적용 예정
학습: 동시성 이슈에 대한 이해와 해결 방법 학습
배치 작업 설계
도전: 대용량 사용자 알림 처리 방식 설계
해결: Spring Batch의 청크 기반 처리 방식 적용
학습: 배치 처리 패턴, 성능 최적화 전략
🔮 향후 개발 계획
Phase 1 - 핵심 기능 완성 (예상 완료: 2024년 2월)

 습관 CRUD API 완성
 알림 발송 기능 구현
 기본 통계 기능
 API 문서화

Phase 2 - 고도화 (예상 완료: 2024년 3월)

 프론트엔드 연동 (React)
 실시간 알림 (WebSocket)
 성능 최적화
 배포 자동화

Phase 3 - 확장 (학습 목적)

 캐싱 적용 (Redis)
 모니터링 구성
 마이크로서비스 분리 실험

🚀 로컬 개발 환경 구성
Prerequisites

Java 17+
MySQL 8.0+
Gradle 7.5+

실행 방법
bash# 1. 프로젝트 클론
git clone https://github.com/yourusername/positive-habit.git
cd positive-habit

# 2. 데이터베이스 생성
mysql -u root -p
CREATE DATABASE positive_habit;

# 3. 설정 파일 복사 및 수정
cp src/main/resources/application-example.yml src/main/resources/application.yml
# application.yml에서 DB 설정 수정

# 4. 애플리케이션 실행
./gradlew bootRun
📖 학습 기록 및 회고
이 프로젝트를 통해 학습한 것들

Spring Boot 생태계 전반에 대한 이해
배치 처리 시스템의 설계와 구현
OAuth2 인증 메커니즘의 실제 적용
JPA 성능 최적화 실무 경험
도메인 모델링과 객체지향 설계

앞으로 학습하고 싶은 것들
Spring WebFlux (반응형 프로그래밍)
이벤트 기반 아키텍처
클라우드 네이티브 개발
메시징 시스템 (RabbitMQ, Kafka)


👨‍💻 개발자 정보
GitHub: franjisca
이메일: hanj1yeon@naver.com
블로그: [개발 학습 기록](https://velog.io/@hanj1yeon/)

📝 참고사항
이 프로젝트는 클로드와 chatgpt를 활용하여 학습 목적으로 개발 중인 프로젝트입니다.
완성된 제품이 아닌 기술 학습과 실전 적용을 위한 실험적 프로젝트임을 참고해 주세요.
