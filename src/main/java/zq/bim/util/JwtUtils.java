package zq.bim.util;

import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {
	private static final String SECRET = "session_secret";

	private static final String ISSUER = "qyj";

	public static void main(String[] args) {
		String token = createToken(null);
		System.out.println(token);
		Map<String, String> verifyToken = verifyToken(token);
		System.out.println(verifyToken);
	}

	// 获取token的方法
	public static String createToken(Map<String, String> map) {
		try {
			// 使用该加密算法
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			
			JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER); // 设置发布者
			if(map != null) {
				map.forEach((k, v) -> builder.withClaim(k, v));// 设置参数
			}
			
			return builder.sign(algorithm).toString(); // 使用上面的加密算法进行签名，返回String，就是token
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
	}

	// 验证token方法
	public static Map<String, String> verifyToken(String token) {
		Algorithm algorithm = null;
		try {
			algorithm = Algorithm.HMAC256(SECRET);
		} catch (IllegalArgumentException e) {
			return null;
		}
		JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
		DecodedJWT jwt = verifier.verify(token);
		Map<String, Claim> map = jwt.getClaims();
		Map<String, String> resultMap = new HashMap<String, String>();
		map.forEach((k, v) -> resultMap.put(k, v.asString()));
		return resultMap;
	}
}
