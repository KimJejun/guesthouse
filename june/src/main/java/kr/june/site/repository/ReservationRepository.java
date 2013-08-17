package kr.june.site.repository;

import java.util.Collection;
import java.util.Map;

import kr.june.site.domain.Reservation;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface ReservationRepository extends GraphRepository<Reservation> {
	@Query("START n = node(*) MATCH (n)-[r:RESERVED]->() RETURN r ORDER BY r.reservedAt DESC")
	public Collection<Reservation> getReservation();
	
	@Query("START n = node(*) MATCH (n)-[r:RESERVED]->(c) WHERE r.reservedAt = {0} RETURN c.name as roomName, sum(r.guestCount) as guestCount")
	public EndResult<Map<String,Object>> getReservation(String date);
	
	@Query("START n = node(*) MATCH (n)-[r:RESERVED]->() where n.id? = {0} RETURN r ORDER BY r.reservedAt DESC")
	public Collection<Reservation> getUserReservations(String userId); 
	
	@Query("START n = node(*) MATCH (n)-[r:RESERVED]->() WHERE r.status IN {0} RETURN r")
	public Collection<Reservation> getReservationListByStatus(Reservation.Status... status); 
}
