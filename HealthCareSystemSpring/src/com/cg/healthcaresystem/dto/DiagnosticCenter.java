package com.cg.healthcaresystem.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "hcs_center")
public class DiagnosticCenter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "center_id")
	private BigInteger centerId;

	@NotBlank(message="centerName cannot be empty")
	@Size(max = 50, message = "Center name cannot be this big. Max 50 characters allowed.")
	@Column(name = "center_name")
	private String centerName;

	@NotNull(message = "Phone number cannot be empty")
	@Min(value = 1000000000, message = "Should not be less than 10 digits")
	@Digits(integer = 10, message = "Phone number cannot be more than 10 characters", fraction = 0)
	@Column(name = "center_contact_no")
	private BigInteger centerContactNo;

	@NotBlank(message="Center address cannot be blank")
	@Column(name = "center_address")
	private String centerAddress;
	
	@OneToMany
	@JoinColumn(name="center_id_fk")
	private List<Test> listOfTests = new ArrayList<Test>();
	
	@OneToMany(mappedBy = "center")
	List<Appointment> userAppointmentList = new ArrayList<Appointment>();
	
	@Column(name="is_deleted")
	private boolean isDeleted;

	public DiagnosticCenter() {
	}

	public DiagnosticCenter(String centerName, String centerAddress, BigInteger centerContactNo) {
		super();
		this.centerName = centerName;
		this.centerContactNo = centerContactNo;
		this.centerAddress = centerAddress;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public BigInteger getCenterId() {
		return centerId;
	}

	public void setCenterId(BigInteger centerId) {
		this.centerId = centerId;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public BigInteger getCenterContactNo() {
		return centerContactNo;
	}

	public void setCenterContactNo(BigInteger centerContactNo) {
		this.centerContactNo = centerContactNo;
	}

	public String getCenterAddress() {
		return centerAddress;
	}

	public void setCenterAddress(String centerAddress) {
		this.centerAddress = centerAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((centerAddress == null) ? 0 : centerAddress.hashCode());
		result = prime * result + ((centerContactNo == null) ? 0 : centerContactNo.hashCode());
		result = prime * result + ((centerId == null) ? 0 : centerId.hashCode());
		result = prime * result + ((centerName == null) ? 0 : centerName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiagnosticCenter other = (DiagnosticCenter) obj;
		if (centerAddress == null) {
			if (other.centerAddress != null)
				return false;
		} else if (!centerAddress.equals(other.centerAddress))
			return false;
		if (centerContactNo == null) {
			if (other.centerContactNo != null)
				return false;
		} else if (!centerContactNo.equals(other.centerContactNo))
			return false;
		if (centerId == null) {
			if (other.centerId != null)
				return false;
		} else if (!centerId.equals(other.centerId))
			return false;
		if (centerName == null) {
			if (other.centerName != null)
				return false;
		} else if (!centerName.equals(other.centerName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DiagnosticCenter [centerId=" + centerId + ", centerName=" + centerName + ", centerContactNo="
				+ centerContactNo + ", centerAddress=" + centerAddress + "]";
	}

	public List<Test> getListOfTests() {
		return listOfTests;
	}

	public void setListOfTests(List<Test> listOfTests) {
		this.listOfTests = listOfTests;
	}

}