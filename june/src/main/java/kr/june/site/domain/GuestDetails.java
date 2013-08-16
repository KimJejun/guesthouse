package kr.june.site.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class GuestDetails implements UserDetails {
	private Guest guest;

	public GuestDetails(Guest guest) {
		this.setGuest(guest);
	}
	 @Override
	    public Collection<GrantedAuthority> getAuthorities() {
		 	Guest.Roles[] roles = guest.getRoles();
	        if (roles ==null) return Collections.emptyList();
	        return Arrays.<GrantedAuthority>asList(roles);
	    }

	@Override
	public String getPassword() {
		return guest.getPassword();
	}

	@Override
	public String getUsername() {
		return guest.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

}
