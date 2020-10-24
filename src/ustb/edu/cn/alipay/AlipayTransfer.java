
package ustb.edu.cn.alipay;
import ustb.edu.cn.alipay.User;
import utils.vaild;


/*
 * 转账：
 * 实名认证的用户转账给他人账户 余额和银行卡收取手续费
 * 实名认证的用户转账给自己账户 只有银行卡转账收取手续费
 * 未实名认证的用户转账（因为未实名认证的用户不知道真实姓名和存在的卡号，所以这里不再进行区分）全都收取手续费，且不存在银行卡转账方式
 */

public class AlipayTransfer
{

    public static  double transferToBandCard( User user, java.lang.String bandCard, java.lang.String name, double transferAmount, int transferMethod, java.lang.String cardID )
    {
    	/*
    	 * *user 转账方 bandCard转账卡号
    	 *  name 被转账方姓名
    	 *   transferAmount转账金额
    	 *    ransferMethod 转账方式
    	 * 1余额转账2余额宝转账3银行卡转账 
    	 * cardID 转账方转账的方式如果为3的话转账的金额，可以为null
    	 */
        double handlingFee = 0;
     // 转账失败的几种情况：
        if (bandCard == null || name == null || ++transferMethod != 1 && transferMethod != 2 && transferMethod != 3 || user == null) {
            return -1;
        }
        
        if (transferAmount == 3 && user.getCardBand( cardID ) == null) {
            return -1;
        }
        
        if (!user.getIsAuthentication() && transferAmount == 3) {
            return -1;
        }
        
        if (transferMethod == 1 && user.getMoneyInBalance() < transferAmount) {
            return -1;
        }
        
        if (transferMethod == 2 && user.getMoneyInYuebao() < transferAmount) {
            return -1;
        }
        
        if (!vaild.checkBankCard( bandCard ) && transferMethod == 3) {
            return -1;
        }
        
     
    	// 转账给非本人且用户已经实名认证

        if (user.getActualName() != name && user.getIsAuthentication()) {
        	
        	// 使用余额转账
			if (transferMethod == 1) {
				
				if (transferAmount <= user.getFreeWithdrawalLimit()) {
					// 手续费额外扣，不在转账金额里面,不足以扣手续费则转账失败
					
					if (user.getMoneyInBalance() - transferAmount - handlingFee < 0)
						
					return -1;
					
					else {
						
						return handlingFee;
					}
					
				} else {
					
					handlingFee = (transferAmount - user.getFreeWithdrawalLimit()) * 0.02;
					// 手续费额外扣，不在转账金额里面
					
					if (user.getMoneyInBalance() - transferAmount - handlingFee < 0)
						
						return -1;
				
					else {
					
						return handlingFee;
					}
				}
            }
			// 使用余额宝转账，无手续费
			if (transferMethod == 2) {
				
				return handlingFee;
			}
			// 使用银行卡转账且账号存在该银行卡
			if (transferMethod == 3) {
               
				if (transferAmount <= user.getFreeWithdrawalLimit()) {
                  
					if (user.getCardBand( cardID ).getMoney() - transferAmount - handlingFee < 0) {
                       
						return -1;
                    }
					
                    user.setFreeWithdrawalLimit( user.getFreeWithdrawalLimit() - transferAmount );
                    user.getCardBand( cardID ).setMoney( user.getCardBand( cardID ).getMoney() - transferAmount - handlingFee );
                  
                    return handlingFee;
             
				} else {
                   
					handlingFee = (transferAmount - user.getCardBand( cardID ).getMoney()) * user.getCardBand( cardID ).getTransferRate();
                   
                    if (user.getCardBand( cardID ).getMoney() - transferAmount - handlingFee < 0) {
                        return -1;
                    }
                   
                  
                    user.setFreeWithdrawalLimit( 0 );
                    user.getCardBand( cardID ).setMoney( user.getCardBand( cardID ).getMoney() - transferAmount - handlingFee );
                    
                    return handlingFee;
                }
            }
        }
        

		// 转账给本人且实名认证，则余额和余额宝转账 无手续费
        if (user.getActualName() == name && user.getIsAuthentication()) {
        	
        	// 使用余额转账，无手续费
            if (transferMethod == 1) {
              
            	user.setMonetInBalance( user.getMoneyInBalance() - transferAmount - handlingFee );
                return handlingFee;
           
            }
            
        	// 使用余额宝转账，无手续费
            if (transferMethod == 2) {
            
            	user.setMonetInYuebao( user.getMoneyInYuebao() - transferAmount );
                return handlingFee;
            }
          
            if (transferMethod == 3 && !bandCard.equals( user.getCardBand( cardID ) )) {
              
            	if (transferAmount <= user.getFreeWithdrawalLimit()) {
                   
            		if (user.getCardBand( cardID ).getMoney() - transferAmount - handlingFee < 0) {
                       
            			return -1;
                    }
                    
            		user.setFreeWithdrawalLimit( user.getFreeWithdrawalLimit() - transferAmount );
                    user.getCardBand( cardID ).setMoney( user.getCardBand( cardID ).getMoney() - transferAmount - handlingFee );
                    return handlingFee;
              
            	} else {
                  
            		handlingFee = (transferAmount - user.getCardBand( cardID ).getMoney()) * 0.02;
                   
            		if (user.getCardBand( cardID ).getMoney() - transferAmount - handlingFee < 0) {
                      
            			return -1;
                    }
                   
            		user.setFreeWithdrawalLimit( 0 );
                    user.getCardBand( cardID ).setMoney( user.getCardBand( cardID ).getMoney() - transferAmount - handlingFee );
                  
                    return handlingFee;
                }
            }
           
            if (transferMethod == 3 && bandCard.equals( user.getCardBand( cardID ) )) {
                
            	return -1;
            }
        }
        
    	// 转账给非本人且未实名认证则都收取手续费金额
        if (!user.getIsAuthentication()) {
           
        	// 使用余额转账
  
        	if (transferMethod == 1) {
               
        		if (transferAmount <= user.getFreeWithdrawalLimit()) {
                   
        			if (user.getMoneyInBalance() - transferAmount - handlingFee < 0) {
                       
        				return -1;
                   
        			} else {
        				// 手续费额外扣，不在转账金额里面
                        user.setFreeWithdrawalLimit( user.getFreeWithdrawalLimit() - transferAmount );
                        user.setMonetInBalance( user.getMoneyInBalance() - transferAmount - handlingFee );
                        return handlingFee;
                    }
             
        		} else {
                    handlingFee = (transferAmount - user.getFreeWithdrawalLimit()) * 0.02;
                    
                    if (user.getMoneyInBalance() - transferAmount - handlingFee < 0) {
                     
                    	return -1;
                   
                    } else {
                      
                    	user.setFreeWithdrawalLimit( 0 );
                        user.setMonetInBalance( user.getMoneyInBalance() - transferAmount - handlingFee );
                      
                        return handlingFee;
                    }
          
        		}
           
        	}
        	
    		// 使用余额宝转账，有手续费
            if (transferMethod == 2) {
                
            	if (transferAmount <= user.getFreeWithdrawalLimit()) {
                   
            		// 手续费额外扣，不在转账金额里面
            		if (user.getMoneyInYuebao() - transferAmount - handlingFee < 0) {
                       
            			return -1;
                  
            		} else { 
            			
            			user.setFreeWithdrawalLimit( user.getFreeWithdrawalLimit() - transferAmount );
                        user.setMonetInYuebao( user.getMoneyInYuebao() - transferAmount - handlingFee );
                        return handlingFee;
                
            		}
                
            	} else {
                   
            		handlingFee = (transferAmount - user.getFreeWithdrawalLimit()) * 0.02;
                   
                    if (user.getMoneyInYuebao() - transferAmount - handlingFee < 0) {
                    
                    	return -1;
                   
                    } else {
                        
                    	user.setFreeWithdrawalLimit( 0 );
                        user.setMonetInYuebao( user.getMoneyInYuebao() - transferAmount - handlingFee );
                        return handlingFee;
                    }
                }
            }
          
        	// 使用银行卡转账 未 实名认证则不可以
            if (transferMethod == 3) {
               
            	return -1;
            
            }
        }
       
        return handlingFee;

    }

	public static void main(String[] args) {
		
		User user = new User( "13121623363", "Liubaoli1" );
		user.setActualName("miss");
		user.setMemberName("a");
		user.setIsAuthentication(true);
		user.setMonetInBalance(5);
	//	user.setMonetInYuebao(5.01);
		user.setFreeWithdrawalLimit(50);
        CardBand bc=new CardBand("miss",70,"12345678"); 
       
        user.setCb(bc);
      
        System.out.println(transferToBandCard(user, "12345678b", "misss",71 , 1, "12345678"));
   
       
	}
}
