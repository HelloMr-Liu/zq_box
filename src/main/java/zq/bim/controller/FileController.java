package zq.bim.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import zq.bim.result.ReturnView;
import zq.bim.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {
	
	private static final Logger log = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileService fileService;
	
	
	@RequestMapping(value = "/insert", produces = "application/json;charset=utf-8")
	public String insertFile( String fileName,Long fileSize, String fileMd5) {
		
		try {
			ReturnView rtn= fileService.insertFile(fileName,fileSize,fileMd5);
			return rtn.toJson();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ReturnView.error("请求失败!").toJson();
		}
	}
	/**
	 * 预上传
	 * @param fileChildId	进度标识
	 * @param fileMd5		文件md5
	 * @param fileSize		文件大小
	 * @param fileStep		文件步长
	 * @return
	 */
	@RequestMapping(value = "/preparedUpload", produces = "application/json;charset=utf-8")
	public String preparedUpload(String fileChildId, String fileMd5, Long fileSize,Long fileStep) {
		
		try {
			ReturnView rtn= fileService.preparedUpload(fileChildId, fileMd5, fileSize, fileStep);
			return rtn.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return ReturnView.error("请求失败!").toJson();
		}
	}
	
	/**
	 * 上传
	 * @param file			文件数据
	 * @param fileChildId	进度标识
	 * @param fileMd5		文件md5
	 * @param start			位置
	 * @param fileStep		文件步长
	 * @param isThumbnail	压缩
	 * @return
	 */
	@RequestMapping(value = "/upload", produces = "application/json;charset=utf-8")
	public String upload(
			@RequestParam(value = "file", required = false) MultipartFile file,
			String fileChildId, String fileMd5, Long start, Long fileStep) {

		try {
			ReturnView rtn= fileService.upload(file, fileChildId, fileMd5,start, fileStep);
			return rtn.toJson();
		} catch (Exception e) { 
			e.printStackTrace();
			log.error(e.getMessage());
			return ReturnView.error("上传失败!").toJson();
		}
	}
}
