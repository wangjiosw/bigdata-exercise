package leetcode;

public class leetcode7 {
    public static int reverse(int x) {
    	boolean flag = false;
    	if (x < 0) {
			flag = true;
			x = -x;
		}
    	
    	String num = Integer.toString(x);
    	int num_len = num.length();
    	char[] r_num = new char[num_len];
    	for (int i = 0; i < num_len; i++) {
			r_num[num_len-i-1] = num.charAt(i);
		}
    	String new_num = new String(r_num);
    	
    	int result = 0;
    	try {
    		result = Integer.parseInt(new_num);
		} catch (Exception e) {
			// TODO: handle exception
			result = 0;
		}
    	if (flag) {
			result = - result;
		}
		return result;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 123;
		int y = reverse(x);
		System.out.println(y);
	}

}
