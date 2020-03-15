package com.sxt.hadoop.mr.tfidf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FirstJob2 {

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		conf.set("mapreduce.app-submission.cross-paltform", "true");
		conf.set("mapreduce.framework.name", "local");
		try {
			FileSystem fs = FileSystem.get(conf);
			Job job = Job.getInstance(conf);
			job.setJarByClass(FirstJob2.class);
			job.setJobName("weibo1_2");

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
//			job.setNumReduceTasks(4);
			job.setMapperClass(FirstMapper2.class);
			job.setReducerClass(FirstReduce2.class);

			FileInputFormat.addInputPath(job, new Path("/data/tfidf/input/"));

			Path path = new Path("/data/tfidf/output/weibo4");
			if (fs.exists(path)) {
				fs.delete(path, true);
			}
			FileOutputFormat.setOutputPath(job, path);

			boolean f = job.waitForCompletion(true);
			if (f) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}