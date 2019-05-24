package com.uber.assignment.dao;

import org.springframework.stereotype.Repository;

import com.uber.assignment.model.DriverDetail;
import com.uber.assignment.model.IdentityDetails;

@Repository
public interface IdentityDetailsDao {
	
	public IdentityDetails findIdentityForDriver(DriverDetail driverDetail, IdentityDetails identityDetails);

	public IdentityDetails updateDriver(IdentityDetails identityDetails);
	
	public void deleteIndenity(IdentityDetails identityDetails);
	
}
