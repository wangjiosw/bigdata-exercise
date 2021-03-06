package leetcode;

import javax.xml.soap.Node;

/**
输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
输出：7 -> 0 -> 8
原因：342 + 465 = 807
 * @author wangji
 *
 */
public class leetcode2 {
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    	Boolean flag = false;
    	int sum = 0;
    	ListNode head = new ListNode(0);
    	ListNode p = head;
    	while ((l1!=null && l2!=null) || flag) {
    		// 逐元素相加，一个加完，但还存在进位的情况
    		if (flag && (l1==null || l2==null)) {
				flag = false;
				if (l1==null && l2!=null) {
					l1 = new ListNode(1);
				} else if (l1!=null && l2==null) {
					l2 = new ListNode(1);
				} else if (l1==null && l2==null) {
					l1 = new ListNode(1);
					p.next = l1;
					return head.next;
				}
			}
    		
			sum = l1.val + l2.val;
			if (flag) {
				sum++;
				flag = false;
			}
			if (sum >= 10) {
				sum -= 10;
				flag = true;
			}
			ListNode node = new ListNode(sum);
			p.next = node;
			p = node;
			l1 = l1.next;
			l2 = l2.next;
		}
    	// l1==null或l2==null,且无进位
    	if (l1!=null) {
    		p.next = l1;
		}
    	if (l2!=null) {
    		p.next = l2;
		}
		return head.next;

    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ListNode node11 = new ListNode(3,null);
		ListNode node12 = new ListNode(4,node11);
		ListNode node13 = new ListNode(2,node12);
		ListNode head1 = node13;
		
		ListNode node21 = new ListNode(4,null);
		ListNode node22 = new ListNode(6,node21);
		ListNode node23 = new ListNode(5,node22);
		ListNode head2 = node23;
		
		ListNode result = addTwoNumbers(node13,node23);
		while (result!=null) {
			System.out.println(result.val);
			result = result.next;
		}
		
		

	}

}
