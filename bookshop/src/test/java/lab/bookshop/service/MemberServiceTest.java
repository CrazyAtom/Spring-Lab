package lab.bookshop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import lab.bookshop.domain.Member;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberService memberService;

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

	@Test()
	public void 중복_회원_예외() throws Exception {
		//given
		Member member1 = new Member();
		member1.setName("kim");

		Member member2 = new Member();
		member2.setName("kim");

		//when
		memberService.join(member1);

		//then
		Assertions.assertThrows(IllegalStateException.class, () -> {
			memberService.join(member2);
		}, "중복 회원 예외가 발생해야 합니다.");
	}
}
