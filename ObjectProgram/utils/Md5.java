package ustb.edu.cn.alipay.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {

	public final static String calc(String str) {

		// ���strΪnull����Ǹ�ֵΪ""����ֵΪstr

		String s = str == null ? "" : str;

		// �����ֵ�

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',

				'a', 'b', 'c', 'd', 'e', 'f' };

		// ��ȡs�Ķ�����

		byte[] strTmp = s.getBytes();

		try {

			// �ٻ�MD5������

			MessageDigest mdTemp = MessageDigest.getInstance("MD5");

			// ִ�м���

			mdTemp.update(strTmp);

			// ��ȡ���ܽ��

			byte[] md = mdTemp.digest();

			// ����ĳ���

			int j = md.length;

			// �ַ�����

			char str1[] = new char[j * 2];

			int k = 0;

			// �������Ƶļ��ܽ��ת��Ϊ�ַ�

			for (int i = 0; i < j; i++) {

				byte byte0 = md[i];

				str1[k++] = hexDigits[byte0 >>> 4 & 0xf];

				str1[k++] = hexDigits[byte0 & 0xf];

			}

			// ������ܺ���ַ�

			return new String(str1);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();

			return null;

		}
	}
}
