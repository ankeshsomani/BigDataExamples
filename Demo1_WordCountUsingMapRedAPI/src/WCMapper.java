
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
public class WCMapper extends MapReduceBase implements 
Mapper<LongWritable, Text, Text, IntWritable>{

	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<Text, IntWritable> output, Reporter reporter)
			throws IOException {
		//Reporter is used to report an error
		IntWritable one = new IntWritable(1);
		Text word=new Text();
		
		String line=value.toString();
		StringTokenizer tokenizer=new StringTokenizer(line);
		
		while(tokenizer.hasMoreTokens()){
			word.set(tokenizer.nextToken());
			output.collect(word, one);
		}
		
	}
	

}
