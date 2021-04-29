### 프로젝트 주제

---

사용자가 요청한 URL을 특정 문자열로 단축하여 제공하는 URL Shortener Service.

<br/>

### 프로젝트 요구사항

---

- 주어지는 URL이 동일한 경우에도 다른 단축 URL을 제공하여야 한다.
- 단축 URL은 8자리로 제공되어야 한다.
- 단축된 URL을 요청할 경우 원본 URL 정보로 이동되어야 한다.
- 단축된 URL과 원본 URL은 어떤 방식으로든 일정 시간 동안 보관되어야 한다.
- 단축된 URL을 통한 요청 횟수는 저장되어야 한다.

<br/>

### 프로젝트 기술스택

---

- Spring Boot 
- Spring Boot Data Web
- Spring Boot Data Jdbc
- Spring Boot Data JPA
- Lombok
- MapStruct
- H2 Database
- Docker Compose
- Nginx 
- Redis

<br/>

### API End-Point

---

- */api/urls*

  URL을 제공받고 단축된 URL을 생성한 후 제공한다.

- */api/{shortUrl}*

  단축된 URL을 제공받고 요청 수를 카운팅한 뒤, 원본 URL로 리다이렉트 시킨다.

- */api/{shortUrl}/?*

  단축된 URL이 어느 원본 URL을 가리키고 있는지 제공한다.

- /api/{shortUrl}/count

  단축된 URL이 얼마나 호출되었는지 정보를 제공한다.

<br/>

### 프로젝트 고려 사항

---

<br/>

### 프로젝트 실행 방법

---

<br/>
