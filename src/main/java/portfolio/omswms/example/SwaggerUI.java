package portfolio.omswms.example;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * ============================================================
 * Swagger UI 어노테이션 정리
 * ============================================================
 *
 * http://localhost:8080/swagger-ui/index.html
 *
 * Swagger 어노테이션 사용법을 정리한 예제
 * GET /api/example/swagger/users | 회원 목록 조회
 * GET /api/example/swagger/users/{'id'} 회원 단건 조회
 * GET /api/example/swagger/search 회원 검색
 * POST /api/example/swagger/users 회원 등록
 * GET /api/example/swagger/responses/{'id'} 회원 조회 응답 코드 예제
 * GET /api/example/swagger/old-users 이전 버전 API
 * GET /api/example/swagger/secure 인증 필요 API
 * 위는 예상 API 목록의 예시야. 실제로는 7개 API가 표시되고, @Hidden이 붙은 1개는 표시되지 않아.
 *
 * 1. @Tag                 : API 그룹 이름 및 설명
 * 2. @Operation           : API 요약 및 상세 설명
 * 3. @Parameter           : 요청 파라미터 설명
 * 4. @Schema              : DTO 및 필드 설명
 * 5. @ApiResponse         : 응답 상태 코드와 설명
 * 6. @ApiResponses        : 여러 응답 상태를 묶어서 정의
 * 7. @Hidden              : Swagger 문서에서 API 숨기기
 * 8. @OpenAPIDefinition   : API 문서 전체 정보 설정
 * 9. @SecurityScheme      : JWT 등 인증 방식 정의
 * 10. @SecurityRequirement: 특정 API에 인증 방식 적용
 *
 * Swagger UI 접속:
 * http://localhost:8080/swagger-ui/index.html
 *
 * OpenAPI JSON:
 * http://localhost:8080/v3/api-docs
 *
 * 주의:
 * - Swagger 어노테이션은 주로 문서화 용도
 * - 실제 인증, 유효성 검사, 오류 처리는 별도로 구현해야 함
 * - 이 클래스는 학습용 예제이며 실제 서비스에서는 제거하거나 비활성화
 */


/*
 * ============================================================
 * @OpenAPIDefinition
 * ============================================================
 *
 * Swagger 문서 전체의 제목, 버전, 설명을 설정
 *
 * info.title       : API 문서 제목
 * info.version     : API 버전
 * info.description : API 문서 설명
 */
@OpenAPIDefinition(
        info = @Info(
                title = "OMS/WMS API",
                version = "0.1",
                description = "OMS/WMS 프로젝트 API 문서"
        )
)
/*
 * ============================================================
 * @SecurityScheme
 * ============================================================
 *
 * Swagger UI에서 JWT 인증을 사용할 수 있도록
 * 인증 방식을 문서에 등록
 *
 * name         : 인증 방식의 이름
 * type         : HTTP 인증
 * scheme       : bearer 인증
 * bearerFormat : JWT 토큰 형식
 *
 * 설정하면 Swagger UI에 Authorize 버튼이 표시됨
 *
 * 주의: 실제 JWT 인증 기능을 구현하는 것은 아님
 */
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
/*
 * ============================================================
 * @Tag
 * ============================================================
 *
 * 컨트롤러의 API들을 하나의 그룹으로 묶어 표시
 *
 * name        : 그룹 이름
 * description : 그룹 설명
 */
@Tag(
        name = "Swagger 예제 API",
        description = "Swagger 어노테이션 사용법을 정리한 예제"
)
@RestController
@RequestMapping("/api/example/swagger")
public class SwaggerUI {

    /*
     * ========================================================
     * 1. @Operation
     * ========================================================
     *
     * API의 이름과 상세 설명을 작성
     *
     * summary     : API 주소 옆에 표시되는 간단한 설명
     * description : 펼쳤을 때 확인할 수 있는 상세 설명
     * deprecated  : 사용 중단 예정인 API 표시
     */
    @Operation(
            summary = "회원 목록 조회",
            description = "전체 회원 목록을 조회하는 예제 API"
    )
    @GetMapping("/users")
    public List<String> getUsers() {
        return List.of("홍길동", "김철수");
    }


    /*
     * ========================================================
     * 2. @Parameter
     * ========================================================
     *
     * API 요청에 사용되는 파라미터 설명
     *
     * description : 파라미터 설명
     * example     : 입력 예시
     * required    : 필수 파라미터 여부
     *
     * @PathVariable : URL 경로에서 값을 받음
     * @RequestParam  : URL 쿼리 파라미터에서 값을 받음
     *
     * 위 두 개는 Spring MVC 어노테이션
     */
    @Operation(
            summary = "회원 단건 조회",
            description = "회원 ID를 이용하여 회원을 조회"
    )
    @GetMapping("/users/{id}")
    public String getUser(
            @Parameter(
                    description = "조회할 회원 ID",
                    example = "1",
                    required = true
            )
            @PathVariable Long id

    ) {
        return "회원 ID: " + id;
    }


