# 스테이션3 다방 Back-end 개발자 과제
유혜린

## 구현 범위
### 기술 스택
Java 17, Spring Boot 2.7.4, Spring Security, Jwt Token, Spring Data JPA, QueryDSL, MySQL, JUnit5, H2 Database

### 빌드 & 실행
1) `./gradlew clean`
2) `./gradlew build -x test`

- swagger : http://localhost:8888/swagger-ui/index.html#/

### 기능 요구 사항
1. ✅ 회원 가입 후 기능을 사용할 수 있다.
2. ✅ 회원 인증은 jwt를 사용한다.
- 인증이 필요한 URL은 필터를 통해 인증 절차를 거치도록 설정하였습니다.
- 로그인 후 응답으로 받은 jwtToken 값을 요청 헤더의 `Authorization: Bearer {토큰값}` 형식으로 전달하세요.
3. ✅ 내 방을 등록할 수 있다.
- 하나의 방에 하나의 방 유형을 등록할 수 있다.
- 하나의 방에 여러 거래 유형(월세/전세)을 등록할 수 있다. (단, 보증금/월세를 포함해야 한다.)
4. ✅ 내방을 수정할 수 있다.
5. ✅ 내방을 삭제할 수 있다.
6. ✅ 내방 목록을 조회한다.
7. ✅ 전체방 목록을 조회한다.
- 검색조건 : 방 유형, 거래 유형, 가격 범위
- 조회 시 페이징 처리가 적용되었습니다.

### 개발 시 고려사항
1. 응답 코드 및 에러 메시지 관리 : 모든 응답 코드와 에러 메시지를 `Enum`으로 선언하여 공통으로 관리할 수 있도록 설계하였습니다. 이를 통해 코드의 가독성과 유지보수성을 높였습니다.
2. 일관된 응답 구조 : 모든 API 응답을 일관된 형식으로 반환하도록 표준 응답 구조를 정의했습니다. (`DabangResponse` 클래스)
3. 예외 처리: 애플리케이션에서 발생하는 모든 Exception을 일관된 응답 구조로 처리할 수 있도록 @ControllerAdvice를 사용해 글로벌 예외 처리기를 구현하였습니다.
- 공통 처리: 여러 예외를 공통적으로 처리하고 싶을 때는 DabangException을 사용해 적절한 ResponseCode만 지정하면 됩니다. 예: `throw new DabangException(ResponseCode.ROOM_NOT_FOUND)`
- 인증 예외 처리 : 인증이 안 된 경우와 같은 시큐리티 관련 예외는 스프링 시큐리티 필터에서 발생하며, 컨트롤러에 도달하기 전에 예외가 발생하므로 글로벌 예외 처리기에서 처리하지 않고 `AuthenticationEntryPoint`를 구현한 클래스에서 처리하도록 구현했습니다.
4. 클린 아키텍처 원칙 적용 : 클린 아키텍처 원칙을 적용하여 외부 의존성으로부터 비즈니스 로직을 분리하고, 코드의 유지보수성과 확장성을 높이고자 했습니다.
- 패키지 분리
  - application : 비즈니스 로직을 담당하는 서비스 계층이 포함되며, 비즈니스 요구 사항에 따라 유스케이스를 처리합니다.
  - domain : 핵심 비즈니스 모델, 도메인 규칙을 담은 엔티티 및 레포지토리가 위치합니다. 외부 인프라에 의존하지 않으며, 도메인 로직이 독립적으로 유지됩니다.
  - infrastructure : 데이터베이스와 상호작용을 담당하는 JPA 레포지토리 구현체 및 외부 시스템 연동을 위한 구현체가 포함됩니다. 이 계층은 비즈니스 로직이 아닌 기술적 구현에 중점을 둡니다.
  - web : 사용자 요청을 처리하는 컨트롤러와 DTO 를 포함하여, 사용자 인터페이스와 관련된 로직을 처리합니다.
  - config : 여러 애플리케이션 설정 클래스들이 위치합니다.
