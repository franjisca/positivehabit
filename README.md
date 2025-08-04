🌟 PositiveHabit

긍정적인 변화를 만드는 스마트 습관 관리 플랫폼 (개발 진행 중)
Spring Boot 기반 백엔드 아키텍처 학습 및 실전 적용 프로젝트

🚧 프로젝트 상태: 개발 진행 중 (약 30% 완료)
📋 프로젝트 소개
PositiveHabit은 개인의 습관 관리를 돕는 웹 애플리케이션으로, Spring Boot 생태계의 다양한 기술들을 실전에 적용하며 학습하는 프로젝트입니다.
🎯 개발 목적

Spring Boot 심화 학습 및 실전 적용
Spring Batch를 활용한 배치 처리 시스템 구축
OAuth2 + JWT 기반 인증/인가 시스템 구현
도메인 중심 설계 및 계층형 아키텍처 적용
실무 중심의 코드 품질 및 테스트 코드 작성

현재 개발중인 기능
🔐 인증 시스템 ✅

 JWT 기반 토큰 인증
 OAuth2 소셜 로그인 (Google, Kakao, Naver)
 Spring Security 설정
 사용자 권한 관리

📊 도메인 모델링 ✅

 User, Habit, UserSettings 엔티티 설계
 DailyRecord, Emotion 엔티티 추가
 HabitPhoto 엔티티 (사진 업로드 기능)
 JPA 연관관계 매핑
 소프트 삭제 구현
 BaseTimeEntity 상속 구조

⏰ Spring Batch 알림 시스템 ✅

 HabitReminderJob 배치 작업 구현
 Reader, Processor, Writer 패턴 적용
 NotificationMessage 엔티티 및 상태 관리

🗃️ 데이터 처리 ✅

 QueryDSL 설정 및 활용
 Repository 계층 구현
 커스텀 쿼리 작성

📸 파일 업로드 ✅

 AWS S3 연동 설정
 이미지 업로드 인프라 구축
 습관 인증 사진 기능

📱 REST API

 사용자 관리 API
 습관 CRUD API 구조 설계
 습관 기록 API
 예외 처리 및 응답 표준화
 감정 기록 API 완성
 통계 API 구현
 API 문서화 (Swagger)

📈 데이터 분석

 기본 통계 쿼리 설계
 일별 기록 데이터 모델
 습관 달성률 계산 로직
 감정 패턴 분석
 트렌드 분석 기능
 차트 데이터 API

🔔 알림 시스템

 배치 기반 알림 발송 구조
 알림 메시지 생성 로직
 이메일 발송 기능 구현
 알림 설정 관리
 알림 이력 조회

🎭 감정 기록

 Emotion 엔티티 설계
 감정 데이터 모델링
 감정 기록 API 완성
 감정 패턴 분석
 감정 기반 습관 추천

🛠 기술 스택 및 학습 포인트
Core Framework

Spring Boot 3.5.3 버전 활용, Auto Configuration 학습
Spring Data JPA - N+1 문제 해결, 쿼리 최적화 경험
QueryDSL - 타입 안전한 쿼리 작성, 동적 쿼리 구현
Spring Security 6 - OAuth2, JWT 구현을 통한 보안 이해

Batch Processing

Spring Batch - 대용량 데이터 처리, 청크 기반 처리 패턴 학습
Scheduler - 정시 작업 실행, 크론 표현식 활용

Database & Query

MySQL 8.0 - 인덱스 설계, 쿼리 튜닝
JPA/Hibernate - 영속성 컨텍스트, 지연 로딩 최적화
QueryDSL - 복잡한 검색 조건, 동적 쿼리 처리

Cloud & Storage

AWS S3 - 이미지 파일 업로드 및 관리
Spring Cloud AWS - S3 연동 및 설정

Development Tools

Gradle - 멀티 모듈 구성, 의존성 관리
JUnit 5 - 단위 테스트, 통합 테스트
Mockito - 테스트 더블 활용

