package h.FlvPic;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.run.h.FlvPic.HDFS.FlvPicUploadActor;;


public class Main {
	private static final long period_hour=60*60*1000;
	
	
	public static void main(String[] args) {
		FlvPicUploadActor fpua=new FlvPicUploadActor();
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 20);
		
		Date date=calendar.getTime();
		
//		if (date.before(new Date())) {
//			log.info("start time is after 30 and try to get pic now");
//			try {
//				Thread.sleep(1000);
//				flvPicUpload(fpua);
//			} catch (InterruptedException e) {
//				log.error("sleep error"+e.getMessage());
//			}
//		}else {
//			log.info("stat time is before 30 and then will try to get pic");
//		}
		Timer timer=new Timer();
		HourTask task=new HourTask(fpua);
		timer.schedule(task,date,period_hour);
		
}
	
	
	
//	private static void flvPicUpload(FlvPicUploadActor fpua) {
//		List<String> vlist=fpua.picPathGet(fpua.videoPathGet());
//		for (int i = 0; i < vlist.size(); i++) {
//			File pic=new File(vlist.get(i));
//			while (pic.exists()!=true) {
//				log.info("the pic {} does not exist and wait",pic );
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					log.error("the pic does not exist sleep failure "+e.getMessage());
//				}
//			}
//			log.info("the pic {} exist and try to upload ",pic);
//			fpua.upload(vlist.get(i));
//		}
//	}
	}

