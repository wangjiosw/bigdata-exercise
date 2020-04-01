package com.bjsxt.hbase;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bjsxt.hbase.Phone.DayOfPhone;
import com.bjsxt.hbase.Phone.PhoneDetail;

public class PhoneCase {

	// 表的管理类
	HBaseAdmin admin = null;
	//数据的管理类
	HTable table = null;
	String tm = "phone";
	
	@Before
	public void init() throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		Configuration conf = new Configuration();
		conf.set("hbase.zookeeper.quorum", "node002,node003,node004");
		admin = new HBaseAdmin(conf);
		table = new HTable(conf, tm.getBytes());
	}
	
	@Test
	public void createTable() throws IOException {
		HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tm));
		HColumnDescriptor family = new HColumnDescriptor("cf".getBytes());
		desc.addFamily(family);
		if (admin.tableExists(tm)) {
			admin.disableTable(tm);
			admin.deleteTable(tm);
		}
		admin.createTable(desc);
	}
	
	@Test
	public void insert() throws RetriesExhaustedWithDetailsException, InterruptedIOException {
		Put put = new Put("1111".getBytes());
		put.add("cf".getBytes(), "name".getBytes(), "zhangsan".getBytes());
		put.add("cf".getBytes(), "age".getBytes(), "12".getBytes());
		put.add("cf".getBytes(), "sex".getBytes(), "man".getBytes());
		table.put(put);
	}
	
	@Test
	public void get() throws IOException {
		Get get = new Get("1111".getBytes());
		// 添加要获取的列族和列，减少网络IO，相当于在服务器端做列过滤
		get.addColumn("cf".getBytes(), "name".getBytes());
		get.addColumn("cf".getBytes(), "age".getBytes());
		get.addColumn("cf".getBytes(), "sex".getBytes());
		Result result = table.get(get);
		Cell cell1 = result.getColumnLatestCell("cf".getBytes(), "name".getBytes());
		Cell cell2 = result.getColumnLatestCell("cf".getBytes(), "age".getBytes());
		Cell cell3 = result.getColumnLatestCell("cf".getBytes(), "sex".getBytes());
		System.out.println(Bytes.toString(CellUtil.cloneValue(cell1)));
		System.out.println(Bytes.toString(CellUtil.cloneValue(cell2)));
		System.out.println(Bytes.toString(CellUtil.cloneValue(cell3)));
	}
	
	@Test
	public void scan() throws IOException {
		Scan scan = new Scan();
		// 限定范围，否则全表扫描
//		scan.setStartRow(startRow);
//		scan.setStopRow(stopRow)
		ResultScanner rss = table.getScanner(scan);
		for (Result result : rss) {
			Cell cell1 = result.getColumnLatestCell("cf".getBytes(), "name".getBytes());
			Cell cell2 = result.getColumnLatestCell("cf".getBytes(), "age".getBytes());
			Cell cell3 = result.getColumnLatestCell("cf".getBytes(), "sex".getBytes());
			System.out.println(Bytes.toString(CellUtil.cloneValue(cell1)));
			System.out.println(Bytes.toString(CellUtil.cloneValue(cell2)));
			System.out.println(Bytes.toString(CellUtil.cloneValue(cell3)));
		}
	}
	
	/**
	 * 10个用户，每个1000条记录，每一条记录当作一个对象存储
	 * @throws ParseException 
	 * @throws InterruptedIOException 
	 * @throws RetriesExhaustedWithDetailsException 
	 */
	@Test
	public void insert2() throws ParseException, RetriesExhaustedWithDetailsException, InterruptedIOException {
		java.util.List<Put> puts = new ArrayList<Put>();
		for (int i = 0; i < 10; i++) {
			String phoneNumber = getPhone("158");
			for (int j = 0; j < 1000; j++) {
				String dnum = getPhone("177");
				String length = String.valueOf(r.nextInt(99));
				String type = String.valueOf(r.nextInt(2));
				String data = getDate("2020");
				
				//保存属性到对象中
				Phone.PhoneDetail.Builder phoneDetail = Phone.PhoneDetail.newBuilder();
				phoneDetail.setData(data);
				phoneDetail.setDnum(dnum);
				phoneDetail.setLength(length);
				phoneDetail.setType(type);
				
				String rowkey = phoneNumber+"_"+(Long.MAX_VALUE-sdf.parse(data).getTime());
				Put put = new Put(rowkey.getBytes());
				put.add("cf".getBytes(), "phoneDetail".getBytes(), phoneDetail.build().toByteArray());
								
				puts.add(put);
			}
		}
		table.put(puts);
	}
	
	@Test
	public void get2() throws IOException {
		Get get = new Get("15898603022_9223370459104413807".getBytes());
		Result result = table.get(get);
		PhoneDetail phoneDetail = Phone.PhoneDetail.parseFrom(CellUtil.cloneValue(result.getColumnLatestCell("cf".getBytes(), "phoneDetail".getBytes())));
		System.out.println(phoneDetail);
	}
	
	/**
	 * 10个用户，每天1000条记录，每天所有数据放在一个rowkey中
	 * @param string
	 * @return
	 * @throws ParseException 
	 * @throws InterruptedIOException 
	 * @throws RetriesExhaustedWithDetailsException 
	 */
	@Test
	public void insert3() throws ParseException, RetriesExhaustedWithDetailsException, InterruptedIOException {
		java.util.List<Put> puts = new ArrayList<Put>();
		for (int i = 0; i < 10; i++) {
			String phoneNumber = getPhone("133");
			String rowkey = phoneNumber+"_"+(Long.MAX_VALUE-sdf.parse("20201225000000").getTime());
			Phone.DayOfPhone.Builder dayOfPhone = Phone.DayOfPhone.newBuilder();
			for (int j = 0; j < 1000; j++) {
				String dnum = getPhone("177");
				String length = String.valueOf(r.nextInt(99));
				String type = String.valueOf(r.nextInt(2));
				String data = getDate2("20201225");
				
				//保存属性到对象中
				Phone.PhoneDetail.Builder phoneDetail = Phone.PhoneDetail.newBuilder();
				phoneDetail.setData(data);
				phoneDetail.setDnum(dnum);
				phoneDetail.setLength(length);
				phoneDetail.setType(type);
				
				dayOfPhone.addDayPhone(phoneDetail);
			}
			Put put = new Put(rowkey.getBytes());
			put.add("cf".getBytes(), "day".getBytes(), dayOfPhone.build().toByteArray());				
			puts.add(put);
		}
		table.put(puts);

	}
	
	@Test
	public void get3() throws IOException {
		Get get = new Get("13316727928_9223370428029175807".getBytes());
		Result result = table.get(get);
		DayOfPhone dayOfPhone = Phone.DayOfPhone.parseFrom(CellUtil.cloneValue(result.getColumnLatestCell("cf".getBytes(), "day".getBytes())));
		int count = 0;
		for (PhoneDetail pd : dayOfPhone.getDayPhoneList()) {
			System.out.println(pd);
			count++;
		}
		System.out.println(count);
		
	}
	
	
	private String getDate2(String string) {
		// TODO Auto-generated method stub
		return string+String.format("%%02d%02d%02d", r.nextInt(24),r.nextInt(60),r.nextInt(60));
	}

	private String getPhone(String string) {
		// TODO Auto-generated method stub
		return string+String.format("%08d", r.nextInt(99999999));
	}

	Random r = new Random();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
	
	private String getDate(String phonePrefix) {
		// TODO Auto-generated method stub
		return phonePrefix+String.format("%02d%02d%02d%02d%02d", r.nextInt(12)+1,r.nextInt(31),r.nextInt(24),r.nextInt(60),r.nextInt(60));
	}
	
	@After
	public void destory() throws IOException {
		if (admin!=null) {
			admin.close();
		}
	}
}















