package kr.june.site.domain;

import java.util.Date;
import java.util.Set;

import lombok.Data;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;


@NodeEntity
@Data
public class GuestBook {
	@GraphId private Long nodeId;
	private String author;
	private String password;
	private String content;
	private Date registeAt;
	@RelatedTo(type = "AUTHOR", direction = Direction.INCOMING)
	private Guest guest;
}
