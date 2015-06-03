package h.FlvPic;

import java.io.File;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import properties.propertiesReader;

import com.run.h.FlvPic.HDFS.FlvPicUploadActor;

public class HourTask extends TimerTask {
	public static Logger log=LoggerFactory.getLogger(HourTask.class);
	private FlvPicUploadActor fpua;
	private propertiesReader pr;
	public  HourTask(FlvPicUploadActor fpua) {
		this.fpua=fpua;
	}
	@Override
	public void run() {
			List<String> vlist=fpua.picPathGet(fpua.videoPathGet());
			for (int i = 0; i < vlist.size(); i++) {
				File pic=new File(vlist.get(i));
				int rectryCount=0;
				while (pic.exists()!=true || pic.length()<1) {
					if (rectryCount>5) {
						log.info("{} up to the max trials and stop",pic);
						break;
					}
					log.info("the pic {} does not exist and wait",pic );
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						log.error("the pic does not exist sleep failure "+e.getMessage());
					}
					String videoPath[];
					videoPath=vlist.get(i).split(".");
					 try {   
	                     Runtime.getRuntime().exec("ffmpeg -i "+videoPath[0]+".flv"+" -y -f image2 -ss 10 -t 1 -s "+pr.getProperty("pic_ratio")+" "+vlist.get(i));   
	        		 } catch (Exception e) {   
	                   log.error("pic create failure "+e.getMessage());
	               }
					 try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						rectryCount++;
					 
				}
				if (pic.exists()==true&&pic.length()>0) {
					log.info("the pic {} exist and try to upload ",pic);
					fpua.upload(vlist.get(i));
				}
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
	}

}
