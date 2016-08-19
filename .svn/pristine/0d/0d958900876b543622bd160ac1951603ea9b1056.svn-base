package com.capitalbio.oemv2.myapplication.FirstPeriod.Utils;

import android.content.Context;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.FirstPeriod.Interfaces.OnSearchResponseListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * @author lzq
 * @Time 2016/1/20 11:38
 */
public class SearchAreaidByNamecn {

    private Context context;
    private OnSearchResponseListener onSearchResponseListener;
    private static final  String filename = "areaid_v.xls";

    public SearchAreaidByNamecn(Context context) {
        this.context = context;
    }

    public void setOnSearchResponseListener(OnSearchResponseListener listener){
        this.onSearchResponseListener = listener;
    }

    public void search(String namecn){
        if(namecn==null||namecn.equals("")){
            onSearchResponseListener.onSearchFailed(namecn);
        }else{
             Search search = new Search(namecn,context);
            search.start();
        }

    }

    private class Search extends Thread{

        private String namecn;
        private Context context;

        public Search(String namecn,Context context){
            this.namecn = namecn;
            this.context = context;
        }

        @Override
        public void run() {
            Workbook workbook=null;
            boolean isalreadyfind = false;
            try {

                InputStream is = context.getResources().getAssets().open(filename);
                workbook = Workbook.getWorkbook(is);
                Sheet sheet = workbook.getSheet(0);

                //表格行数
                int rows = sheet.getRows();
                //开始遍历每一行
                for(int i=0;i<rows;i++){
                    //获取当前行的第三列的内容
                   Cell cell = sheet.getCell(2, i);
                   String contents = cell.getContents();

                    if(namecn.equals(contents)||namecn.contains(contents)){
                        //获取id
                        Cell cell1 = sheet.getCell(0,i);
                        String contents1 = cell1.getContents();
                        onSearchResponseListener.onSearchSuccess(contents1);
                        isalreadyfind = true;
                        break;
                    }
                }

                if(!isalreadyfind){
                    onSearchResponseListener.onSearchFailed(namecn);
                }




            } catch (Exception e) {
                e.printStackTrace();
                onSearchResponseListener.onSearchFailed(namecn);

            }finally {
                if(workbook!=null){
                    workbook.close();
                    workbook = null;
                }
            }

        }
    }


}
