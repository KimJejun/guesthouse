package kr.june.site.domain;

import java.util.Set;

import lombok.Data;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;
import org.springframework.security.core.GrantedAuthority;

@Data
@NodeEntity
public class Guest {
	@GraphId private Long nodeId;
	@Indexed(indexType = IndexType.FULLTEXT, indexName = "guestName")
	private String name;
	@Indexed(unique = true)
	private String id;
	private String email;
	private String contact;
	private String password;
	private Roles[] roles;
	@RelatedToVia
	@Fetch
	Set<Reservation> reserveList;
	
	public Guest() {
		super();
	}
	
	public Reservation reserve(Room room, int guestCount, String reservedAt) {
		Reservation reservation = new Reservation(this, room, guestCount, reservedAt);
		reserveList.add(reservation);
		return reservation;
	}



	public Guest(String id, String name, String password, String email, String contact, Roles... roles) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.contact = contact;
		this.roles = roles;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Guest guest = (Guest) o;
		if (nodeId == null) {
			return super.equals(o);
		}
		return nodeId.equals(guest.nodeId);

	}

	@Override
	public int hashCode() {
		return nodeId != null ? nodeId.hashCode() : super.hashCode();
	}
	
	public enum Roles implements GrantedAuthority {
		ROLE_USER, ROLE_ADMIN;

		@Override
		public String getAuthority() {
			return name();
		}
	}
}
