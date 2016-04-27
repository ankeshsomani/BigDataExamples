import java.util.HashSet;

import org.apache.hadoop.io.Text;


public class Reducer extends org.apache.hadoop.mapreduce.Reducer
<Text, Text, Text, Text>{
	public static final String delimeter=",";
	public static final String underscore="_";
	public static final String tab="\t";
	protected void reduce(Text key, java.lang.Iterable<Text> values, 
			org.apache.hadoop.mapreduce.Reducer<Text,Text,Text,Text>.Context context) throws java.io.IOException ,InterruptedException {
		HashSet<Long> uniqueCustomers=new HashSet<Long>();
		Double totalSaleAmount=0.0;
		String outValue="";
		for(Text value:values){
			String record[]=value.toString().split(delimeter);
			uniqueCustomers.add(Long.parseLong(record[4]));
			totalSaleAmount=totalSaleAmount+Double.parseDouble(record[9]);
			outValue=tab+"(UniqueCustomers,"+uniqueCustomers.size()+")"+tab+"TotalSalesAmount,"+totalSaleAmount.longValue()+")";			
		}
		context.write(key, new Text(outValue));
		
	};
	
}
