package ustb.edu.cn.alipay;
//银行卡

public class CardBand {
	
	@Override
	public String toString() {
		
		return "CardBand [name=" + name + ", money=" + money + ", ID=" + ID + "]";
	}

	//持卡人姓名
	
	private String name;
	//卡中资金
	
	private double money;
	//卡号
	
	private String ID;
	
	public CardBand(CardBand cb){
		
		if(cb!=null){
		this.name=cb.getName();
		this.ID=cb.getID();
		this.money=cb.getMoney();}
		else {
			this.name="not";
			this.ID="000000";
			this.money=0;
		}
		
	}
	public CardBand(String name,double money, String iD) {
		
		this.name = name;
		this.money = money;
		ID = iD;
	
	}
	public CardBand(){}
	
	public void setName(String name) {
		
		this.name = name;
	}

	public void setID(String iD) {
		
		ID = iD;
	}

	public String getName() {
		
		return name;
	}
	
	public double getMoney() {
		
		return money;
	}
	
	public void setMoney(double money) {
		
		this.money = money;
	}
	
	public String getID() {
		
		return ID;
	}

	public double getTransferRate() {
		// TODO Auto-generated method stub
		
		return 0.02;
	}
}
