package kr.june.site.service;

import kr.june.site.domain.Room;
import kr.june.site.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
	@Autowired private RoomRepository roomRepository;
	
	
	public String addRoom(Room room) {
		Room existRoom = roomRepository.findByPropertyValue("name", room.getName());
		if (existRoom != null) {
			return "EXIST_ROOM";
		}
		roomRepository.save(room);
		
		return "SUCCESS";
	}
}
