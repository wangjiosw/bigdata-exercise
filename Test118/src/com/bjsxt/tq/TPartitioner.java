package com.bjsxt.tq;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class TPartitioner extends Partitioner<Tq, IntWritable> {

	@Override
	public int getPartition(Tq key, IntWritable value, int numPartitions) {
		// TODO Auto-generated method stub
		return key.getYear()%numPartitions;
	}

}
