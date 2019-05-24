package com.uber.assignment.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.uber.assignment.model.DriverDetail;
import com.uber.assignment.model.IdentityDetails;
import com.uber.assignment.service.RegisterService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class RegistrationController {

	public static final Logger logger = Logger.getLogger(RegistrationController.class);

	@Autowired
	RegisterService registerService;

	/*
	 * This API is to Create new Driver Records handles identity and documents
	 */
	@PostMapping(value = "/creatNewDriverRecord", produces = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "API to create New User", response = Response.class)
	public ResponseEntity<?> creatNewDriverRecord(@RequestBody DriverDetail driverDetail) {

		logger.info("creatNewDriverRecord -------->" + registerService);

		if (registerService.isDriverExist(driverDetail)) {

			logger.error("Unable to create. A User with name already exist" + driverDetail.getName());
			return new ResponseEntity("Unable to create. A User with name already exist", HttpStatus.CONFLICT);

		}

		logger.info("After search------>");
		registerService.addDriverDetails(driverDetail);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", "New Entry created Successfully !!");
		return new ResponseEntity(jsonObject.toString(), HttpStatus.CREATED);
	}

	/*
	 * * API To Update Name OR Locality of registered Driver
	 */
	@RequestMapping(value = "/updateDriverNameAndLocality/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "API to update User Name", response = Response.class)
	public ResponseEntity<?> updateUserName(@PathVariable("id") long id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "locality", required = false) String locality) {

		logger.info("Updating User with id " + id);

		DriverDetail existingDriver = registerService.getDriver(id);

		if (existingDriver == null) {
			logger.error("Unable to update. User with id {} not found." + id);
			return new ResponseEntity<String>("Unable to update. User with id " + id + " not found.",
					HttpStatus.NOT_FOUND);
		}

		if (name != null)
			existingDriver.setName(name);

		if (locality != null)
			existingDriver.setLocality(locality);

		registerService.updateDriver(existingDriver);

		return new ResponseEntity<DriverDetail>(existingDriver, HttpStatus.OK);
	}

	@PostMapping(value = "/addAndUpdateIdentity", produces = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "API Add and Update Identity", response = Response.class)
	public ResponseEntity addAndUpdateIdentity(@RequestParam("driverId") Integer driverId,
			@RequestBody IdentityDetails identityDetails) {

		DriverDetail driverDetail = registerService.getDriver(driverId);

		if (driverDetail == null) {
			logger.error("Unable to Add/update Identity Details. User  does not exist");
			return new ResponseEntity("Unable to Add/update Identity Details. User  does not exist",
					HttpStatus.CONFLICT);
		}

		JSONObject jsonObject = registerService.addAndUpdateIdentityDetails(driverDetail, identityDetails);
		logger.info("json objects------>");
		return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);
	}

	/*
	 * * Get All Registered Driver with Identity Details and Documents
	 */
	@RequestMapping(value = "/getAllDriverDetails", method = RequestMethod.GET)
	@ApiOperation(value = "API to get all drivers Details", response = Response.class)
	public ResponseEntity<List<DriverDetail>> getAllDriverDetails() {
		List<DriverDetail> users = registerService.getAllDriversList();

		logger.info("users size------->" + users.size());

		if (users.isEmpty()) {
			return new ResponseEntity<List<DriverDetail>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<DriverDetail>>(users, HttpStatus.OK);
	}

	/*
	 * * Get All Details of Individual Registed Driver Details
	 */
	@RequestMapping(value = "/getDriverDetails/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "API to get respective driver details", response = Response.class)
	public ResponseEntity<DriverDetail> getUserDetails(@PathVariable("id") long id) {
		DriverDetail driver = registerService.getDriver(id);

		logger.info("users size------->" + driver);

		if (driver == null) {
			return new ResponseEntity<DriverDetail>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<DriverDetail>(driver, HttpStatus.OK);
	}

	@PostMapping(value = "/deleteIdentity", produces = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "API Delete Identity", response = Response.class)
	public ResponseEntity deleteIntity(@RequestParam(value = "identityId", required = true) long identityId,
			@RequestParam(value = "driverId", required = true) long driverId) {
	
		DriverDetail driverDetails = registerService.getDriver(driverId);
		
		JSONObject jsonObject = new JSONObject();
		if(driverDetails==null){
			jsonObject.put("error", "Driver Details not Found !");
		}else{
			jsonObject = registerService.deleteIdentity(driverDetails, identityId);
		}
	
		return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);

	}

	/*
	 * { "documents": [ { "description": "driver3 registration",
	 * 
	 * "status": "ACTIVE", "type": "RC_BOOK", "url":
	 * "/amazon/RC_BOOK/driver3.png" }, { "description":
	 * "driver1 registration 3",
	 * 
	 * "status": "ACTIVE", "type": "INSUARANCE", "url":
	 * "/amazon/INSUARANCE/driver3.png" } ], "identityDetails": [ {
	 * 
	 * "status": "ACTIVE", "type": "AADHAR", "uid": "AADHAR11", "url":
	 * "/amazon/AADHAR/driver3.png" }, {
	 * 
	 * "status": "ACTIVE", "type": "LICENCE", "uid": "LICENCE13", "url":
	 * "/amazon/LICENCE/driver3.png" }
	 * 
	 * ], "locality": "mahadevpura", "name": "driver6" }
	 * 
	 * 
	 */
}
