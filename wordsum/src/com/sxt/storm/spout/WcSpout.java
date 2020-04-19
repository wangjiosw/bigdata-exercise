package com.sxt.storm.spout;

import java.util.List;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class WcSpout extends BaseRichSpout {

	private Map conf;
	private TopologyContext context;
	private SpoutOutputCollector collector;
	int i = 0;

	@Override
	public void nextTuple() {
		i++;
		// TODO Auto-generated method stub
		List<Object> tuple = new Values(i);
		this.collector.emit(tuple);
		System.err.println("spout----------------------"+i);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.conf = conf;
		this.context = context;
		this.collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("num"));
	}

}
