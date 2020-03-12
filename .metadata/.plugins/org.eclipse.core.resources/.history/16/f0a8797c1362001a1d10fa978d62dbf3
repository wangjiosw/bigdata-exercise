package com.sxt.hdfs.test;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;


public class TestHDFA {
	Configuration conf = null; 
	FileSystem fs = null;

	
	@Before
	public void conn() throws IOException{
		conf = new Configuration();
		fs = FileSystem.get(conf);
	}
	
	@Test
	public void mkdir() throws IOException {
		Path path = new Path("/mytemp");
		if (fs.exists(path))
			fs.delete(path, true);
		
		fs.mkdirs(path);
		
	}
	
	@Test
	public void uploadFile() throws IOException {
		
		// 文件的上传路径
		Path path = new Path("/mytemp/hahah.txt");
		FSDataOutputStream fdos = fs.create(path);
		
		InputStream is = new BufferedInputStream(new FileInputStream("/Users/wangji/Desktop/hello.txt"));
		IOUtils.copyBytes(is, fdos, conf, true);
		
	}
	
	@Test
	public void readFile() throws IOException {
		Path path = new Path("/user/root/test.txt");
		FileStatus file = fs.getFileStatus(path);
//		BlockLocation[] blks = fs.getFileBlockLocations(file, 0, file.getLen());
//		// 遍历数组
//		for (BlockLocation blk : blks) {
//			System.out.println(blk);
//		}
		
		// 读取文件
		FSDataInputStream fdis =fs.open(path);
		fdis.seek(1048576);
		System.out.println((char)fdis.readByte());
		System.out.println((char)fdis.readByte());
		System.out.println((char)fdis.readByte());
		System.out.println((char)fdis.readByte());
		System.out.println((char)fdis.readByte());

	}
	
	@After
	public void close() throws IOException {
		fs.close();
	}

}
