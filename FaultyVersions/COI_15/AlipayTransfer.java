// This is a mutant program.
// Author : ysma

package ustb.edu.cn.alipay.mutant.COI_15;


import ustb.edu.cn.alipay.CardBand;
import ustb.edu.cn.alipay.User;
import utils.vaild;


public class AlipayTransfer
{

    public static  double transferToBandCard( ustb.edu.cn.alipay.User user, java.lang.String bandCard, java.lang.String name, double transferAmount, int transferMethod, java.lang.String cardID )
    {
        double handlingFee = 0;
        if (bandCard == null || name == null || transferMethod != 1 && transferMethod != 2 && transferMethod != 3 || user == null) {
            return -1;
        }
        if (!user.getActualName().equals( name ) && user.getIsAuthentication()) {
            if (transferMethod == 1) {
                if (transferAmount <= user.getFreeWithdrawalLimit()) {
                    if (user.getMoneyInBalance() - transferAmount - handlingFee < 0) {
                        return -1;
                    } else {
                        return handlingFee;
                    }
                } else {
                    handlingFee = (transferAmount - user.getFreeWithdrawalLimit()) * 0.02;
                    if (!(user.getMoneyInBalance() - transferAmount - handlingFee < 0)) {
                        return -1;
                    } else {
                        return handlingFee;
                    }
                }
            }
            if (transferMethod == 2) {
                return handlingFee;
            }
            if (transferMethod == 3) {
                if (transferAmount <= user.getFreeWithdrawalLimit()) {
                    if (user.getCardBand( cardID ).getMoney() - transferAmount - handlingFee < 0) {
                        return -1;
                    }
                    return handlingFee;
                } else {
                    handlingFee = (transferAmount - user.getCardBand( cardID ).getMoney()) * 0.02;
                    if (user.getCardBand( cardID ).getMoney() - transferAmount - handlingFee < 0) {
                        return -1;
                    }
                    return handlingFee;
                }
            }
        }
        if (user.getActualName().equals( name ) && user.getIsAuthentication()) {
            if (transferMethod == 1) {
                return handlingFee;
            }
            if (transferMethod == 2) {
                return handlingFee;
            }
            if (transferMethod == 3) {
                return -1;
            }
        }
        if (!user.getIsAuthentication()) {
            if (transferMethod == 1) {
                handlingFee = (transferAmount - user.getFreeWithdrawalLimit()) * 0.02;
                if (user.getMoneyInBalance() - transferAmount - handlingFee < 0) {
                    return -1;
                } else {
                    return handlingFee;
                }
            }
            if (transferMethod == 2) {
                handlingFee = (transferAmount - user.getFreeWithdrawalLimit()) * 0.02;
                if (user.getMoneyInYuebao() - transferAmount - handlingFee < 0) {
                    return -1;
                } else {
                    return handlingFee;
                }
            }
            if (transferMethod == 3) {
                return -1;
            }
        }
        return handlingFee;
    }

    public static  void main( java.lang.String[] args )
    {
        ustb.edu.cn.alipay.User user = new ustb.edu.cn.alipay.User( "13121623363", "Liubaoli1" );
        user.setActualName( "miss" );
        user.setMemberName( "a" );
        user.setIsAuthentication( true );
        ustb.edu.cn.alipay.CardBand bc = new ustb.edu.cn.alipay.CardBand( "miss", 10, "12345678" );
        user.setCb( bc );
        System.out.println( transferToBandCard( user, "12345678", "miss", 3, 3, "12345678" ) );
    }

}
