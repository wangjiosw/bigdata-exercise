package leetcode;

public class leetcode1 {
	
    public static int[] twoSum(int[] nums, int target) {
    	
    	int len = nums.length;
    	int[] result = new int[2];
    	for (int i = 0; i < len-1; i++) {
			for (int j = i+1; j < len; j++) {
				if (nums[i]+nums[j]==target) {
					result[0]=i;
					result[1]=j;
				}
			}
		}
    	
		return result;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int[] nums = new int[10];
		int[] nums = {2, 7, 11, 15};
		int target = 9;
		int[] result = twoSum(nums,target);
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
		
	}

}