package com.capitalbio.oemv2.myapplication.Devices.Bracelete;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.Base.Const;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;
import com.capitalbio.oemv2.myapplication.Bean.SportDataDetailBean;
import com.capitalbio.oemv2.myapplication.BraceleteLib.SleepDataTotalBean;
import com.capitalbio.oemv2.myapplication.BraceleteLib.SleepDetailData;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.bean.BodyFatSaid;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.db.sqlite.SqlInfo;

import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * Created by chengkun on 15-12-14.
 */
public class UploadOperation {

    public static final String TAG = "UploadOperation";

    public final static int UPLOAD_SPORT_DETAIL_DATA_COMEPLETE = 2000;
    public final static int UPLOAD_SLEEP_DETAIL_DATA_COMEPLETE = 2001;
    public final static int UPLOAD_SLEEP_TOTAL_DATA_COMEPLETE = 2002;
    public final static int UPLOAD_BODY_FAT_DATA_COMEPLETE = 2003;



    private final static int UPLOAD_BODYFAT_DATA_COMEPLETE = 3000;

    private final static int UPLOAD_BLOOD_PRESS_DATA_COMEPLETE = 30001;


    Handler callbackHandler;

    private Context mContext = MyApplication.getInstance();

    //运动明细数据
    private List<SportDataDetailBean> sportDataDetailBeanList;

    //睡眠明细数据
    private List<SleepDetailData> sleepDetailDataList;

    //血压数据
    private List<BloodPressureBean> bloodPressureBeanList;

    //睡眠总数据
    private List<SleepDataTotalBean> sleepDataTotalBeanList;

    //体脂数据
    private List<BodyFatSaid> bodyFatSaidList;

    private DbManager dbManager = MyApplication.getDB();

    //睡眠谁据上传json
    private JSONObject sleepDataDetailUploadJson = new JSONObject();
    private JSONObject sleepDataDetailDataJson = new JSONObject();

    //运动数据上传json
    private JSONObject sportDataUploadJson = new JSONObject();
    private JSONObject sportDataJson = new JSONObject();

    //血压数据上传json
    private JSONObject bloodPressUploadJson = new JSONObject();
    private JSONObject bloodPressDataJson = new JSONObject();

    //体脂数据上传json
    private JSONObject bodyFatUploadJson = new JSONObject();
    private JSONObject bodyFatDataJson = new JSONObject();


    //睡眠数据上传json
    private JSONObject sleepDataTotalUploadJson = new JSONObject();
    private JSONObject sleepDataTotalDataJson = new JSONObject();


    private Iterator<SportDataDetailBean> sportDataDetailBeanIterator;

    private Iterator<SleepDetailData> sleepDetailIterator;

    private Iterator<BloodPressureBean> iteratorBloodPress;

    private Iterator<BodyFatSaid> iteratorBodyFat;

    private Iterator<SleepDataTotalBean> iteratorSleepTotal;





    SportDataDetailBean bean;;


    private boolean isUploading = false;


