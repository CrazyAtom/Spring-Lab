package lab.servlet.web.frontcontroller.v4.controller;

import java.util.Map;

import lab.servlet.domain.member.Member;
import lab.servlet.domain.member.MemberRepository;
import lab.servlet.web.frontcontroller.v4.ControllerV4;

public class MemberSaveControllerV4 implements ControllerV4 {

	private final MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	public String process(Map<String, String> paramMap, Map<String, Object> model) {
		String username = paramMap.get("username");
		int age = Integer.parseInt(paramMap.get("age"));

		Member member = new Member(username, age);
		memberRepository.save(member);

		model.put("member", member);
		return "save-result";
	}
}
