package com.sxt.storm.bolt;

import java.util.List;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WsplitBolt extends BaseRichBolt {

	private OutputCollector collector;

	
	/**
	 * 
	 * 1. 获取数据
	 * 2. 切分为单词数组
	 * 3. 发送单词
	 * 
	 */
	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		String line = input.getStringByField("line");
		String[] words = line.split(" ");
		for (String word : words) {
			List w = new Values(word);
			this.collector.emit(w);
		}
	}

	@Override
	public void prepare(Map arg0, TopologyContext arg1, OutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("w"));
	}

}