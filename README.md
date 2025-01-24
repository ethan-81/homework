# 프로젝트 설명
## 목차
- Used Tech
- 디렉토리 구조
- APIs
- 과제 설명
- 실행 방법

## Used Tech
| Category       | Items                             | Note |
|----------------|-----------------------------------|------|
| Architecture   | hexagonal architecture            |      |
| Framework      | Springboot v3.3.5                 |      |
| Java           | Java 21 LTS                       |      |
| Database       | SpringDataJPA / H2                |      |
| Code Formatter | Spotless                          |      |
| Test           | Junit5 / Mockito                  |      | 
| Build          | Gradle (Kotlin ver)               |      |

## 디렉토리 구조
```
├── java
│   └── com
│       └── homework
│           └── mpay
│               ├── MpayApplication.java
│               ├── account
│               │   ├── adopter
│               │   │   ├── in
│               │   │   │   ├── AccountController.java
│               │   │   │   ├── PointController.java
│               │   │   │   └── dto
│               │   │   │       ├── AccountDtoMapper.java
│               │   │   │       ├── CancelEarnPointRequest.java
│               │   │   │       ├── EarnPointRequest.java
│               │   │   │       ├── GetAccountResponse.java
│               │   │   │       ├── PointResponse.java
│               │   │   │       └── UsePointRequest.java
│               │   │   └── out
│               │   │       ├── AccountDaoMapper.java
│               │   │       ├── AccountPersistAdopter.java
│               │   │       ├── PointTypeDaoMapper.java
│               │   │       ├── PointTypePersistAdopter.java
│               │   │       ├── entity
│               │   │       │   ├── PointEntity.java
│               │   │       │   ├── PointProjection.java
│               │   │       │   ├── PointTransactionEntity.java
│               │   │       │   ├── PointTransactionLedgerEntity.java
│               │   │       │   ├── PointTransactionProjection.java
│               │   │       │   ├── PointTypeEntity.java
│               │   │       │   └── PointUseLedgerEntity.java
│               │   │       └── repository
│               │   │           ├── PointRepository.java
│               │   │           ├── PointTransactionLedgerRepository.java
│               │   │           ├── PointTransactionRepository.java
│               │   │           ├── PointTypeRepository.java
│               │   │           └── PointUseLedgerRepository.java
│               │   ├── application
│               │   │   ├── port
│               │   │   │   ├── in
│               │   │   │   │   ├── EarnPointUseCase.java
│               │   │   │   │   ├── InquiryAccountUseCase.java
│               │   │   │   │   └── UsePointUseCase.java
│               │   │   │   └── out
│               │   │   │       ├── LoadAccountPort.java
│               │   │   │       ├── LoadPointTypePort.java
│               │   │   │       └── UpdateAccountPort.java
│               │   │   └── service
│               │   │       ├── EarnPointService.java
│               │   │       ├── InquiryAccountService.java
│               │   │       └── UsePointService.java
│               │   └── domain
│               │       ├── Account.java
│               │       ├── Point.java
│               │       ├── PointTransaction.java
│               │       ├── PointTransactionLedger.java
│               │       ├── PointType.java
│               │       └── PointWindow.java
│               └── common
│                   ├── config
│                   │   └── database
│                   │       ├── RoutingDataSource.java
│                   │       └── RoutingDatabaseConfig.java
│                   ├── constant
│                   │   ├── PointStatusCode.java
│                   │   ├── PointTypeCode.java
│                   │   └── TransactionTypeCode.java
│                   └── exception
│                       ├── ApiControllerAdvice.java
│                       ├── BaseException.java
│                       ├── ErrorConstant.java
│                       ├── ErrorResponse.java
│                       ├── InvalidParameterException.java
│                       └── UnexpectedException.java
└── resources
    ├── application.yml
    ├── data.sql
    └── erd.png


```

