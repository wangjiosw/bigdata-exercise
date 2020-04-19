package com.sxt.storm.ack;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class MySpout implements IRichSpout{

	private static final long serialVersionUID = 1L;

	int index = 0;
	
	FileInputStream fis;
	InputStreamReader isr;
	BufferedReader br;			
	SpoutOutputCollector collector = null;
	String str = null;

	@Override
	public void nextTuple() {
		try {
			if ((str = this.br.readLine()) != null) {
				// 过滤动作
				index++;
				collector.emit(new Values(str), index);
//				collector.emit(new Values(str));
			}
		} catch (Exception e) {
		}
		
		
	}
	@Override
	public void close() {
		try {
			br.close();
			isr.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		try {
			this.collector = collector;
			this.fis = new FileInputStream("track.log");
			this.isr = new InputStreamReader(fis, "UTF-8");
			this.br = new BufferedReader(isr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("log"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
	
	@Override
	public void ack(Object msgId) {
		System.err.println(" [" + Thread.currentThread().getName() + "] "+ " spout ack:"+msgId.toString());
	}

	@Override
	public void activate() {
		
	}

	@Override
	public void deactivate() {
		
	}

	@Override
	public void fail(Object msgId) {
		System.err.println(" [" + Thread.currentThread().getName() + "] "+ " spout fail:"+msgId.toString());
	}

}
