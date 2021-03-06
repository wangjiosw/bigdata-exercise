package com.bjsxt.fd;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;
/**
 * 
tom hello hadoop cat
world hadoop hello hive
cat tom hive
mr hive hello
hive cat hadoop world hello mr
hadoop tom hive world
hello tom world hive mr

 * @author wangji
 *
 */
public class FMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	Text tkey = new Text();
	IntWritable tval = new IntWritable();
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String[] words = StringUtils.split(value.toString(), ' ');
		for (int i = 1; i < words.length; i++) {
			// 把一对封装在tkey中
			tkey.set(getFd(words[0],words[i]));
			// 如果是直接好友，则直接输出0
			tval.set(0);
			context.write(tkey, tval);
			
			for (int j = i+1; j < words.length; j++) {
				// 把一对封装在tkey中
				tkey.set(getFd(words[i],words[j]));
				// 如果是间接好友，则直接输出1
				tval.set(1);
				context.write(tkey, tval);
			}
		}
	}
	private String getFd(String a, String b) {
		// TODO Auto-generated method stub
		return a.compareTo(b)>0?b+":"+a:a+":"+b ;
	}
}