    private Handler uploadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPLOAD_SPORT_DETAIL_DATA_COMEPLETE:
                    uploadSportData();
                    break;
                case UPLOAD_SLEEP_DETAIL_DATA_COMEPLETE:
                    uploadSleepData();
                    break;
                case UPLOAD_BLOOD_PRESS_DATA_COMEPLETE:
                    uploadBloodPressData();
                    break;
                case UPLOAD_SLEEP_TOTAL_DATA_COMEPLETE:
                    uploadSleepToataData();
                    break;
                case UPLOAD_BODY_FAT_DATA_COMEPLETE:
                    uploadBodyFatData();
                    break;
                default:
                    break;
            }
        }
    };

    public UploadOperation() {

    }

    public void uploadData() {

        OemLog.log(TAG, "upload data");

        String username = MyApplication.getInstance().getCurrentUserName();

        try {
            sportDataDetailBeanList = dbManager.selector(SportDataDetailBean.class).where("isUpload", "=", "0").and("steps", " > ", "0").and("username", "=", username).findAll();

            sleepDetailDataList = dbManager.selector(SleepDetailData.class).where("isUpload", "=", "0").and("userName", "=", username).findAll();

            bloodPressureBeanList = dbManager.selector(BloodPressureBean.class).where("isUpload", "=", "0").and("username", "=", username).findAll();

            bodyFatSaidList = dbManager.selector(BodyFatSaid.class).where("isUpload", "=", "0").and("userName", "=", username).findAll();

            sleepDataTotalBeanList = dbManager.selector(SleepDataTotalBean.class).where("isUpload", "=", "0").and("username", "=", username).findAll();


            //给要上传的json提供头信息
            fillHearderInfo(sleepDataDetailUploadJson);
            fillHearderInfo(sportDataUploadJson);
            fillHearderInfo(bloodPressUploadJson);
            fillHearderInfo(bodyFatUploadJson);
            fillHearderInfo(sleepDataTotalUploadJson);


            //这里可以保证只上传有用户名的数据
            if (NetTool.isNetwork(mContext, true) && MyApplication.getInstance().getCurrentUserName() != null) {
                //运动数据上传
                if (sportDataDetailBeanList != null) {
                    OemLog.log(TAG, "upload sport data...");
                    sportDataDetailBeanIterator = sportDataDetailBeanList.iterator();
                    uploadSportData();
                }
                //睡眠数据上传
                if (sleepDetailDataList != null) {
                    OemLog.log(TAG, "upload sleep detail data...");
                    sleepDetailIterator = sleepDetailDataList.iterator();
                    uploadSleepData();
                }
                if (bloodPressureBeanList != null) {
                    OemLog.log(TAG, "upload bloodpress data...");
                    iteratorBloodPress = bloodPressureBeanList.iterator();
                    uploadBloodPressData();
                }
                if (bodyFatSaidList != null) {
                    OemLog.log(TAG, "upload bodyfat data...");
                    iteratorBodyFat = bodyFatSaidList.iterator();
                    uploadBodyFatData();
                }

                if (sleepDataTotalBeanList != null) {
                    OemLog.log(TAG, "upload sleep total data...");
                    iteratorSleepTotal = sleepDataTotalBeanList.iterator();
                    uploadSleepToataData();
                }
            }
            callbackHandler.sendEmptyMessageDelayed(Const.UPLOAD_DATA_DELAY_MESAGE, Const.UPLOAD_DATA_DELAY_TIME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCallbackHandler(Handler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    private void uploadSportData() {

        OemLog.log(TAG, "upload sportDataDetailBeanList size is " + sportDataDetailBeanList.size() + ", has next is " + sportDataDetailBeanIterator.hasNext() + ", is uploading is " + isUploading);
        try {
            SportDataDetailBean sp;
            if (!isUploading && sportDataDetailBeanIterator.hasNext()) {
                isUploading = true;
                sp = sportDataDetailBeanIterator.next();
                sportDataJson.put("steps", sp.getSteps());
                sportDataJson.put("distance", sp.getDistance());
                sportDataJson.put("cal", sp.getCal());
                sportDataJson.put("testTime", sp.getLongTime());
                sportDataJson.put("modelType", "braceletSports");
                sportDataUploadJson.put("data", sportDataJson.toString());
                OemLog.log(TAG, "upload json is " + sportDataUploadJson.toString());
                HttpTools.post(mContext, Base_Url.Url_Base + Base_Url.uploadData_Url, sportDataUploadJson, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        OemLog.log(TAG, "uploadSportData success result is " + new String(responseBody));

                        try {
                            OemLog.log(TAG, "update testTime is " + sportDataJson.getLong("testTime"));
                            isUploading = false;
                            uploadHandler.sendEmptyMessage(UPLOAD_SPORT_DETAIL_DATA_COMEPLETE);
//                            dbManager.update(SleepDetailData.class, WhereBuilder.b("longTime", "=", sportDataJson.getLong("testTime")), new KeyValue("isUpload", 1));
                            dbManager.executeUpdateDelete(new SqlInfo("update sportdatadetailbean  set isUpload = 1 where longTime = " + sportDataJson.getLong("testTime") + ';'));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                        OemLog.log(TAG, "uploadSportData failurre result is " + new String(responseBody));
                        isUploading = false;
                        uploadHandler.sendEmptyMessage(UPLOAD_SPORT_DETAIL_DATA_COMEPLETE);
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                        isUploading = false;
                        uploadHandler.sendEmptyMessage(UPLOAD_SPORT_DETAIL_DATA_COMEPLETE);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadSleepData() {

        try {
                if (!isUploading && sleepDetailIterator.hasNext()) {
                    isUploading = true;
                    SleepDetailData sleepDetail = sleepDetailIterator.next();
                    sleepDataDetailDataJson.put("sleepType", sleepDetail.getSleepType());
                    sleepDataDetailDataJson.put("testTime", sleepDetail.getLongSleepTime());
                    sleepDataDetailDataJson.put("modelType", "braceletSleep");
                    sleepDataDetailUploadJson.put("data", sleepDataDetailDataJson.toString());

                    HttpTools.post(mContext, Base_Url.Url_Base + Base_Url.uploadData_Url, sleepDataDetailUploadJson, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            OemLog.log(TAG, "uploadSleepData success result is " + new String(responseBody));
                            try {
                                isUploading = false;
                                uploadHandler.sendEmptyMessage(UPLOAD_SLEEP_DETAIL_DATA_COMEPLETE);
                                dbManager.executeUpdateDelete(new SqlInfo("update sleepDetailData  set isUpload = 1 where longSleepTime = " + sleepDataDetailDataJson.getLong("testTime") + ';'));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                           // OemLog.log(TAG, "uploadSleepData failurre result is " + new String(responseBody));
                            isUploading = false;
                            uploadHandler.sendEmptyMessage(UPLOAD_SLEEP_DETAIL_DATA_COMEPLETE);
                        }

                        @Override
                        public void onCancel() {
                            super.onCancel();
                            isUploading = false;
                            uploadHandler.sendEmptyMessage(UPLOAD_SLEEP_DETAIL_DATA_COMEPLETE);
                        }
                    });
                }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void uploadBloodPressData() {
        try {
                if (!isUploading && iteratorBloodPress.hasNext()) {
                    isUploading = true;
                    BloodPressureBean blood = iteratorBloodPress.next();
                    bloodPressDataJson.put("highPressure", blood.getSysBp());
                    bloodPressDataJson.put("lowPressure", blood.getDiaBp());
                    bloodPressDataJson.put("heartRate", blood.getHeartRate());
                    bloodPressDataJson.put("testTime", blood.getTime());
                    bloodPressDataJson.put("modelType", "bloodpressure");

                    bloodPressUploadJson.put("data", bloodPressDataJson.toString());

                    Log.i("info","====测量上传数据的entity===="+bloodPressUploadJson.toString());

                    HttpTools.post(mContext, Base_Url.Url_Base + Base_Url.uploadData_Url, bloodPressUploadJson, new AsyncHttpResponseHandler() {


                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            OemLog.log(TAG, "uploadBloodPressData success result is " + new String(responseBody));
                            try {
                                isUploading = false;
                                uploadHandler.sendEmptyMessage(UPLOAD_BLOOD_PRESS_DATA_COMEPLETE);
                                dbManager.executeUpdateDelete(new SqlInfo("update bloodPressure  set isUpload = 1 where testTime = " + bloodPressDataJson.getLong("testTime") + ';'));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                           // OemLog.log(TAG, "uploadBloodPressData failurre result is " + new String(responseBody));
                            isUploading = false;
                            uploadHandler.sendEmptyMessage(UPLOAD_BLOOD_PRESS_DATA_COMEPLETE);
                        }

                        @Override
                        public void onCancel() {
                            super.onCancel();
                            isUploading = false;
                            uploadHandler.sendEmptyMessage(UPLOAD_BLOOD_PRESS_DATA_COMEPLETE);
                        }
                    });
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadBodyFatData() {

        try {
                if (!isUploading && iteratorBodyFat.hasNext()) {
                    isUploading = true;
                    BodyFatSaid bodyfat = iteratorBodyFat.next();

                    bodyFatDataJson.put("weight", bodyfat.getWeight());
                    bodyFatDataJson.put("bmi", bodyfat.getBmi());
                    //bmr
                    bodyFatDataJson.put("bmr", bodyfat.getKcal());
                    bodyFatDataJson.put("fat", bodyfat.getFat());
                    bodyFatDataJson.put("visceralLevel", bodyfat.getVisceraFat());
                    bodyFatDataJson.put("water", bodyfat.getWater());
                    bodyFatDataJson.put("muscle", bodyfat.getMuscle());
                    bodyFatDataJson.put("bone", bodyfat.getBone());
                    bodyFatDataJson.put("motorPattern", bodyfat.getSportMode());
                    bodyFatDataJson.put("modelType", "bodyfat");
                    bodyFatDataJson.put("testTime", bodyfat.getLongTime());

                    bodyFatUploadJson.put("data", bodyFatDataJson.toString());

                    HttpTools.post(mContext, Base_Url.Url_Base + Base_Url.uploadData_Url, bodyFatUploadJson, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            OemLog.log(TAG, "uploadBodyFatData success result is " + new String(responseBody));
                            try {
                                isUploading = false;
                                uploadHandler.sendEmptyMessage(UPLOAD_BODY_FAT_DATA_COMEPLETE);
                                dbManager.executeUpdateDelete(new SqlInfo("update bodyfatsaid  set isUpload = 1 where longTime = " + bodyFatDataJson.getLong("testTime") + ';'));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            isUploading = false;
                            uploadHandler.sendEmptyMessage(UPLOAD_BODY_FAT_DATA_COMEPLETE);
                          //  OemLog.log(TAG, "uploadBodyFatData failurre result is " + new String(responseBody));
                        }

                        @Override
                        public void onCancel() {
                            super.onCancel();
                            isUploading = false;
                            uploadHandler.sendEmptyMessage(UPLOAD_BODY_FAT_DATA_COMEPLETE);
                        }
                    });
                }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private JSONObject fillHearderInfo(JSONObject jsonObject) {
        try {
            jsonObject.put("apikey", MyApplication.getInstance().apikey);
            jsonObject.put("username", MyApplication.getInstance().getCurrentUserName());
            jsonObject.put("token", MyApplication.getInstance().getCurentToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }


    private void uploadSleepToataData() {
        try {
                if (!isUploading && iteratorSleepTotal.hasNext()) {
                    isUploading = true;
                    SleepDataTotalBean sleepDataTotalBean = iteratorSleepTotal.next();

                    sleepDataTotalDataJson.put("sleepQuality", "0");
                    sleepDataTotalDataJson.put("timeToSleep", sleepDataTotalBean.getGotoSleepTime());
                    sleepDataTotalDataJson.put("shallowSleepTime", sleepDataTotalBean.getSomnolenceTime());
                    sleepDataTotalDataJson.put("deepSleepTime", sleepDataTotalBean.getSeepSleepTime());
                    sleepDataTotalDataJson.put("awakeTime", sleepDataTotalBean.getSoberTime());
                    sleepDataTotalDataJson.put("wakeUpNumber", sleepDataTotalBean.getRouseNumber());
                    sleepDataTotalDataJson.put("sleepDate", TimeStampUtil.currTime3(sleepDataTotalBean.getLongTime()));
                    sleepDataTotalDataJson.put("totalSleepTime", sleepDataTotalBean.getSomnolenceTime() + sleepDataTotalBean.getSeepSleepTime());

                    sleepDataTotalDataJson.put("testTime", sleepDataTotalBean.getLongTime());

                    sleepDataTotalDataJson.put("modelType", "braceletSleepTotal");

                    sleepDataTotalUploadJson.put("data", sleepDataTotalDataJson.toString());
                    OemLog.log(TAG, "sleepDataTotalUploadJson is " + sleepDataTotalUploadJson.toString());
                    HttpTools.post(mContext, Base_Url.Url_Base + Base_Url.uploadData_Url, sleepDataTotalUploadJson, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            OemLog.log(TAG, "uploadSleepToataData success result is " + new String(responseBody));
                            try {
                                dbManager.executeUpdateDelete(new SqlInfo("update SleepDataTotalBean  set isUpload = 1 where longTime = " + sleepDataTotalDataJson.getLong("testTime") + ';'));
                                uploadHandler.sendEmptyMessage(UPLOAD_SLEEP_TOTAL_DATA_COMEPLETE);
                                isUploading = false;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                           // OemLog.log(TAG, "uploadSleepToataData failurre result is " + new String(responseBody));
                            uploadHandler.sendEmptyMessage(UPLOAD_SLEEP_TOTAL_DATA_COMEPLETE);
                            isUploading = false;
                        }

                        @Override
                        public void onCancel() {
                            super.onCancel();
                            uploadHandler.sendEmptyMessage(UPLOAD_SLEEP_TOTAL_DATA_COMEPLETE);
                            isUploading = false;
                        }
                    });

                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}










