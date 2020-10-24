package ustb.edu.cn.alipay;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ustb.edu.cn.alipay.CardBand;
import ustb.edu.cn.alipay.User;

public class Utils {
	// 对测试用例进行随机排序
		public static List<TCBean> randomPiority(List<TCBean> list) throws IOException {
			List<TCBean> result = new ArrayList<TCBean>();// 返回排序好的测试用例
			int a[] = new int[25];
			for (int i = 0; i < 25; i++) {
				a[i] = i;
			}
			for (int i = 0; i <= 100; i++) {
				int middle = (int) ((a.length -1)/ 2 * Math.random());
				int radius = (int) (middle * Math.random());
				int t;
				t = a[middle - radius + 1];
				a[middle - radius + 1] = a[middle + radius + 3];
				a[middle + radius + 3] = t;
			}
			// 按优先级顺序添加测试用例到result
			for (int i = 0; i < a.length; i++) {
				result.add(list.get(a[i]));
			}
		//	System.out.println(Arrays.toString(a));
			return result;
		}

	// 对测试用例集进行优先级排序
	public static List<TCBean> piority(List<TCBean> list, String path) throws IOException {
		List<TCBean> result = new ArrayList<TCBean>();// 返回排序好的测试用例
		List<ArrayList<String>> paths = getPaths(path);// 得到所有路径的set表示形式
		int[] order = new int[list.size()];// 每个测试用例的优先顺序
		int orderIndex = 0;
		Random r = new Random();// 用于最大差集路径中随机生成一条路径
		Set<String> set = new HashSet<String>();
		while (paths.size() > 0) {
			List<Integer> indexs = new ArrayList<Integer>();// 记录差集最大路径的下标
			int max = 0;// 记录最大差集的值
			for (int i = 0; i < paths.size(); i++) {
				ArrayList<String> al = paths.get(i);
				ArrayList<String> temp = new ArrayList<String>();
				temp.addAll(al);
				temp.removeAll(set);
				if (temp.size() > max) {
					indexs.clear();
					max = temp.size();
					indexs.add(i);
				} else if (temp.size() == max) {
					indexs.add(i);
				}
			}
			int index = indexs.get(r.nextInt(indexs.size()));
			ArrayList<String> maxTC = paths.remove(index);
			set.addAll(maxTC);
			order[orderIndex++] = Integer.parseInt(maxTC.get(0));
		}

		// 按优先级顺序添加测试用例到result
		for (int i = 0; i < order.length; i++) {
			result.add(list.get(order[i]));
		}
		//System.out.println(Arrays.toString(order));
		return result;
	}

	// 从路径集文件中读取把路径转成list
	public static List<ArrayList<String>> getPaths(String path) throws IOException {
		List<ArrayList<String>> paths = new ArrayList<ArrayList<String>>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = null;
		int count = 0;// 记录是第几条路径
		while ((line = br.readLine()) != null) {
			if (!line.contains("~")) {
				line = line.substring(0, line.lastIndexOf("-->"));
				String[] nodes = line.split("-->");
				ArrayList<String> set = new ArrayList<String>();
				set.add(count + "");
				for (int i = 0; i < nodes.length; i++) {
					set.add(nodes[i]);
				}
				paths.add(set);
				count++;
			}
		}
		br.close();
		return paths;
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
			// 判断测试用例是否满足蜕变关系
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

	// 从测试用例中提取出来有效测试用例
	public static List<Alipay> getTestCase(String path) {
		String line = null;
		List<Alipay> list = new ArrayList<Alipay>();
		Alipay tc = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				int start = line.indexOf('(') + 1;
				int end = line.lastIndexOf(')');
				String tcString = line.substring(start, end);
				String[] tcitems = tcString.split(","); // 判断测试用例是否满足蜕变关系
				tc = new Alipay();
				tc.setIa(Boolean.parseBoolean(tcitems[0]));
				tc.setFreeWithdrawalLimit(Double.parseDouble(tcitems[4]));
				tc.setMoneyInBalance(Double.parseDouble(tcitems[5]));
				tc.setMoneyInYuebao(Double.parseDouble(tcitems[6]));
				tc.setBandCard(Integer.parseInt(tcitems[1]));
				tc.setTransferAmount(Double.parseDouble(tcitems[2]));
				tc.setTransferMethod(Integer.parseInt(tcitems[3]));
				tc.setMoneyInBankCard(Double.parseDouble(tcitems[7]));
				list.add(tc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) throws IOException {
		List<TCBean> tcs = genTC("C:\\Users\\Administrator\\Desktop\\实验结果\\alipay\\alipay.txt");
		String path = "C:\\Users\\Administrator\\Desktop\\实验结果\\alipay\\alipayTransferPath.txt";
		List<TCBean> list = piority(tcs, path);
		// for(TCBean tc:list){
		// System.out.println(tc.getPlanType_s()+" "+tc.getPlanFee_s()+
		// " "+tc.getTalkTime_s()+" "+tc.getFlow_s()+"
		// "+tc.getCallViewTime_s());
		// }
	}

}
