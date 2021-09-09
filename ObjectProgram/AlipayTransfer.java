
package ustb.edu.cn.alipay;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ustb.edu.cn.alipay.User;
import utils.vaild;


/*
 * ת�ˣ�
 * ʵ����֤���û�ת�˸������˻� �������п���ȡ������
 * ʵ����֤���û�ת�˸��Լ��˻� ֻ�����п�ת����ȡ������
 * δʵ����֤���û�ת�ˣ���Ϊδʵ����֤���û���֪����ʵ�����ʹ��ڵĿ��ţ��������ﲻ�ٽ������֣�ȫ����ȡ�����ѣ��Ҳ��������п�ת�˷�ʽ
 */

public class AlipayTransfer
{

    public static  double transferToBandCard( User user, java.lang.String bandCard, java.lang.String name, double transferAmount, int transferMethod, java.lang.String cardID )
    {
    	/*
    	 * *user ת�˷� bandCardת�˿���
    	 *  name ��ת�˷�����
    	 *   transferAmountת�˽��
    	 *    ransferMethod ת�˷�ʽ
    	 * 1���ת��2��ת��3���п�ת�� 
    	 * cardID ת�˷�ת�˵ķ�ʽ���Ϊ3�Ļ�ת�˵Ľ�����Ϊnull
    	 */
        double handlingFee = 0;
     // ת��ʧ�ܵļ��������
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
        
     
    	// ת�˸��Ǳ������û��Ѿ�ʵ����֤

        if (user.getActualName() != name && user.getIsAuthentication()) {
        	
        	// ʹ�����ת��
			if (transferMethod == 1) {
				
				if (transferAmount <= user.getFreeWithdrawalLimit()) {
					// �����Ѷ���ۣ�����ת�˽������,�����Կ���������ת��ʧ��
					
					if (user.getMoneyInBalance() - transferAmount - handlingFee < 0)
						
					return -1;
					
					else {
						
						return handlingFee;
					}
					
				} else {
					
					handlingFee = (transferAmount - user.getFreeWithdrawalLimit()) * 0.02;
					// �����Ѷ���ۣ�����ת�˽������
					
					if (user.getMoneyInBalance() - transferAmount - handlingFee < 0)
						
						return -1;
				
					else {
					
						return handlingFee;
					}
				}
            }
			// ʹ����ת�ˣ���������
			if (transferMethod == 2) {
				
				return handlingFee;
			}
			// ʹ�����п�ת�����˺Ŵ��ڸ����п�
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
        

		// ת�˸�������ʵ����֤����������ת�� ��������
        if (user.getActualName() == name && user.getIsAuthentication()) {
        	
        	// ʹ�����ת�ˣ���������
            if (transferMethod == 1) {
              
            	user.setMonetInBalance( user.getMoneyInBalance() - transferAmount - handlingFee );
                return handlingFee;
           
            }
            
        	// ʹ����ת�ˣ���������
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
        
    	// ת�˸��Ǳ�����δʵ����֤����ȡ�����ѽ��
        if (!user.getIsAuthentication()) {
           
        	// ʹ�����ת��
  
        	if (transferMethod == 1) {
               
        		if (transferAmount <= user.getFreeWithdrawalLimit()) {
                   
        			if (user.getMoneyInBalance() - transferAmount - handlingFee < 0) {
                       
        				return -1;
                   
        			} else {
        				// �����Ѷ���ۣ�����ת�˽������
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
        	
    		// ʹ����ת�ˣ���������
            if (transferMethod == 2) {
                
            	if (transferAmount <= user.getFreeWithdrawalLimit()) {
                   
            		// �����Ѷ���ۣ�����ת�˽������
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
          
        	// ʹ�����п�ת�� δ ʵ����֤�򲻿���
            if (transferMethod == 3) {
               
            	return -1;
            
            }
        }
       
        return handlingFee;

    }

	public static void main(String[] args) throws IOException {
		
		 List<TCBean> tcs=genTC("F:\\Seafile\\˽�����Ͽ�\\MTʵ��\\ʵ��\\alipay.txt");
		 int i=1;
		 for(TCBean tc:tcs){
        System.out.println("key="+i+","+ transferToBandCard( tc.getUser_s(), tc.getBandCard_s(), tc.getName_s(), tc.getTransferAmount_s(), tc.getTransferMethod_f(), tc.getCardID_s()) );
		i++;
		 }
       
	}
	public static List<TCBean> genTC(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = null;
		List<TCBean> list = new ArrayList<TCBean>();
		while ((line = br.readLine()) != null) {
			int start = line.indexOf('(') + 1;
			int end = line.lastIndexOf(')');
			String tcString = line.substring(start, end);
			String[] tcitems = tcString.split(",");
			double m2=Double.parseDouble(tcitems[2]);
			double m4=Double.parseDouble(tcitems[4]);
			double m5=Double.parseDouble(tcitems[5]);
			double m6=Double.parseDouble(tcitems[6]);
			double m7=Double.parseDouble(tcitems[7]);
			// �жϲ��������Ƿ������ɱ��ϵ
			TCBean tc = new TCBean();
			User user = new User("13121623363", "Ll1231234");
			CardBand cb;
			if (tcitems[0].equals("false")) {
				user.setIsAuthentication(false);
				user.setActualName("not");
				tc.setCardID_s("000000");
				cb = new CardBand("not", 0, "000000");
				user.setCb(cb);
			} else {
				user.setIsAuthentication(true);
				user.setActualName("miss");
				cb = new CardBand("miss", m7, "12345678");
				user.setCb(cb);
				tc.setCardID_s("12345678");
			}

			user.setFreeWithdrawalLimit(m4);
			user.setMonetInBalance(m5);
			user.setMonetInYuebao(m6);
			tc.setUser_s(user);
			if (tcitems[1].equals("1")) {
				tc.setBandCard_s("12345678");
				tc.setName_s("miss");
			} else {
				tc.setBandCard_s("1234567812345678");
				tc.setName_s("misss");
			}
			tc.setTransferAmount_s(m2);
			tc.setTransferMethod_s(Integer.parseInt(tcitems[3]));
			list.add(tc);
		}
		br.close();
		return list;
	}
}
