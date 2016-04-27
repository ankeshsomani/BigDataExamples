import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class WCReducer extends Reducer<Text, Text, Text, IntWritable>{

	
	protected void reduce(Text key, java.lang.Iterable<Text> values, 
			org.apache.hadoop.mapreduce.Reducer<Text,Text,Text,IntWritable>.Context context)
			throws IOException ,InterruptedException {
		int salary=0;
		for(Text value:values){
			String record[]=value.toString().split("\t");
			int currSal=new Integer(record[4]);
			if(currSal > salary){
				salary=currSal;
			}
		}
		context.write(key, new IntWritable(salary));
	};

}
