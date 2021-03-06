package com.bjsxt.tq;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/**
 1949-10-01	39
 1949-10-01	37
 1949-10-02	34
 1949-10-03	32

 * @author wangji
 *
 */
public class TReducer extends Reducer<Tq, IntWritable, Text, IntWritable> {

	Text tkey = new Text();
	IntWritable tval = new IntWritable();
	@Override
	protected void reduce(Tq key, Iterable<IntWritable> vals,
			Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		int flag = 0;
		int day = 0;
		for (IntWritable val : vals) {
			if (flag ==0) {
				tkey.set(key.toString());
				tval.set(val.get());
				context.write(tkey, tval);
				flag++;
				day = key.getDay();
			}
			// key 也会跟着迭代吗？？？
			if (flag != 0 && day != key.getDay()) {
				tkey.set(key.toString());
				tval.set(val.get());
				context.write(tkey, tval);
//				break;
				return;
			}
			
		}
	}
}