    /*
     * ========================================================
     * 3. @Parameter + @RequestParam
     * ========================================================
     *
     * 쿼리 파라미터 예시
     *
     * 요청:
     * GET /api/example/swagger/search?keyword=홍길동
     */
    @Operation(
            summary = "회원 검색",
            description = "이름을 이용하여 회원을 검색"
    )
    @GetMapping("/search")
    public String searchUser(

            @Parameter(
                    description = "검색할 회원 이름",
                    example = "홍길동"
            )
            @RequestParam String keyword

    ) {
        return "검색어: " + keyword;
    }


    /*
     * ========================================================
     * 4. @Schema + @RequestBody
     * ========================================================
     *
     * @Schema
     * DTO 클래스 및 필드의 구조, 설명, 예시를 정의
     *
     * description : 필드 설명
     * example     : 예시 데이터
     * requiredMode: 필수 여부를 문서에 표시
     *
     * @RequestBody
     * HTTP 요청 Body의 JSON 데이터를 DTO로 변환
     *
     * 주의:
     * @Schema의 필수 여부 설정은 문서화 목적
     * 실제 값 검증은 @NotBlank, @NotNull 등의
     * Bean Validation을 사용해야 함
     */
    @Schema(description = "회원 등록 요청 DTO")
    public record UserRequest(

            @Schema(
                    description = "회원 이름",
                    example = "홍길동",
                    requiredMode = Schema.RequiredMode.REQUIRED
            )
            String name,

            @Schema(
                    description = "회원 나이",
                    example = "25"
            )
            Integer age

    ) {}


    @Operation(
            summary = "회원 등록",
            description = "JSON 데이터를 받아 회원을 등록하는 예제"
    )
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(
            @RequestBody UserRequest request
    ) {
        return request.name() + " 회원 등록 완료";
    }


    /*
     * ========================================================
     * 5. @ApiResponse / @ApiResponses
     * ========================================================
     *
     * API의 HTTP 응답 상태 코드를 문서화
     *
     * responseCode : HTTP 상태 코드
     * description  : 해당 응답에 대한 설명
     *
     * 200 : 요청 성공
     * 201 : 리소스 생성 성공
     * 400 : 잘못된 요청
     * 401 : 인증되지 않음
     * 403 : 접근 권한 없음
     * 404 : 리소스를 찾을 수 없음
     * 500 : 서버 내부 오류
     *
     * 실제 HTTP 응답을 변경하는 기능은 아님
     */
    @Operation(
            summary = "회원 조회 응답 코드 예제",
            description = "여러 응답 상태를 Swagger에 표시"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "회원 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "회원이 존재하지 않음"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류"
            )
    })
    @GetMapping("/responses/{id}")
    public String responseExample(
            @PathVariable Long id
    ) {
        return "회원 ID: " + id;
    }


    /*
     * ========================================================
     * 6. @Operation(deprecated = true)
     * ========================================================
     *
     * 더 이상 사용하지 않거나
     * 추후 제거할 예정인 API를 문서에 표시
     */
    @Operation(
            summary = "이전 버전 회원 조회 API",
            description = "현재는 사용을 권장하지 않는 API",
            deprecated = true
    )
    @GetMapping("/old-users")
    public String oldUsers() {
        return "이전 버전 API";
    }


    /*
     * ========================================================
     * 7. @Hidden
     * ========================================================
     *
     * Swagger UI에서 특정 API를 숨기는 기능
     *
     * 실제 API 호출을 차단하는 것은 아님
     * 보안 기능이 아니라 문서 표시 여부만 변경
     */
    @Hidden
    @GetMapping("/hidden")
    public String hiddenExample() {
        return "Swagger 문서에 표시되지 않는 API";
    }


    /*
     * ========================================================
     * 8. @SecurityRequirement
     * ========================================================
     *
     * 특정 API에 인증이 필요하다는 정보를 표시
     *
     * name은 @SecurityScheme에서 설정한 이름과 일치해야 함
     *
     * Swagger UI에서 자물쇠 아이콘이 표시되고
     * Authorize 버튼에 등록한 토큰을 요청에 사용할 수 있음
     *
     * 실제 인증은 Spring Security 등에서 구현
     */
    @Operation(
            summary = "인증 필요 API 예제",
            description = "JWT 인증을 사용하는 API 문서 예제"
    )
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/secure")
    public String secureExample() {
        return "인증 API 예제";
    }

}
