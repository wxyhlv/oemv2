package com.capitalbio.oemv2.myapplication.BraceleteLib;


import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.SportDataDetailBean;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.Utils.Utils;

import java.util.Date;

/**
 * 手环数据解析
 * 
 * @author jiantao.tu
 *
 */
public final class DataAnalytical {

	/**
	 * 运动数据解析
	 * 
	 * @author jiantao.tu
	 *
	 */
	public final static class Sports {

		/**
		 * 数据后续是否还有数据
		 * 
		 * @param bytes
		 *            完整的一段运动数据
		 * @return
		 * @throws DataErrorException
		 */
		public final static boolean isHaveData(byte[] bytes)
				throws DataErrorException {
			if (bytes.length == 21 && bytes[2] == 0x05
					&& bytes[20] == (byte) 0x8F) {
				if (HexUtils.byteToBinary(bytes[3]).charAt(0) == '1') {
					return true;
				} else {
					return false;
				}
			} else {
				throw new DataErrorException();
			}
		}

		/**
		 * 获取运动数据
		 * 
		 * @param bytes
		 * @return
		 * @throws DataErrorException
		 */
		public final static SportsDetailData getSportsData(byte[] bytes)
				throws DataErrorException {
			SportsDetailData data =null;
			if (bytes.length == 21 && bytes[2] == 0x05 && bytes[20] == (byte) 0x8F) {
				data = new SportsDetailData();
				/**
				 * 此四个字节的顺序是要倒过来计算才能得到正确的值并且要加三个0才是最终的时间戳
				 */
				String time = ProtocolParser.byteReverseToInt(new byte[]{
						bytes[4], bytes[5], bytes[6], bytes[7]})
						+ "000";
				data.setSprtsDate(new Date(Long.valueOf(time)));
				data.setStepNumber(ProtocolParser.byteReverseToInt(new byte[]{
						bytes[8], bytes[9], bytes[10], bytes[11]}));
				data.setCalorie(ProtocolParser.byteReverseToInt(new byte[]{
						bytes[16], bytes[17], bytes[18], bytes[19]}));
			} else if( (bytes.length == 19 && bytes[0] == 0x6e && bytes[2] == 0x05                           //L11/L28T/W/H 返回
                  && bytes[18] == (byte) 0x8f)){
				data = new SportsDetailData();
				SportsData sData = ProtocolParser.parseSportDetailData(bytes);
				data.setSprtsDate(new Date(Long.valueOf(sData.time_stamp+ "000")));
				data.setStepNumber(sData.steps);
				data.setCalorie(sData.cal);
			}else{
				throw new DataErrorException();
			}
			return data;
		}


		/**
		 * 获取运动数据
		 *
		 * @param bytes
		 * @return
		 * @throws DataErrorException
		 */
		public final static SportDataDetailBean getSportsDataBean(byte[] bytes)
				throws DataErrorException {
			byte[] tmp = new byte[2];
			SportDataDetailBean data =null;
			if (bytes.length == 21 && bytes[2] == 0x05 && bytes[20] == (byte) 0x8F) {
				data = new SportDataDetailBean();
				/**
				 * 此四个字节的顺序是要倒过来计算才能得到正确的值并且要加三个0才是最终的时间戳
				 */
				String time = ProtocolParser.byteReverseToInt(new byte[] {
						bytes[4], bytes[5], bytes[6], bytes[7]})
						+ "000";
				data.setLongTime(Long.parseLong(time));
				data.setYmd(DateUtil.getDateToString(new Date(Long.valueOf(time)), DateUtil.YMD));
				data.setHour(TimeStampUtil.getHour(Long.parseLong(time)) + "");
				data.setSamplePointTime(DateUtil.getDateToString(new Date(Long.valueOf(time)), DateUtil.YMDHM));
				data.setSteps(ProtocolParser.byteReverseToInt(new byte[] {
						bytes[8], bytes[9], bytes[10], bytes[11]}));
				data.setCal(ProtocolParser.byteReverseToInt(new byte[]{
						bytes[16], bytes[17], bytes[18], bytes[19]}));
			} else if((bytes.length == 19 && bytes[0] == 0x6e && bytes[2] == 0x05                           //L11/L28T/W/H 返回
					&& bytes[18] == (byte) 0x8f)) {
				data = new SportDataDetailBean();
				SportsData sData = ProtocolParser.parseSportDetailData(bytes);
				Date sampleData = new Date(Long.valueOf(sData.time_stamp + "000"));
				long testLongTime = Long.valueOf(sData.time_stamp + "000");
				data.setSamplePointTime(DateUtil.getDateToString(new Date(Long.valueOf(sData.time_stamp + "000")), DateUtil.YMDHM));
				data.setYmd(TimeStampUtil.currTime3(String.valueOf(testLongTime)));
				data.setHour(String.valueOf(sampleData.getHours()));
				data.setSteps(sData.steps);
				data.setCal(sData.cal);
				data.setDay(TimeStampUtil.getDay(testLongTime) + "");
				data.setLongTime(testLongTime);
				data.setDeviceId(PreferencesUtils.getString(MyApplication.getInstance(), "default_bracelete_address", ""));
				tmp[0] = bytes[17];
				tmp[1] = bytes[16];
				data.setIndex(Utils.changeByteArrayToInt(tmp));
				data.setCtime(TimeStampUtil.currTime2(0));
			} else {
				throw new DataErrorException();
			}
			return data;
		}
	}

