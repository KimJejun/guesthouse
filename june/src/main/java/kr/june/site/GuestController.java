package kr.june.site;

import kr.june.site.domain.Guest;
import kr.june.site.service.GuestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/guest")
@Controller
public class GuestController {
	@Autowired private GuestService service;
	
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String registForm(Model model) {
		model.addAttribute("guest", new Guest());
		return "/guest/registForm";
	}
	
	@RequestMapping("/loginForm")
	public String loginForm(Model model) {
		model.addAttribute("guest", new Guest());
		return "/guest/loginForm";
	}
	
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regist(Guest guest, BindingResult result) {
		if (service.isExistId(guest.getId())) {
			result.addError(new ObjectError("guest", "이미 존재하는 ID 입니다."));
		}
		
		if (result.hasErrors()) {
	    	return "/guest/registForm";
		}
		
		service.regist(guest);
		
		return "/guest/loginForm";
	}
	
	@RequestMapping("/myReservations")
	@Secured("isAuthenticated()")
	public String myReservations(Model model) {
		model.addAttribute("reservations", service.getMyReservations());
		return "/guest/myReservations";
	}
}
