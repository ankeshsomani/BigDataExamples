import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class UpperCaserMapper extends MapReduceBase implements Mapper<Text, IntWritable,Text, IntWritable> {

     public void map(Text key, IntWritable value,OutputCollector output,Reporter reporter) throws IOException {
         String word = key.toString().toUpperCase();
         output.collect(new Text(word), value);    
     }
 }