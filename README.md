# blog-search


### 기능 요구구 사항
- [ ] 블로그 검색
    - [X] Open API 연결
    - [X] 키워드를 통한 검색
    - [X] 검색 결과 정렬
        - [X] 정확도순, 최신순 기능 제공
    - [X] 검색 결과 페이징
    - [ ] 검색 소스 여러 개 제공
        - [ ] 소스별 유효성 체크
            - [ ] 키워드 유효성 (길이 제한)
            - [ ] 특정 블로그 조회 조건 확
    - [ ] Open API 서킷 브레이커
- [ ] 인기 검색어 목록
    - [ ] 많이 검색한 순서대로 10개 키워드 제공
    - [ ] 검색어 검색 횟수 표기
    - [ ] 검색 동시성 제어

### 프로그램 요구 사항
- [ ] 기능별 테스트 코드
- [ ] 에러 처리
- [ ] 불필요한 코드 제거
- [ ] 멀티 모듈 구성
