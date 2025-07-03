package lab.bookshop.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lab.bookshop.domain.Address;
import lab.bookshop.domain.Member;
import lab.bookshop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

	private final MemberService memberService;

	/**
	 * V1: 회원 전체 조회
	 * - 요청: 엔티티를 직접 사용
	 * - 응답: 엔티티를 직접 사용
	 */
	@GetMapping("/api/v1/members")
	public List<Member> membersV1() {
		return memberService.findMembers();
	}

	/**
	 * V2: 회원 전체 조회
	 * - 요청: 엔티티를 직접 사용하지 않고, DTO를 사용
	 * - 응답: DTO를 사용하여 필요한 정보만 반환
	 */
	@GetMapping("/api/v2/members")
	public Result membersV2() {
		List<Member> findMembers = memberService.findMembers();
		List<MemberDto> collect = findMembers.stream()
			.map(m -> new MemberDto(m.getName()))
			.collect(Collectors.toList());

		return new Result(collect.size(), collect);
	}

	@Data
	@AllArgsConstructor
	static class Result<T> {
		private int count;
		private T data;
	}

	@Data
	@AllArgsConstructor
	static class MemberDto {
		private String name;
	}

	/**
	 * V1: 요청과 응답을 엔티티로 처리
	 * - 요청: Member 엔티티를 직접 사용
	 * - 응답: ID만 반환하는 DTO를 사용
	 */
	@PostMapping("/api/v1/members")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}

	/**
	 * V2: 요청과 응답을 분리
	 * - 요청: Member 엔티티를 직접 사용하지 않고, DTO를 사용
	 * - 응답: ID만 반환하는 DTO를 사용
	 */
	@PostMapping("/api/v2/members")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
		Member member = Member.createMember(request.getName(), request.getAddress());
		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}

	/**
	 * V2: 회원 정보 수정
	 * - 요청: Member 엔티티를 직접 사용하지 않고, DTO를 사용
	 * - 응답: ID와 이름을 반환하는 DTO를 사용
	 */
	@PutMapping("/api/v2/members/{id}")
	public UpdateMemberResponse updateMemberV2(
		@PathVariable("id") Long id,
		@RequestBody @Valid UpdateMemberRequest request) {

		memberService.update(id, request.getName());
		Member member = memberService.findOne(id);
		return new UpdateMemberResponse(member.getId(), member.getName());
	}

	@Data
	static class UpdateMemberRequest {
		private Long id;
		@NotEmpty
		private String name;
	}

	@Data
	@AllArgsConstructor
	static class UpdateMemberResponse {
		private Long id;
		private String name;
	}

	@Data
	@AllArgsConstructor
	static class CreateMemberRequest {
		@NotEmpty
		private String name;
		private Address address;
	}

	@Data
	@AllArgsConstructor
	static class CreateMemberResponse {
		private Long id;
	}
}
