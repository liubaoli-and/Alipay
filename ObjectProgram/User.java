package ustb.edu.cn.alipay;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import utils.IdentificationUtil;
import utils.Md5;
import utils.vaild;

public class User {
	private String account;// 账号，手机号或者邮箱，格式限制
	private String memberName;// 会员名
	private Boolean isAuthentication;// 是否实名认证，若没有实名认证，则真实姓名、银行卡都为空
	private String actualName = null;// 用户真实姓名，没有实名认证则为null
	private String password;// 密码 至少8个字符，至少1个大写字母，1个小写字母和1个数字,不能包含特殊字符（非数字字母）：
	// private Map<String, CardBand> cards = new HashMap();// 绑定的银行卡
	private CardBand cb ; // 若没有实名认证则一定不能绑定银行卡;

	private double freeWithdrawalLimit;// 免费转账额度，可以实名获得免费基础额度，也可以使用积分兑换
	private double moneyInBalance; // 余额中的资产
	private double moneyInYuebao;// 余额宝中的资产
	// 创建一个未实名认证的初始账号

	public User(String account, String password) {
		// 判断账号是否是邮箱或者手机号，密码是否符合规则
		// if ((vaild.clickMail(account) || vaild.clickMobile(account)) &&
		// vaild.clickPassword(password)) {
		this.account = account;
		this.password = Md5.calc(password);
		this.freeWithdrawalLimit = 0.0;
		this.actualName = null;
		this.cb = null;
		this.moneyInBalance = 0.0;
		this.moneyInYuebao = 0.0;
		this.isAuthentication = false;
		this.memberName = "a";
		// } else
	
		// System.out.println("用户账号格式不对或者密码不对");
	}

	public User(User u) {
		
		this.setAccount(u.getAccount());
		this.setPassword(u.getPassword());
		this.freeWithdrawalLimit = u.getFreeWithdrawalLimit();
		this.actualName = u.getActualName();
		this.setCb(u.getCb());
		this.moneyInBalance = u.getMoneyInBalance();
		this.moneyInYuebao = u.getMoneyInYuebao();
		this.memberName = u.memberName;
		this.isAuthentication = u.getIsAuthentication();
	}
	
	@Override
	public String toString() {
		
		return "User [ isAuthentication=" + isAuthentication
				 + ", freeWithdrawalLimit="
				+ freeWithdrawalLimit + ", moneyInBalance=" + moneyInBalance + ", moneyInYuebao=" + moneyInYuebao + "]";
	}

	public String getAccount() {
		
		return account;
	}

	public void setAccount(String account) {
		
		this.account = account;
	}

	public String getPassword() {
		
		return password;
	}

	public void setPassword(String password) {
		
		this.password = password;
	}

	public CardBand getCb() {
		
		return cb;
	}

	public void setCb(CardBand b) {
		
		this.cb= new CardBand(b);
		//if (b .getMoney()!=Double.NaN&&b.getID()!=null&&b.getName()!=null&&b!=null) {
		//	this.cb.setMoney(b.getMoney());
		//	this.cb.setName(b.getName());
		//	this.cb.setID(b.getID());
	}

	public void setIsAuthentication(Boolean isAuthentication) {
		
		this.isAuthentication = isAuthentication;
	}

	// 创建一个实名认证的初始账号
	public User(String account, String memberName, String id, String actualName, String password) {
		if ((vaild.clickMail(account) || vaild.clickMobile(account)) && vaild.clickPassword(password)
				&& IdentificationUtil.verify(id)) {

			this.account = account;
			this.memberName = memberName;
			this.isAuthentication = true;
			this.actualName = actualName;
			this.password = Md5.calc(password);
			// 初始免费额度2w
			this.freeWithdrawalLimit = 20000.00;
			this.moneyInBalance = 0.0;
			this.moneyInYuebao = 0.0;
			System.out.println("创建实名用户");
		} else if ((vaild.clickMail(account) || vaild.clickMobile(account)) && vaild.clickPassword(password)) {
			this.account = account;
			this.password = Md5.calc(password);
			this.freeWithdrawalLimit = 0.00;
			this.moneyInBalance = 0.0;
			this.moneyInYuebao = 0.0;
			this.isAuthentication = false;
			this.actualName = null;
			System.out.println("创建未实名用户");
		} else
			System.out.println("用户账号格式不对或者密码不对");
	
	}

	// 实名认证：
	public boolean Verified(String actualName, String id) {
		
		if (this.isAuthentication) {
			return true;
		}
	
		if (IdentificationUtil.verify(id)) {
			this.isAuthentication = IdentificationUtil.verify(id);// 这个地方需要连接数据库进行检测，但是没有权限
			this.actualName = actualName;
			this.freeWithdrawalLimit = 20000.00;
			this.isAuthentication = true;
		}
		
		return isAuthentication;
	}

	public String getMemberName() {
	
		return memberName;
	}

	public void setMemberName(String memberName) {
	
		this.memberName = memberName;
	}

	// public Map<String, CardBand> getCards() {
	// return cards;
	// }

	// public void setCards(Map<String, CardBand> cards) {
	// this.cards = cards;
	// }

	public void setActualName(String actualName) {
		
		this.actualName = actualName;
	}

	public void setMoneyInBalance(double moneyInBalance) {
		
		this.moneyInBalance = moneyInBalance;
	}

	public boolean setPassword(String oldPassword, String newPassword) {
		
		if (this.password == Md5.calc(oldPassword)) {// 这里最好使用各加密
			this.password = Md5.calc(newPassword);
			return true;
		} else
			return false;

	}

	public String getActualName() {
		
		return actualName;
	}

	public Boolean getIsAuthentication() {
		
		return isAuthentication;
	}

	public double getFreeWithdrawalLimit() {
		
		return freeWithdrawalLimit;
	}

	public void setFreeWithdrawalLimit(double freeWithdrawalLimit) {
		
		if (freeWithdrawalLimit < 0)
			freeWithdrawalLimit = 0;
		
		this.freeWithdrawalLimit = freeWithdrawalLimit;
	}

	public double getMoneyInBalance() {
		
		return moneyInBalance;
	}

	public void setMonetInBalance(double money) {
		
		this.moneyInBalance = money;
	}

	public double getMoneyInYuebao() {
		
		return moneyInYuebao;
	}

	public void setMoneyInYuebao(double money) {
		
		this.moneyInYuebao = money;
	}

	
	// 添加银行卡，如果没有实名认证，实名认证后添加银行卡
//	 public void addCardBand(CardBand cb, String id, String actualName, String
	// identification) {
	// 
	//if (!vaild.checkBankCard(cb.getID())) {
	//
	//System.out.print("银行卡输入格式错误");
	// 
	//} else if (this.isAuthentication) {
	//// 
	//	cards.put(id, cb);
	/// 
		//} else if (Verified(actualName, identification)) {
	// cards.put(id, cb);
	// 
		//		} else
	// System.out.print("请 进行 实名认证");
	// }

	public CardBand getCardBand(String str) {
		
		return cb;
	}

	public void setMonetInYuebao(double d) {
		// TODO Auto-generated method stub
		
		this.moneyInYuebao = d;
	}
	public static void main(String[] args){
		User u=new User("13121623363","lLiu12345");
		CardBand cb=new CardBand("liubao",20,"12345678");
		u.setIsAuthentication(true);
		u.setCb(cb);
	}

}
