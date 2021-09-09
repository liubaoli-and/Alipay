package ustb.edu.cn.alipay.utils;

import java.util.HashMap;

/**
 * Created by liqun.chen on 2017/3/15.
 */
public class IdentificationUtil {

    private static String _codeError;

    //wi =2(n-1)(mod 11)
    final static int[] wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
    // verify digit
    final static int[] vi = {1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};
    private static int[] ai = new int[18];
    private static String[] _areaCode = {"11", "12", "13", "14", "15", "21", "22"
            , "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44"
            , "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91"};
    private static HashMap<String, Integer> dateMap;
    private static HashMap<String, String> areaCodeMap;

    static {
        dateMap = new HashMap<String, Integer>();
        dateMap.put("01", 31);
        dateMap.put("02", null);
        dateMap.put("03", 31);
        dateMap.put("04", 30);
        dateMap.put("05", 31);
        dateMap.put("06", 30);
        dateMap.put("07", 31);
        dateMap.put("08", 31);
        dateMap.put("09", 30);
        dateMap.put("10", 31);
        dateMap.put("11", 30);
        dateMap.put("12", 31);
        areaCodeMap = new HashMap<String, String>();
        for (String code : _areaCode) {
            areaCodeMap.put(code, null);
        }
    }

    //��֤���֤λ��,15λ��18λ���֤
    public static boolean verifyLength(String code) {
        int length = code.length();
        if (length == 15 || length == 18) {
            return true;
        } else {
            _codeError = "������������֤�Ų���15λ��18λ��";
            return false;
        }
    }

    //�жϵ�����
    public static boolean verifyAreaCode(String code) {
        String areaCode = code.substring(0, 2);
//            Element child=  _areaCodeElement.getChild("_"+areaCode);
        if (areaCodeMap.containsKey(areaCode)) {
            return true;
        } else {
            _codeError = "������������֤�ŵĵ�����(1-2λ)[" + areaCode + "]�������й����������ִ���涨(GB/T2260-1999)";
            return false;
        }
    }

    //�ж��·ݺ�����
    public static boolean verifyBirthdayCode(String code) {
        //��֤�·�
        String month = code.substring(10, 12);
        boolean isEighteenCode = (18 == code.length());
        if (!dateMap.containsKey(month)) {
            _codeError = "������������֤��" + (isEighteenCode ? "(11-12λ)" : "(9-10λ)") + "������[" + month + "]�·�,������Ҫ��(GB/T7408)";
            return false;
        }
        //��֤����
        String dayCode = code.substring(12, 14);
        Integer day = dateMap.get(month);
        String yearCode = code.substring(6, 10);
        Integer year = Integer.valueOf(yearCode);

        //��2�µ����
        if (day != null) {
            if (Integer.valueOf(dayCode) > day || Integer.valueOf(dayCode) < 1) {
                _codeError = "������������֤��" + (isEighteenCode ? "(13-14λ)" : "(11-13λ)") + "[" + dayCode + "]�Ų�����С��1-30�����1-31��Ĺ涨(GB/T7408)";
                return false;
            }
        }
        //2�µ����
        else {
            //���µ����
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                if (Integer.valueOf(dayCode) > 29 || Integer.valueOf(dayCode) < 1) {
                    _codeError = "������������֤��" + (isEighteenCode ? "(13-14λ)" : "(11-13λ)") + "[" + dayCode + "]����" + year + "����������δ����1-29�ŵĹ涨(GB/T7408)";
                    return false;
                }
            }
            //�����µ����
            else {
                if (Integer.valueOf(dayCode) > 28 || Integer.valueOf(dayCode) < 1) {
                    _codeError = "������������֤��" + (isEighteenCode ? "(13-14λ)" : "(11-13λ)") + "[" + dayCode + "]����" + year + "ƽ��������δ����1-28�ŵĹ涨(GB/T7408)";
                    return false;
                }
            }
        }
        return true;
    }

    //��֤��ݳ������λ�������Ƿ������ĸ
    public static boolean containsAllNumber(String code) {
        String str = "";
        if (code.length() == 15) {
            str = code.substring(0, 15);
        } else if (code.length() == 18) {
            str = code.substring(0, 17);
        }
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (!(ch[i] >= '0' && ch[i] <= '9')) {
                _codeError = "������������֤�ŵ�" + (i + 1) + "λ������ĸ";
                return false;
            }
        }
        return true;
    }
    public String getCodeError() {
        return _codeError;
    }
    //��֤18λУ����,У�������ISO 7064��1983��MOD 11-2 У����ϵͳ
    public static boolean verifyMOD(String code) {
        String verify = code.substring(17, 18);
        if ("x".equals(verify)) {
            code = code.replaceAll("x", "X");
            verify = "X";
        }
        String verifyIndex = getVerify(code);
        if (verify.equals(verifyIndex)) {
            return true;
        }
//            int x=17;
//            if(code.length()==15){
//                  x=14;
//            }
        _codeError = "������������֤����ĩβ��������֤�����";
        return false;
    }

    //���У��λ
    public static String getVerify(String eightcardid) {
        int remaining = 0;

        if (eightcardid.length() == 18) {
            eightcardid = eightcardid.substring(0, 17);
        }
        if (eightcardid.length() == 17) {
            int sum = 0;
            for (int i = 0; i < 17; i++) {
                String k = eightcardid.substring(i, i + 1);
                ai[i] = Integer.parseInt(k);
            }
            for (int i = 0; i < 17; i++) {
                sum = sum + wi[i] * ai[i];
            }
            remaining = sum % 11;
        }
        return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
    }
    //15λת18λ���֤
    public static String uptoeighteen(String fifteencardid) {
        String eightcardid = fifteencardid.substring(0, 6);
        eightcardid = eightcardid + "19";
        eightcardid = eightcardid + fifteencardid.substring(6, 15);
        eightcardid = eightcardid + getVerify(eightcardid);
        return eightcardid;
    }

    //��֤���֤
    public static boolean verify(String idcard) {
        _codeError = "";
        //��֤���֤λ��,15λ��18λ���֤
        if (!verifyLength(idcard)) {
            return false;
        }
        //��֤��ݳ������λ�������Ƿ������ĸ
        if (!containsAllNumber(idcard)) {
            return false;
        }
        //�����15λ�ľ�ת��18λ�����֤
        String eifhteencard = "";
        if (idcard.length() == 15) {
            eifhteencard = uptoeighteen(idcard);
        } else {
            eifhteencard = idcard;
        }
        //��֤���֤�ĵ�����
        if (!verifyAreaCode(eifhteencard)) {
            return false;
        }
        //�ж��·ݺ�����
        if (!verifyBirthdayCode(eifhteencard)) {
            return false;
        }
        //��֤18λУ����,У�������ISO 7064��1983��MOD 11-2 У����ϵͳ
        if (!verifyMOD(eifhteencard)) {
            return false;
        }
        return true;
    }
}