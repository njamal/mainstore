package com.my.mainstore.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@Entity
@Table(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class Products {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long prodid;
	@Column(name="prodcat")
	private String prodcat;
	private String prodname;
	private String proddesc;
	private int qty;
	private Timestamp createddate;
	private long createdby;
	private Timestamp updateddate;
	private long updatedby;
}