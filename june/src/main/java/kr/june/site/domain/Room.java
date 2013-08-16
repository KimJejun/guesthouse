package kr.june.site.domain;

import java.util.Set;

import lombok.Data;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

@Data
@NodeEntity
public class Room {
	@GraphId
	Long nodeId;
	@Indexed(indexType = IndexType.FULLTEXT, indexName = "roomName", unique = true)
	private String name;
	private int capacity;
	private String color;
	@RelatedTo(elementClass = Guest.class, type = "BOOKED", direction = Direction.BOTH)
	Set<Guest> reserveList;

	public Room() {
	}

	public Room(String name, int capacity, String color) {
		this.name = name;
		this.capacity = capacity;
		this.color = color;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Room room = (Room) o;
		if (nodeId == null) {
			return super.equals(o);
		}
		return nodeId.equals(room.nodeId);

	}

	@Override
	public int hashCode() {
		return nodeId != null ? nodeId.hashCode() : super.hashCode();
	}

}
