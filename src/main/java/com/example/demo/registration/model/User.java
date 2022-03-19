package com.example.demo.registration.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
//@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "emailAddress"))
//Table named 'user' on postgreSQL is not a good idea
@Table(name = "person")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(updatable = false, nullable = false)
	private UUID uuid;
	
    @Column(nullable = false, length = 32)
	private String firstName;

    @Column(nullable = false, length = 32)
	private String lastName;

	@Column(nullable = false, unique = true, length = 128)
	private String emailAddress;

    @Column(nullable = false, length = 60) // BCrypt encoded password is 60 character long
	private String password;

	private boolean locked; // Account locked if too many bad password trials

	private boolean confirmedEmailAddress; // account has been activated with secret sent by email

	private LocalDateTime lastEmailConfirmation; // If email has been validated longer than X time, needs to be reconfirmed (confirmedEmailAddress switched to false and confirmation email sent)
	
	private boolean enabled; // account is not suspended (user can be banned)

	private LocalDateTime lastPasswordUpdate;
	
	private LocalDateTime lastLoginSuccess;

    @Column(nullable = false, length = 64)
	private String timeZone;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Role> roles;  // Set doesn't allow duplicates

	public User() {
		super();
	}

	public User(String firstName, String lastName, String emailAddress, String password) {
		super();
		this.uuid=UUID.randomUUID();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.locked=false;
		this.confirmedEmailAddress=false;
		this.enabled=true;
		this.timeZone="Europe/Paris";
		this.lastPasswordUpdate=LocalDateTime.now();
		roles = new HashSet<>();
	}

    public void addRole(Role role) {
        roles.add(role);
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getLastEmailConfirmation() {
        return lastEmailConfirmation;
    }

    public void setLastEmailConfirmation(LocalDateTime lastEmailConfirmation) {
        this.lastEmailConfirmation = lastEmailConfirmation;
    }

    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isConfirmedEmailAddress() {
		return confirmedEmailAddress;
	}

	public void setConfirmedEmailAddress(boolean confirmedEmailAddress) {
		this.confirmedEmailAddress = confirmedEmailAddress;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public LocalDateTime getLastLoginSuccess() {
		return lastLoginSuccess;
	}

	public void setLastLoginSuccess(LocalDateTime lastLoginSuccess) {
		this.lastLoginSuccess = lastLoginSuccess;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public LocalDateTime getLastPasswordUpdate() {
		return lastPasswordUpdate;
	}

	public void setLastPasswordUpdate(LocalDateTime lastPasswordUpdate) {
		this.lastPasswordUpdate = lastPasswordUpdate;
	}
}