- 엔티티와 DTO 분리

### 실행 전 참고사항
1. 테스트 계정
    - ID : `test@test.com`
    - PASSWORD : `password123`
2. JWT Token 사용
- 로그인 후 응답으로 받은 jwtToken 값을 요청 헤더의 `Authorization: Bearer {토큰값}` 형식으로 전달하세요.


### API 명세
### 1. 회원가입
- API URL : localhost:8888/api/v1/register
- Http Method : POST
- Request Body
  ```json
  {
    "email" : "test2@test.com",
    "password" : "password123"
  }

- Response
  ```json
  {
    "data": {
        "userId": 1,
        "email": "test2@test.com"
    },
    "result": 200,
    "message": "성공"
  }
- curl
  ```
  curl --location 'localhost:8888/api/v1/register' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "email" : "test2@test.com",
  "password" : "password123"
  }'

### 2. 로그인
- API URL : localhost:8888/api/v1/authenticate
- Http Method : POST
- Request Body
  ```json
  {
    "email" : "test2@test.com",
    "password" : "password123"
  }
- Response
  ```json
  {
    "data": {
        "jwtToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MkBhYmx5LmNvbSIsInVzZXJJZCI6NCwiaWF0IjoxNzI1ODAzMTE1LCJleHAiOjE3MjU4MzkxMTV9.Yfs165QNT18EVi1kzxOgJ8t_Ab387_liCviEUg8lYUA"
    },
    "result": 200,
    "message": "성공"
  }
- curl
  ```
  curl --location 'localhost:8888/api/v1/authenticate' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "email" : "test2@test.com",
  "password" : "password123"
  }'

### 3. 전체 방 목록 조회
- API URL : localhost:8888/api/v1/search/rooms/type/{roomType}?page=0&size=3
- Http Method : GET
- Path Param
    - roomType : 방 유형 (ALL or ONE_ROOM or TWO_ROOM or THREE_ROOM)
- Request Param
    - page : 페이지 번호
    - size : 한 페이지 당 갯수
    - sellingTypeList : 거래 유형 리스트 (MONTHLY_RENT, LEASE)
    - depositMin : 보증금 범위 최소값 (만원 단위, ex: 1000만원 → 1000)
    - depositMax : 보증금 범위 최대값 (만원 단위, ex: 1000만원 → 1000)
    - priceMin : 월세 범위 최소값 (만원 단위, ex: 1000만원 → 1000)
    - priceMax : 월세 범위 최대값 (만원 단위, ex: 1000만원 → 1000)
- Response
  ```json
  {
    "data": {
        "items": [
            {
                "id": 1,
                "roomType": "ONE_ROOM",
                "sellingType": "MONTHLY_RENT",
                "deposit": 1000,
                "price": 50,
                "description": "원룸1000에50입니다."
            },
            {
                "id": 2,
                "roomType": "ONE_ROOM",
                "sellingType": "MONTHLY_RENT",
                "deposit": 2000,
                "price": 30,
                "description": "원룸2000에30야~"
            },
            {
                "id": 3,
                "roomType": "TWO_ROOM",
                "sellingType": "LEASE",
                "deposit": 5000,
                "price": 0,
                "description": "투룸5000전세!!"
            }
        ],
        "pagination": {
            "totalPages": 2,
            "totalElements": 4,
            "size": 3,
            "number": 0,
            "numberOfElements": 3
        }
    },
    "result": 200,
    "message": "성공"
  }
- curl
  ```
  curl --location 'localhost:8888/api/v1/search/rooms/type/ALL?sellingTypeList=MONTHLY_RENT%2C%20LEASE&depositMin=0&depositMax=999999&priceMin=0&priceMax=999999&page=0&size=3' \
    --header 'Authorization: ••••••'

