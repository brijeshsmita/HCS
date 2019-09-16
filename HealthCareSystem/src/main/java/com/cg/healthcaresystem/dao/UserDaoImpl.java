package com.cg.healthcaresystem.dao;

import com.cg.healthcaresystem.dto.DiagnosticCenter;
import com.cg.healthcaresystem.dto.Test;
import com.cg.healthcaresystem.dto.User;
import com.cg.healthcaresystem.exception.UserDefinedException;
import com.cg.healthcaresystem.exception.UserErrorMessage;
import com.cg.healthcaresystem.util.DbUtil;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class UserDaoImpl implements UserDao {

	  
	List<User> userList = new ArrayList<User>();
	DiagnosticCenter dignosticcenter=null;
	Test test=null;
	private static Connection connection;
	private PreparedStatement ps;
	private ResultSet rs;
	private static Logger myLogger;
	static{
	  	
	  	  Properties props = System.getProperties();
	  	  String userDir= props.getProperty("user.dir")+"\\src\\main\\resources\\"
	  	  		+ "";
	  	  System.out.println("Current working directory is " +userDir);
	  	  PropertyConfigurator.configure(userDir+"log4j.properties");
			myLogger=Logger.getLogger("DBUtil.class");
	
		try {
			connection=DbUtil.getConnection();
			myLogger.info("connection obtained.....");
		} catch (UserDefinedException e) {
			// TODO Auto-generated catch block
			myLogger.error("connection not established at EmployeeDao: "+e);
		}
	}
	

	public DiagnosticCenter addCenter(DiagnosticCenter center) {

		DiagnosticCenter newCenter=null;

		String sql="insert into Center(center_name,center_address,center_contact_no,isEmpty) values(?,?,?,?)";
		try
		{
			ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setLong(3,center.getCenterContactNo().longValue());
			ps.setString(1,center.getCenterName());
			ps.setString(2,center.getCenterAddress());
			ps.setInt(4, 1);
			
			int noOfRecords=ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			BigInteger centerid=null;
			if(rs!=null && rs.next())
			{
				centerid=BigInteger.valueOf(rs.getLong(1));
			}
			center.setCenterId(centerid);
			
			if(noOfRecords<=0)
			{
				throw new UserDefinedException(UserErrorMessage.userErrorNoCenterAdded);
			}

			newCenter=center;

			
		}
		catch(Exception exception)
		{
			
			//1. Insert appropriate error messages
			myLogger.error("Error at addCenter Dao method: "+exception.getMessage());
		}
		finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					myLogger.error("Error at addCenter Dao method:"+e.getMessage());
				}
			}
		}
		
		
		
		return newCenter;
	}

	public boolean removeCenter(BigInteger centerId) {
		String sql="update Test SET isEmpty=0 where center_id=?";
		String sql1="update Center set isEmpty=0 where center_id=?";
		System.out.println("888888888");
		try
		{
			System.out.println("1");
			ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1,centerId.longValue());
			int a=ps.executeUpdate();
			ps=connection.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1,centerId.longValue());
			System.out.println("2");
			int noOfRecords=ps.executeUpdate();
			if(noOfRecords<=0)
			{
				throw new UserDefinedException(UserErrorMessage.userErrorNoCenterDeleted);
			}
			
			
		}
		catch(Exception exception)
		{
			myLogger.error("Error at removeCenter Dao method: "+exception.getMessage());
		}
		finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					myLogger.error("Error at removeCenter Dao method:"+e.getMessage());
				}
			}
		}
		return true;
		
	}

	public Test addTest(BigInteger centerId, Test test) {
		Test newtest=null;
		String sql="insert into Test(test_name,center_id,isEmpty) values(?,?,?)";
		try
		{
			ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,test.getTestName());
			ps.setLong(2, centerId.longValue());
			ps.setInt(3,1);
			int noOfRecords=ps.executeUpdate();
			if(noOfRecords<=0)
			{
				throw new UserDefinedException(UserErrorMessage.userErrorAddTestFailed);
			}
			newtest=test;
		}
		catch(Exception exception)
		{
			myLogger.error("Error at addTest Dao method: "+exception.getMessage());
		}
		finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					myLogger.error("Error at addTest Dao method:"+e.getMessage());
				}
			}
		}
	return newtest;
	}

