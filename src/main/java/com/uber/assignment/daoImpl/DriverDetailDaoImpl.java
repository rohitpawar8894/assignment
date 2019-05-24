package com.uber.assignment.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.uber.assignment.controller.RegistrationController;
import com.uber.assignment.dao.DriverDetailDao;
import com.uber.assignment.model.Documents;
import com.uber.assignment.model.DriverDetail;
import com.uber.assignment.model.IdentityDetails;

@Repository
public class DriverDetailDaoImpl implements DriverDetailDao{
	
	public static final Logger logger = Logger.getLogger(DriverDetailDaoImpl.class);
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	@Autowired 
	private SessionFactory sessionFactory;
	
	@Override
	public void addDriverDetails(DriverDetail driverDetails) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(driverDetails);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DriverDetail> getAllDriversList() {
		
		List<DriverDetail> driverDetails = sessionFactory.getCurrentSession().createQuery("from DriverDetail").list();
		logger.info("Driver Details ---->"+driverDetails);
		return driverDetails;
		
	}

	@Override
	public DriverDetail updateDriver(DriverDetail driver) {
		
		sessionFactory.getCurrentSession().update(driver);
        return driver;
	
	}

	@Override
	public DriverDetail getDriver(long driverId) {
		
		DriverDetail driver = sessionFactory.getCurrentSession().get(DriverDetail.class, driverId);
		
		if(driver==null){
			try{
				driver = (DriverDetail) sessionFactory.getCurrentSession()
						.createNativeQuery("select * from DriverDetail WHERE id=:id",DriverDetail.class)
						.setParameter("id", driverId).getSingleResult();
			}catch (NoResultException nre){
				
			}
		}else{
			Hibernate.initialize(driver.getIdentityDetails());
		  	Hibernate.initialize(driver.getDocuments());
		}
			
		return (DriverDetail) driver;	 
	}

	@Override
	public DriverDetail getDriverByName(String name) {
		
		logger.info("Testing-- "+name+" -->");
		
		DriverDetail obj = null;
		try{
			obj = (DriverDetail) sessionFactory.getCurrentSession()
					.createNativeQuery("select * from DriverDetail WHERE name=:name",DriverDetail.class)
					.setParameter("name", name).getSingleResult();
			
		}catch (NoResultException nre){
			
		}
		logger.info("Testing-- "+obj+" -->");			
		return obj;
				
	}

}
