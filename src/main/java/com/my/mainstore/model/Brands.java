package com.my.mainstore.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "brands")
@EntityListeners(AuditingEntityListener.class)
public class Brands {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long brandid;
	private String brandname;
	private String branddesc;
	private String isActive;
	private Timestamp createddate;
	private long createdby;
	private Timestamp updateddate;
	private long updatedby;
}