package com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch;

import android.content.Context;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.command.Constant;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.task.EsptouchTaskParameter;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.task.IEsptouchTaskParameter;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.task.__EsptouchTask;

import java.util.List;

public class EsptouchTask implements IEsptouchTask {

	public __EsptouchTask _mEsptouchTask;
	private IEsptouchTaskParameter _mParameter;
	
	private int mTaskType;

	private ISetTargetApCallBack setTargetApCallBack;
	private ISearchMacCallBack searchMacCallBack;


	/**
	 * Constructor of EsptouchTask
	 * 
	 * @param apSsid
	 *            the Ap's ssid
	 * @param apBssid
	 *            the Ap's bssid
	 * @param apPassword
	 *            the Ap's password
	 * @param isSsidHidden
	 *            whether the Ap's ssid is hidden
	 * @param context
	 *            the Context of the Application
	 */
	//构造参数对象和真正的任务操作对象
	public EsptouchTask(String apSsid, String apBssid, String apPassword,
			boolean isSsidHidden, Context context, int taskType, ISetTargetApCallBack setTargetApCallBack, ISearchMacCallBack searchMacCallBack) {
	    
	    mTaskType = taskType;
	    
	    if (mTaskType == Constant.AIRCAT_SET_AP_COMMAND) {
	        //设置ap命令参数
	        _mParameter = new EsptouchTaskParameter(Constant.AIRCAT_SET_AP_COMMAND_LISTEN_PORT, Constant.AIRCAT_SET_AP_COMMAND_TARGET_PORT);
	      //构造真正的任务对象，和参数对象一起完成广播的监听和发送
	        _mEsptouchTask = new __EsptouchTask(apSsid, apBssid, apPassword,
	                context, _mParameter, isSsidHidden, setTargetApCallBack);
        } else if (mTaskType == Constant.AIRCAT_SEARCH_COMMAND) {
            //搜索命令参数
            _mParameter = new EsptouchTaskParameter(Constant.AIRCAT_SEARCH_COMMAND_LISTEN_PORT, Constant.AIRCAT_SEARCH_COMMAND_TARGET_PORT);
          //构造真正的任务对象，和参数对象一起完成广播的监听和发送
            _mEsptouchTask = new __EsptouchTask(context, _mParameter, isSsidHidden, searchMacCallBack);
        }
	}

	/**
	 * Constructor of EsptouchTask
	 * 
	 * @param apSsid
	 *            the Ap's ssid
	 * @param apBssid
	 *            the Ap's bssid
	 * @param apPassword
	 *            the Ap's password
	 * @param isSsidHidden
	 *            whether the Ap's ssid is hidden
	 * @param timeoutMillisecond
	 *            (it should be >= 15000+6000) millisecond of total timeout
	 * @param context
	 *            the Context of the Application
	 */
	public EsptouchTask(String apSsid, String apBssid, String apPassword,
			boolean isSsidHidden, int timeoutMillisecond, Context context) {
	    //保证原来的逻辑不受影响
		_mParameter = new EsptouchTaskParameter(Constant.AIRCAT_SET_AP_COMMAND_LISTEN_PORT, Constant.AIRCAT_SET_AP_COMMAND_TARGET_PORT);
		_mParameter.setWaitUdpTotalMillisecond(timeoutMillisecond);
		_mEsptouchTask = new __EsptouchTask(apSsid, apBssid, apPassword,
				context, _mParameter, isSsidHidden, null);
	}

	@Override
	public void interrupt() {
		_mEsptouchTask.interrupt();
	}

	@Override
	public IEsptouchResult executeForResult() throws RuntimeException {
		return _mEsptouchTask.executeForResult();
	}

	@Override
	public boolean isCancelled() {
		return _mEsptouchTask.isCancelled();
	}

	@Override
	public void setEsptouchListener(IEsptouchListener listener, ISetTargetApCallBack setTargetApCallBack, ISearchMacCallBack searchMacCallBack) {
		_mEsptouchTask.setEsptouchListener(listener);
		this.setTargetApCallBack = setTargetApCallBack;
		this.searchMacCallBack = searchMacCallBack;
	}


	@Override
	public List<IEsptouchResult> executeForResults(int expectTaskResultCount)
			throws RuntimeException {
		if (expectTaskResultCount <= 0) {
			expectTaskResultCount = Integer.MAX_VALUE;
		}
		
		if (_mParameter.getPortListening() == Constant.AIRCAT_SEARCH_COMMAND_LISTEN_PORT) {
            return _mEsptouchTask.executeForSearchMacResults(expectTaskResultCount);
        } else {
            return _mEsptouchTask.executeForResults(expectTaskResultCount);
        }
	}

	@Override
	public void setEsptouchListener(IEsptouchListener esptouchListener) {
		_mEsptouchTask.setEsptouchListener(esptouchListener);
	}

	public void setSetTargetApCallBack(ISetTargetApCallBack setTargetApCallBack) {
		this.setTargetApCallBack = setTargetApCallBack;
	}

	public void setSearchMacCallBack(ISearchMacCallBack searchMacCallBack) {
		this.searchMacCallBack = searchMacCallBack;
	}
}
