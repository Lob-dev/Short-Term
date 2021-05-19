### 프로젝트 주제

---

사용자가 요청한 URL을 특정 문자열로 단축하여 제공하는 URL Shortener Service.

<br/>

### 프로젝트 요구사항

---

- 주어지는 URL이 동일한 경우에 같은 단축 URL을 제공하여야 한다.
- 단축 URL은 8자리로 제공되어야 한다.
- 단축된 URL을 요청할 경우 원본 URL 정보로 이동되어야 한다.
- 단축된 URL과 원본 URL은 어떤 방식으로든 일정 시간 동안 보관되어야 한다.
- 단축된 URL을 통한 요청 횟수는 저장되어야 한다.

<br/>

### 프로젝트 사용 기술

---

- Spring Boot, Web, Data JPA
- Lombok
- MapStruct
- Base62 Encoder
- H2 Database
- Docker
- Redis
- Nginx

<br/>

**build.gradle**
```gradle
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-data-redis'
	implementation 'io.seruco.encoding:base62:0.1.3'
	implementation 'com.h2database:h2'

	implementation 'org.mapstruct:mapstruct:1.4.2.Final'
	implementation 'org.projectlombok:lombok:1.18.12'

	annotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final'
	annotationProcessor 'org.projectlombok:lombok:1.18.12'

	testImplementation('org.springframework.boot:spring-boot-starter-test') {
		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
	}
}
```

<br/>

**Docker script**
```docker
    
    docker run --name redis -p 6379:6379 -d redis
   
    docker run -p 5432:5432 -e POSTGRES_PASSWORD=password -e POSTGRES_USER=username -e POSTGRES_DB=springboot --name postgres_DB -d postgres
    
```

<br/>

### API End-Point

---

- */api/urls*

  접근할 URL을 받아 단축된 URL을 생성한 후 반환한다.

- */api/{shortUrl}*

  단축된 URL을 받아 요청 수를 카운팅한 뒤, 매핑된 원본 URL로 리다이렉트 시킨다.

- */api/{shortUrl}/info*

  단축된 URL이 어떤 원본 URL을 가리키고 있는지 정보를 제공한다.

- /api/{shortUrl}/count

  단축된 URL이 얼마나 호출되었는지 정보를 제공한다.

<br/>

### 프로젝트 목적

---

- Docker를 통해 Redis 인스턴스를 띄우고 간단한 설정을 하여 사용한다.
- Intellij의 http File을 사용하여 Postman을 대체한다.
- 단축 URL이 어떻게 만들어지는지 고려해보고 최소한의 알고리즘을 사용하여 이를 동작하게 한다.
  - url 정보를 굳이 숨길 필요는 없으므로 별도 상태 관리를 통한 중복 처리없이 같은 URL을 제공한다.
- Mapstruct를 사용하여 변환 계층 코드를 최소화한다.


<br/> 
