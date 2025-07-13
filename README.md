# Geeson - 마이크로서비스 기반 이커머스 플랫폼

## 📋 프로젝트 개요

Geeson은 마이크로서비스 아키텍처를 기반으로 한 이커머스 플랫폼입니다. 주문, 결제, 재고 관리, 상품 관리 등의 핵심 비즈니스 기능을 독립적인 서비스로 분리하여 구현했습니다.

## 🏗️ 아키텍처

### 기술 스택
- **언어**: Java 21
- **프레임워크**: Spring Boot 3.4.5, Spring Cloud 2023.0.1
- **빌드 도구**: Gradle
- **데이터베이스**: MySQL 8.0
- **메시징**: Apache Kafka
- **캐시**: Redis
- **컨테이너**: Docker & Docker Compose

### 서비스 구성
```
geeson/
├── api/                    # API 서비스들
│   ├── inventory-api/      # 재고 관리 API
│   ├── order-api/         # 주문 관리 API
│   ├── payment-api/       # 결제 관리 API
│   └── product-api/       # 상품 관리 API
├── application/           # 애플리케이션 서비스들
│   ├── inventory-app/     # 재고 애플리케이션
│   ├── order-app/         # 주문 애플리케이션
│   └── payment-app/       # 결제 애플리케이션
├── domain/               # 도메인 모델들
│   ├── inventory-domain/ # 재고 도메인
│   ├── order-domain/     # 주문 도메인
│   ├── payment-domain/   # 결제 도메인
│   └── product-domain/   # 상품 도메인
├── infra/               # 인프라스트럭처
│   ├── rdb/            # 데이터베이스 관련
│   ├── queue/          # 메시징 큐 (Kafka)
│   └── redis/          # Redis 관련
├── support/            # 공통 지원 모듈들
│   ├── messaging/      # 메시징 지원
│   ├── logging/        # 로깅 지원
│   ├── web-api/        # 웹 API 지원
│   └── uuid/           # UUID 생성 지원
└── commander/          # 관리자 웹 인터페이스
```

## 🚀 시작하기

### 사전 요구사항
- Java 21
- Docker & Docker Compose
- Gradle (선택사항, Gradle Wrapper 포함)

### 1. 저장소 클론
```bash
git clone <repository-url>
cd geeson
```

### 2. Docker Compose로 서비스 실행
```bash
docker compose up -d
```

이 명령어는 다음 서비스들을 시작합니다:
- MySQL 8.0 (포트: 3306)
- Apache Kafka (포트: 9092)
- Redis (포트: 6379)
- 각종 API 서비스들

## 🔧 개발 환경 설정

### 로컬 개발
1. **데이터베이스 스키마 생성**

# 스키마 생성
source /path/to/infra/rdb/ddl-script/*.sql
```

2. **개별 서비스 실행**
```bash
# 주문 API 실행
./gradlew :api:order-api:bootRun --spring.profiles.active=local

# 결제 API 실행
./gradlew :api:payment-api:bootRun --spring.profiles.active=local

# 재고 API 실행
./gradlew :api:inventory-api:bootRun --spring.profiles.active=local
```

### 빌드
```bash
# 전체 프로젝트 빌드
./gradlew build

# 특정 모듈 빌드
./gradlew :api:order-api:build
```

## 📊 주요 기능

### 주문 관리
- 주문 생성 및 처리

### 결제 관리

### 재고 관리
- 창고 등록 및 관리
- 재고 예약 시스템

### 상품 관리

## 🔄 메시징 아키텍처

Kafka를 사용한 이벤트 기반 아키텍처:
- **주문 이벤트**: 주문 생성,
- **결제 이벤트**: 결제 요청, 완료
- **재고 이벤트**: 재고 예약, 완료

## 🗄️ 데이터베이스

각 도메인별로 독립적인 데이터베이스 스키마를 사용:
- `order_db`: 주문 관련 데이터
- `payment_db`: 결제 관련 데이터
- `inventory_db`: 재고 관련 데이터
- `product_db`: 상품 관련 데이터

## 🧪 테스트

```bash
# 전체 테스트 실행
./gradlew test

# 특정 모듈 테스트
./gradlew :api:order-api:test
```

## 📝 로깅

모든 서비스는 구조화된 로깅을 사용하며, 로그는 `./logs` 디렉토리에 저장됩니다.


## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 📞 지원

문제가 발생하거나 질문이 있으시면 이슈를 생성해 주세요.

---

**주의사항**: 
- MySQL 데이터를 보존하려면 `docker compose down` 명령어를 `-v` 플래그 없이 사용하세요.
- `docker compose down -v`를 사용하면 볼륨이 제거되어 데이터가 손실됩니다. 
