package kr.june.site.repository;

import kr.june.site.domain.Guest;

import org.springframework.data.neo4j.repository.GraphRepository;

public interface GuestRepository extends GraphRepository<Guest> {
	
}