### 4. 내 방 생성
- API URL : localhost:8888/api/v1/my/rooms
- Http Method : POST
- Request Body
  ```json
  {
    "roomType" : "TWO_ROOM",
    "sellingType" : "LEASE",
    "deposit" : 7000,
    "price" : 0,
    "description" : "넓은 투룸이에요~"
  }
- Response
  ```json
  {
    "data": {
        "id": 5,
        "roomType": "TWO_ROOM",
        "sellingType": "LEASE",
        "deposit": 7000,
        "price": 0,
        "description": "넓은 투룸이에요~"
    },
    "result": 200,
    "message": "성공"
  }
- curl
  ```
  curl --location 'localhost:8888/api/v1/my/rooms' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: ••••••' \
  --data '{
    "roomType" : "TWO_ROOM",
    "sellingType" : "LEASE",
    "deposit" : 7000,
    "price" : 0,
    "description" : "넓은 투룸이에요~"
  }'
  
### 5. 내 방 조회
- API URL : localhost:8888/api/v1/my/rooms/{roomId}
- Http Method : GET
- Path Param
  - roomId : 룸 id
- Request Param
    - page : 페이지 번호
    - size : 페이지 당 데이터 갯수
- Response
  ```json
  {
    "data": {
        "id": 6,
        "roomType": "TWO_ROOM",
        "sellingType": "LEASE",
        "deposit": 7000,
        "price": 0,
        "description": "넓은 투룸이에요~"
    },
    "result": 200,
    "message": "성공"
  }
- curl
  ```
  curl --location 'localhost:8888/api/v1/my/rooms/6' \
    --header 'Authorization: ••••••' 

### 6. 내 방 수정
- API URL : localhost:8888/api/v1/my/rooms/{roomId}
- Http Method : PUT
- Path Param
  - roomId : 방 id
- Request Body
  ```json
  {
    "roomType" : "TWO_ROOM",
    "sellingType" : "LEASE",
    "deposit" : 8500,
    "price" : 15,
    "description" : "넓은 투룸이에요~ 보증금 수정"
  }
- Response
  ```json
  {
    "data": {
        "id": 5,
        "roomType": "TWO_ROOM",
        "sellingType": "LEASE",
        "deposit": 8500,
        "price": 15,
        "description": "넓은 투룸이에요~ 보증금 수정"
    },
    "result": 200,
    "message": "성공"
  }
- curl
  ```
  curl --location 'localhost:8888/api/v1/my/rooms/5' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: ••••••' \
  --data '{
    "roomType" : "TWO_ROOM",
    "sellingType" : "LEASE",
    "deposit" : 8500,
    "price" : 15,
    "description" : "넓은 투룸이에요~ 보증금 수정"
  }'


### 7. 내 방 삭제
- API URL : localhost:8888/api/v1/my/rooms/{roomId}
- Http Method : DELETE
- Path Param
    - roomId : 룸 id
- Response
    - data : 룸 id
  ```json
  {
    "data": 5,
    "result": 200,
    "message": "성공"
  }
- curl
  ```
  curl --location --request DELETE 'localhost:8888/api/v1/my/rooms/5' \
    --header 'Authorization: ••••••'

### 8. 내 방 목록 조회
- API URL : localhost:8888/api/v1/my/rooms?page=0&size=2
- Http Method : GET
- Request Param
    - page : 페이지 번호
    - size : 한 페이지 당 갯수
- Response
  ```json
  {
    "data": {
        "items": [
            {
                "id": 6,
                "roomType": "TWO_ROOM",
                "sellingType": "LEASE",
                "deposit": 7000,
                "price": 0,
                "description": "넓은 투룸이에요~"
            },
            {
                "id": 7,
                "roomType": "TWO_ROOM",
                "sellingType": "LEASE",
                "deposit": 7000,
                "price": 0,
                "description": "넓은 투룸이에요~"
            }
        ],
        "pagination": {
            "totalPages": 2,
            "totalElements": 3,
            "size": 2,
            "number": 0,
            "numberOfElements": 2
        }
    },
    "result": 200,
    "message": "성공"
  }
- curl
  ```
  curl --location 'localhost:8888/api/v1/my/rooms?page=0&size=2' \
    --header 'Authorization: ••••••'
