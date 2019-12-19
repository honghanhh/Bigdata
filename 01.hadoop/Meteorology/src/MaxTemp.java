import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class MaxTemp {
	//Mapper class
	//Mapper<input key type, input value type, output key type, output value type>
	public static class YearTempMapper extends Mapper<Object, Text, IntWritable, IntWritable>{ 
		
		private IntWritable year = new IntWritable(); 
		private IntWritable temperature = new IntWritable(); 
		//Map function 
		public void map(Object key, Text value, Context context ) 
		throws IOException, InterruptedException {
			// The 'value' parameter is one line in the input that we must tokenize
			int yearInt, tempInt;
  			StringTokenizer itr = new StringTokenizer(value.toString(), ":");
  			
  			// Skipping the first 2 tokens
  			itr.nextToken();
  			itr.nextToken();
  				
  			try {
  				// Get the year from the third token
  				yearInt = Integer.parseInt(itr.nextToken().trim());
  				
  				// Temperature from the fourth token
  				tempInt = Integer.parseInt(itr.nextToken().trim());
  			}
  			catch (NumberFormatException e) {
  				yearInt = 0;
  				tempInt = 0;
  			}
  			
  			year.set(yearInt);
  			temperature.set(tempInt);
  			context.write(year, temperature); 
		} 
	}
	
	//Reducer class 
	public static class IntMaxReducer extends Reducer<IntWritable,IntWritable,IntWritable,IntWritable> { 
		private IntWritable result = new IntWritable(); 

		public void reduce(IntWritable key, Iterable<IntWritable> values, Context context ) 
		throws IOException, InterruptedException {
  			
			int max = -1000; // impossible temperature
  			
  			for (IntWritable val : values) { 
    				if (val.get() > max) max = val.get(); 
  			} 
  			result.set(max); 
  			context.write(key, result); 
		} 
	} 
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration(); 
		Job job = Job.getInstance(conf, "max temperature by year"); 
		job.setJarByClass(MaxTemp.class); 
		job.setMapperClass(YearTempMapper.class); 
		job.setCombinerClass(IntMaxReducer.class); 
		job.setReducerClass(IntMaxReducer.class); 
		job.setOutputKeyClass(IntWritable.class); 
		job.setOutputValueClass(IntWritable.class); 
		FileInputFormat.addInputPath(job, new Path(args[0])); 
		FileOutputFormat.setOutputPath(job, new Path(args[1])); 
		long t1 = System.currentTimeMillis(); 
		int res = job.waitForCompletion(true) ? 0 : 1; 
		long t2 = System.currentTimeMillis(); 
		System.out.println("time in ms ="+(t2-t1)); 
		System.exit(res);
	}
}