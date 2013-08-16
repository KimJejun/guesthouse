package kr.june.site;

import kr.june.site.domain.Guest;
import kr.june.site.domain.GuestDetails;
import kr.june.site.repository.GuestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("guestDetailService")
public class GuestDetailServiceImpl implements GuestDetailService {
	@Autowired private Neo4jOperations template;
	@Autowired private GuestRepository guestRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private SaltSource saltSource;
	
	
	@Override
	public GuestDetails loadUserByUsername(String id)
			throws UsernameNotFoundException {
		Guest guest = template.lookup(Guest.class, "id", id).to(Guest.class).single();
		return new GuestDetails(guest);
	}

	@Override
	public Guest getGuestFromSession() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof GuestDetails) {
			GuestDetails userDetails = (GuestDetails) principal;
			return userDetails.getGuest();
		}
		return null;
	}

	@Override
	public Guest register(Guest guest) {
		GuestDetails guestDetails = new GuestDetails(guest);
		guest.setPassword(passwordEncoder.encodePassword(guest.getPassword(), saltSource.getSalt(guestDetails)));
		
		guestRepository.save(guest);
		return guest;
	}

	@Override
	public void addFriend(String login, Guest userFromSession) {
		// TODO Auto-generated method stub

	}

}
