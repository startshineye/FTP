package com.order.cc.ftp.timer;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.order.cc.ftp.domain.DeviceVO;
import com.order.cc.ftp.service.DeviceService;
import com.order.cc.ftp.util.DateUtil;
import com.order.cc.ftp.util.FTPUtil;
import com.order.cc.ftp.util.FileUtil;
import com.order.cc.ftp.util.XmlUtil;

/** 
 * 基于注解的定时器 
 * @author yxm 
 */  
@Component
public class Task {
	@Autowired
	private DeviceService deviceService;
	/**  
     * 心跳更新。启动时执行一次，之后每隔2秒执行一次  
     */  
	@Scheduled(cron = "0 0 23 * *") 
    public void getDeviceInfo(){ 
		   FTPUtil fu = new FTPUtil();
		   Map<String, Map<String, String>> parseXml = XmlUtil.parseXml();
		   //ftp下载
		   Map<String, String> map = parseXml.get("COREFTPDownLoad");
		   String host = map.get("Host");
		   String port = map.get("Port");
		   String user = map.get("User");
		   String password = map.get("Password");
		   String remotedir = map.get("Remotedir");
		   String remoteFileName = map.get("RemoteFileName");
		   String localdir = map.get("Localdir");              
		   String prefix = map.get("Prefix");
		   String suffix = map.get("Suffix");
		   
		   //map转换
		   Map<String,String> newMap = FileUtil.getDateLocaldirMap(map);
		   
		   String new_localdir = newMap.get("Localdir");
		   
		   //创建目录
		   FileUtil.createDir(new_localdir);
		   
		    
		   //创建文件
		   String destFileName = new_localdir+prefix+suffix;
		   FileUtil.CreateFile(destFileName);
		  // String createTempFile = FileUtil.createTempFile(prefix, suffix, new_localdir);
		   
		   //本地文件
		   String localFile = destFileName;
		  //String remoteFile = remotedir+DateUtil.date2SStr()+"/810/"+remoteFileName;
		   String remoteFile = remotedir+DateUtil.getLastMonthDay()+"/810/"+remoteFileName;
		   try {
		     fu.connectFTPServer(host, Integer.parseInt(port), user, password);
		     fu.downloadFile(remoteFile, localFile);
		     fu.closeFTPClient();
		   } catch (Exception e) {
			 System.out.println("异常信息：" + e.getMessage());
		  }
		   System.out.println("********ftp文件传至数据库**********");
		   
			//1.獲取文件數據
			String filePath=null;
			List dataLiat = FileUtil.readTxtFile(localFile);
			for (Object object : dataLiat) {
				DeviceVO deviceVO=(DeviceVO)object;
				System.out.println(deviceVO);
			}
			
			deviceService.update(1,0);
			//插入到數據庫
			boolean save = deviceService.save(dataLiat);
			if(save){
				deviceService.delete(1);
			}else{
				System.out.println("插入数据失败！");
			}
     }; 
}
