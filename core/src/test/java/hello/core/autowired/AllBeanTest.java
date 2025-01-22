package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscoutPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    public void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");
        Assertions.assertThat(discountPrice).isEqualTo(1000);
    }

    static class DiscountService {
        private final Map<String, DiscoutPolicy> policyMap;
        private final List<DiscoutPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscoutPolicy> policyMap, List<DiscoutPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscoutPolicy discoutPolicy = policyMap.get(discountCode);

            System.out.println("discountCode = " + discountCode);
            System.out.println("discoutPolicy = " + discoutPolicy);

            return discoutPolicy.discount(member, price);
        }
    }
}
