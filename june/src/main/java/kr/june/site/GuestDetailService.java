package kr.june.site;

import kr.june.site.domain.Guest;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface GuestDetailService extends UserDetailsService {
	public Guest getGuestFromSession();
	public Guest register(Guest guest);
    public void addFriend(String login, Guest userFromSession);
}
