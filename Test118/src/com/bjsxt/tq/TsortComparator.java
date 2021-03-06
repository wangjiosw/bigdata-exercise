package com.bjsxt.tq;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 
 * 实现年月正序，温度倒叙
 * @author wangji
 *
 */
public class TsortComparator extends WritableComparator {
	
	Tq t1 = null;
	Tq t2 = null;
	public TsortComparator() {
		// TODO Auto-generated constructor stub
		super(Tq.class,true);
	}
	
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		// TODO Auto-generated method stub
		
		t1 = (Tq) a;
		t2 = (Tq) b;
		
		
		
		int c1 = Integer.compare(t1.getYear(), t2.getYear());
		if (c1==0) {
			int c2 = Integer.compare(t1.getMonth(), t2.getMonth());
			if (c2==0) {
				return -Integer.compare(t1.getWd(), t2.getWd());
			}
			return c2;
		}
		
		return c1;
	}

}
