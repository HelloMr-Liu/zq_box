package zq.bim.util;


import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  功能：默认应用工具类信息
 *  作者：刘梓江
 *  时间：2020/9/7 11:43
 */
public class ApplicationUtil {

	/**
	 * 用于加密前缀
	 */
	private static final String prefix="box";




	/**
	 * 获取一个Long类型长度20的id
	 * @return
	 */
	public static Long getID(){
		long id = IdUtils.nextId();
		return id;
	}

	/**
	 * 获取一个uuid
	 * @return
	 */
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		return str;
	}

	/**
	 * 对明文进行加密
	 * @param clearWriting
	 * @return
	 */
	public static String encryption(String clearWriting){
		//对明文前缀防止破解一眼看出是用户输入的密码
		String currentClearWriting=prefix+clearWriting;
		String firstEncryption= DigestUtils.md5DigestAsHex(currentClearWriting.getBytes());
		//对前缀明文加密
		String prefixEncryption= DigestUtils.md5DigestAsHex(prefix.getBytes());
		//对加密后的密码二次加密
		String secondaryEncryption = DigestUtils.md5DigestAsHex((firstEncryption+prefixEncryption).getBytes());

		return secondaryEncryption;
	}

	/**
	 * 生成随机用户名，数字和字母组成,
	 * @param length  名字长度
	 * @return
	 */
	public static String getStringRandom(int length) {
		String val = "";
		Random random = new Random();

		//参数length，表示生成几位随机数
		for(int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字
			if( "char".equalsIgnoreCase(charOrNum) ) {
				//输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char)(random.nextInt(26) + temp);
			} else if( "num".equalsIgnoreCase(charOrNum) ) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}


	/**
	 * 判断字符串是否是纯数值类型
	 * @param str
	 * @return
	 */
	public static  boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}

	/**
	 * 获取某年某月有多少天
	 * @param year	某年
	 * @param month 某月
	 * @return
	 */
	public static int getDayOfMonth(int year,int month){
		Calendar c = Calendar.getInstance();
		c.set(year, month, 0); //输入类型为int类型
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 对字符串时间转换成Date对象
	 * @param dateTime
	 * @return
	 */
	public static Date formatToDate(String dateTime) throws Exception {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date parse = format.parse(dateTime);
		return parse;
	}

	/**
	 * 将时间对象转换成时间
	 * @param date
	 * @return
	 */
	public static String dateToFormat(Date date) throws Exception {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	
	
	/**
	 * 获取UUID返回32位字符窜
	 * @return
	 */
	public static String randomUUID() {
		String uuid = UUID.randomUUID().toString();
		String newuuid = uuid.substring(0, 8) + uuid.substring(9, 13)
				+ uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);

		return newuuid;
	}


}
