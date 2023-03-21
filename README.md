# blog-search


### 기능 요구 사항
- [ ] 블로그 검색
    - [X] Open API 연결
    - [X] 키워드를 통한 검색
    - [X] 검색 결과 정렬
        - [X] 정확도순, 최신순 기능 제공
    - [X] 검색 결과 페이징
    - [X] 특정 블로그 조회 조건 확인
    - [X] 검색 소스 여러 개 제공
        - [X] 소스별 유효성 체크
            - [X] 키워드 유효성 (길이 제한)
- [X] 인기 검색어 목록
    - [X] 많이 검색한 순서대로 10개 키워드 제공
    - [X] 검색어 검색 횟수 표기
    - [X] 검색 동시성 제어

### 프로그램 요구 사항
- [X] 기능별 테스트 코드
- [X] 에러 처리
- [X] 불필요한 코드 제거


### 고려할 사항들
- 캐시에서 Lock을 잡을 경우 성능 및 안정성 차이
- 서킷브레이커 사용
- 멀티 모듈 구성 의존 제약 사항
- 로그 처리 (logback)
- tomcat, hikari timeout 및 pool
- 테스트코드를 통한 문서화


### API 문서
- 서버 실핼 후 : localhost:8080/swagger-ui.html
