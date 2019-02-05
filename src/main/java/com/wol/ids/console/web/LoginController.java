package com.wol.ids.console.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.wol.ids.console.Utils;
import com.wol.ids.console.model.User;
import com.wol.ids.console.service.UserService;

@Controller
public class LoginController
{

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	public LoginController()
	{
	}

	@GetMapping("/")
	public String index()
	{
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String getLogin(ModelMap map)
	{
		logger.debug("Received getLogin request");
		map.put("loginForm", new LoginForm());
		return "pages/account/login";
	}

	@PostMapping("/doLogin")
	public String doLogin(@Valid @ModelAttribute("loginForm") LoginForm form,
	        BindingResult bindingResult, HttpSession httpSession)
	{
		logger.debug("Received doLogin request");

		validateLogin(form, bindingResult);

		if (bindingResult.hasErrors())
		{
			return "pages/account/login";
		}

		User user = null;

		try
		{
			user = userService.findByEmail(form.email);
		}
		catch (Exception e)
		{
			logger.error("Failed to load user account: " + e.getMessage());
		}

		if (user == null)
		{
			bindingResult.addError(new ObjectError("error", "User with email [" + form.email
			        + "] is unknown. " + "Please register for an account."));
			return "pages/account/login";
		}
		else
		{
			logger.info("Attempted login from username: " + form.email);

			if (form.password.equals(Utils.Crypto.decrypt(user.getPassword())))
			{
				userService.login(user);
				httpSession.setAttribute("activeUser", user);
			}
			else
			{
				bindingResult
				        .addError(new ObjectError("error", "Passwords did not match our records"));
				return "pages/account/login";
			}
		}

		return "redirect:/dashboard";
	}

	@PostMapping("doLogout")
	public String doLogout(HttpSession httpSession)
	{
		logger.debug("Received doLogout request");
		User activeUser = (User) httpSession.getAttribute("activeUser");
		userService.logout(activeUser);
		httpSession.removeAttribute("activeUser");
		return "redirect:/login";
	}

	@GetMapping("/register")
	public String getRegistration(ModelMap modelMap)
	{
		logger.debug("Received getRegistration request");
		modelMap.put("registrationForm", new RegistrationForm());
		return "pages/account/register";
	}

	@PostMapping("/doRegister")
	public String doRegistration(@Valid @ModelAttribute("registrationForm") RegistrationForm form,
	        BindingResult bindingResult)
	{
		logger.debug("Received doRegistration request");

		vaidateRegistration(form, bindingResult);

		if (bindingResult.hasErrors())
		{
			return "pages/account/register";
		}

		User user = createUser(form);
		userService.save(user);
		logger.info("Created " + user);
		return "redirect:/login";
	}

	@GetMapping("/reset/password")
	public String getPasswordReset(ModelMap modelMap)
	{
		logger.debug("Received getPasswordReset request");
		modelMap.put("resetPasswordForm", new ResetPasswordForm());
		return "pages/account/reset-password";
	}

	@PostMapping("/doPasswordReset")
	public String doPasswordReset(
	        @Valid @ModelAttribute("resetPasswordForm") ResetPasswordForm form,
	        BindingResult bindingResult, HttpServletRequest request)
	{
		logger.debug("Received doPasswordReset request");

		if (StringUtils.isBlank(form.email))
		{
			bindingResult.addError(
			        new ObjectError("email", "Email is required in order to reset your password"));
			return "pages/account/reset-password";
		}

		User user = userService.findByEmail(form.email);

		if (user == null)
		{
			bindingResult.addError(new ObjectError("error", ("Unknown email: " + form.email)));
			return "pages/account/reset-password";
		}

		return "redirect:/login";
	}

	private User createUser(RegistrationForm form)
	{
		User user = userService.create(form.firstName, form.lastName, form.password, form.email);
		return user;
	}

	private void validateLogin(LoginForm form, BindingResult bindingResult)
	{
		if (StringUtils.isBlank(form.email))
		{
			logger.warn("doLogin request contained a blank email.");
			bindingResult.addError(new ObjectError("email", "No email provided with login"));
		}

		if (StringUtils.isBlank(form.password))
		{
			logger.warn("doLogin request contained a blank password.");
			bindingResult.addError(new ObjectError("password", "No password provided with login"));
		}
	}

	private void vaidateRegistration(RegistrationForm form, BindingResult bindingResult)
	{
		if (StringUtils.isBlank(form.firstName))
		{
			logger.warn("doRegistration request contained a blank first name.");
			bindingResult.addError(
			        new ObjectError("firstName", "First name is required for registration"));
		}

		if (StringUtils.isBlank(form.lastName))
		{
			logger.warn("doRegistration request contained a blank last name.");
			bindingResult.addError(
			        new ObjectError("lastName", "Last name is required for registration"));
		}

		if (StringUtils.isBlank(form.email))
		{
			logger.warn("doRegistration request contained a blank email.");
			bindingResult.addError(new ObjectError("email", "Email is required for registration"));
		}

		if (StringUtils.isBlank(form.password) || StringUtils.isBlank(form.confirmedPassword))
		{
			logger.warn("doRegistration request contained a blank password.");
			bindingResult
			        .addError(new ObjectError("password", "Password is required for registration"));
		}

		if (!form.password.equals(form.confirmedPassword))
		{
			logger.warn("doRegistration request passwords did not match.");
			bindingResult.addError(new ObjectError("confirmedPassword", "Passwords do not match."));
		}
	}
}
