package com.bjsxt.wc2;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.bjsxt.wc.WCMapper;
import com.bjsxt.wc.WCReducer;
import com.bjsxt.wc.WCRunner;

public class MyReadWriteJob {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		conf.set("hbase.zookeeper.quorum", "node002,node003,node004");
		conf.set("fs.defaultFS", "hdfs://node002:8020");
		
		Job job = Job.getInstance(conf);
		job.setJarByClass(MyReadWriteJob.class);
		
		Scan scan = new Scan();
		scan.setCaching(500);        // 1 is the default in Scan, which will be bad for MapReduce jobs
		scan.setCacheBlocks(false);  
		
		TableMapReduceUtil.initTableMapperJob(
				  "wc",      // input table
				  scan,             // Scan instance to control CF and attribute selection
				  WCMapper2.class,   // mapper class
				  null,             // mapper output key
				  null,             // mapper output value
				  job);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		
		job.setReducerClass(WCReducer2.class);
		Path outPath = new Path("/output/wc2");
		// if output path exist, delete
		if (outPath.getFileSystem(conf).exists(outPath))
			outPath.getFileSystem(conf).delete(outPath, true);
		FileOutputFormat.setOutputPath(job, outPath);
		// reducer端输出的Key和value的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.waitForCompletion(true);
	}
}
