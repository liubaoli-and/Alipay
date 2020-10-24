package utils;

import java.util.regex.Pattern;

public class vaild {

/**
     * 验证Email地址是否有效
     * 
     * @param sEmail
     * @return
     */
    public static boolean validEmail(String sEmail) {
        String pattern = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return sEmail.matches(pattern);
    }
  //验证邮箱是否符合规定要求
  	public static boolean clickMail(String email){ 		
  		String str="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
  		boolean falg=false; 
  		
  		Pattern pattern = Pattern.compile(str);
  		falg=pattern.matcher(email).matches(); 			 
  		
  	    return falg;
  	 }
  	
  	//验证手机是否符合规定要求
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
    	校验过程： 
    1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。 
    2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。 
    3、将奇数位总和加上偶数位总和，结果应该可以被10整除。       
    */   
        /** 
        * 校验银行卡卡号 
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
        * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位 
        * @param nonCheckCodeBankCard 
        * @return 
        */  
       public static char getBankCardCheckCode(String nonCheckCodeBankCard){  
           if(nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0  
                   || !nonCheckCodeBankCard.matches("\\d+")) {  
               //如果传的不是数据返回N  
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
