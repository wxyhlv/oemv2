package com.capitalbio.oemv2.myapplication.FirstPeriod.Utils;

import android.app.Application;
import android.content.Context;

import com.capitalbio.oemv2.myapplication.R;


public class CatDataTransferUtil {
	
	
	private static int season = HTG.currSeason();

	/**
	 * 温度
	 */
	
	public static class TemUtil {
		
		
        public static String temText(float tem){
        	String s ="极热";
        	switch (season) {
			case 2:
				//夏季
				if(tem<=12){
	        		return "极冷";
	        	}
	        	if(tem<=18){
	        		return "偏冷";
	        	}
	        	if(tem<=28){
	        		return "舒适";
	        	}
	        	if(tem<=33){
	        		return "偏热";
	        	}
				break;
			case 4:
				//冬季
				if(tem<=12){
	        		return "极冷";
	        	}
	        	if(tem<=17){
	        		return "偏冷";
	        	}
	        	if(tem<=25){
	        		return "舒适";
	        	}
	        	if(tem<=33){
	        		return "偏热";
	        	}
				break;

			default:
				break;
			}
        	
        	return s;
        }
        public static float temPercent(float tem){
        	float f = 0.0f;
        	if(tem<=0){
        		return f;
        	}else{
        		return tem/50;
        	}
        }
        public static int temColor(float tem,Context context){
        	switch (season) {
			case 2:
				//夏季
				if(tem<=12){
	        		return context.getResources().getColor(R.color.red_qipao_arc);
	        	}
	        	if(tem<=18){
	        		return context.getResources().getColor(R.color.yellow_qipao_arc);
	        	}
	        	if(tem<=28){
	        		return context.getResources().getColor(R.color.green_qipao_arc);
	        	}
	        	if(tem<=33){
	        		return context.getResources().getColor(R.color.yellow_qipao_arc);
	        	}
				break;
			case 4:
				//冬季
				if(tem<=12){
	        		return context.getResources().getColor(R.color.red_qipao_arc);
	        	}
	        	if(tem<=17){
	        		return context.getResources().getColor(R.color.yellow_qipao_arc);
	        	}
	        	if(tem<=25){
	        		return context.getResources().getColor(R.color.green_qipao_arc);
	        	}
	        	if(tem<=33){
	        		return context.getResources().getColor(R.color.yellow_qipao_arc);
	        	}
				break;
			default:
				break;
			}
        	
        	return context.getResources().getColor(R.color.red_qipao_arc);
        }
        
	}

	/**
	 * 湿度
	 */
	
	public static class HumUtil {
        public static String humText(float hum){
        	switch (season) {
			case 2:
				//夏季
				if(hum<=30){
	        		return "干燥";
	        	}
	        	if(hum<=60){
	        		return "舒适";
	        	}
				break;
			case 4:
				//冬季
				if(hum<=30){
	        		return "干燥";
	        	}
	        	if(hum<=80){
	        		return "舒适";
	        	}
				break;

			default:
				break;
			}
        	
        	return "湿润";
        }
        public static float humPercent(float hum){
        	return hum/100;
        }
        public static int humColor(float hum,Context context){
        	switch (season) {
			case 2:
				//夏季
				if(hum<=15){
	        		return context.getResources().getColor(R.color.red_qipao_arc);
	        	}
	        	if(hum<=30){
	        		return context.getResources().getColor(R.color.yellow_qipao_arc);
	        	}
	        	if(hum<=60){
	        		return context.getResources().getColor(R.color.green_qipao_arc);
	        	}
	        	if(hum<=80){
	        		return context.getResources().getColor(R.color.yellow_qipao_arc);
	        	}
				break;
			case 4:
				//冬季
				if(hum<=15){
	        		return context.getResources().getColor(R.color.red_qipao_arc);
	        	}
	        	if(hum<=30){
	        		return context.getResources().getColor(R.color.yellow_qipao_arc);
	        	}
	        	if(hum<=80){
	        		return context.getResources().getColor(R.color.green_qipao_arc);
	        	}
	        	if(hum<=90){
	        		return context.getResources().getColor(R.color.yellow_qipao_arc);
	        	}
				break;

			default:
				break;
			}
        	
        	return context.getResources().getColor(R.color.red_qipao_arc);
        }
	}

