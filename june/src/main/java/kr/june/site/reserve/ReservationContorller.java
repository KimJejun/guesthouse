package kr.june.site.reserve;

import java.util.List;

import kr.june.site.GuestDetailService;
import kr.june.site.domain.Reservation;
import kr.june.site.domain.Room;
import kr.june.site.repository.RoomRepository;
import lombok.extern.log4j.Log4j;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import scala.reflect.generic.Trees.New;

@Controller
@RequestMapping("/reserve")
@Log4j
public class ReservationContorller {
	@Autowired private ReservationService reservationService;
	@Autowired private GuestDetailService guestDetailService;

	@RequestMapping("/index")
	public String index(Model model, boolean success) {
		model.addAttribute("roomList", reservationService.getRoomList());
		model.addAttribute("reservation", new  Reservation());
		model.addAttribute("reservations", reservationService.getReservations());
		model.addAttribute("user", guestDetailService.getGuestFromSession());
		model.addAttribute("success", success);
		return "/reservation/index";
	}
	
	@RequestMapping("/roomInfo")
	@ResponseBody
	public List<Room> getRoomInfo(String date) {
		return reservationService.getRoomReservationInfo(date);
	}
	
	@RequestMapping("/reserve")
	public String reserve(Reservation reservation) {
		reservationService.reserve(reservation);
		return "redirect:/reserve/index?success=true";
	}
	
	@RequestMapping("/updatereservationStatus")
	public String updateReservationStatus() {
		
		return "redirect:/reserve/index?success=true";
	}
}