## APIs
+ ### http://localhost:8080/swagger-ui/index.html
+ ### 포인트 거래 
    + ### 포인트 적립 API
        + 리소스 : POST http://localhost:8080/homework/point/earn
        + 스펙 : http://localhost:8080/swagger-ui/index.html#/%ED%8F%AC%EC%9D%B8%ED%8A%B8/postEarnPoint
    + ### 포인트 적립 취소 API
        + 리소스 : POST http://localhost:8080/homework/point/earn/cancel
        + 스펙 : http://localhost:8080/swagger-ui/index.html#/%ED%8F%AC%EC%9D%B8%ED%8A%B8/deleteEarnPoint
  + ### 포인트 시용 API
      + 리소스 : POST http://localhost:8080/homework/point/use
      + 스펙 : http://localhost:8080/swagger-ui/index.html#/%ED%8F%AC%EC%9D%B8%ED%8A%B8/postUsePoint
  + ### 포인트 사용 취소 API
      + 리소스 : POST http://localhost:8080/homework/point/use/cancel
      + 스펙 : http://localhost:8080/swagger-ui/index.html#/%ED%8F%AC%EC%9D%B8%ED%8A%B8/deleteUsePoint
+ ### 계좌 정보
    + ### 계좌 정보 조회 API
        + 리소스 : GET http://localhost:8080/homework/account/{userId}
        + 스펙 : http://localhost:8080/swagger-ui/index.html#/%EA%B3%84%EC%A2%8C/getAccount

## 과제 설명
+ ### 핵사고날 아키텍처 적용
    + 개요
        + 헥사고날 아키텍처는 애플리케이션의 핵심 비즈니스 로직을 외부 세계(입출력, DB, API 등)와 분리합니다. 이 패턴은 다음의 주요 구성 요소를 포함합니다:
            + 도메인: 애플리케이션의 핵심 비즈니스 로직과 규칙을 포함합니다.
            + 포트: 도메인과 외부 세계 간의 인터페이스를 정의합니다. 입력 포트와 출력 포트로 나뉩니다.
            + 어댑터: 포트를 구현하여 외부 세계와 도메인 간의 상호작용을 처리합니다. 예를 들어, 웹 API 어댑터, 데이터베이스 어댑터 등이 있습니다.
    + 장점
        + 유연한 외부 인터페이스
        + 비즈니스 로직의 재사용성
        + 테스트 용이성
    + 단점
        + 복잡성 증가
        + 초기 개발 비용
    + 적용 이유
        + 다양한 다른 외부 시스템(데이터베이스, 캐시, 외부 API 등)과의 상호작용을 고려하여
        + 비즈니스 로직을 도메인 레이어에 집중시키고 외부 시스템의 세부 사항을 어댑터에서 처리 할 수 있는 장점으로 인해 적용하였습니다.
+ ### 핵심 컴포넌트
    + `Account.java`
        + 포인트 거래에 대한 어그리게이트 루트 역할
        + 사용자 포인트 계좌에서 포인트 적립, 적립 취소, 사용, 사용 취소 비지니스 로직을 담당
        + 관련 도메인 모델
          + `PointWindow.java`
            + 사용자 보유 포인트 목록에 대한 핸들링 기능을 제공
          + `Point.java`
            + 특정 포인트에 대한 적립, 적립 취소, 사용, 사용 취소 상태 변경을 담당
          + `PointTransaction.java`
            + 포인트 거래 처리 결과를 저장하기 위한 command 객체
          + `PointTransactionLedger.java`
            + 포인트 사용 취소 처리를 위한 포인트 별 금액 정보를 관리하는 ValueObject
    + `EarnPointService.java`, `UsePointService.java`
        + 외부 Port와 도메인 간의 연결
    + `AccountPersistAdopter.java`
        + Out-Port 구현체
        + `AccountDaoMapper.java`와 함께 도메인 모델과 엔티티 모델 간의 데이터 변환과 데이터 CRUD를 담당
        

## 실행 방법
```SHELL
프로젝트 루트> ./gradlew build
프로젝트 루트> ./gradlew bootRun

또는 
첨부 파일 저장 위치 > java -jar mpay-0.0.1-SNAPSHOT.jar
```
