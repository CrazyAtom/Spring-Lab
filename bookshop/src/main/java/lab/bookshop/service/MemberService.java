package lab.bookshop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.bookshop.domain.Member;
import lab.bookshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	/**
	 * 회원 가입
	 */
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	/**
	 * 회원 전체 조회
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	/**
	 * 회원 단건 조회
	 */
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}

	@Transactional
	public void update(Long id, String name) {
		Member member = memberRepository.findOne(id);
		member.setName(name);
	}
}
