package zq.bim.util;

import java.io.IOException;
import java.util.Properties;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.springframework.beans.factory.annotation.Autowired;

import zq.bim.config.FileProperties;


public class ImageUtil {
	
	private static FileProperties properties;
	
	@Autowired
	public ImageUtil(FileProperties properties) {
		ImageUtil.properties=properties;
	}

	

	/**
	 * 压缩图片，只缩小不放大（等比）
	 * @param srcPath	原图地址
	 * @param newPath	压缩后地址
	 * @param width		压缩后宽度
	 * @param height	压缩后高度
	 * @param type		压缩类型，1比列压缩，2百分比压缩
	 * @param quality	压缩质量
	 */
	public static String thumbnail(String srcPath,String newPath,int width, int height,int type,String quality){
		IMOperation op = new IMOperation();
		ConvertCmd cmd = new ConvertCmd(true);
		op.addImage();
		String raw = "";
		if (type == 1) {
			// 按像素
			raw = width + "x" + height + ">";
		} else {
			// 按像素百分比
			raw = width + "%x" + height + "%";
		}
		op.addRawArgs("-thumbnail", raw);
		if ((quality != null && !quality.equals(""))) {
			op.addRawArgs("-quality", quality);
		}
		op.addImage();

		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("win") != -1) {
			cmd.setSearchPath(properties.getGraphicsPath());
		}

		try {
			cmd.run(op, srcPath, newPath);
			return "success_压缩成功！";
		} catch (Exception e) {
			e.printStackTrace();
			return "error_压缩失败，"+e.getMessage();
		}
		
	}
	
	/**
	 * 压缩图片，只缩小不放大（等比）
	 * @param srcPath	原图地址
	 * @param newPath	压缩后地址
	 * @param width		压缩后宽度
	 * @param height	压缩后高度
	 * @param type		压缩类型，1比列压缩，2百分比压缩
	 * @param quality	压缩质量
	 * @throws Exception 
	 */
	public static void compress(String srcPath, String newPath, int width, int height,int type, String quality) throws Exception {
		
		IMOperation op = new IMOperation();
		ConvertCmd cmd = new ConvertCmd(true);
		op.addImage();
		String raw = "";
		if (type == 1) {
			// 按像素
			raw = width + "x" + height + ">";
		} else {
			// 按像素百分比
			raw = width + "%x" + height + "%";
		}
		op.addRawArgs("-thumbnail", raw);
		if ((quality != null && !quality.equals(""))) {
			op.addRawArgs("-quality", quality);
		}
		op.addImage();
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("win") != -1) {
			cmd.setSearchPath(properties.getGraphicsPath());
		}
		cmd.run(op, srcPath, newPath);
	}
	
	/**
	 * 给图片加水印
	 * 
	 * @param srcPath
	 *            源图片路径
	 */
	public static void addImgText(String srcPath) throws Exception {
		IMOperation op = new IMOperation();
		op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8")
				.draw("text 100,100 co188.com");
		op.addImage();
		op.addImage();

		String osName = System.getProperty("os.name").toLowerCase();
		ConvertCmd cmd = new ConvertCmd(true);
		if (osName.indexOf("win") != -1) {
			// linux下不要设置此值，不然会报错
			cmd.setSearchPath("C://Program Files//GraphicsMagick-1.3.14-Q16");
		}

		try {
			cmd.run(op, srcPath, srcPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		
	
		
		Long start = System.currentTimeMillis();
		
		thumbnail("e:/1.png", "e:/img/1.jpg", 1920, 1080, 1, "75");

		
		Long end = System.currentTimeMillis();
		System.out.println("time:" + (end - start));
		
	}
	
	
	
	
	
	
	
	
	
	
	
}