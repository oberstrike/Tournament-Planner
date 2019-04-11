package com.agil.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.agil.model.Member;
import com.agil.service.MemberService;

@Component
public class MemberValidator implements Validator {

	@Autowired
	private MemberService memberService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Member.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Member member = (Member) target;
		if (memberService.findByUsername(member.getUsername()) != null) {
			errors.rejectValue("username", "username.duplicate");
		}
		
		if(memberService.findByEmail(member.getEmail()) != null) {
			errors.rejectValue("email", "email.duplicate");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "password.notempty");
		if(!member.getPassword().equals(member.getPasswordConfirm())) {
			errors.rejectValue("password", "password.notequals");
		}
		
		
	}

}
