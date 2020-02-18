import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;


public class WordCountTopology {

  public static class RandomSentenceSpout extends BaseRichSpout {
	    SpoutOutputCollector _collector;
	    Random _rand;

	    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
	      _collector = collector;
	      _rand = new Random();
	    }

	    public void nextTuple() {
	      Utils.sleep(10);
	      String[] sentences = new String[]{ "the cow jumped over the moon", "an apple a day keeps the doctor away", "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature" };
	      String sentence = sentences[_rand.nextInt(sentences.length)];
	      _collector.emit(new Values(sentence));
	    }

	    public void ack(Object id) {System.out.println("Spout ack on "+ id);}

	    public void fail(Object id) {}

	    public void declareOutputFields(OutputFieldsDeclarer declarer) {
	      declarer.declare(new Fields("sentence"));
	    }
	}
  public static class SplitSentence extends BaseBasicBolt {

	    public void execute(Tuple tuple, BasicOutputCollector collector) {
		String sentence = tuple.getString(0);
		StringTokenizer st = new StringTokenizer(sentence);
	     	while (st.hasMoreTokens()) {
	        		collector.emit(new Values(st.nextToken()));
	        		
	        	}
	    }

	    public void declareOutputFields(OutputFieldsDeclarer declarer) {
	      declarer.declare(new Fields("word"));
	    }
  }
  public static class WordCount extends BaseBasicBolt {
	    Map<String, Integer> counts = new HashMap<String, Integer>();

	    public void execute(Tuple tuple, BasicOutputCollector collector) {
	      String word = tuple.getString(0);
	      Integer count = counts.get(word);
	      if (count == null)
	        count = 0;
	      count++;
	      counts.put(word, count);
	      collector.emit(new Values(word, count));

	    }

	    public void declareOutputFields(OutputFieldsDeclarer declarer) {
	      declarer.declare(new Fields("word", "count"));
	    }
	}

  public static void main(String[] args) throws Exception {

    TopologyBuilder builder = new TopologyBuilder();

    builder.setSpout("spout", new RandomSentenceSpout(), 5);
    builder.setBolt("split", new SplitSentence(), 8).shuffleGrouping("spout");
    builder.setBolt("count", new WordCount(), 12).fieldsGrouping("split", new Fields("word"));

    Config conf = new Config();
    conf.setDebug(true);
    
    if (args != null && args.length > 1) {
    	int numberWorker = 3;
    	try {
    		numberWorker = Integer.parseInt(args[1]);
    	}
    	catch(Exception e){
    		numberWorker = 3;
    	}
        
    	conf.setNumWorkers(numberWorker);
        
        StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
      }
      else {
//        conf.setMaxTaskParallelism(3);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("word-count", conf, builder.createTopology());

        Thread.sleep(10000);

        cluster.shutdown();
      }
  }
}