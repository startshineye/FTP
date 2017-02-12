package com.order.cc.ftp.service;

import java.util.List;

public interface DeviceService {
	/**
	 * 保存數據
	 * @param dataLiat
	 */
   boolean save(List dataLiat);
   /**
	 * 更新
	 * @param dataLiat
	 */
   void update(Integer newflag,Integer oldflag);
   /**
	 * 删除
	 * @param dataLiat
	 */
   void delete(Integer flag);
}
