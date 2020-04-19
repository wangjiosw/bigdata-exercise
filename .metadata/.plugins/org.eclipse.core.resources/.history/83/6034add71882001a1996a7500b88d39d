package com.sxt.storm.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class WcBolt extends BaseRichBolt {

	private Map stormConf;
	private TopologyContext context;
	private OutputCollector collector;
	int sum = 0;

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		int i = input.getIntegerByField("num");
		sum +=i;
		System.out.println("sum-----------------"+sum);
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub

		this.stormConf = stormConf;
		this.context = context;
		this.collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub

	}

}
