package zq.bim.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import zq.bim.result.ReturnView;

public interface FileService {
	
	
	public ReturnView insertFile(String fileName,Long fileSize, String fileMd5);
	
	/****
	 * 预上传
	 * @param fileChildId
	 * @param fileMd5
	 * @param fileSize
	 * @param fileStep
	 * @return
	 */
	public ReturnView preparedUpload(String fileChildId, String fileMd5,Long fileSize, Long fileStep) throws IOException ;
	
	/***
	 * 上传
	 * @param file
	 * @param fileChildId
	 * @param fileMd5
	 * @param start
	 * @param fileStep
	 * @return
	 */
	public ReturnView upload(MultipartFile file, String fileChildId,String fileMd5,
			Long start, Long fileStep) throws IOException;
	
}
