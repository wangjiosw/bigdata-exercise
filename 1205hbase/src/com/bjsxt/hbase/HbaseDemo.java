package com.bjsxt.hbase;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.io.filefilter.PrefixFileFilter;
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
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;

public class HbaseDemo {

	// 表的管理类
	HBaseAdmin admin = null;
	//数据的管理类
	HTable table = null;
	
	ResultScanner scanner = null;
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
	
	/**
	 * 10 user, each user generate 1000 recode per year
	 * @throws ParseException 
	 * @throws InterruptedIOException 
	 * @throws RetriesExhaustedWithDetailsException 
	 */
	@Test
	public void insert() throws ParseException, RetriesExhaustedWithDetailsException, InterruptedIOException {
		java.util.List<Put> puts = new ArrayList<Put>();
		for (int i = 0; i < 10; i++) {
			String phoneNumber = getPhone("158");
			for (int j = 0; j < 1000; j++) {
				String dnum = getPhone("177");
				String length = String.valueOf(r.nextInt(99));
				String type = String.valueOf(r.nextInt(2));
				String data = getDate("2020");
				
				String rowkey = phoneNumber+"_"+(Long.MAX_VALUE-sdf.parse(data).getTime());
				
				Put put = new Put(rowkey.getBytes());
				put.add("cf".getBytes(), "dnum".getBytes(), dnum.getBytes());
				put.add("cf".getBytes(), "length".getBytes(), length.getBytes());
				put.add("cf".getBytes(), "type".getBytes(), type.getBytes());
				put.add("cf".getBytes(), "data".getBytes(), data.getBytes());
				
				puts.add(put);
			}
		}
		table.put(puts);
		
	}
	
	
	/**
	 * 查询某一个用户3月份的所有通话记录
	 * @param string
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 */
	
	@Test
	public void scan() throws ParseException, IOException {
		String phoneNumber = "15892086688";
		String startRow = phoneNumber+"_"+(Long.MAX_VALUE-sdf.parse("20200401000000").getTime());
		String stopRow = phoneNumber+"_"+(Long.MAX_VALUE-sdf.parse("20200301000000").getTime());
		Scan scan = new Scan();
		scan.setStartRow(startRow.getBytes());
		scan.setStopRow(stopRow.getBytes());
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			System.out.print(Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell("cf".getBytes(), "dnum".getBytes()))));
			System.out.print("--"+Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell("cf".getBytes(), "length".getBytes()))));
			System.out.print("--"+Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell("cf".getBytes(), "type".getBytes()))));
			System.out.println("--"+Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell("cf".getBytes(), "data".getBytes()))));
		}
		scanner.close();
	}
	
	/**
	 * 查询某一个用户的所有主叫电话
	 * @param string
	 * @return
	 * @throws IOException 
	 */
	@Test
	public void scan2() throws IOException {
		FilterList filters = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		SingleColumnValueFilter filter1 = new SingleColumnValueFilter("cf".getBytes(), "type".getBytes(), CompareOp.EQUAL, "0".getBytes());
		PrefixFilter filter2 = new PrefixFilter("15892086688".getBytes());
		filters.addFilter(filter1);
		filters.addFilter(filter2);
		
		Scan scan = new Scan();
		scan.setFilter(filters);
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			System.out.print(Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell("cf".getBytes(), "dnum".getBytes()))));
			System.out.print("--"+Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell("cf".getBytes(), "length".getBytes()))));
			System.out.print("--"+Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell("cf".getBytes(), "type".getBytes()))));
			System.out.println("--"+Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell("cf".getBytes(), "data".getBytes()))));
		}
		scanner.close();
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















