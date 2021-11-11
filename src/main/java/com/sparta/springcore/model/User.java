package com.sparta.springcore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블과 매핑 for JPA 관리
public class User extends Timestamped {
	// ID가 자동으로 생성 및 증가합니다.
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	@Column(nullable = true)
	private Long kakaoId;

	// 반드시 값을 가지도록 합니다.
	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRole role;

	public User(String username, String password, String email, UserRole role, Long kakaoId) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.kakaoId = kakaoId;
	}

	public User(String username, String password, String email, UserRole role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.kakaoId = null;
	}
}