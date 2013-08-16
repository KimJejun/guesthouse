package kr.june.site.service;

import java.util.Collection;

import kr.june.site.GuestDetailService;
import kr.june.site.domain.Guest;
import kr.june.site.domain.GuestDetails;
import kr.june.site.domain.Reservation;
import kr.june.site.repository.GuestRepository;
import kr.june.site.repository.ReservationRepository;
import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class GuestService {
	@Autowired private GuestRepository guestRepository;
	@Autowired private GuestDetailService guestService;
	@Autowired private ReservationRepository reservationRepository;
	@Autowired private GuestDetailService guestDetailService;
	
	public boolean isExistId(String id) {
		Guest guest = guestRepository.findByPropertyValue("id", id);
		if (guest == null) {
			return false;
		}
		return true;
	}
	
	public void regist(Guest guest) {
		guestService.register(guest);
	}
	
	public Collection<Reservation> getMyReservations() {
		Guest user = guestDetailService.getGuestFromSession();
		return reservationRepository.getUserReservations(user.getId());
	}
}
