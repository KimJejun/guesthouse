package kr.june.site.reserve;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.june.site.GuestDetailService;
import kr.june.site.domain.Guest;
import kr.june.site.domain.Reservation;
import kr.june.site.domain.ReservationInfo;
import kr.june.site.domain.Room;
import kr.june.site.domain.Reservation.Status;
import kr.june.site.repository.ReservationRepository;
import kr.june.site.repository.RoomRepository;
import lombok.extern.log4j.Log4j;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import scala.util.parsing.json.JSONObject;

@Service
@Log4j
public class ReservationService {
	@Autowired private RoomRepository roomRepository;
	@Autowired private Neo4jTemplate template;
	@Autowired private ReservationRepository reservationRepository;
	@Autowired private GuestDetailService guestDetailService;
	
	@SuppressWarnings("unchecked")
	public List<Room> getRoomList() {
		List<Room> list = IteratorUtils.toList(roomRepository.findAll().iterator());
		return list;
	}
	
	public Collection<Reservation> getReservationList() {
		return reservationRepository.getReservation();
	}
	
	public String getReservations(String startDate) {
		EndResult<Map<String,Object>> result = template.query("START n=node(*) MATCH (n)-[r:RESERVED]->(c) "
				+ "WHERE r.reservedAt >= {startDate} AND r.reservedAt <= {endDate} "
				+ "RETURN r.reservedAt, c.name, c.color as color, c.capacity as capacity, sum(r.guestCount) as guestCount order by r.reservedAt", getDateRange(startDate));
		List<ReservationInfo> reserveList = new ArrayList<>();
		for (Map<String, Object> map : result) {
			log.debug(map);
			ReservationInfo reservationInfo = new ReservationInfo();
			String count = "(" + map.get("guestCount") + "/" + map.get("capacity") + ")";
			reservationInfo.setTitle((String) map.get("c.name") + " " + count);
			reservationInfo.setStart((String) map.get("r.reservedAt"));
			reservationInfo.setColor((String) map.get("color"));
			reserveList.add(reservationInfo);
		}
		
		Gson gson = new Gson();
		String jsonResult = gson.toJson(reserveList);
		log.debug(jsonResult);
		
		return jsonResult;
	}
	
	public List<Room> getRoomReservationInfo(String date) {
		List<Room> roomList = IteratorUtils.toList(roomRepository.findAll().iterator());
		EndResult<Map<String,Object>> reservedInfoMap =reservationRepository.getReservation(date);
		
		for (Room room : roomList) {
			String defaultRoomName = room.getName();
			int defalutCapacity = room.getCapacity();
			for (Map<String, Object> map : reservedInfoMap) {
				String bookedRoomName = MapUtils.getString(map, "roomName");
				if (defaultRoomName.equals(bookedRoomName)) {
					int reservedCount = MapUtils.getIntValue(map, "guestCount");
					room.setCapacity(defalutCapacity - reservedCount);
					break;
				}
			}
		}
		return roomList;
	}
	
	public Reservation reserve(Reservation reservation) {
		Room room = roomRepository.findOne(reservation.getRoom().getNodeId());
		Guest guest = guestDetailService.getGuestFromSession();
		
		reservation.setRoom(room);
		
		reservation = guest.reserve(reservation);
		template.save(reservation);
		return reservation;
	}
	
	/**
	 * 예약 상태에 따라 예약 목록 가져오기
	 * @param status
	 * @return
	 */
	public Collection<Reservation> getReservationListByStaus(Reservation.Status... status) {
		return reservationRepository.getReservationListByStatus(status);
	}
	
	/**
	 * 예약 상태를 변경
	 * @param reservationId
	 * @param status
	 */
	public void changeReservationStatus(long reservationId, Reservation.Status status) {
		Reservation reservation = reservationRepository.findOne(reservationId);
		reservation.setStatus(status);
		reservationRepository.save(reservation);
	}
	
	private Map<String, Object> getDateRange(String startDate) {
		Calendar cal = Calendar.getInstance();
		if (startDate == null) {
			cal.setTime(new Date());
			startDate = new SimpleDateFormat("yyyy-MM").format(new Date());
		}
		Map<String, Object> params = new HashMap<>();
		
		String newStartDate = startDate + "-01";
		try {
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(newStartDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String endDate = startDate + "-" + String.valueOf(cal.getMaximum(Calendar.DATE));
		
		params.put("startDate", newStartDate);
		params.put("endDate", endDate);
		
		
		return params;
	}
}
