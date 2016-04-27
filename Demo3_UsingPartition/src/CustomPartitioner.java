import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


public class CustomPartitioner extends Partitioner<Text, Text> {

	@Override
	public int getPartition(Text key, Text value, int reducers) {
		// TODO Auto-generated method stub
		String line[]=value.toString().split("\t");
		int age=new Integer(line[2]);
		
		int reducerNumber=0;
		if(reducers==0){
			return 0;
		}
		
		if(age > 15 && age <=30){
			reducerNumber=0;
		}
		else if(age >30 && age <=40){
			reducerNumber=1%reducers;
		}
		else if(age >40 && age <50){
			reducerNumber=2%reducers;
		}
		return reducerNumber;
	}

}
