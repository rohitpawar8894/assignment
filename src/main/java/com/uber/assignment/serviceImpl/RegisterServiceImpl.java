package com.uber.assignment.serviceImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uber.assignment.controller.RegistrationController;
import com.uber.assignment.dao.DriverDetailDao;
import com.uber.assignment.dao.IdentityDetailsDao;
import com.uber.assignment.model.DriverDetail;
import com.uber.assignment.model.IdentityDetails;
import com.uber.assignment.service.RegisterService;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {

	public static final Logger logger = Logger.getLogger(RegisterServiceImpl.class);
	
	@Autowired
	DriverDetailDao driverDetailDto;
	
	@Autowired
	IdentityDetailsDao identityDetailDao;
	
	@Override
	@Transactional
	public void addDriverDetails(DriverDetail driverDetails) {
		
		driverDetailDto.addDriverDetails(driverDetails);
		
	}

	@Override
	@Transactional
	public List<DriverDetail> getAllDriversList() {
	
		return driverDetailDto.getAllDriversList();
	}

	@Override
	@Transactional
	public DriverDetail updateDriver(DriverDetail driver) {
		
		return driverDetailDto.updateDriver(driver);
	}

	@Override
	@Transactional
	public DriverDetail getDriver(long driverId) {
		
		return driverDetailDto.getDriver(driverId);
		
	}

	@Override
	@Transactional
	public Boolean isDriverExist(DriverDetail driverDetails) {
		
		logger.info("--Inside isDriverExist--");
		
		DriverDetail driver = driverDetailDto.getDriverByName(driverDetails.getName());
		
		if(driver!=null){
			return true;
		}
		return false;
		
	}

	@Override
	public JSONObject addAndUpdateIdentityDetails(DriverDetail driverDetails, IdentityDetails identityDetails) {
		
		IdentityDetails existingIdentityDetails = identityDetailDao.findIdentityForDriver(driverDetails, identityDetails);
		logger.info("existingIdentityDetails---------->"+existingIdentityDetails);
		
		JSONObject returnData = new JSONObject();
		if(existingIdentityDetails!=null){
			returnData.put("error", "Identity already exists, can't modify now");
		}else{
			driverDetails.addIdentityDetails(identityDetails);
			returnData.put("success", "New Identity Added");
			driverDetailDto.addDriverDetails(driverDetails);
		}
		return returnData;
	}

	public JSONObject deleteIdentity(DriverDetail driverDetails, long identityId){
		
		JSONObject jsonObject = new JSONObject();
		boolean flag=false;
		List<IdentityDetails> identityDetails = driverDetails.getIdentityDetails();
		
		for(IdentityDetails detail : identityDetails){
			if(detail.getId()==identityId){
				identityDetailDao.deleteIndenity(detail);
				flag=true;
			}
		}
		
		if(flag==true){
			jsonObject.put("status", "Identity detail removed successfully !");
		}else{
			jsonObject.put("status", "Identity Not found !");
		}
		
		return jsonObject;
	}
}