	/**
	 * 睡眠数据解析
	 * 
	 * @author jiantao.tu
	 *
	 */
	public final static class Sleep {

		/**
		 * 检测接受类型为0x13的数据后续是否还有同样得的数据,如果没有就开始接受0x14类型的数据且只接受一次
		 * 
		 * @param bytes
		 *            完整的一段运动数据
		 * @return
		 * @throws DataErrorException
		 */
		public final static boolean isHave13Data(byte[] bytes)
				throws DataErrorException {
			if (bytes.length == 9 && bytes[2] == 0x13
					&& bytes[8] == (byte) 0x8F) {
				if (bytes[3] == 0x11) {
					return false;
				} else {
					return true;
				}
			} else {
				throw new DataErrorException();
			}
		}

		/**
		 * 获取睡眠记录数据
		 * @param bytes
		 * @return
		 * @throws DataErrorException
		 */
		public final static SleepDataTotalBean.SleepRecordData getSleepRecordData(byte[] bytes)
				throws DataErrorException {
			if (bytes.length == 9 && bytes[2] == 0x13
					&& bytes[8] == (byte) 0x8F) {
				SleepDataTotalBean.SleepRecordData data = new SleepDataTotalBean.SleepRecordData();
				if (bytes[3] == SleepRecords.DOZEOFF.getHex()) {
					data.setSleepRecords(SleepRecords.DOZEOFF);
				} else if (bytes[3] == SleepRecords.SOMNOLENCE.getHex()) {
					data.setSleepRecords(SleepRecords.SOMNOLENCE);
				} else if (bytes[3] == SleepRecords.AWAKE.getHex()) {
					data.setSleepRecords(SleepRecords.AWAKE);
				} else if (bytes[3] == SleepRecords.READY_SLEEP.getHex()) {
					data.setSleepRecords(SleepRecords.READY_SLEEP);
				} else if (bytes[3] == SleepRecords.EXIT_SLEEP.getHex()) {
					data.setSleepRecords(SleepRecords.EXIT_SLEEP);
				} else if (bytes[3] == SleepRecords.ENTER_SLEEP.getHex()) {
					data.setSleepRecords(SleepRecords.ENTER_SLEEP);
				} else if (bytes[3] == SleepRecords.EXIT_ENTER_SLEEP.getHex()) {
					data.setSleepRecords(SleepRecords.EXIT_ENTER_SLEEP);
				}
				String time = ProtocolParser.byteReverseToInt(new byte[]{
						bytes[4], bytes[5], bytes[6], bytes[7]})
						+ "000";
				data.setTime(new Date(Long.valueOf(time)));
				return data;
			} else {
				throw new DataErrorException();
			}
		}
		
