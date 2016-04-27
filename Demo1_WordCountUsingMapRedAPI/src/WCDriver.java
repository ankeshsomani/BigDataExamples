import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class WCDriver extends Configured implements Tool{
	
	public static void main(String args[]) throws Exception{
				
		ToolRunner.run(new WCDriver(), args);
	}

	@Override
	public int run(String[] args) throws Exception {

		JobConf conf=new JobConf();
		//Driver class
		conf.setJarByClass(WCDriver.class);
		
		conf.setJobName("word count job using Toolrunner.Final Demo using");
		//Mapper and Reducer classes
		conf.setMapperClass(WCMapper.class);
		conf.setReducerClass(WCReducer.class);
		
		//Input format of job
		conf.setInputFormat(TextInputFormat.class);
		
		conf.setNumReduceTasks(3);
		
		//
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(IntWritable.class);
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.setInputPaths(conf,new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		
		JobClient.runJob(conf);
		return 0;
	}

}
