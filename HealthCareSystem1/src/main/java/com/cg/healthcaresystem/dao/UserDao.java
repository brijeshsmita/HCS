package com.cg.healthcaresystem.dao;

import java.math.BigInteger;
import java.util.List;

import com.cg.healthcaresystem.dto.Appointment;
import com.cg.healthcaresystem.dto.DiagnosticCenter;
import com.cg.healthcaresystem.dto.Test;
import com.cg.healthcaresystem.dto.User;
import com.cg.healthcaresystem.exception.UserDefinedException;

public interface UserDao {
	public DiagnosticCenter addCenter(DiagnosticCenter center);

	public boolean removeCenter(BigInteger centerid);

	public Test addTest(BigInteger centerId, Test test);

	public boolean removeTest(BigInteger removeCenterId, BigInteger removeTestId) throws UserDefinedException;
	
	public Appointment addAppointment(Appointment appointment);

	public BigInteger register(User user);

	public List<DiagnosticCenter> getCenterList();

	public List<User> getUserList();

	public List<Test> getListOfTests(BigInteger centerId);

	public List<Appointment> getAppointmentList(BigInteger userId);
	
	public boolean approveAppointment(BigInteger appointmentId);
	
	public DiagnosticCenter findCenter(BigInteger centerId);
	
	public User findUser(BigInteger userId);
	
	public Test findTest(BigInteger testId);

	public List<Appointment> getCenterAppointmentList(BigInteger centerId);

	//public boolean setCenterList(List<DiagnosticCenter> centerList);
}