🏗 프로젝트 구조
src/
├── main/
│   ├── java/com/side/positivehabit/
│   │   ├── batch/                     # 배치 작업 관련
│   │   ├── config/                    # 설정 클래스들
│   │   │   ├── auth/                  # 인증 설정
│   │   │   ├── batch/                 # Spring Batch 설정
│   │   │   │   ├── processor/         # 배치 프로세서
│   │   │   │   └── reader/            # 배치 리더
│   │   │   ├── querydsl/              # QueryDSL 설정
│   │   │   ├── s3/                    # AWS S3 설정
│   │   │   └── security/              # Spring Security 설정
│   │   │       ├── jwt/               # JWT 관련
│   │   │       ├── oauth/             # OAuth2 관련
│   │   │       │   └── user/          # OAuth2 사용자 정보
│   │   │       └── service/           # 보안 서비스
│   │   ├── controller/                # REST API 컨트롤러
│   │   │   ├── User/                  # 사용자 컨트롤러
│   │   │   ├── habit/                 # 습관 컨트롤러
│   │   │   └── habitlog/              # 습관 기록 컨트롤러
│   │   ├── domain/                    # 도메인 엔티티
│   │   │   ├── common/                # 공통 엔티티
│   │   │   ├── dailyrecord/           # 일별 기록
│   │   │   ├── emotion/               # 감정 기록
│   │   │   ├── habit/                 # 습관 도메인
│   │   │   ├── user/                  # 사용자 도메인
│   │   │   └── usersettings/          # 사용자 설정
│   │   ├── dto/                       # 데이터 전송 객체
│   │   │   ├── emotion/               # 감정 DTO
│   │   │   ├── habit/                 # 습관 DTO
│   │   │   ├── habitlog/              # 습관 기록 DTO
│   │   │   ├── habitphoto/            # 습관 사진 DTO
│   │   │   └── user/                  # 사용자 DTO
│   │   ├── exception/                 # 예외 처리
│   │   ├── global/                    # 전역 설정
│   │   ├── infrastructure/            # 외부 시스템 연동
│   │   │   ├── image/                 # 이미지 처리
│   │   │   └── storage/               # 스토리지 서비스
│   │   ├── repository/                # 데이터 접근 계층
│   │   │   ├── dailyrecord/           # 일별 기록 리포지토리
│   │   │   ├── emotion/               # 감정 리포지토리
│   │   │   ├── habit/                 # 습관 리포지토리
│   │   │   ├── habitphoto/            # 습관 사진 리포지토리
│   │   │   └── user/                  # 사용자 리포지토리
│   │   ├── service/                   # 비즈니스 로직
│   │   │   ├── User/                  # 사용자 서비스
│   │   │   ├── habit/                 # 습관 서비스
│   │   │   └── habitlog/              # 습관 기록 서비스
│   │   └── util/                      # 유틸리티 클래스
│   └── resources/                     # 설정 파일 및 정적 자원
│       ├── static/                    # 정적 파일
│       └── templates/                 # 템플릿 파일
└── test/                              # 테스트 코드
    └── java/com/side/positivehabit/


    
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
3. QueryDSL 동적 쿼리 구현
java@Repository
public class HabitQueryRepository {
    public List<Habit> searchHabits(HabitSearchCondition condition) {
        return queryFactory
            .selectFrom(habit)
            .where(
                categoryEq(condition.getCategory()),
                isActiveEq(condition.getIsActive()),
                userIdEq(condition.getUserId())
            )
            .fetch();
    }
}
학습 포인트: 타입 안전한 쿼리, 복잡한 검색 조건 처리
4. AWS S3 파일 업로드 시스템
java@Service
public class S3ImageService {
    public String uploadImage(MultipartFile file, String directory) {
        // S3 업로드 로직 및 URL 반환
        return s3Client.putObject(bucketName, key, file.getInputStream());
    }
}
학습 포인트: 클라우드 스토리지 연동, 파일 업로드 최적화
5. 도메인 중심 설계
java@Entity
public class Habit extends BaseTimeEntity {
    // 연관관계 편의 메서드
    public void addHabitPhoto(HabitPhoto photo) {
        habitPhotos.add(photo);
        photo.setHabit(this);
    }
    
    // 비즈니스 로직
    public void recordEmotion(Emotion emotion) {
        this.dailyRecords.add(DailyRecord.of(this, emotion));
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
현재 테스트 예정 현황:

 도메인 엔티티 단위 테스트
 Repository 계층 테스트
 Service 계층 테스트 (진행 중)
 Controller 계층 테스트
 통합 테스트

🔮 향후 개발 계획
Phase 1 - 핵심 기능 완성 (예상 완료: 2025년 8월)

 습관 완료 처리 API 구현
 감정 기록 API 완성
 습관 사진 업로드 기능 완성
 이메일 알림 발송 기능
 기본 통계 API 구현
 API 문서화 (Swagger)

Phase 2 - 데이터 분석 고도화 (예상 완료: 2025년 9월)

 습관 달성률 분석 기능
 감정 패턴 분석 및 시각화
 일별/주별/월별 통계 대시보드
 개인 성장 지표 리포트
 습관 연관성 분석

Phase 3 - 사용자 경험 개선 (예상 완료: 2025년 9월)

 프론트엔드 연동 (React)
 실시간 알림 (WebSocket)
 모바일 반응형 UI
 사용자 피드백 시스템
 개인화된 습관 추천

Phase 4 - 성능 최적화 & 확장 (학습 목적)

 Redis 캐싱 적용
 데이터베이스 쿼리 최적화
 이미지 CDN 적용
 모니터링 시스템 구성 (Prometheus, Grafana)
 배포 자동화 (Docker + GitHub Actions)
 로드 테스트 및 성능 튜닝

Phase 5 - 고급 기능 실험 (장기 계획)

 머신러닝 기반 습관 패턴 분석
 소셜 기능 (친구, 그룹 챌린지)
 마이크로서비스 아키텍처 분리 실험
 이벤트 기반 아키텍처 적용
 메시징 시스템 도입 (RabbitMQ)

🚀 로컬 개발 환경 구성
Prerequisites

Java 17+
MySQL 8.0+
Gradle 7.5+

실행 방법
bash# 1. 프로젝트 클론
git clone https://github.com/franjisca/positive-habit.git
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

앞으로 학습 및 활용하고 싶은 것들

Spring WebFlux (반응형 프로그래밍)
이벤트 기반 아키텍처
클라우드 네이티브 개발
메시징 시스템 (RabbitMQ, Kafka)


👨‍💻 개발자 정보
GitHub: franjisca, 한지연
이메일: hanj1yeon@naver.com
블로그: https://velog.io/@hanj1yeon/

📝 참고사항
이 프로젝트는 클로드와 chatgpt를 활용하여 학습 목적으로 개발 중인 프로젝트입니다.
완성된 제품이 아닌 기술 학습과 실전 적용을 위한 실험적 프로젝트임을 참고해 주세요.
