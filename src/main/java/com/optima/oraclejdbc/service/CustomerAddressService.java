package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.daoimpl.CustomerAddressDao;
import com.optima.oraclejdbc.daoimpl.CustomerInfoDao;
import com.optima.oraclejdbc.model.CustomerAddress;
import com.optima.oraclejdbc.model.CustomerInfo;

@Service
public class CustomerAddressService {
	@Autowired
	CustomerAddressDao dao;
	
	@Autowired
	CustomerInfoDao customerInfoDao;
	
	public void saveBatch(String cifKey, List<CustomerAddress> data) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		List<CustomerAddress> listInsert = new ArrayList<CustomerAddress>();
		List<CustomerAddress> listUpdate = new ArrayList<CustomerAddress>();
		List<CustomerAddress> addrFromDb = dao.getCustomerAddressByCif(cifKey);
		
		CustomerInfo customerInfo = customerInfoDao.getUserByCIFKey(cifKey);
		for (CustomerAddress customerAddress : data) {
			boolean found = false;
			for (CustomerAddress customerAddressFromDb : addrFromDb ) {
				if( customerAddressFromDb.getSeqNum().equals(customerAddress.getSeqNum()) ){
					customerAddress.setId(customerAddressFromDb.getId());
					customerAddress.setIsDefault(customerAddressFromDb.getIsDefault());				
					found = true;
					break;
				}
			}			
			customerAddress.setCustomerInfoId(customerInfo.getId());			
			if( found )
				listUpdate.add(customerAddress);
			else
				listInsert.add(customerAddress);
			
		}
		if( listUpdate.size() > 0 )
			dao.updateBatch(listUpdate.toArray());
		
		if( listInsert.size() > 0 )
			dao.createBatch(listInsert.toArray());
	}
	
	public void setCustomerDefaultAddress(String cifKey, String seqNum){
		dao.setCustomerDefaultAddress(cifKey, seqNum);
	}
	
	public void setCustomerCustomAddress(String cifKey, String address) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		dao.resetCustomerDefaultAddress(cifKey);
		String addrs[] = new String[]{"","","",""};
		int spos=0;
		int epos=40;
		int i=0;
		do{
			if( epos > address.length() )
				epos = address.length();
			addrs[i] = address.substring(spos, epos);
			spos+=40;
			epos+=40;
			i++;
		}while( i<4 && spos < address.length() );
				
		CustomerAddress customAddress = dao.getCustomerCustomAddress(cifKey);
		if( customAddress == null ){
			customAddress = new CustomerAddress();
			customAddress.setSource(1);
			customAddress.setCustomerInfoId(customerInfoDao.getUserByCIFKey(cifKey).getId());
			customAddress.setIsDefault(1);
			customAddress.setAddress1(addrs[0]);
			customAddress.setAddress2(addrs[1]);
			customAddress.setAddress3(addrs[2]);
			customAddress.setAddress4(addrs[3]);
			customAddress.setSeqNum("999");
			dao.create(customAddress);
		}else{
			customAddress.setAddress1(addrs[0]);
			customAddress.setAddress2(addrs[1]);
			customAddress.setAddress3(addrs[2]);
			customAddress.setAddress4(addrs[3]);
			customAddress.setIsDefault(1);
			dao.update(customAddress);
		}
	}
	
	public CustomerAddress getCustomerCustomAddress(String cifKey) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return dao.getCustomerCustomAddress(cifKey);
	}
	
	public List<CustomerAddress> getCustomerAddresses(String cifKey) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return dao.getCustomerAddressByCif(cifKey);
	}
}
