
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class WCMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

protected void map(LongWritable key, Text value, 
		org.apache.hadoop.mapreduce.Mapper<LongWritable,Text,Text,IntWritable>.Context context) throws IOException ,InterruptedException {
	//Reporter is used to report an error
			IntWritable one = new IntWritable(1);
			Text word=new Text();
			
			String line=value.toString();
			StringTokenizer tokenizer=new StringTokenizer(line);
			
			while(tokenizer.hasMoreTokens()){
				word.set(tokenizer.nextToken());
				//output.collect(word, one);
				context.write(word, one);
			}
};

	

}
