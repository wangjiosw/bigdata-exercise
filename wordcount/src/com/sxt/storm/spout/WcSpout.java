package com.sxt.storm.spout;
import java.util.List;
import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class WcSpout extends BaseRichSpout {

	private SpoutOutputCollector collector;
	String[] text  = {
			"nihao hello welcome",
			"hello hi ok",
			"nihao sxt hi"
			};
	Random r = new Random();

	@Override
	public void nextTuple() {
		List line = new Values(text[r.nextInt(text.length)]);
		// TODO Auto-generated method stub
		this.collector.emit(line);
		System.err.println("spout----------------------"+line);
		Utils.sleep(1000);
	}

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("line"));
		
	}

}
