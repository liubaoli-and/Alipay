package ustb.edu.cn.alipay.utils;

import java.util.regex.Pattern;

public class vaild {

/**
     * ��֤Email��ַ�Ƿ���Ч
     * 
     * @param sEmail
     * @return
     */
    public static boolean validEmail(String sEmail) {
        String pattern = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return sEmail.matches(pattern);
    }
  //��֤�����Ƿ���Ϲ涨Ҫ��
  	public static boolean clickMail(String email){ 		
  		String str="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
  		boolean falg=false; 
  		
  		Pattern pattern = Pattern.compile(str);
  		falg=pattern.matcher(email).matches(); 			 
  		
  	    return falg;
  	 }
  	
  	//��֤�ֻ��Ƿ���Ϲ涨Ҫ��
  	public static boolean clickMobile(String mobile){ 		
  		String str="^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0-9])|(18[0,0-9]))\\d{8}$";
  		boolean falg=false; 
  		
  		Pattern pattern = Pattern.compile(str);
  		falg=pattern.matcher(mobile).matches(); 			 
  		
  	    return falg;
  	}
  	public static boolean clickPassword(String password){ 		
  		String str="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
  		boolean falg=false; 
  		
  		Pattern pattern = Pattern.compile(str);
  		falg=pattern.matcher(password).matches(); 			 
  		
  	    return falg;
  	}
  	 /*
    	У����̣� 
    1���ӿ������һλ���ֿ�ʼ����������λ(1��3��5�ȵ�)��ӡ� 
    2���ӿ������һλ���ֿ�ʼ������ż��λ���֣��ȳ���2������˻�Ϊ��λ��������λʮλ������ӣ��������ȥ9��������͡� 
    3��������λ�ܺͼ���ż��λ�ܺͣ����Ӧ�ÿ��Ա�10������       
    */   
        /** 
        * У�����п����� 
        */  
       public static boolean checkBankCard(String bankCard) {  
          //      if(bankCard.length() < 15 || bankCard.length() > 19) {
         //           return false;
        //        }
        //        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));  
         //       if(bit == 'N'){  
       //             return false;  
       //         }  
       //         return bankCard.charAt(bankCard.length() - 1) == bit;  
    	   return true;
       }  

       /** 
        * �Ӳ���У��λ�����п����Ų��� Luhm У���㷨���У��λ 
        * @param nonCheckCodeBankCard 
        * @return 
        */  
       public static char getBankCardCheckCode(String nonCheckCodeBankCard){  
           if(nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0  
                   || !nonCheckCodeBankCard.matches("\\d+")) {  
               //������Ĳ������ݷ���N  
               return 'N';  
           }  
           char[] chs = nonCheckCodeBankCard.trim().toCharArray();  
           int luhmSum = 0;  
           for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {  
               int k = chs[i] - '0';  
               if(j % 2 == 0) {  
                   k *= 2;  
                   k = k / 10 + k % 10;  
               }  
               luhmSum += k;             
           }  
           return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');  
       }  
  	
    
}
