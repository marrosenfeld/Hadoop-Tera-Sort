package hadoop.terasort.hawk.iit.edu;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TeraSortReducer extends Reducer<Text, Text, Text, Text> {
	public void reduce(Text text, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		for (Text value : values) {
			context.write(text, value);
		}

	}

}
