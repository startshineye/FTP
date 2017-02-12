package com.order.cc.ftp.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.FTPReply;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class FTPUtil {

	private FTPClient ftp = null;
	/**
	 * Ftp服务器
	 */
	private String server;
	/**
	 * 用户名
	 */
	private String uname;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 连接端口，默认21
	 */
	private int port = 21;

	public FTPUtil() {

	}

	/**
	 * 连接FTP服务器
	 * 
	 * @param server
	 * @param uname
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public FTPClient connectFTPServer(String server, int port, String uname,
			String password) throws Exception {

		// 初始化并保存信息
		this.server = server;
		this.port = port;
		this.uname = uname;
		this.password = password;

		ftp = new FTPClient();
		try {
			ftp.configure(getFTPClientConfig());
			ftp.connect(this.server, this.port);
			ftp.login(this.uname, this.password);

			// 文件类型,默认是ASCII
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

			// 设置被动模式
			ftp.enterLocalPassiveMode();

			ftp.setConnectTimeout(2000);
			ftp.setControlEncoding("GBK");

			// 响应信息
			int replyCode = ftp.getReplyCode();
			if ((!FTPReply.isPositiveCompletion(replyCode))) {
				// 关闭Ftp连接
				closeFTPClient();
				// 释放空间
				ftp = null;
				throw new Exception("登录FTP服务器失败,请检查![Server:" + server + "、"
						+ "User:" + uname + "、" + "Password:" + password);
			} else {
				return ftp;
			}
		} catch (Exception e) {
			ftp.disconnect();
			ftp = null;
			throw e;
		}
	}

	/**
	 * 配置FTP连接参数
	 * 
	 * @return
	 * @throws Exception
	 */
	public FTPClientConfig getFTPClientConfig() throws Exception {

		String systemKey = FTPClientConfig.SYST_NT;
		String serverLanguageCode = "zh";
		FTPClientConfig conf = new FTPClientConfig(systemKey);
		conf.setServerLanguageCode(serverLanguageCode);
		conf.setDefaultDateFormatStr("yyyy-MM-dd");

		return conf;
	}

	/**
	 * 上传文件到FTP根目录
	 * 
	 * @param localFile
	 * @param newName
	 * @throws Exception
	 */
	public void uploadFile(String localFile, String newName) throws Exception {
		InputStream input = null;
		try {
			File file = null;
			if (checkFileExist(localFile)) {
				file = new File(localFile);
			}
			input = new FileInputStream(file);
			boolean result = ftp.storeFile(newName, input);
			if (!result) {
				throw new Exception("文件上传失败!");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}
	/**
	 * 上传多个文件到FTP根目录
	 * 
	 * @param localFile
	 * @param newName
	 * @throws Exception
	 */
	public void uploadFile(String[] localFile, String[] newName) throws Exception {
		InputStream input = null;
		try {
			
			File file = null;
			for(int i=0;i<localFile.length;i++){
				String s=localFile[i];
				String n=newName[i];
				if (checkFileExist(s)) {
					file = new File(s);
				}
				input = new FileInputStream(file);
				boolean result = ftp.storeFile(n, input);
				if (!result) {
					throw new Exception("文件上传失败!");
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

	/**
	 * 上传文件到FTP根目录
	 * 
	 * @param input
	 * @param newName
	 * @throws Exception
	 */
	public void uploadFile(InputStream input, String newName) throws Exception {
		try {
			boolean result = ftp.storeFile(newName, input);
			if (!result) {
				throw new Exception("文件上传失败!");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
		}

	}

	/**
	 * 上传文件到指定的FTP路径下
	 * 
	 * @param localFile
	 * @param newName
	 * @param remoteFoldPath
	 * @throws Exception
	 */
	public void uploadFile(String localFile, String newName,
			String remoteFoldPath) throws Exception {
		InputStream input = null;
		try {
			File file = null;
			if (checkFileExist(localFile)) {
				file = new File(localFile);
			}
			input = new FileInputStream(file);

			// 改变当前路径到指定路径
			this.changeDirectory(remoteFoldPath);
			boolean result = ftp.storeFile(newName, input);
			if (!result) {
				throw new Exception("文件上传失败!");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}
	/**
	 * 上传多个文件到指定的FTP路径下
	 * 
	 * @param localFile
	 * @param newName
	 * @param remoteFoldPath
	 * @throws Exception
	 */
	public void uploadFile(String[] localFile, String[] newName,
			String remoteFoldPath) throws Exception {
		InputStream input = null;
		try {
			File file = null;
			// 改变当前路径到指定路径
			if(remoteFoldPath!=null&&!remoteFoldPath.equals("")){
				this.changeDirectory(remoteFoldPath);
			}
			
			for(int i=0;i<localFile.length;i++){
				String s=localFile[i];
				String n=newName[i];
				if (checkFileExist(s)) {
					file = new File(s);
				}
				input = new FileInputStream(file);
				boolean result = ftp.storeFile(n, input);
				if (!result) {
					throw new Exception("文件上传失败!");
				}
				input.close();
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
//			if (input != null) {
//				System.out.println(input);
//				input.close();
//				System.out.println(input);
//			}
		}
	}

	/**
	 * 上传文件到指定的FTP路径下
	 * 
	 * @param input
	 * @param newName
	 * @param remoteFoldPath
	 * @throws Exception
	 */
	public void uploadFile(InputStream input, String newName,
			String remoteFoldPath) throws Exception {
		try {
			// 改变当前路径到指定路径
			this.changeDirectory(remoteFoldPath);
			boolean result = ftp.storeFile(newName, input);
			if (!result) {
				throw new Exception("文件上传失败!");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

	/**
	 * 从FTP指定的路径下载文件
	 * 
	 * @param remotePath
	 * @param localPath
	 */
	public void downloadFile(String remotePath, String localPath)
			throws Exception {

		OutputStream output = null;
		try {
			File file = null;
			if (checkFileExist(localPath)) {
				file = new File(localPath);
			}
			output = new FileOutputStream(file);
			boolean result = ftp.retrieveFile(remotePath, output);
			if (!result) {
				throw new Exception("文件下载失败!");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (output != null) {
				output.close();
			}
		}

	}

	/**
	 * 从FTP指定的路径下载文件
	 * 
	 * @param remoteFilePath
	 * @return
	 * @throws Exception
	 */
	public InputStream downFile(String remoteFilePath) throws Exception {
		return ftp.retrieveFileStream(remoteFilePath);
	}

	/**
	 * 获取FTP服务器上指定路径下的文件列表
	 * 
	 * @param filePath
	 * @return
	 */
	public List<String> getFtpServerFileList(String filePath) throws Exception {

		List<String> nlist = new ArrayList<String>();
		FTPListParseEngine engine = ftp.initiateListParsing(filePath);
		List<FTPFile> ftpfiles = Arrays.asList(engine.getNext(25));
		return getFTPServerFileList(nlist, ftpfiles);
	}

	/**
	 * 获取FTP服务器上指定路径下的文件列表
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public List<String> getFileList(String path) throws Exception {

		List<String> nlist = new ArrayList<String>();
		List<FTPFile> ftpfiles = Arrays.asList(ftp.listFiles(path));
		return getFTPServerFileList(nlist, ftpfiles);
	}
	/**
	 * 获取FTP服务器上指定路径下的文件列表
	 * 
	 * @param filePath
	 * @return
	 */
	public String[] getFtpFileList(String filePath) throws Exception {
		
		if(filePath!=null||!filePath.equals("")){
			
			this.changeDirectory(filePath);
		}
		String[] names =ftp.listNames();
		System.out.println("aa "+ftp.listNames());
		String[] returnStr=new String[names.length];
		for(int i=0;i<names.length;i++){
			if(names[i].length()>4){
				if(names[i].substring(names[i].length()-4,names[i].length()).equals(".xml")){
					returnStr[i]=names[i];
					

				}
			}
		}
		return returnStr;
	}
	/**
	 * 列出FTP服务器文件列表信息
	 * 
	 * @param nlist
	 * @param ftpFiles
	 * @return
	 */
	public List<String> getFTPServerFileList(List<String> nlist,
			List<FTPFile> ftpFiles) {
		if (ftpFiles == null || ftpFiles.size() == 0)
			return nlist;
		for (FTPFile ftpFile : ftpFiles) {
			if (ftpFile.isFile()) {
				nlist.add(ftpFile.getName());
			}
		}
		return nlist;
	}

	/**
	 * 改变工作目录，如失败则创建文件夹
	 * 
	 * @param remoteFoldPath
	 */
	public void changeDirectory(String remoteFoldPath) throws Exception {

		if (remoteFoldPath != null) {
			boolean flag = ftp.changeWorkingDirectory(remoteFoldPath);
			if (!flag) {
				ftp.makeDirectory(remoteFoldPath);
				ftp.changeWorkingDirectory(remoteFoldPath);
			}
		}

	}

	/**
	 * 检查文件是否存在
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public boolean checkFileExist(String filePath) throws Exception {
		boolean flag = false;
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("文件不存在,请检查!");
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * 获取文件名,不包括后缀
	 * 
	 * @param filePath
	 * @return
	 */
	public String getFileNamePrefix(String filePath) throws Exception {

		boolean flag = this.checkFileExist(filePath);
		if (flag) {
			File file = new File(filePath);
			String fileName = file.getName();
			String _fileName = fileName.substring(0, fileName.lastIndexOf("."));
			return _fileName;
		}
		return null;
	}

	/**
	 * 关闭FTP连接
	 * 
	 * @param ftp
	 * @throws Exception
	 */
	public void closeFTPClient(FTPClient ftp) throws Exception {

		try {
			if (ftp.isConnected())
				ftp.disconnect();
		} catch (Exception e) {
			throw new Exception("关闭FTP服务出错!");
		}
	}

	/**
	 * 关闭FTP连接
	 * 
	 * @throws Exception
	 */
	public void closeFTPClient() throws Exception {

		this.closeFTPClient(this.ftp);

	}

	/**
	 * Get Attribute Method
	 * 
	 */
	public FTPClient getFtp() {
		return ftp;
	}

	public String getServer() {
		return server;
	}

	public String getUname() {
		return uname;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	/**
	 * Set Attribute Method
	 * 
	 */
	public void setFtp(FTPClient ftp) {
		this.ftp = ftp;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
	}
	

//	/**
//	 * 主方法(测试)
//	 * 
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		try {
//			FTPUtils fu = new FTPUtils();
//			fu.connectFTPServer("172.18.116.46", 21, "aaa", "aaa");
//			fu.uploadFile("C:\\smsFile\\sendDat\\a.txt", "a.txt"); 
//			fu.closeFTPClient();
//		} catch (Exception e) {
//			System.out.println("异常信息：" + e.getMessage());
//
//		}
//	}
	
}