	/**
	 * 甲醛
	 */
	
	public static class JiaquanUtil {
		public static String jiaquanText(float jiaquan){
			if(jiaquan<=0.08){
				return "优";
			}
			if(jiaquan<=0.1){
				return "良好";
			}
			if(jiaquan<=0.3){
				return "轻度污染";
			}
			if(jiaquan<=0.5){
				return "中度污染";
			}
			if(jiaquan<=0.7){
				return "重度污染";
			}
			return "严重污染";
			
		}
		public static float jiaquanPercent(float jiaquan){
			return jiaquan/0.5f;
		}
		public static int jiaquanColor(float jiaquan,Context context){
			if(jiaquan<=0.08){
				return context.getResources().getColor(R.color.green_qipao_arc);
			}
			if(jiaquan<=0.10){
				return context.getResources().getColor(R.color.yellow_qipao_arc);
			}
			return context.getResources().getColor(R.color.red_qipao_arc);
		}

	}

	/**
	 * TVOC
	 */
	
	public static class TvocUtil {
		public static String tvocText(float tvoc){
			if(tvoc<=0.16){
				return "优";
			}
			if(tvoc<=0.5){
				return "良好";
			}
			if(tvoc<=1.2){
				return "轻度污染";
			}
			if(tvoc<=1.8){
				return "中度污染";
			}
			if(tvoc<=2.5){
				return "重度污染";
			}
			return "严重污染";
		}
		public static float tvocPercent(float tvoc){
			return tvoc/4;
		}
		public static int tvocColor(float tvoc,Context context){
			if(tvoc<=0.16){
				return context.getResources().getColor(R.color.green_qipao_arc);
			}
			if(tvoc<=0.5){
				return context.getResources().getColor(R.color.yellow_qipao_arc);
			}
			return context.getResources().getColor(R.color.red_qipao_arc);
		}

	}

	//pm2.5
	public static class PmUtil{
		public static String pmText(float pm){
              if(pm<=35){
				  return "优";
			  }
			if(pm<=75){
				return "良好";
			}
			if(pm<=115){
				return "轻度污染";
			}
			if(pm<=150){
				return "中度污染";
			}
			if(pm<=250){
				return "重度污染";
			}
			return "严重污染";

		}

		public static float pmPercent(float pm){
			return pm/100;
		}

		public static int pmColor(float pm,Context context){
			if(pm<=35){
				return context.getResources().getColor(R.color.green_qipao_arc);
			}
			if(pm<=75){
				return context.getResources().getColor(R.color.yellow_qipao_arc);
			}

			return context.getResources().getColor(R.color.red_qipao_arc);
		}

	}

	//污染指数
	public static class WrzsUtil{
		public static String wrzsText(float wrzs){
			if(wrzs<=50){
				return "优";
			}
			if(wrzs<=100){
				return "良好";
			}
			if(wrzs<=150){
				return "轻微污染";
			}
			if(wrzs<=200){
				return "轻度污染";
			}
			if(wrzs<=300){
				return "中度重污染";
			}
			return "重度污染";
		}
		public static float wrzsPercent(float wrzs){
			return wrzs/100;
		}
		public static int wrzsColor(float wrzs,Context context){
			if(wrzs<=50){
				return context.getResources().getColor(R.color.green_qipao_arc);
			}
			if(wrzs<=100){
				return context.getResources().getColor(R.color.yellow_qipao_arc);
			}

			return context.getResources().getColor(R.color.red_qipao_arc);
		}
	}

}
