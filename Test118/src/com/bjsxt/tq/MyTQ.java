package com.bjsxt.tq;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MyTQ {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		// TODO Auto-generated method stub
		// Create a new Job
		Job job = Job.getInstance(conf);
		job.setJarByClass(MyTQ.class);
		 
		// Specify various job-specific parameters     
		job.setJobName("tq");
		 
		Path inPath = new Path("/tq/input");
		FileInputFormat.addInputPath(job, inPath);
		Path outPath = new Path("/tq/output");
		// if output path exist, delete
		if (outPath.getFileSystem(conf).exists(outPath))
			outPath.getFileSystem(conf).delete(outPath, true);
		FileOutputFormat.setOutputPath(job, outPath);
		 
		job.setMapperClass(Tmapper.class);
		job.setOutputKeyClass(Tq.class);
		job.setOutputValueClass(IntWritable.class);
		
		// 自定义排序比较器（年月正序，温度倒叙）
		job.setSortComparatorClass(TsortComparator.class);
		
		// 自定义分区器
		job.setPartitionerClass(TPartitioner.class);
		
		//自定义组排序（之前比较器用了年月和温度，reduce会把年月和温度相同的归为一组，而我们想要的是相同年月的为一组）
		job.setGroupingComparatorClass(TGroupComparator.class);
		
		// 设置reducetask数量
		job.setNumReduceTasks(2);
		
		// set reducer
		job.setReducerClass(TReducer.class);
		
		// Submit the job, then poll for progress until the job is complete
		job.waitForCompletion(true);

	}

}
