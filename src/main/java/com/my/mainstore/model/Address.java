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
@Table(name = "address")
@EntityListeners(AuditingEntityListener.class)
public class Address {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long addressid;
	private String address1;
	private String address2;
	private int userid;
	private int poskod;
	private Timestamp createddate;
	private long createdby;
	private Timestamp updateddate;
	private long updatedby;
}