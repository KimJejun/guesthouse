package kr.june.site.repository;

import kr.june.site.domain.Room;

import org.springframework.data.neo4j.repository.GraphRepository;

public interface RoomRepository extends GraphRepository<Room> {

}
