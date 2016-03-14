package hadoop.terasort.hawk.iit.edu;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.lib.TotalOrderPartitioner;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.util.ReflectionUtils;

public class TeraSort {
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

		Path inputPath = new Path(args[0]);
		Path outputDir = new Path(args[1]);

		// Create configuration
		Configuration conf = new Configuration(true);
		conf.addResource(new Path("/usr/local/hadoop/etc/hadoop/core-site.xml"));
		conf.addResource(new Path("/usr/local/hadoop/etc/hadoop/hdfs-site.xml"));

		long startTime = System.currentTimeMillis();

		// Create job
		Job job = Job.getInstance(conf, "TeraSort");
		job.setJarByClass(TeraSortMapper.class);

		// Setup MapReduce
		job.setMapperClass(TeraSortMapper.class);
		job.setReducerClass(TeraSortReducer.class);

		// Setup Partitioner
		job.setPartitionerClass(TotalOrderPartitioner.class);

		Path inputDir = new Path("/input");
		Path partitionFile = new Path(inputDir, "partitioning");
		TotalOrderPartitioner.setPartitionFile(job.getConfiguration(), partitionFile);

		// Specify key / value
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
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
		Integer numReduceTasks = 2;
		job.setNumReduceTasks(numReduceTasks);
		double pcnt = 10.0;
		int numSamples = numReduceTasks;
		int maxSplits = numReduceTasks - 1;
		if (0 >= maxSplits)
			maxSplits = Integer.MAX_VALUE;

		InputSampler.Sampler<Text, Text> sampler = new InputSampler.RandomSampler<Text, Text>(pcnt, numSamples,
				maxSplits);
		final InputFormat inf = ReflectionUtils.newInstance(job.getInputFormatClass(), conf);
		InputSampler.writePartitionFile(job, sampler);

		int code = job.waitForCompletion(true) ? 0 : 1;
		long endTime = System.currentTimeMillis();
		System.out.println("End");
		System.out.println("Time taken: " + (endTime - startTime));
		System.exit(code);
	}
}
