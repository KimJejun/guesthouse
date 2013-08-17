package kr.june.site;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import kr.june.site.domain.Guest;
import kr.june.site.domain.Reservation;
import kr.june.site.domain.Reservation.Status;
import kr.june.site.domain.ReservationInfo;
import kr.june.site.domain.Room;
import kr.june.site.repository.GuestRepository;
import kr.june.site.repository.ReservationRepository;
import kr.june.site.repository.RoomRepository;
import lombok.extern.log4j.Log4j;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/root-context.xml", "classpath:/security-app-context.xml"})
@Log4j
public class Neo4jTest {
	@Autowired private Neo4jTemplate template;
	@Autowired private RoomRepository repository;
	@Autowired private GuestRepository guestRepository;
	@Autowired private ReservationRepository reservationRepository;
	@Autowired private GuestDetailService guestDetailService;

	@Test
	@Transactional
	public void make() {
		makeRoom();
		makeGuest();
		reserve();
	}
	
	@Test
	public void delete() {
		Room room8 = repository.findByPropertyValue("name", "8번방");
		Guest guest = guestRepository.findByPropertyValue("name", "김제준");
		
		template.delete(room8);
		template.delete(guest);
	}
	
	@Test
	public void makeRoom() {
		Room room8 = template.save(new Room("8인실", 10, "RED"));
		template.save(new Room("6인실-1", 8, "GREEN"));
		template.save(new Room("6인실-2", 8, "BLUE"));
		Room retrievedRoom8 = template.findOne(room8.getNodeId(), Room.class);
		    
		assertEquals("retrieved movie matches persisted one", room8, retrievedRoom8);
		assertEquals("retrieved movie title matches", "8인실", retrievedRoom8.getName());
	}
	
	@Test
	public void deleteRoom() {
		Room room8 = repository.findByPropertyValue("name", "8인실");
		
		repository.delete(room8);
	}
	
	@Test
	public void deleteRoom2() {
		//repository.delete(4L);
		repository.delete(6L);
	}
	
	@Test
	public void deleteUser() {
		Guest guest = template.findOne(8, Guest.class);
		template.delete(guest);
	}
	
	@Test
	public void makeGuest() {
		guestDetailService.register(new Guest("admin", "운영자", "admin", "admin@gmail.com", "010-4199-3120", Guest.Roles.ROLE_ADMIN, Guest.Roles.ROLE_USER));
		guestDetailService.register(new Guest("dosajun", "김제준", "dosajun", "dosajun@gmail.com", "010-4199-3120", Guest.Roles.ROLE_ADMIN, Guest.Roles.ROLE_USER));
		guestDetailService.register(new Guest("lion", "김리온", "lion", "lion@gmail.com", "010-4100-3120", Guest.Roles.ROLE_USER));
		guestDetailService.register(new Guest("dosajun2", "김제준2", "dosajun2", "dosajun2@gmail.com", "010-4199-31202", Guest.Roles.ROLE_USER));
	}
	
	@Test
	public void test2() {
		Room room8 = repository.findByPropertyValue("name", "8인실");
		
		assertEquals("retrieved movie matches persisted one", room8.getName(), "8인실");
		
		//repository.delete((long) 1);
	}
	
	@Test
	public void findGeust() {
		Guest guest = guestRepository.findByPropertyValue("name", "김제준");
		
		log.info(guest);
	}
	
	
	@Test
	@Transactional
	@Rollback(false)
	public void reserve() {
		Room room6_1 = repository.findByPropertyValue("name", "6인실-1");
		Room room8 = repository.findByPropertyValue("name", "8인실");
		Guest guest = guestRepository.findByPropertyValue("name", "김제준");
		Guest guest2 = guestRepository.findByPropertyValue("name", "김리온");
		
		
		guest.reserve(new Reservation(guest, room8, 2, DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd"), "김제준"));
		guest.reserve(new Reservation(guest, room6_1, 1, DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd"), "김제준"));
		template.save(guest);
		
		
		
		guest2.reserve(new Reservation(guest2, room6_1, 3, DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd"), "김리온"));
		guest2.reserve(new Reservation(guest2, room6_1, 2, "2013-08-12", "김리온"));
		template.save(guest2);
		

	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void reserve2() {
		Room room8 = repository.findByPropertyValue("name", "8번방-1");
		Room room10 = repository.findByPropertyValue("name", "10번방");
		Guest guest = guestRepository.findByPropertyValue("name", "김제준2");
		Guest guest2 = guestRepository.findByPropertyValue("name", "김리온");
		
		//guest.reserve(room10, 2, DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd"));
		//Reservation reservation = guest.reserve(room8, 1, "20130812");
		//guestRepository.save(guest);
		//template.save(reservation);
		
	/*	guest2.reserve(room8, 3, DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd"));
		guest2.reserve(room8, 2, "20130812");
		template.save(guest2);
		*/
		/*
		Reservation reservation = guest.reserve(room8, 3, "2013-08-20");
		template.save(reservation);
		*/
		//template.createRelationshipBetween(guest, room8, new Reservation(guest, room8, 5, "20130812").getClass(), "RESERVED", true);
	}
	
	@Test
	public void deleteReserve() {
		Room room8 = repository.findByPropertyValue("name", "8번방");
		Guest guest = guestRepository.findByPropertyValue("name", "김제준");
		
		
		template.deleteRelationshipBetween(guest, room8, "RESERVE");

	}
	
	@Test
	public void test() {
/*		List<Reservation> lists = IteratorUtils.toList(reservationRepository.getReservation().iterator());
		
		Gson gson = new Gson();
		Object result = gson.toJson(lists);
		log.debug(result);
*/		
		EndResult<Map<String,Object>> result = template.query("START n=node(*) MATCH (n)-[r:RESERVED]->(c) RETURN r.reservedAt, c.name, c.capacity, sum(r.guestCount) order by r.reservedAt", null);
		List<ReservationInfo> reserveList = new ArrayList<>();
		
		for (Map<String, Object> map : result) {
			log.debug(map);
			ReservationInfo reservationInfo = new ReservationInfo();
			reservationInfo.setTitle((String) map.get("c.name"));
			reservationInfo.setStart("eval(new Date(y, m, 1))");
			reservationInfo.setColor("red");
			reserveList.add(reservationInfo);
		}
		
		Gson gson = new Gson();
		Object jsonResult = gson.toJson(reserveList);
		log.debug(jsonResult);
	}
	
	@Test
	public void dbclean() {
		new DBCleanner(template.getGraphDatabaseService()).cleanDb();
	}
	
	@Test
	public void simpleTest() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, +8);
		log.debug(DateFormatUtils.format(calendar, "yyyyMMdd"));
		log.debug(DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd HHmm"));
	}
	
}
