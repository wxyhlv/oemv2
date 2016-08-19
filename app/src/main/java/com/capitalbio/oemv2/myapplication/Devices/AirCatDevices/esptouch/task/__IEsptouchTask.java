package com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.task;

import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchListener;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchResult;

import java.util.List;


/**
 * IEsptouchTask defined the task of esptouch should offer. INTERVAL here means
 * the milliseconds of interval of the step. REPEAT here means the repeat times
 * of the step.
 * 
 * @author afunx
 * 
 */
public interface __IEsptouchTask {

	/**
	 * set the esptouch listener, when one device is connected to the Ap, it will be called back
	 * @param esptouchListener when one device is connected to the Ap, it will be called back
	 */
	void setEsptouchListener(IEsptouchListener esptouchListener);
	
	/**
	 * Interrupt the Esptouch Task when User tap back or close the Application.
	 */
	void interrupt();

	/**
	 * Note: !!!Don't call the task at UI Main Thread or RuntimeException will
	 * be thrown Execute the Esptouch Task and return the result
	 * 
	 * @return the IEsptouchResult
	 * @throws RuntimeException
	 */
	IEsptouchResult executeForResult() throws RuntimeException;

	/**
	 * Note: !!!Don't call the task at UI Main Thread or RuntimeException will
	 * be thrown Execute the Esptouch Task and return the result
	 * 
	 * @param expectTaskResultCount
	 *            the expect result count(if expectTaskResultCount <= 0,
	 *            expectTaskResultCount = Integer.MAX_VALUE)
	 * @return the list of IEsptouchResult
	 * @throws RuntimeException
	 */
	List<IEsptouchResult> executeForResults(int expectTaskResultCount) throws RuntimeException;
	
	/**
	 * Turn on or off the log.
	 */
	static final boolean DEBUG = true;

	boolean isCancelled();

    List<IEsptouchResult> executeForSearchMacResults(int expectTaskResultCount)
            throws RuntimeException;
}
