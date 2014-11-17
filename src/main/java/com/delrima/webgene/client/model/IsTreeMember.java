package com.delrima.webgene.client.model;

import java.io.Serializable;
import java.util.Date;

public interface IsTreeMember extends Serializable {

	Long getId();

	Long getMotherid();

	Long getFatherid();

	Long getSpouseid();

	Date getDob();

	String getGender();

	String getFirstname();

	String getLastname();

}
