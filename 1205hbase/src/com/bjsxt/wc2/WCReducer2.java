package com.bjsxt.wc2;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WCReducer2 extends Reducer<Text, IntWritable, Text, Text> {

	@Override
	protected void reduce(Text key, Iterable<IntWritable> value,
			Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		for (IntWritable intWritable : value) {
			
			context.write(key, new Text(intWritable.get()+"_"+(intWritable.get()+10)));
		}
	}
}
