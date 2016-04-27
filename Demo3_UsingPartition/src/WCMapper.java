
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class WCMapper extends Mapper<LongWritable, Text, Text, Text>{

protected void map(LongWritable key, Text value, 
		org.apache.hadoop.mapreduce.Mapper<LongWritable,Text,
		Text,Text>.Context context) throws IOException ,InterruptedException {
	String line=value.toString();
	String record[]=line.split("\t");
	context.write(new Text(record[3]),value);
	
};

	

}
