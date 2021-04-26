package com.ntnu.gidd.controller;

import com.ntnu.gidd.dto.JwtTokenResponse;
import com.ntnu.gidd.dto.User.UserPasswordForgotDto;
import com.ntnu.gidd.dto.User.UserPasswordUpdateDto;
import com.ntnu.gidd.exception.PasswordIsIncorrectException;
import com.ntnu.gidd.exception.RefreshTokenNotFound;
import com.ntnu.gidd.exception.ResetPasswordTokenNotFoundException;
import com.ntnu.gidd.exception.UserNotFoundException;
import com.ntnu.gidd.model.PasswordResetToken;
import com.ntnu.gidd.security.config.JWTConfig;
import com.ntnu.gidd.security.service.JwtService;
import com.ntnu.gidd.service.User.UserService;
import com.ntnu.gidd.util.Response;
import com.ntnu.gidd.dto.User.UserPasswordResetDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("auth/")
@AllArgsConstructor
public class AuthenticationController {
	
	private JWTConfig jwtConfig;
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/refresh-token/")
	public JwtTokenResponse refreshToken(HttpServletRequest request) {
		String header = request.getHeader(jwtConfig.getHeader());
		try {
			return jwtService.refreshToken(header);
		} catch (RefreshTokenNotFound ex) {
			log.error("[X] Token refresh failed with exception", ex);
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
		}
	}
	
	@PostMapping("/change-password/")
	@ResponseStatus(HttpStatus.OK)
	public Response updatePassword(Principal principal, @RequestBody UserPasswordUpdateDto user) {
		try {
			userService.changePassword(principal, user);
		} catch (PasswordIsIncorrectException ex) {
			log.error("[X] User {} tried to change password with incorrect current password", principal.getName(), ex);
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
		}
		return new Response("Password was successfully changed");
	}
	
	/**
	 * Takes in a post request containing a UserPasswordForgotDto for which user should reset its password
	 * If user is found and all suceeds an UserPasswordForgotDto will be sent to the user containing a link for resetting password
	 * If user is not found a response stating that the user could not be found will be returned
	 *
	 * @param UserPasswordForgotDto of the users which should have its password reset
	 * @return
	 */
	@PostMapping("/forgot-password/")
	@ResponseStatus(HttpStatus.OK)
	public Response forgotPassword(@RequestBody UserPasswordForgotDto UserPasswordForgotDto) {
		try {
			userService.forgotPassword(UserPasswordForgotDto.getEmail());
			log.info("Email sent to {} for resetting password", UserPasswordForgotDto.getEmail());
		} catch (UserNotFoundException | MessagingException ex) {
			log.error("Could not find user {}", UserPasswordForgotDto);
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User not found");
		}
		return new Response("An email for resetting password has been sent!");
	}
	
	/**
	 * Takes in a post request containing the new passord the user want to change, the email of the user and the PasswordResetToken
	 * If the passwordResetToken is found and validated with the linked userEmail the new password will be set
/**
* Reset the given users password, if a valid token is provided. 
*
* @param UserPasswordResetDto userPasswordResetDto DTO the new passord the user want to change, the email of the user and the PasswordResetToken.
* @throws ResponseStatusException if the token or linked email is invalid.
**/
	 */
	@PostMapping("/reset-password/{passwordResetTokenId}/")
	@ResponseStatus(HttpStatus.OK)
	public Response resetPassword(@RequestBody UserPasswordResetDto userPasswordResetDto, @PathVariable UUID passwordResetTokenId) {
		try {
			userService.validateResetPassword(userPasswordResetDto, passwordResetTokenId);
			log.info("Password was changed for user {}", userPasswordResetDto.getEmail());
		} catch (UserNotFoundException e) {
			log.error("Could not change password. User {} was not found!", userPasswordResetDto.getEmail());
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User not found");
		} catch (ResetPasswordTokenNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
		return new Response("Password was changed sucessfully");
	}
	
}
