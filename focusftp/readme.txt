
version-01:
   1.实现ftp文件下载到xml配置目录
   2.实现将ftp下载的文件保存到数据库中
   
version-02
   1.实现按照日期创建时间文件夹，将ftp文件保存到相应文件夹下
   2.读取相应按照日期的文件夹到数据库保存
   3.实现定时器
   4.删除宝存数据
     ftp下载
     update flag = 2;
     insert flag =1;
     delete flag=2;
             操作中有2个flag状态 最后剩下1个
    5.因为是一天更新一次，所以ftp下载文件前不需要删除文件（因为文件夹是根据 日期的）
   
ftp：测试数据
  <FtpGroup>
	<Depict>COREFTPDownLoad</Depict>	
	<FtpSwitch>1</FtpSwitch>
	<Host>192.168.1.1</Host>
	<Port>2121</Port>
	<User>yxm</User>
	<Password>321</Password>
	<Remotedir>\</Remotedir>
	<RemoteFile>js.txt</RemoteFile>
	<Localdir>D:/download/</Localdir>
	<BakLocaldir>c:\focusapp\knowledge\import\ftpbak\</BakLocaldir>
	<Prefix>temp</Prefix>
	<Suffix>.txt</Suffix>
</FtpGroup>

IP:188.185.1.170
目录：/odsdata/ATM/日期/810/ADMIN_INFO.del
20131231

eg:/odsdata/ATM/20131231/810/ADMIN_INFO.del
/odsdata/ATM/20131231/810/DEVINFO.ok

用户名和密码：dsadm 


ADMIN_INFO.ok


		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	
			
		