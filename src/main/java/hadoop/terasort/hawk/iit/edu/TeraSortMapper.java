package hadoop.terasort.hawk.iit.edu;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TeraSortMapper extends Mapper<Object, Text, Text, Text> {

	private Text recordKey = new Text();
	private Text recordValue = new Text();

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

		String lines = value.toString();
		String[] lineArr = lines.split("\n");

		for (String line : lineArr) {
			recordKey.set(key.toString().substring(0, 10));
			recordValue.set(key.toString().substring(10, 98));
			context.write(recordKey, recordValue);
		}
	}
}
