package SalesPackage;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

public class SalesMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	
	//create counter groups as enumeration
	static enum MySalesCounters{MISSING,BAD};
	
	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

		String valueString = value.toString();
		String[] SingleCountryData = valueString.split(",");
		String country=SingleCountryData[7];
		if(country.length()==0){
			reporter.incrCounter(MySalesCounters.MISSING, 1);
		}
		else if(country.startsWith("/")){
			reporter.incrCounter(MySalesCounters.BAD, 1);
		}
		else{
			output.collect(new Text(country), one);
		}
	}
}
