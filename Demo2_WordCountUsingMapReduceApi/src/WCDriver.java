import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.jobcontrol.Job;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class WCDriver extends Configured implements Tool{
	
	public static void main(String args[]) throws Exception{
				
		ToolRunner.run(new WCDriver(), args);
	}

	@Override
	public int run(String[] args) throws Exception {

		//JobConf conf=new JobConf();
		JobConf conf = new JobConf();
		//Driver class
		conf.setJarByClass(WCDriver.class);
		
		conf.setJobName("word count job using new MR API.FCombiner Included");
		//Mapper and Reducer classes
		conf.setMapperClass(WCMapper.class);
		conf.setReducerClass(WCReducer.class);
		//set combiner
		conf.setCombinerClass(WCReducer.class);
		//Input format of job
		conf.setInputFormatClass(TextInputFormat.class);
		
		conf.setNumReduceTasks(3);
		
		//
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(IntWritable.class);
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.setInputPaths(conf,new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		
		JobConf stringTokenizerConf= new JobConf(false);
		//JobClient.runJob(conf);
		
		//wait for completion is synchronus method
		conf.waitForCompletion(true);
		//Asynchronous method cof.submit()
		//conf.submit();
		return 0;
	}

}
