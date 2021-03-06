package kr.june.site;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import kr.june.site.domain.Guest;
import kr.june.site.domain.Reservation;
import kr.june.site.domain.ReservationInfo;
import kr.june.site.domain.Room;
import kr.june.site.domain.Reservation.Status;
import kr.june.site.repository.GuestRepository;
import kr.june.site.repository.ReservationRepository;
import kr.june.site.repository.RoomRepository;
import lombok.extern.log4j.Log4j;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.kernel.AbstractGraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/root-context.xml", "classpath:/security-app-context.xml"})
@Log4j
public class RepositoryTest {
	@Autowired
	private Neo4jTemplate template;
	@Autowired private RoomRepository roomRepository;
	@Autowired private GuestRepository guestRepository;
	@Autowired private ReservationRepository reservationRepository;

	@Test
	public void test() {
		List<Room> roomList = IteratorUtils.toList(roomRepository.findAll().iterator());
		EndResult<Map<String,Object>> reservedInfoMap =reservationRepository.getReservation("2013-08-13");
		
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
		
		Object jsonResult = new Gson().toJson(roomList);
		log.debug(jsonResult);
	}
	
	@Test
	public void getReservedList() {
		Collection<Reservation> reservations = reservationRepository.getUserReservations("dosajun");
		for (Reservation reservation : reservations) {
			log.info(reservation);
		}
	}
	
	@Test
	public void getReservedListByStatus() {
		Collection<Reservation> reservations = reservationRepository.getReservationListByStatus(Status.RESERVATION, Status.CONFIRM);
		for (Reservation reservation : reservations) {
			log.info(reservation);
		}
	}
	
	@Test
	public void updateReservationStatus() {
		Reservation reservation = reservationRepository.findOne(25L);
		reservation.setStatus(Status.RESERVATION);
		reservationRepository.save(reservation);
		
	}
	
	@Test
	public void dateTest() throws ParseException {
		String startDate = "2013-08"+ "-01";
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
		
		
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		log.info(cal.getActualMinimum(Calendar.DATE));
		
	}
	
}
