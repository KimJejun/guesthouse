package kr.june.site.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.security.sasl.AuthenticationException;

import kr.june.site.GuestDetailService;
import kr.june.site.domain.Guest;
import kr.june.site.domain.GuestDetails;
import kr.june.site.domain.Reservation;
import kr.june.site.domain.Reservation.Status;
import kr.june.site.repository.GuestRepository;
import kr.june.site.repository.ReservationRepository;
import kr.june.site.reserve.ReservationService;
import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
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
	@Autowired private ReservationService reservationService;
	
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
	
	public List<Guest> getGuestList() {
		return guestRepository.findAll().as(List.class);
	}
	
	public Collection<Reservation> getMyReservations() {
		Guest user = guestDetailService.getGuestFromSession();
		return reservationRepository.getUserReservations(user.getId());
	}

	public void updateReservationStatus(long nodeId, Status status) throws AuthenticationException {
		Guest user = guestDetailService.getGuestFromSession();
		Reservation reservation = reservationRepository.findOne(nodeId);
		
		if (reservation.getGuest().getNodeId() != user.getNodeId()) {
			throw new AuthenticationException("본인 예약만 상태를 변경할 수 있습니다.");
		}
		
		reservationService.changeReservationStatus(nodeId, status);
	}
}
