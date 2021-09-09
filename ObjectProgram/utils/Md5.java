package ustb.edu.cn.alipay.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {

	public final static String calc(String str) {

		// 如果str为null则给是赋值为""否则赋值为str

		String s = str == null ? "" : str;

		// 数据字典

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',

				'a', 'b', 'c', 'd', 'e', 'f' };

		// 获取s的二进制

		byte[] strTmp = s.getBytes();

		try {

			// 召唤MD5加密器

			MessageDigest mdTemp = MessageDigest.getInstance("MD5");

			// 执行加密

			mdTemp.update(strTmp);

			// 获取加密结果

			byte[] md = mdTemp.digest();

			// 结果的长度

			int j = md.length;

			// 字符数组

			char str1[] = new char[j * 2];

			int k = 0;

			// 将二进制的加密结果转换为字符

			for (int i = 0; i < j; i++) {

				byte byte0 = md[i];

				str1[k++] = hexDigits[byte0 >>> 4 & 0xf];

				str1[k++] = hexDigits[byte0 & 0xf];

			}

			// 输出加密后的字符

			return new String(str1);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();

			return null;

		}
	}
}
