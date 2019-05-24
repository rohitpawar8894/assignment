package com.uber.assignment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uber.assignment.model.DriverDetail;

@Repository
public interface DriverDetailDao {
	
	public void addDriverDetails(DriverDetail driverDetails);	
	
	public List<DriverDetail> getAllDriversList();
	
    public DriverDetail updateDriver(DriverDetail employee);
    
    public DriverDetail getDriver(long dirverId);
    
    public DriverDetail getDriverByName(String name);
    
}
