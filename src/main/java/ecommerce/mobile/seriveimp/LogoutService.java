package ecommerce.mobile.seriveimp;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutService implements LogoutHandler {
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Cookie cookie = new Cookie("jwt", "");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		SecurityContextHolder.clearContext();
	}

}
