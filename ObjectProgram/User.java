package ustb.edu.cn.alipay;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import utils.IdentificationUtil;
import utils.Md5;
import utils.vaild;

public class User {
	private String account;// �˺ţ��ֻ��Ż������䣬��ʽ����
	private String memberName;// ��Ա��
	private Boolean isAuthentication;// �Ƿ�ʵ����֤����û��ʵ����֤������ʵ���������п���Ϊ��
	private String actualName = null;// �û���ʵ������û��ʵ����֤��Ϊnull
	private String password;// ���� ����8���ַ�������1����д��ĸ��1��Сд��ĸ��1������,���ܰ��������ַ�����������ĸ����
	// private Map<String, CardBand> cards = new HashMap();// �󶨵����п�
	private CardBand cb ; // ��û��ʵ����֤��һ�����ܰ����п�;

	private double freeWithdrawalLimit;// ���ת�˶�ȣ�����ʵ�������ѻ�����ȣ�Ҳ����ʹ�û��ֶһ�
	private double moneyInBalance; // ����е��ʲ�
	private double moneyInYuebao;// ���е��ʲ�
	// ����һ��δʵ����֤�ĳ�ʼ�˺�

	public User(String account, String password) {
		// �ж��˺��Ƿ�����������ֻ��ţ������Ƿ���Ϲ���
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
	
		// System.out.println("�û��˺Ÿ�ʽ���Ի������벻��");
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

	// ����һ��ʵ����֤�ĳ�ʼ�˺�
	public User(String account, String memberName, String id, String actualName, String password) {
		if ((vaild.clickMail(account) || vaild.clickMobile(account)) && vaild.clickPassword(password)
				&& IdentificationUtil.verify(id)) {

			this.account = account;
			this.memberName = memberName;
			this.isAuthentication = true;
			this.actualName = actualName;
			this.password = Md5.calc(password);
			// ��ʼ��Ѷ��2w
			this.freeWithdrawalLimit = 20000.00;
			this.moneyInBalance = 0.0;
			this.moneyInYuebao = 0.0;
			System.out.println("����ʵ���û�");
		} else if ((vaild.clickMail(account) || vaild.clickMobile(account)) && vaild.clickPassword(password)) {
			this.account = account;
			this.password = Md5.calc(password);
			this.freeWithdrawalLimit = 0.00;
			this.moneyInBalance = 0.0;
			this.moneyInYuebao = 0.0;
			this.isAuthentication = false;
			this.actualName = null;
			System.out.println("����δʵ���û�");
		} else
			System.out.println("�û��˺Ÿ�ʽ���Ի������벻��");
	
	}

	// ʵ����֤��
	public boolean Verified(String actualName, String id) {
		
		if (this.isAuthentication) {
			return true;
		}
	
		if (IdentificationUtil.verify(id)) {
			this.isAuthentication = IdentificationUtil.verify(id);// ����ط���Ҫ�������ݿ���м�⣬����û��Ȩ��
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
		
		if (this.password == Md5.calc(oldPassword)) {// �������ʹ�ø�����
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

	
	// ������п������û��ʵ����֤��ʵ����֤��������п�
//	 public void addCardBand(CardBand cb, String id, String actualName, String
	// identification) {
	// 
	//if (!vaild.checkBankCard(cb.getID())) {
	//
	//System.out.print("���п������ʽ����");
	// 
	//} else if (this.isAuthentication) {
	//// 
	//	cards.put(id, cb);
	/// 
		//} else if (Verified(actualName, identification)) {
	// cards.put(id, cb);
	// 
		//		} else
	// System.out.print("�� ���� ʵ����֤");
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