		/**
		 * 获取睡眠总记录数
		 * @param bytes
		 * @return
		 * @throws DataErrorException
		 */
		public final static SleepDataTotalBean getSleepSumData(byte[] bytes) throws DataErrorException {
			if(bytes.length ==17 && bytes[2] == 0x14 && bytes[16] == (byte) 0x8F){
				SleepDataTotalBean data=new SleepDataTotalBean();
				//[start]入睡时间
				String time = ProtocolParser.byteReverseToInt(new byte[]{
						bytes[4], bytes[5]})+"";
				data.setGotoSleepTime(Integer.valueOf(time));
				//[end]

				//[start]清醒时间
				time = ProtocolParser.byteReverseToInt(new byte[]{
						bytes[6], bytes[7]})+"";
				data.setSoberTime(Integer.valueOf(time));
				//[end]
				
				//[start]浅睡时间
				time = ProtocolParser.byteReverseToInt(new byte[]{
						bytes[8], bytes[9]})+"";
				data.setSomnolenceTime(Integer.valueOf(time));
				//[end]
				
				//[start]深睡时间
				time = ProtocolParser.byteReverseToInt(new byte[]{
						bytes[10], bytes[11]})+"";
				data.setSeepSleepTime(Integer.valueOf(time));
				//[end]
				
				//[start]唤醒次数
				int num = ProtocolParser.byteReverseToInt(new byte[]{
						bytes[12], bytes[13]});
				data.setRouseNumber(num);
				//[end]
				
				//[start]总时间
				time = ProtocolParser.byteReverseToInt(new byte[]{
						bytes[14], bytes[15]})+"";
				data.setSumNumber(Integer.valueOf(time));
				//[end]

				data.setIsUpload(false);

				//设置数据来源信息
				data.setDataSource("手环");

				data.setCtime(TimeStampUtil.currTime2(0));

				return data;
			}else{
				throw new DataErrorException();
			}
			
		}
	}

	public static SleepDetailData getSleepDetailData(byte[] bytes)
			throws DataErrorException {
		if (bytes.length == 9 && bytes[2] == 0x13
				&& bytes[8] == (byte) 0x8F) {
			SleepDetailData data = new SleepDetailData();
			if (bytes[3] == SleepRecords.DOZEOFF.getHex()) {
				data.setSleepType(SleepRecords.DOZEOFF.name);
			} else if (bytes[3] == SleepRecords.SOMNOLENCE.getHex()) {
				data.setSleepType(SleepRecords.SOMNOLENCE.name);
			} else if (bytes[3] == SleepRecords.AWAKE.getHex()) {
				data.setSleepType(SleepRecords.AWAKE.name);
			} else if (bytes[3] == SleepRecords.READY_SLEEP.getHex()) {
				data.setSleepType(SleepRecords.READY_SLEEP.name);
			} else if (bytes[3] == SleepRecords.EXIT_SLEEP.getHex()) {
				data.setSleepType(SleepRecords.EXIT_SLEEP.name);
			} else if (bytes[3] == SleepRecords.ENTER_SLEEP.getHex()) {
				data.setSleepType(SleepRecords.ENTER_SLEEP.name);
			} else if (bytes[3] == SleepRecords.EXIT_ENTER_SLEEP.getHex()) {
				data.setSleepType(SleepRecords.EXIT_ENTER_SLEEP.name);
			}
			String time = ProtocolParser.byteReverseToInt(new byte[]{
					bytes[4], bytes[5], bytes[6], bytes[7]})
					+ "000";
			data.setLongSleepTime(Long.valueOf(time));
			String fomatTime = DateUtil.getDateToString(new Date(Long.valueOf(time)), DateUtil.YMDHM);
			data.setFomatTime(fomatTime);
			data.setUserName(MyApplication.getInstance().getCurrentUserName());
			data.setYmd(TimeStampUtil.currTime3(Long.valueOf(time)));
			data.setHour(TimeStampUtil.getHour(Long.parseLong(time)) + "");
			data.setUserName(MyApplication.getInstance().getCurrentUserName());
			data.setIsUpload(false);
			data.setDataSource("手环");


			data.setCtime(TimeStampUtil.currTime2(0));

			return data;
		} else {
			throw new DataErrorException();
		}
	}
}