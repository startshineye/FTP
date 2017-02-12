package com.order.cc.ftp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.cc.ftp.dao.DeviceDao;
import com.order.cc.ftp.domain.DeviceVO;
import com.order.cc.ftp.service.DeviceService;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService{
    @Autowired
	private DeviceDao deviceDao; 
    
	@Override
	public boolean save(List dataLiat) {
		try {
			for (Object object : dataLiat) {
				DeviceVO deviceVO = (DeviceVO)object;
				deviceDao.insert(deviceVO);
			};
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public void update(Integer newflag,Integer oldflag) {
		deviceDao.update(newflag,oldflag);
	}
	@Override
	public void delete(Integer flag) {
		deviceDao.delete(flag);
	}
}
