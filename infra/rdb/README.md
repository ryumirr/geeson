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

테이블 DDL 스크립트는 따로 정리하겠습니다.

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

## 주문 도메인(order_db)

| 테이블 이름                      | 설명                 |
| --------------------------- | ------------------ |
| **products**                | 상품 기본 정보           |
| **warehouses**              | 창고 정보              |
| **inventory**               | 재고 기본 정보           |
| **inventory\_items**        | 개별 상품의 물리적 재고      |
| **inventory\_reservations** | 주문 시 재고 예약         |
| **stock\_movements**        | 재고 입출고 내역          |
| **suppliers**               | 공급자 정보             |
| **purchase\_orders**        | 입고 주문 (공급자로부터의 구매) |
| **inventory\_audit\_logs**  | 재고 감사 로그 (추적성)     |
| **inventory\_adjustments**  | 수동 재고 조정           |
| **reorder\_levels**         | 자동 재주문 설정          |
| **batch\_lots**             | 배치 관리 (유통기한 포함)    |
| **inventory\_transactions** | 재고 변동 트랜잭션 로그      |
| **stock\_alerts**           | 재고 부족 알림           |


## 결제 도메인

| 테이블 이름                       | 설명                      |
| ---------------------------- | ----------------------- |
| **payments**                 | 결제 요청 정보 (주문 1건당 1개)    |
| **payment\_methods**         | 사용된 결제 수단 정보            |
| **payment\_status\_history** | 결제 상태 변경 이력             |
| **payment\_gateways**        | 외부 결제대행(PG)사 정보         |
| **transactions**             | 결제 트랜잭션 기록 (실제 승인/취소 등) |
| **refunds**                  | 환불 처리 내역                |
| **billing\_keys**            | 정기결제/자동결제용 토큰화된 카드 정보   |
| **payment\_errors**          | 결제 실패 로그                |
| **settlements**              | 정산 대상 내역 (가맹점 정산용)      |
| **receipts**                 | 전자 영수증 발행 정보            |
| **payment\_audit\_logs**     | 결제 도메인 감사 로그 (옵션)       |


## 상품 도메인

| 테이블 이름                      | 설명                            |
| --------------------------- | ----------------------------- |
| **products**                | 상품 기본 정보                      |
| **product\_details**        | 상품의 상세 설명 (이미지, 스펙 등)         |
| **product\_categories**     | 상품 분류 정보                      |
| **product\_category\_map**  | 상품-카테고리 매핑 (N\:M)             |
| **product\_prices**         | 가격 정보 (할인가 포함)                |
| **product\_options**        | 옵션 그룹 (예: 색상, 사이즈 등)          |
| **product\_option\_values** | 옵션 항목 (예: 블랙, M)              |
| **product\_variants**       | 옵션 조합으로 구성된 실제 판매 상품 (SKU 단위) |
| **product\_images**         | 상품 이미지 정보                     |
| **brands**                  | 브랜드 정보                        |
| **tags**                    | 태그 (검색 및 필터용)                 |
| **product\_tag\_map**       | 상품-태그 매핑 테이블                  |
| **product\_reviews**        | 사용자 후기                        |
| **product\_qna**            | 사용자 질문 및 답변                   |
| **product\_visibility**     | 상품 노출 여부 및 상태 관리              |
| **product\_audit\_logs**    | 상품 변경 이력 (감사 로그)              |
