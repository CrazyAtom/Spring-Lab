package lab.bookshop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import lab.bookshop.domain.Member;
import lab.bookshop.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;

	@Test
	public void 회원가입() throws Exception {
		//given
		Member member = new Member();
		member.setName("kam");

		//when
		Long savedId = memberService.join(member);

		//then
		Assertions.assertEquals(member, memberService.findOne(savedId));
	}

	@Test
	public void 중복_회원_예외() throws Exception {
		//given

		//when

		//then
	}

}
