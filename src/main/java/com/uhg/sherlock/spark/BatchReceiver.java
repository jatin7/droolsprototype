package com.uhg.sherlock.spark;

import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;
import com.uhg.sherlock.drools.dao.BatchProcessStateHelper;

public class BatchReceiver extends Receiver<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long batchFrequency;

	public BatchReceiver(long frequency) {
		super(StorageLevel.MEMORY_AND_DISK_2());
		batchFrequency = frequency;
	}

	@Override
	public void onStart() {
		// Start the thread that receives batchId from the batch connector
		// library.
		new Thread() {
			@Override
			public void run() {
				receive();
			}
		}.start();

	}

	@Override
	public void onStop() {

	}

	private void receive() {
		try {
			while (true) {
				Thread.sleep(this.batchFrequency);
				store(BatchProcessStateHelper.getDroolsOrcBatches());
				// For test store("P20170608097");
			}
		} catch (Exception e) {
			restart("Error receiving data", e);
		}

	}

}
