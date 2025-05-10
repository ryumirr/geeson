# 디비 설계도

## 특이사항

1. 리소스의 한계로 도메인 별 DB 격리는 논리적으로 진행

## 스키마 설계

### 스키마 리스트 
- order_db
- payment_db
- product_db
- inventory_db

## 주문 도메인(order_db)

### 테이블 리스트

| 테이블 이름                      | 설명             |
| --------------------------- | -------------- |
| **customers**               | 고객 기본 정보       |
| **orders**                  | 주문 기본 정보       |
| **order\_items**            | 주문 항목 상세       |
| **payments**                | 결제 정보          |
| **shipping\_addresses**     | 배송지 정보         |
| **shipments**               | 배송 상태 관리       |
| **order\_status\_history**  | 주문 상태 변경 이력    |
| **order\_returns**          | 반품/교환 요청       |
| **coupons**                 | 쿠폰 및 할인 정보     |
| **order\_coupons**          | 주문에 적용된 쿠폰     |
| **order\_events**           | 주문 이벤트 로그      |
| **inventory\_reservations** | 재고 예약 관리       |
| **refunds**                 | 환불 관리          |
| **order\_audit\_logs**      | 주문 감사 로그 (추적성) |
| **order\_notifications**    | 주문 상태 알림       |
| **transaction\_logs**       | 결제 트랜잭션 기록     |

테이블 DDL 스크립트는 따로 정리하겠습니다.

