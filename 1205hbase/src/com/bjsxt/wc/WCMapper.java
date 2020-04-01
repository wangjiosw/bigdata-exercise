package com.bjsxt.wc;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// hdfs读数据，继承Mapper
// hbase读数据，继承TableMapper
public class WCMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String[] split = value.toString().split(" ");
		for (String string : split) {
			context.write(new Text(string), new IntWritable(1));
		}
	}
}
