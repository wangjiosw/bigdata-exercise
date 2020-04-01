package com.bjsxt.wc;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class WCRunner {


	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		conf.set("hbase.zookeeper.quorum", "node002,node003,node004");
		conf.set("fs.defaultFS", "hdfs://node001:8020");
		
		Job job = Job.getInstance(conf);
		job.setJarByClass(WCRunner.class);
		job.setMapperClass(WCMapper.class);
		
		// 如果是hadoop自带类型，key和value类型可省略，如果是自定义类型，则一定要写出来
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		TableMapReduceUtil.initTableReducerJob("wc", WCReducer.class, job, null, null, null, null, false);
		FileInputFormat.addInputPath(job, new Path("/data/wordcount/input/wc.txt"));
		
		// reducer端输出的Key和value的类型
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Put.class);
		
		// 控制向哪个源写数据，从哪个源读数据
//		job.setOutputFormatClass(cls);
//		job.setInputFormatClass(cls);
		
		job.waitForCompletion(true);
		
	}
}
