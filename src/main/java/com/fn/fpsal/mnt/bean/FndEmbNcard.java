package com.fn.fpsal.mnt.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

public class FndEmbNcard {
	@NotNull
	@Column(name = "CARD_NBR")
	private String cardNbr;
	
	@Column(name = "CUST_UID")
	private String custUid;
	
	@Column(name = "PRD_NAME")
	private String prdName;
	
	@NotNull
	@Column(name = "EXP_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.sql.Date expDate;
	
	@Column(name = "BBK_NBR")
	private String bbkNbr;
	
	@Column(name = "CARD_GRD")
	private String cardGrd;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;
	
	@Column(name = "CRT_DATE")
	private Date crtDate = new Date();

	public String getCardNbr() {
		return cardNbr;
	}

	public void setCardNbr(String cardNbr) {
		this.cardNbr = cardNbr;
	}

	public String getCustUid() {
		return custUid;
	}

	public void setCustUid(String custUid) {
		this.custUid = custUid;
	}

	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	public java.sql.Date getExpDate() {
		return expDate;
	}

	public void setExpDate(java.sql.Date expDate) {
		this.expDate = expDate;
	}

	public String getBbkNbr() {
		return bbkNbr;
	}

	public void setBbkNbr(String bbkNbr) {
		this.bbkNbr = bbkNbr;
	}

	public String getCardGrd() {
		return cardGrd;
	}

	public void setCardGrd(String cardGrd) {
		this.cardGrd = cardGrd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
}
