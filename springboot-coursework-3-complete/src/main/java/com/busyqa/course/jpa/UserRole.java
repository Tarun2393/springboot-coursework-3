package com.busyqa.course.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the user_role database table.
 * 
 */
@Entity
@Table(name="user_role")
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_USER_ROLE", unique=true, nullable=false)
	private int idUserRole;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="ID_ROLE", nullable=false)
	private Role role;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="ID_USER", nullable=false)
	private User user;

	public UserRole() {
	}

	public int getIdUserRole() {
		return this.idUserRole;
	}

	public void setIdUserRole(int idUserRole) {
		this.idUserRole = idUserRole;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}