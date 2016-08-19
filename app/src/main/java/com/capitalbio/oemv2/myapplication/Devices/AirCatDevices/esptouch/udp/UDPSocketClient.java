package com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.udp;

import android.util.Log;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.command.Constant;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.task.__IEsptouchTask;

import java.io.IOException;
import java.net.*;

/**
 * this class is used to help send UDP data according to length
 * 
 * @author afunx
 * 
 */
public class UDPSocketClient {

	private static final String TAG = "UDPSocketClient";
	private DatagramSocket mSocket;
	private volatile boolean mIsStop;
	private volatile boolean mIsClosed;

	public UDPSocketClient() {
		try {
			this.mSocket = new DatagramSocket();
			this.mIsStop = false;
			this.mIsClosed = false;
		} catch (SocketException e) {
			if (__IEsptouchTask.DEBUG) {
				Log.e(TAG, "SocketException");
			}
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	public void interrupt() {
		if (__IEsptouchTask.DEBUG) {
			Log.i(TAG, "USPSocketClient is interrupt");
		}

		this.mIsStop = true;
	}

	/**
	 * close the UDP socket
	 */
	public synchronized void close() {
		if (!this.mIsClosed) {
			this.mSocket.close();
			this.mIsClosed = true;
		}
	}

	/**
	 * send the data by UDP
	 * 
	 * @param data
	 *            the data to be sent
	 * @param targetHost
	 *            the host name of target, e.g. 192.168.1.101
	 * @param targetPort
	 *            the port of target
	 * @param interval
	 *            the milliseconds to between each UDP sent
	 */
	public void sendData(byte[][] data, String targetHostName, int targetPort,
			long interval) {
	    Log.d(TAG, "client send data , targetHostName is " + targetHostName + " targetPort is " + targetPort);
	    
	    if (targetPort == Constant.AIRCAT_SET_AP_COMMAND_TARGET_PORT) {
	        sendData(data, 0, data.length, targetHostName, targetPort, interval);
        } else {
            //向空气猫发送的新的命令
            byte[] command = new byte[] {(byte)0xaa, (byte)0x04, (byte)0x00, 
                                           (byte)0x11, (byte)0x00, (byte)0x0e,
                                           (byte)0x31, (byte)0x30, (byte)0x31,
                                           (byte)0x2e, (byte)0x32, (byte)0x30,
											(byte)0x30, (byte)0x2E,
                                           (byte)0x31, (byte)0x38, (byte)0x32,
					                       (byte)0x2E,   (byte)0x31,(byte)0x31,(byte)0x32,(byte)0xEB, (byte)0x00};
            sendQueryPacket(command, 9, targetHostName, targetPort, interval);
        }
		
	}
	
	
	/**
	 * send the data by UDP
	 * 
	 * @param data
	 *            the data to be sent
	 * @param offset
	 * 			  the offset which data to be sent
	 * @param count
	 * 			  the count of the data
	 * @param targetHost
	 *            the host name of target, e.g. 192.168.1.101
	 * @param targetPort
	 *            the port of target
	 * @param interval
	 *            the milliseconds to between each UDP sent
	 */
	public void sendData(byte[][] data, int offset, int count,
			String targetHostName, int targetPort, long interval) {
	    Log.d("MyTest", "target host is " + targetHostName + " port is " + targetPort);
		if ((data == null) || (data.length <= 0)) {
			if (__IEsptouchTask.DEBUG) {
				Log.e(TAG, "sendData(): data == null or length <= 0");
			}
			return;
		}
		//用一个for循环将二维数组发送到指定的端口号上
		for (int i = offset; !mIsStop && i < offset + count; i++) {

			if (data[i].length == 0) {
				continue;
			}
			try {
				 Log.i(TAG, "data[" + i + " +].length = " + data[i].length);
				DatagramPacket localDatagramPacket = new DatagramPacket(
						data[i], data[i].length,
						InetAddress.getByName(targetHostName), targetPort);
				this.mSocket.send(localDatagramPacket);
			} catch (UnknownHostException e) {
				if (__IEsptouchTask.DEBUG) {
					Log.e(TAG, "sendData(): UnknownHostException");
				}
				e.printStackTrace();
				mIsStop = true;
				break;
			} catch (IOException e) {
				if (__IEsptouchTask.DEBUG) {
					Log.e(TAG, "sendData(): IOException, but just ignore it");
				}
				// for the Ap will make some troubles when the phone send too many UDP packets,
	            // but we don't expect the UDP packet received by others, so just ignore it
			}
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
				if (__IEsptouchTask.DEBUG) {
					Log.e(TAG, "sendData is Interrupted");
				}
				mIsStop = true;
				break;
			}
		}
		if (mIsStop) {
			close();
		}
	}
	
	
	public void sendQueryPacket(byte[]data, int count,
            String targetHostName, int targetPort, long interval) {
        Log.d("MyTest1", "target host is " + targetHostName + " port is " + targetPort);
        if ((data == null) || (data.length <= 0)) {
            if (__IEsptouchTask.DEBUG) {
                Log.e(TAG, "sendData(): data == null or length <= 0");
            }
            return;
        }
        //发指定次数的广播
        for (int i = 0; !mIsStop && i < count; i++) {
            try {
                DatagramPacket localDatagramPacket = new DatagramPacket(
                        data, data.length,
                        InetAddress.getByName(targetHostName), targetPort);
                this.mSocket.send(localDatagramPacket);
            } catch (UnknownHostException e) {
                if (__IEsptouchTask.DEBUG) {
                    Log.e(TAG, "sendData(): UnknownHostException");
                }
                e.printStackTrace();
                mIsStop = true;
                break;
            } catch (IOException e) {
                if (__IEsptouchTask.DEBUG) {
                    Log.e(TAG, "sendData(): IOException, but just ignore it");
                }
                // for the Ap will make some troubles when the phone send too many UDP packets,
                // but we don't expect the UDP packet received by others, so just ignore it
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
                if (__IEsptouchTask.DEBUG) {
                    Log.e(TAG, "sendData is Interrupted");
                }
                mIsStop = true;
                break;
            }
        }
        if (mIsStop) {
            close();
        }
    }
}
