package com.bjsxt.fd;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	IntWritable tval = new IntWritable();
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		// tom:hello 1
		// tom:hello 0
		// tom:hello 1
		int num = 0;
		for (IntWritable val : values) {
			if (val.get()==0) {
				return;
			}
			num++;
		}
		tval.set(num);
		context.write(key, tval);
	}	
}
