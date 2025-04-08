# Spring Boot Annotation

### 1. 핵심 구성 Annotations

- 스프링 부트 애플리케이션의 기본 구성을 담당하는 가장 핵심적인 어노테이션들

1. [_@SpringBootApplication_](#springbootapplication)
2. [_@Component_](#component)
3. [_@Bean_](#bean)
4. [_@Configuration_](#configuration)

### 2. 스테레오타입 Annotations

- 각 계층을 구분하는 기본적인 컴포넌트 어노테이션들

1. [_@Controller_](#controller) / [_@RestController_](#restcontroller)
2. [_@Service_](#service)
3. [_@Repository_](#repository)

### 3. 의존성 주입 관련

- 스프링의 핵심 기능인 DI를 구현하는 어노테이션들

1. [_@Autowired_](#autowired)
2. [_@Qualifier_](#qualifier)
3. [_@Resource_](#resource)

### 4. 웹 요청 처리

- REST API 개발시 가장 빈번하게 사용되는 어노테이션들

1. [_@RequestMapping_](#requestmapping)
2. [_@GetMapping_](#getmapping)
3. [_@PostMapping_](#postmapping)
4. [_@PutMapping_](#putmapping)
5. [_@DeleteMapping_](#deletemapping)
6. [_@RequestBody_](#requestbody)
7. [_@RequestParam_](#requestparam)
8. [_@PathVariable_](#pathvariable)
9. [_@ResponseBody_](#responsebody)

### 5. 트랜잭션 관리

- 데이터 무결성을 보장하는 중요한 어노테이션

1. _@Transactional_

### 6. 유효성 검증

- 입력값 검증을 위한 필수적인 어노테이션

1. _@Valid_

### 7. 예외 처리

- 전역적인 예외 처리를 위한 어노테이션들

1. _@ControllerAdvice_ / _@RestControllerAdvice_
2. _@ExceptionHandler_

---

### _@SpringBootApplication_

- Spring Boot Application으로 설정
- _@EnableAutoConfiguration_, _@ComponentScan_, *@Configuration*을 포함
  - _@EnableAutoConfiguration_: Spring Boot의 자동 설정 기능을 활성화
  - _@ComponentScan_: 현재 패키지와 하위 패키지를 스캔하여 _@Component_, _@Service_, _@Repository_, _@Controller_ 등의 어노테이션이 붙은 클래스를 자동으로 등록
  - _@Configuration_: Spring의 설정 클래스를 나타내며, _@Bean_ 메서드를 통해 빈을 등록할 수 있음

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### _@Bean_

- 메서드 레벨에서 사용되며, 반환되는 객체를 Spring 컨테이너에 등록
- 주로 _@Configuration_ 클래스 내에서 사용
- 외부 라이브러리 클래스를 Bean으로 등록할 때 사용

```java
@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:3306/db")
            .username("user")
            .password("password")
            .build();
    }
}
```

### _@Autowired_

- 의존성 자동 주입 (DI)
- Type을 통해 의존하는 객체를 찾아 자동으로 주입
- required 속성으로 필수 여부 설정 가능

```java
@Service
public class PaymentService {
    // 생성자 주입 방식 (권장)
    private final PaymentProvider paymentProvider;

    public PaymentService(PaymentProvider paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    // 필드 주입 방식
    @Autowired
    private PaymentProvider anotherPaymentProvider;
}
```

### _@Component_

- 개발자가 직접 작성한 클래스를 Bean으로 등록
- _@Service_, _@Repository_, _@Controller_ 의 메타 어노테이션

### _@Service_

- 비즈니스 로직을 처리하는 서비스 계층의 클래스임을 나타냄
- _@Component_ 의 특화된 형태
- 해당 클래스가 서비스 계층의 컴포넌트임을 명시적으로 표현

### _@Repository_

- 데이터(데이터베이스) 접근 계층의 클래스임을 나타냄
- DAO(Data Access Object) 객체임을 표시
- 데이터베이스 예외를 Spring의 통합된 예외로 변환

### _@RestController_

- REST API를 제공하는 controller로 설정 (view로 응답하지 않는 Controller)
- method의 반환 결과를 JSON형태로 반환
- data(json, xml 등)의 return이 주목적
- 해당 Annotation이 있는 Controller의 method는 HttpResponse로 바로 응답이 가능
- _@ResponseBody_ 역할을 자동적으로 해주는 Annotation
- _@Controller_, _@ResponseBody_ 를 포함

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public User createUser(@RequestBody @Valid UserDto userDto) {
        return userService.create(userDto);
    }
}
```

### _@Controller_

- View를 제공하는 controller로 설정, view(화면) return이 주목적
- API와 view를 동시에 사용하는 경우에 사용
- API 서비스로 사용하는 경우는 _@ResponseBody_ 를 사용하여 객체를 반환

### _@ResponseBody_

- Controller의 메서드가 반환하는 값을 HTTP 응답 본문에 직접 쓰도록 지정
- 주로 객체를 JSON 또는 XML 형태로 변환하여 반환할 때 사용

### _@RestControllerAdvice_

- _@ControllerAdvice_, _@ResponseBody_ 를 포함

### _@ControllerAdvice_

- Class 단위로 사용
- 전역적으로 예외를 처리할 수 있게 해주는 Annotation
- 모든 Controller에서 발생할 수 있는 예외를 한 곳에서 관리
- _@ExceptionHandler_, _@InitBinder_, _@ModelAttribute_ 메서드들을 포함할 수 있음

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(e.getMessage()));
    }
}
```

### _@ExceptionHandler_(ExceptionClassName.class)

- 특정 예외가 발생했을 때 처리할 메서드를 지정
- Controller나 _@ControllerAdvice_ 클래스 내에서 사용
- 지정된 예외 타입이 발생하면 해당 메서드가 처리
- 여러 예외를 하나의 메서드에서 처리 가능

### _@RequestMapping_

- URL 주소를 맵핑
- 요청 URL을 어떤 method가 처리할지 mapping해주는 Annotation
- 요청 받는 형식을 정의하지 않는다면, 자동적으로 GET으로 설정됨

### _@GetMapping_

- HTTP GET 요청을 특정 핸들러 메서드에 매핑
- _@RequestMapping(method = RequestMethod.GET)_ 의 축약형

```java
@GetMapping("/{id}/posts/{postId}")
public Post getUserPost(
    @PathVariable Long id,
    @PathVariable Long postId) {
    return postService.getUserPost(id, postId);
}
```

### _@PostMapping_

- HTTP POST 요청을 특정 핸들러 메서드에 매핑
- _@RequestMapping(method = RequestMethod.POST)_ 의 축약형

### _@PutMapping_

- HTTP PUT 요청을 특정 핸들러 메서드에 매핑
- _@RequestMapping(method = RequestMethod.PUT)_ 의 축약형
- 주로 리소스 수정 작업에 사용

### _@DeleteMapping_

- HTTP DELETE 요청을 특정 핸들러 메서드에 매핑
- _@RequestMapping(method = RequestMethod.DELETE)_ 의 축약형
- 주로 리소스 삭제 작업에 사용

### _@RequestParam_

- HTTP 요청 파라미터를 메서드의 파라미터로 바인딩
- URL의 쿼리 파라미터나 폼 데이터를 처리할 때 사용
- required 속성으로 필수 여부 설정 가능

```java
@GetMapping("/search")
public List<User> searchUsers(
    @RequestParam(required = false) String name,
    @RequestParam(defaultValue = "0") int page) {
    return userService.searchUsers(name, page);
}
```

### _@PathVariable_

- URL 경로의 일부를 파라미터로 사용할 수 있게 해주는 어노테이션
- RESTful API에서 리소스를 식별하는데 주로 사용
- URI 템플릿 변수를 처리하며, 경로의 특정 부분을 메서드 파라미터로 바인딩

```java
@RestController
@RequestMapping("/api")
public class UserController {
    // 단일 경로 변수 사용
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    // 다중 경로 변수 사용
    @GetMapping("/users/{userId}/orders/{orderId}")
    public Order getUserOrder(
        @PathVariable("userId") Long userId,
        @PathVariable("orderId") Long orderId) {
        return orderService.findUserOrder(userId, orderId);
    }

    // Optional PathVariable
    @GetMapping({"/users", "/users/{id}"})
    public User getOptionalUser(@PathVariable(required = false) Long id) {
        return id == null ? userService.getCurrentUser() : userService.findById(id);
    }
}
```

### _@RequestBody_

- HTTP 요청 본문을 자바 객체로 변환
- JSON/XML 데이터를 객체로 매핑할 때 사용
- POST, PUT 요청의 body 내용을 자바 객체로 매핑

### _@Valid_

- 객체의 유효성 검증을 실행
- JSR-303 검증 어노테이션으로 설정된 규칙에 따라 검증
- 주로 _@RequestBody_ 와 함께 사용되어 입력값 검증

```java
public class UserDto {
    @NotNull
    @Size(min = 2, max = 30)
    private String username;

    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;
}

@PostMapping("/users")
public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) {
    // 유효성 검증 통과 후 실행
    return ResponseEntity.ok(userService.create(userDto));
}
```

### _@Qualifier_

- _@Autowired_ 사용 시 같은 타입의 빈이 여러 개 있을 경우 특정 빈을 지정
- 빈의 이름을 기준으로 주입할 빈을 선택
- 동일한 타입의 빈이 여러 개 존재할 때 명확하게 식별하기 위해 사용

```java
@Service
public class PaymentService {
    @Autowired
    @Qualifier("kakaoPayment")
    private PaymentProvider paymentProvider;
}
```

### _@Resource_

- Java에서 제공하는 어노테이션으로 *@Autowired*와 유사한 기능
- 이름을 기준으로 빈을 주입 (없을 경우 타입으로 주입)
- name 속성을 통해 특정 빈을 지정 가능

### _@Transactional_

- 메서드나 클래스에 트랜잭션 기능을 부여
- 선언적 트랜잭션 관리를 가능하게 함
- 주요 속성:

  - readOnly: 읽기 전용 트랜잭션 설정
  - isolation: 트랜잭션 격리 수준 설정
    - DEFAULT
    - READ_UNCOMMITTED
    - READ_COMMITTED
    - REPEATABLE_READ
    - SERIALIZABLE
  - propagation: 트랜잭션 전파 방식 설정
    - REQUIRED (기본값)
    - REQUIRES_NEW
    - SUPPORTS
    - NOT_SUPPORTED
    - MANDATORY
    - NEVER
    - NESTED
  - rollbackFor: 특정 예외 발생 시 롤백 설정
  - noRollbackFor: 특정 예외 발생 시 롤백하지 않음
  - timeout: 트랜잭션 타임아웃 설정

```java
@Service
public class OrderService {
  @Transactional(rollbackFor = Exception.class)
  public void createOrder(OrderDto orderDto) {
      // 주문 생성 로직
      // 예외 발생시 자동 롤백
  }

  @Transactional(readOnly = true)
  public Order getOrder(Long id) {
      // 조회 전용 트랜잭션
      return orderRepository.findById(id);
  }
}
```
