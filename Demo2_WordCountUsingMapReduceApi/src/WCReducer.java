import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class WCReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

	protected void reduce(Text key, java.lang.Iterable<IntWritable> values, 
			org.apache.hadoop.mapreduce.Reducer<Text,IntWritable,Text,IntWritable>.Context arg2) 
			throws IOException ,InterruptedException {
		int sum=0;
		Iterator iterator=values.iterator();
		while(iterator.hasNext()){			
			sum=sum+Integer.parseInt(iterator.next().toString());
		}
		arg2.write(key, new IntWritable(sum));
		
	};
	

}
