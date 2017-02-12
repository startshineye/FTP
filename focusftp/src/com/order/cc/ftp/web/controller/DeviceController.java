package com.order.cc.ftp.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.order.cc.ftp.domain.DeviceVO;
import com.order.cc.ftp.service.DeviceService;
import com.order.cc.ftp.util.FTPUtil;
import com.order.cc.ftp.util.FileUtil;
import com.order.cc.ftp.util.XmlUtil;

@Controller
@RequestMapping("device")
public class DeviceController {
	@Autowired
	private DeviceService deviceService;
	//請求發送：http://localhost:8088/focusftp/device/save.do
	@RequestMapping("/ftp")
	public void ftpDownload(){
		
	}
	
	@RequestMapping("/save")
	public void save(){
		 System.out.println("********下载ftp文件**********");
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
		   String remoteFile = remotedir+"20161113"+"/810/"+remoteFileName;
		  /* try {
		     fu.connectFTPServer(host, Integer.parseInt(port), user, password);
		     fu.downloadFile(remoteFile, localFile);
		     fu.closeFTPClient();
		   } catch (Exception e) {
			 System.out.println("异常信息：" + e.getMessage());
		  }*/
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
	}
}
