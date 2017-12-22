package com.uhg.sherlock.spark;

import com.uhg.sherlock.drools.common.Utils;
import com.uhg.sherlock.spark.functions.ExecuteBatchVoidFunction;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

import com.example.drools.model.Product;

public class SparkStreaming {

	static Logger logger = Logger.getLogger("Drools");

	public static void main(String[] args) throws Exception {
		long receiverSleepTime = Long.parseLong(Utils.getString("sherlock.drools.receiverSleepTime"));
		long streamFrequency = Long.parseLong(Utils.getString("sherlock.drools.streamingFrequency"));
		// if (args.length < 1) {
		// System.err.println("Missing argument==>Properties File Path");
		// System.exit(1);
		// }
		PropertyConfigurator
				.configure("/mapr/datalake/uhc/ei/pi_ara/sherlock_prod/applications/DROOLS/config/log4j.properties");
		SparkConf sparkConf = new SparkConf();
		JavaStreamingContext jsct = new JavaStreamingContext(sparkConf, new Duration(streamFrequency));
		logger.info("New Run! Application Id==>" + jsct.sparkContext().sc().applicationId());

		try {
			JavaReceiverInputDStream<String> batchDstream = jsct.receiverStream(new BatchReceiver(receiverSleepTime));
			batchDstream.print();
			batchDstream.foreachRDD(new ExecuteBatchVoidFunction());
			jsct.start();
			jsct.awaitTermination();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jsct != null)
				jsct.close();
		}

	}

	public static void test(String[] args) throws ClassNotFoundException {
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		Product p1 = new Product("Laptop", 10000);
		System.out.println(p1);
		// knowledgeBase.newStatelessKieSession().execute(p1);
		kieContainer.newStatelessKieSession().execute(p1);
		System.out.println(p1);

	}
}
