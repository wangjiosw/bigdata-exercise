package com.bjsxt.wc2;

import java.io.IOException;

import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WCMapper2 extends TableMapper<Text, IntWritable>{
	public static final byte[] CF = "cf".getBytes();
	public static final byte[] ATTR1 = "cf".getBytes();

	private final IntWritable ONE = new IntWritable(1);
	private Text text = new Text();

	@Override
	protected void map(ImmutableBytesWritable row, Result value,
			Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String val = Bytes.toString(CellUtil.cloneValue(value.getColumnLatestCell("cf".getBytes(), "cf".getBytes())));
	    text.set(Bytes.toString(row.get()));     // we can only emit Writables..
	    
	    context.write(text, new IntWritable(Integer.parseInt(val)));
		
	}


}
