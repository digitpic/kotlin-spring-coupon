# kotlin-spring-coupon

## step3 : distributed-lock 

## 문제
- 편의점과 제휴로 총 100장의 쿠폰을 제공
- 쿠폰은 회사에서 발급

## 구현 기능 목록
- 쿠폰 발급
  - user 당 쿠폰은 1장만 발급 가능
  - 이미 쿠폰을 발급 받은 user 가 쿠폰 발급을 시도하면 이전에 발급받은 쿠폰을 리턴
  - 쿠폰은 숫자와 영대소문자로 구성된 총 10자리 문자열
  - user 가 쿠폰 발급을 요청할 때 쿠폰을 생성해도 무관

## 예외 처리 목록
- 쿠폰 100장이 모두 발급된 후에는 http 상태 코드 404 반환
- 쿠폰 100장이 모두 발급된 경우를 제외하고는 user 가 쿠폰 발급에 실패하는 경우는 없다고 가정

## 제한 조건
- multi application 환경임을 가정하고 Lock 구현

## 구현 steps
- step1. 비관 락을 이용해 구현
- step2. 낙관 락을 이용해 구현
- step3. 분산 락을 이용해 구현

## 과제 제출 요령
- github 에 repository 생성 후, 각 단계별로 pull request 남기기
- k6 부하 테스트 결과를 pull request 에 포함시키기
