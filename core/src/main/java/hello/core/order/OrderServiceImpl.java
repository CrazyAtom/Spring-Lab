package hello.core.order;

import hello.core.discount.DiscoutPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscoutPolicy discoutPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscoutPolicy discoutPolicy) {
        this.memberRepository = memberRepository;
        this.discoutPolicy = discoutPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discoutPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
