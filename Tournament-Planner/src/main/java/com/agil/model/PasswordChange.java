package com.agil.model;

public class PasswordChange {

	private String password;
	private String confirmPassword;
	private String oldPassword;

	public PasswordChange(String password, String confirmPassword, String oldPassword) {
		super();
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.oldPassword = oldPassword;
	}

	public PasswordChange() {
		// TODO Auto-generated constructor stub
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}
