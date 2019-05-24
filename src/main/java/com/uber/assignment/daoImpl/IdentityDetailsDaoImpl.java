package com.uber.assignment.daoImpl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.uber.assignment.dao.IdentityDetailsDao;
import com.uber.assignment.model.DriverDetail;
import com.uber.assignment.model.IdentityDetails;

@Repository
public class IdentityDetailsDaoImpl implements IdentityDetailsDao {

	public static final Logger logger = Logger.getLogger(IdentityDetailsDaoImpl.class);
	
	@Autowired 
	private SessionFactory sessionFactory;
	
	
	@Override
	public IdentityDetails findIdentityForDriver(DriverDetail driverDetail, IdentityDetails identityDetails) {
		
	/*	String hqlQuery ="select i from DriverDetail d "
							+ "inner join d.IdentityDetails "
							+ "where d.id =:did and i.uid=:uid and i.type=:type";*/
		
		String sqlQuery =" select id.* from driverdetail d "
				+ "inner join driver_identity di on d.id=di.ID "
				+ "inner join identitydetails id on di.IDENTITY_ID = id.IDENTITY_ID "
				+ "where d.id=:did and id.uid=:uid";
					
		IdentityDetails existingIdentityDetails = sessionFactory.getCurrentSession()
												.createNativeQuery(sqlQuery, IdentityDetails.class)
												.setParameter("did", driverDetail.getId())
												.setParameter("uid", identityDetails.getUid())
												.uniqueResult();
		
		logger.info("existingIdentityDetails Found--->"+existingIdentityDetails);
				
		return existingIdentityDetails;
	}
	
	@Override
	public IdentityDetails updateDriver(IdentityDetails identityDetails) {
				
		sessionFactory.getCurrentSession().update(identityDetails);
        return identityDetails;
	
	}

	@Override
	public void deleteIndenity(IdentityDetails identityDetails) {
		sessionFactory.getCurrentSession().delete(identityDetails);
	}
	
	
	
}

/*
select id.* from driverdetail d
inner join driver_identity di
 on d.id=di.ID
 inner join identitydetails id
 on di.IDENTITY_ID  = id.IDENTITY_ID
 where d.id=1 and id.uid="LICENCE11" and type="0";*/

