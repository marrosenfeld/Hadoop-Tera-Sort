package hadoop.terasort.hawk.iit.edu;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.lib.TotalOrderPartitioner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class TeraSort {
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

		Path inputPath = new Path(args[0]);
		Path outputDir = new Path(args[1]);

		// Create configuration
		Configuration conf = new Configuration(true);

		conf.addResource(new Path("/usr/local/hadoop/etc/hadoop/core-site.xml"));
		conf.addResource(new Path("/usr/local/hadoop/etc/hadoop/hdfs-site.xml"));

		// Create job
		Job job = Job.getInstance(conf, "TeraSort");
		job.setJarByClass(TeraSortMapper.class);

		// Setup MapReduce
		job.setMapperClass(TeraSortMapper.class);
		job.setReducerClass(TeraSortReducer.class);
		job.setNumReduceTasks(1);

		// Setup Partitioner
		job.setPartitionerClass(TotalOrderPartitioner.class);

		// Specify key / value
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// Input
		FileInputFormat.addInputPath(job, inputPath);
		job.setInputFormatClass(TextInputFormat.class);

		// Output
		FileOutputFormat.setOutputPath(job, outputDir);
		job.setOutputFormatClass(TextOutputFormat.class);

		// Delete output if exists
		FileSystem hdfs = FileSystem.get(conf);
		if (hdfs.exists(outputDir))
			hdfs.delete(outputDir, true);

		// Execute job
		int code = job.waitForCompletion(true) ? 0 : 1;
		System.exit(code);
	}
}
