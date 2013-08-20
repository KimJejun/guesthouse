package kr.june.site;

import java.util.Date;

import kr.june.site.domain.Guest;
import kr.june.site.domain.GuestBook;
import kr.june.site.repository.GuestRepository;
import kr.june.site.repository.ReservationRepository;
import kr.june.site.repository.RoomRepository;
import lombok.extern.log4j.Log4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/root-context.xml", "classpath:/security-app-context.xml"})
@Log4j
public class GuestBookTest {
	@Autowired private Neo4jTemplate template;
	@Autowired private RoomRepository repository;
	@Autowired private GuestRepository guestRepository;
	@Autowired private ReservationRepository reservationRepository;
	@Autowired private GuestDetailService guestDetailService;
	
	@Test
	@Transactional
	public void test() {
		Guest guest = guestRepository.findByPropertyValue("name", "김리온");
		GuestBook guestBook = new GuestBook();
		guestBook.setAuthor("김리온");
		guestBook.setPassword(guest.getPassword());
		guestBook.setContent("Guestbook 등록!!");
		guestBook.setRegisteAt(new Date());
		guestBook.setGuest(guest);
		
		template.save(guestBook);
	}

}
