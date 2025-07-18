package lab.bookshop.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

	@Id
	@Column(name = "member_id")
	@GeneratedValue
	private Long id;

	private String name;

	@Embedded
	private Address address;

	@JsonIgnore
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	//== 생성 메소드 ==//
	public static Member createMember(String name, Address address) {
		Member member = new Member();
		member.setName(name);
		member.setAddress(address);
		return member;
	}
}
