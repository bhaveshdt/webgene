package com.delrima.webgene.client.dto;

// Generated Feb 23, 2008 11:17:51 PM by Hibernate Tools 3.2.0.b11

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Configurable;

import com.delrima.webgene.client.model.IsTreeMember;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
@Configurable
@Entity
@Table(name = "member")
public class Member implements Serializable, IsTreeMember, IsSerializable {

	@Column(name = "firstname", length = 50)
	@NotNull
	private String firstname;

	@Column(name = "middlename", length = 50)
	private String middlename;

	@Column(name = "lastname", length = 50)
	@NotNull
	private String lastname;

	@Column(name = "maidenname", length = 50)
	private String maidenname;

	@Column(name = "dob")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dob;

	@Column(name = "dod")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dod;

	@Column(name = "gender", length = 2, columnDefinition = "enum('M','F','U')")
	@NotNull
	private String gender;

	@Column(name = "occupation", length = 100)
	private String occupation;

	@Column(name = "membersince")
	@Temporal(TemporalType.TIMESTAMP)
	private Date membersince;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Basic
	@Column(name = "motherid")
	private Long motherid;

	@Basic
	@Column(name = "fatherid")
	private Long fatherid;

	@Basic
	@Column(name = "spouseid")
	private Long spouseid;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Member other = (Member) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public Date getDob() {
		return dob;
	}

	public Date getDod() {
		return dod;
	}

	@Override
	public Long getFatherid() {
		return fatherid;
	}

	@Override
	public String getFirstname() {
		return firstname;
	}

	@Override
	public String getGender() {
		return gender;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getLastname() {
		return lastname;
	}

	public String getMaidenname() {
		return maidenname;
	}

	public Date getMembersince() {
		return membersince;
	}

	public String getMiddlename() {
		return middlename;
	}

	@Override
	public Long getMotherid() {
		return motherid;
	}

	public String getOccupation() {
		return occupation;
	}

	@Override
	public Long getSpouseid() {
		return spouseid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setDod(Date dod) {
		this.dod = dod;
	}

	public void setFatherid(Long fatherid) {
		this.fatherid = fatherid;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setMaidenname(String maidenname) {
		this.maidenname = maidenname;
	}

	public void setMembersince(Date membersince) {
		this.membersince = membersince;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public void setMotherid(Long motherid) {
		this.motherid = motherid;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public void setSpouseid(Long spouseid) {
		this.spouseid = spouseid;
	}

	@Override
	public String toString() {
		return "Member [firstname=" + firstname + ", lastname=" + lastname + ", dob=" + dob + ", id=" + id + "]";
	}

}
