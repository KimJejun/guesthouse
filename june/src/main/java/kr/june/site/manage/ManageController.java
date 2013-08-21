package kr.june.site.manage;

import javax.validation.Valid;

import kr.june.site.domain.Reservation;
import kr.june.site.domain.Room;
import kr.june.site.domain.Reservation.Status;
import kr.june.site.helper.model.ValidationResponse;
import kr.june.site.reserve.ReservationService;
import kr.june.site.service.GuestService;
import kr.june.site.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/manage")
@Secured("hasRole('ROLE_ADMIN')")
@Controller
public class ManageController {
	@Autowired private ReservationService reservationService;
	@Autowired private GuestService guestService;
	@Autowired private RoomService roomService;
	
	@RequestMapping("/confirmList")
	public String getConfirmList(Model model, Reservation.Status... status) {
		model.addAttribute("reservationList", reservationService.getReservationListByStaus(status));
		return "/manage/depositManage";
	}
	
	@RequestMapping("/guestList")
	public String getGuestList(Model model) {
		model.addAttribute("guestList", guestService.getGuestList());
		return "/manage/guestList";
	}
	
	@RequestMapping("/roomList")
	public String getRoomList(Model model) {
		model.addAttribute("roomList", reservationService.getRoomList());
		return "/manage/roomList";
	}
	
	
	@RequestMapping(value = "/room/regist")
	public @ResponseBody ValidationResponse registRoom(Model model, @Valid Room room, BindingResult result) {
		ValidationResponse res = new ValidationResponse();
		if (result.hasErrors()) {
			res.setStatus("FAIL");
			res.setErrorMessageList(result.getFieldErrors());
		} else {
			res.setStatus(roomService.addRoom(room));
			
		}
		
		return res;
	}
	
	
	/**
	 * 전체 예약 목록 가져오기
	 * @param model
	 * @return
	 */
	@RequestMapping("/reservationList")
	public String getReservationList(Model model) {
		model.addAttribute("reservationList", reservationService.getReservationList());
		return "/manage/reservationList";
	}
	
	@RequestMapping("/confirm")
	public String confirm(Model model, long nodeId, Status status) {
		reservationService.changeReservationStatus(nodeId, status);
		return "redirect:/manage/confirmList?status=CONFIRM&status=COMPLETE";
	}
}
