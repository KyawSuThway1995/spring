package com.shopping.app.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String email;
	private String name;
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	public Account() {
		this.role = Role.ROLE_MEMBER;
	}
}
