import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.lib.ChainMapper;
import org.apache.hadoop.mapred.lib.ChainReducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class JobChainingDriver extends Configured implements Tool{

	@Override
	public int run(String[] args) throws Exception {
		JobConf conf= new JobConf();
		conf.setJarByClass(JobChainingDriver.class);
		conf.setJobName("Job Chaining");
		FileInputFormat.addInputPath(conf,new Path(args[0]));
		FileOutputFormat.setOutputPath(conf,new Path(args[1]));
		
		conf.setInputFormat(TextInputFormat.class);
		JobConf stringTokenizerConf= new JobConf(false);
		
		//Add tokenizer mapper
		ChainMapper.addMapper(conf,TokenizerMapper.class,
				LongWritable.class,Text.class,Text.class,
				IntWritable.class,true,stringTokenizerConf);
		
		JobConf upperCaseConf= new JobConf(false);
		//Add upper case mapper
		ChainMapper.addMapper(conf,UpperCaserMapper.class,
			Text.class,IntWritable.class,Text.class,
			IntWritable.class,true,upperCaseConf);
		
		JobConf wordCountReducerConf= new JobConf(false);
		//Add word count reducer
		ChainReducer.setReducer(conf, WordCountReducer.class, 
				Text.class, IntWritable.class, Text.class,
				IntWritable.class, true,wordCountReducerConf);
		
		
		JobConf lastMapperConf= new JobConf(false);
		//Add Last Mapper
		ChainReducer.addMapper(conf, LastMapper.class, 
				Text.class, IntWritable.class, Text.class,
				IntWritable.class, true,lastMapperConf);

		JobClient.runJob(conf);
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		ToolRunner.run(new JobChainingDriver(), args);
	}

}
