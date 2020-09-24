package zq.bim.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zq.bim.config.FileProperties;

@Component
public class FileUtil {
	
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
	
	private static FileProperties properties = null;
	
	private final FileProperties fileProperties;
	
	
	
	public FileUtil(FileProperties fileProperties) {
		this.fileProperties = fileProperties;
	}
	
	@PostConstruct
	public void init() {
		properties=fileProperties;
	}

	/**
	 * 创建文件保存路径
	 * @param fileName	文件名称
	 * @return
	 */
	public static String createFilePath(String fileName){
		SimpleDateFormat smf = new SimpleDateFormat("yyyy/MM/dd");
		String dateString = smf.format(new Date());
		String path = getBaseFilePath()+"CFD"+"/"+dateString ;
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		return  path +"/"+fileName;
	}
	
	/**
	 * 获取同步文件路径
	 * @param partPath
	 * @param fileName
	 * @return
	 */
	public static String getTongBuFilePath(String partPath,String fileName){
		
		String path = getBaseFilePath()+"CFD"+"/"+partPath;
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		return  path +"/"+fileName;
	}
	

	/**
	 * 获取文件保存的基础路径
	 * @return
	 */
	public static String getBaseFilePath(){
		
		return properties.getZyPath();
	}
	
}
