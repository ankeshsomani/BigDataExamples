import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;



public class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text>{

	public static final String delimeter=",";
	public static final String underscore="";
	public static final String tab="\t";
	protected void map(LongWritable key, Text value, org.apache.hadoop.mapreduce.Mapper<LongWritable,
			Text,Text,Text>.Context context) throws java.io.IOException ,InterruptedException {
		
		String record[]=value.toString().split(delimeter);		
		String newKey=record[1]+tab+record[3]+tab+record[0]+tab+record[5];
		context.write(new Text(newKey), new Text(value));			
		
	};
}
