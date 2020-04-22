package com.sxt.test;

import com.sxt.storm.bolt.WcBolt;
import com.sxt.storm.bolt.WsplitBolt;
import com.sxt.storm.spout.WcSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TopologyBuilder tb = new TopologyBuilder();
		tb.setSpout("wcspout", new WcSpout());
		tb.setBolt("wsplitbolt", new WsplitBolt()).shuffleGrouping("wcspout");
		tb.setBolt("wcbolt", new WcBolt()).fieldsGrouping("wsplitbolt", new Fields("w"));
		 Config config = new Config();
		if (args.length > 0) {
			try {
				StormSubmitter.submitTopology(args[0], config, tb.createTopology());
			} catch (AlreadyAliveException | InvalidTopologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			// 放入本地集群
			LocalCluster lc = new LocalCluster();
			lc.submitTopology("wc", config, tb.createTopology());			
		}

	}

}
