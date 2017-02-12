package com.order.cc.ftp.dao;

import com.order.cc.ftp.domain.DeviceVO;

/**
 * 设备Dao
 * @author yxm
 * @date 2016-12-7
 */
public interface DeviceDao {
	/**
	 * 保存
	 * @param deviceVO
	 */
  boolean insert(DeviceVO deviceVO);
  /**
   *更新 
   * @param deviceVO
   */
  void update(Integer newflag,Integer oldflag);
  /**
   * 删除
   * @param deviceVO
   */
  void delete(Integer flag);
}
