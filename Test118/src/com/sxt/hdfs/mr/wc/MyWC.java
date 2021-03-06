package com.sxt.hdfs.mr.wc;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MyWC {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration();

		// TODO Auto-generated method stub
		// Create a new Job
		Job job = Job.getInstance(conf);
		job.setJarByClass(MyWC.class);
		 
		// Specify various job-specific parameters     
		job.setJobName("myjob");
		 
//		job.setInputPath(new Path("in"));
//		job.setOutputPath(new Path("out"));
		
		Path inPath = new Path("/user/root/test.txt");
		FileInputFormat.addInputPath(job, inPath);
		Path outPath = new Path("/output/wordcount");
		// if output path exist, delete
		if (outPath.getFileSystem(conf).exists(outPath))
			outPath.getFileSystem(conf).delete(outPath, true);
		FileOutputFormat.setOutputPath(job, outPath);
		 
		job.setMapperClass(MyMapper.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setReducerClass(MyReducer.class);
		
		// Submit the job, then poll for progress until the job is complete
		job.waitForCompletion(true);
	}

}
