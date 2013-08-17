package kr.june.site.manage;

import kr.june.site.domain.Reservation;
import kr.june.site.domain.Reservation.Status;
import kr.june.site.reserve.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/manage")
@Secured("hasRole('ROLE_ADMIN')")
@Controller
public class ManageController {
	@Autowired private ReservationService reservationService;
	
	@RequestMapping("/confirmList")
	public String getConfirmList(Model model, Reservation.Status... status) {
		model.addAttribute("reservationList", reservationService.getReservationListByStaus(status));
		return "/manage/depositManage";
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
