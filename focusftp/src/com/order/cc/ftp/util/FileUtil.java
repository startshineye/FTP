package com.order.cc.ftp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.order.cc.ftp.domain.DeviceVO;

/**
 * 文件读取工具类
 * @author yxm
 * @date 2016-12-7
 *
 */
public class FileUtil {
   /**
    * 读取filePath中文件到内存
    * @param filePath
    * @return List
    */
	public static List readTxtFile(String filePath){
		List dataList = new ArrayList();
		InputStreamReader read =null;
		BufferedReader bufferedReader = null;
		try {
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()){ //判断文件是否存在
			read = new InputStreamReader(new FileInputStream(file), "utf-8");
			bufferedReader = new BufferedReader(read);
		    String temp;
		    while((temp = bufferedReader.readLine())!=null){
		    	//51CC0078正阳支行日照市正阳路12312318688414255
		    	//设备编号，所属机构，设备地址，管理员姓名，联系方式，创建时间，标识
		    	String[] lineData = temp.split("");
		    	int length = lineData.length;
		    	System.out.println("数据长度："+length);
		    	DeviceVO deviceVO = new DeviceVO();
		    	if(length==5){
			    	deviceVO.setDeviceNum(lineData[0]);
			    	deviceVO.setInstitution(lineData[1]);
			    	deviceVO.setDeviceAddress(lineData[2]);
			    	deviceVO.setManagerName(lineData[3]);
			    	deviceVO.setContact(lineData[4]);
		    	}else if(length==4){
			    	deviceVO.setDeviceNum(lineData[0]);
			    	deviceVO.setInstitution(lineData[1]);
			    	deviceVO.setDeviceAddress(lineData[2]);
			    	deviceVO.setManagerName(lineData[3]);
		    	}else if(length==3){
			    	deviceVO.setDeviceNum(lineData[0]);
			    	deviceVO.setInstitution(lineData[1]);
			    	deviceVO.setDeviceAddress(lineData[2]);
		    	}
		    	String currentTime = DateUtil.getCurrentTime();
		    	deviceVO.setCreateTime(currentTime);
		    	deviceVO.setFlag(0);
		    	System.out.println("数据："+deviceVO.toString());
		    	dataList.add(deviceVO);
		     }
			}else{
				System.out.println("系統找不到文件");
			}
		} 
		catch (Exception e) {
		   e.printStackTrace();
		}finally{
			if(read != null){
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bufferedReader != null){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataList;
	}
	
	/**
	 * 根据时间得到动态文件路径
	 * @param map
	 * @return
	 */
	public static Map<String,String> getDateLocaldirMap(Map<String,String> xmlmap){
		Map<String, String> map = new HashMap<String,String>();
		//1.获取日期
		String new_localPath="";
		String currentTime = DateUtil.getLastMonthDay();//2016/12/8
		String localdir = xmlmap.get("Localdir");
		new_localPath = localdir+currentTime+"/";
		xmlmap.put("Localdir", new_localPath);
		map = xmlmap;
		return map;
	}
	
	/**
	 * 创建文件
	 * @param destFileName
	 * @return
	 */
	public static boolean CreateFile(String destFileName) {
	    File file = new File(destFileName);
	    if (file.exists()) {
	     System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
	     return false;
	    }
	    if (destFileName.endsWith(File.separator)) {
	     System.out.println("创建单个文件" + destFileName + "失败，目标不能是目录！");
	     return false;
	    }
	    if (!file.getParentFile().exists()) {
	     System.out.println("目标文件所在路径不存在，准备创建。。。");
	     if (!file.getParentFile().mkdirs()) {
	      System.out.println("创建目录文件所在的目录失败！");
	      return false;
	     }
	    }

	    // 创建目标文件
	    try {
	     if (file.createNewFile()) {
	      System.out.println("创建单个文件" + destFileName + "成功！");
	      return true;
	     } else {
	      System.out.println("创建单个文件" + destFileName + "失败！");
	      return false;
	     }
	    } catch (IOException e) {
	     e.printStackTrace();
	     System.out.println("创建单个文件" + destFileName + "失败！");
	     return false;
	    }
	}
/**
 * 创建目录
 * @param destDirName
 * @return
 */
	public static boolean createDir(String destDirName) {
	    File dir = new File(destDirName);
	    if(dir.exists()) {
	     System.out.println("创建目录" + destDirName + "失败，目标目录已存在！");
	     return false;
	    }
	    if(!destDirName.endsWith(File.separator))
	     destDirName = destDirName + File.separator;
	    // 创建单个目录
	    if(dir.mkdirs()) {
	     System.out.println("创建目录" + destDirName + "成功！");
	     return true;
	    } else {
	     System.out.println("创建目录" + destDirName + "成功！");
	     return false;
	    }
	}
	/**
	 * 创建临时文件
	 * @param prefix
	 * @param suffix
	 * @param dirName
	 * @return
	 */
	public static String createTempFile(String prefix, String suffix, String dirName) {
	    File tempFile = null;
	    try{
	    if(dirName == null) {
	     // 在默认文件夹下创建临时文件
	    
	     tempFile = File.createTempFile(prefix, suffix);
	     return tempFile.getCanonicalPath();
	    }
	    else {
	     File dir = new File(dirName);
	     // 如果临时文件所在目录不存在，首先创建
	     if(!dir.exists()) {
	      if(!FileUtil.createDir(dirName)){
	       System.out.println("创建临时文件失败，不能创建临时文件所在目录！");
	       return null;
	      }
	     }
	     tempFile = File.createTempFile(prefix, suffix, dir);
	     return tempFile.getCanonicalPath();
	    }
	    } catch(IOException e) {
	     e.printStackTrace();
	      System.out.println("创建临时文件失败" + e.getMessage());
	      return null;
	    }
	}

	/**
	 * 删除单个文件
	 * @param fileName  删除的文件名
	 * @return true 成功
	 */
	public static boolean deleteFile(String fileName){
		File file = new File(fileName);
		if(file.exists()&&file.isFile()){
			if(file.delete()){
			System.out.println("删除单个文件"+fileName+"成功");	
			return true;
			}else{
				System.out.println("删除单个文件"+fileName+"失败");
				return false;
			}
		}else{
			System.out.println("删除单个文件失败"+fileName+"不存在");
			return false;
		}
		
	}
	
	
	public static void main(String[] args) {
	    // 创建目录
	    String dirName = "F:/test/test0/test1";
	    FileUtil.createDir(dirName);
	    // 创建文件
	    String fileName = dirName + "/test2/testFile.txt";
	    FileUtil.CreateFile(fileName);
	    // 创建临时文件
	    String prefix = "temp";
	    String suffix = ".txt";
	    for(int i = 0; i < 10; i++) {
	     System.out.println("创建了临时文件:" + FileUtil.createTempFile(prefix, suffix, dirName));
	    }
	    
	    FileUtil.deleteFile(fileName);
	}
}
