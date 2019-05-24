package com.uber.assignment.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.uber.assignment.model.DriverDetail;
import com.uber.assignment.model.IdentityDetails;

@Service
public interface RegisterService {

	public void addDriverDetails(DriverDetail driverDetails);	
	
	public DriverDetail updateDriver(DriverDetail driver);
	
	public List<DriverDetail> getAllDriversList();
	    
    public DriverDetail getDriver(long driverId);
   
    public Boolean isDriverExist(DriverDetail driverDetails);
    
    public JSONObject addAndUpdateIdentityDetails(DriverDetail driverDetails, IdentityDetails identityDetails);

    public JSONObject deleteIdentity(DriverDetail driverDetails, long identityId);
}