//	public boolean removeTest(BigInteger removeCenterId, String removeTestId) {
//		List<Test> tempTestList = new ArrayList<Test>();
//		for (int i = 0; i < centerList.size(); i++) {
//			if (centerList.get(i).getCenterId().equals(removeCenterId)) {
//				tempTestList = centerList.get(i).getListOfTests();
//				for (int j = 0; j < tempTestList.size(); j++) {
//					if (tempTestList.get(j).getTestId().equals(removeTestId)) {
//						tempTestList.remove(j);
//					}
//				}
//				centerList.get(i).setListOfTests(tempTestList);
//				return true;
//			}
//		}
//		return false;
//
//		// int flag=0;
////		Iterator itr=centerList.iterator();
////		while(itr.hasNext())
////		{
////			DiagnosticCenter obj=(DiagnosticCenter) itr.next();
////			if(obj.getCenterName().equals(centername))
////			{
////				List<Test> testList=obj.getListOfTests();
////				Iterator testlistitr=testList.iterator();
////				while(testlistitr.hasNext())
////				{
////					Test testobj=(Test)itr.next();
////					if(testobj.getTestName()==testname)
////					{
////						testList.remove(testobj);
////						flag++;
////						break;
////					}
////				}
////			}
////		}
////		if(flag==0)
////		return false;
////		else
////			return true;
//
//	}

	public List<DiagnosticCenter> getCenterList() {
		List<DiagnosticCenter> centerList = new LinkedList<DiagnosticCenter>();
		String sql="select * from Center where isEmpty=1";
		try
		{
			
			ps=connection.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next())
			{
				 dignosticcenter=new DiagnosticCenter();
				 dignosticcenter.setCenterId(BigInteger.valueOf(rs.getLong(1)));
				 dignosticcenter.setCenterName(rs.getString(2));
				 dignosticcenter.setCenterAddress(rs.getString(3));
				 dignosticcenter.setCenterContactNo(BigInteger.valueOf(rs.getLong(4)));
				
				 centerList.add(dignosticcenter);
				 
			}
			
		}
		catch(Exception exception)
		{
			myLogger.error("Error at listCenter Dao method: "+exception.getMessage());
		}
		finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					myLogger.error("Error at istCenterr Dao method:"+e.getMessage());
				}
			}
		}
		
		return centerList;
	}

	//public boolean setCenterList(List<DiagnosticCenter> centerList) {
	//	return (null != (UserDaoImpl.centerList = centerList));
	//}

	// RegisteredUserList
	public List<User> getUserList() {
		return userList;
	}

	public boolean setUserList(List<User> userList) {
		this.userList = userList;
		return true;
	}

	public BigInteger register(User user) {
		String sql="insert into User(user_name,user_password,user_contact_no,user_role,user_email,user_age,user_gender,isEmpty)"
				+ "values(?,?,?,?,?,?,?,?)";
		BigInteger userid = null;
		try
		{
			
			ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,user.getUserName());
			ps.setString(2,user.getUserPassword());
			ps.setLong(3, user.getContactNo().longValue());
			ps.setString(4,"User");
			ps.setString(5, user.getUserEmail());
			ps.setInt(6, user.getAge());
			ps.setString(7, user.getGender());
			ps.setInt(8, 1);
			int status=ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if(rs!=null && rs.next())
			{
				userid=BigInteger.valueOf(rs.getLong(1));
			}
			
			
		}
		catch(Exception exception)
		{
			myLogger.error("Error at register Dao method: "+exception.getMessage());
		}
		finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					myLogger.error("Error at register Dao method:"+e.getMessage());
				}
			}
		}
		return userid;
	}

	@Override
	public boolean removeTest(BigInteger removeCenterId, BigInteger removeTestId) {
		// TODO Auto-generated method stub
		String sql="update Test set isEmpty=0  where test_id=? AND center_id=?";
		
		try
		{
			ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, removeTestId.longValue());
			ps.setLong(2, removeCenterId.longValue());;
			int noOfRecords=ps.executeUpdate();
			System.out.println(noOfRecords);
			if(noOfRecords<=0)
			{
				throw new UserDefinedException(UserErrorMessage.userErrorNoTestDeleted);
			}
		}
		catch(Exception exception)
		{
			myLogger.error("Error at addTest Dao method: "+exception.getMessage());
		}
		finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					myLogger.error("Error at addTest Dao method:"+e.getMessage());
				}
			}
		}
		return true;
	}
///
	@Override
	public List<Test> getListOfTests(BigInteger centerId) {
		// TODO Auto-generated method stub
///
		  List<Test> testList=new ArrayList<Test>();
		String sql="select * from Test where center_id=? AND isEmpty=?";
		try
		{
			ps=connection.prepareStatement(sql);
			ps.setLong(1,centerId.longValue());
			ps.setInt(2, 1);
			rs=ps.executeQuery();
			while(rs.next())
			{
				test=new Test();
				test.setTestId(BigInteger.valueOf(rs.getLong(1)));
				test.setTestName(rs.getString(2));
				testList.add(test);
			}
		}
		catch(Exception exception)
		{
			myLogger.error("Error at listTest Dao method: "+exception.getMessage());
		}
		finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					myLogger.error("Error at listTest Dao method:"+e.getMessage());
				}
			}
		}
		return testList;
	}

}
