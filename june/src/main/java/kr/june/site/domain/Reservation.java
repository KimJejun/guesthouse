package kr.june.site.domain;


import java.util.Calendar;

import lombok.Data;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import org.springframework.security.core.GrantedAuthority;

@Data
@RelationshipEntity(type = "RESERVED")
public class Reservation {
	@GraphId private Long nodeId;
	@StartNode private Guest guest;
	@Fetch
	@EndNode private Room room;
	private int guestCount;
	private String reservedAt;
	private String registedAt;
	private Status status;
	
	public Reservation() {
	}
	
	public Reservation(Guest guest, Room room, int guestCount, String reservedAt) {
		this.guest = guest;
		this.room = room;
		this.guestCount = guestCount;
		this.reservedAt = reservedAt;
		this.registedAt = DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd:HH-mm");
		this.status = Status.RESERVATION;
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Reservation reservation = (Reservation) o;
		if (nodeId == null) {
			return super.equals(o);
		}
		return nodeId.equals(reservation.nodeId);

	}

	@Override
	public int hashCode() {
		return nodeId != null ? nodeId.hashCode() : super.hashCode();
	}
	
	public enum Status {
		RESERVATION("예약"), CANCLE("취소"), CONFIRM("입금확인요청"), PROCESS("입금확인중"), COMPLETE("입금완료");
		
		private String status;

		private Status(String status) {
			this.status = status;
		}
		public String getStatus() {
			return this.status;
		}
	}
}
