package lab.servlet.web.springmvc.v2;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab.servlet.domain.member.Member;
import lab.servlet.domain.member.MemberRepository;

@Controller
public class SpringMemberControllerV2 {

	private final MemberRepository memberRepository = MemberRepository.getInstance();

	@RequestMapping("/springmvc/v2/members/new-form")
	public ModelAndView newForm() {
		return new ModelAndView("new-form");
	}

	@RequestMapping("/springmvc/v2/members/save")
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		int age = Integer.parseInt(request.getParameter("age"));

		Member member = new Member(username, age);
		memberRepository.save(member);

		ModelAndView mv = new ModelAndView("save-result");
		mv.addObject("member", member);
		return mv;
	}

	@RequestMapping("/springmvc/v2/members")
	public ModelAndView members() {
		List<Member> members = memberRepository.findAll();

		ModelAndView mv = new ModelAndView("members");
		mv.addObject("members", members);
		return mv;
	}
}
