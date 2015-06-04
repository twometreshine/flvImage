package com.run.h.FlvPic.HDFS;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.slf4j.LoggerFactory;

import properties.propertiesReader;

public class FlvPicUploadActor implements FlvPicUpload {
	private static final org.slf4j.Logger log=LoggerFactory.getLogger(FlvPicUploadActor.class);
	FileSystem fs;
	propertiesReader pr=new propertiesReader();
	


	public List<String> videoPathGet() {
		List<String> VideoPathList=new ArrayList<String>();
		File VideoDir=new File("Video");
		if (VideoDir.exists()!=true) {
			log.info("Video does not exist and try to create");
			VideoDir.mkdir();
		}
		File[] dir=VideoDir.listFiles();
		for(File file:dir){
			if (file.isDirectory()) {
				File[] dir2=file.listFiles();
				for(File file2:dir2){
					if (file2.isFile()) {
						VideoPathList.add(file2.getPath());
					}else {
						log.error("videoPath path has dir file");
					}
				}
			}else {
				log.error("cameraPath has file file");
			}
		}
		return VideoPathList;
	}

	public void upload(String PicPath) {
		String localSrc = PicPath;
		FileSystem fs;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(localSrc));
		} catch (FileNotFoundException e1) {
			log.error("open loacal jpg error "+e1.getMessage());
		}
		
		String str="hdfs://"+pr.getProperty("hdfs_NameNode_IP")+"/user/Video/"+PicPath;
		Configuration conf=new Configuration();
		org.apache.hadoop.fs.Path path=new org.apache.hadoop.fs.Path(str);
		conf.set("Hadoop.job.ugi", "root,green123");
		try {
			fs=FileSystem.get(URI.create(str),conf);
			out=fs.create(path,(short)2,new Progressable() {
				public void progress() {
			        System.out.print("*");
			    }
			});
		} catch (IOException e) {
			log.error("Hdfs FileSystem careate failure "+e.getMessage());
		}
		try {
			IOUtils.copyBytes(in, out, 4096, true);
		} catch (IOException e) {
			log.error("upload pic error "+e.getMessage());
		}
		try {
			File lf=new File(PicPath);
			lf.delete();
		} catch (Exception e) {
			log.error("delete local pic failure "+e.getMessage());
		}
	}


	public List<String> picPathGet(List<String> VideoPathList) {
		List<String> PicPathList=new ArrayList<String>();
		String videoRealPath;   
        String imageRealPath;   
        for (int i = 0; i < VideoPathList.size(); i++) {
        	videoRealPath=VideoPathList.get(i);
        	if (videoRealPath.contains("flv")) {
        		String[] PicPath=videoRealPath.split("flv");
        		imageRealPath=PicPath[0]+"jpg";
        		System.out.println(videoRealPath);
        		System.out.println(imageRealPath);
        		PicPathList.add(imageRealPath);
        		 try {   
//                     Process pro=Runtime.getRuntime().exec("ffmpeg -i "+videoRealPath+" -y -f image2 -ss 10 -t 1 -s "+pr.getProperty("pic_ratio")+" "+imageRealPath);   
        			   Runtime.getRuntime().exec("ffmpeg -i "+videoRealPath+" -y -f image2 -ss 10 -t 1 -s "+pr.getProperty("pic_ratio")+" "+imageRealPath);   
//        			   pro.waitFor();
//                     BufferedReader br=new BufferedReader(new InputStreamReader(pro.getInputStream()));
//                     BufferedReader br2=new BufferedReader(new InputStreamReader(pro.getErrorStream()));
//                     int ch;
//                     while((ch=br.read())!=-1){
//                    	 System.out.println("cmd "+(char)ch);
//                     }
//                     while ((ch=br2.read())!=-1) {
//						System.out.print((char)ch);
//					}
//                     System.out.println("cmd");
                    
                     
                     
        		 } catch (Exception e) {   
                   log.error("pic create failure "+e.getMessage());
               }
			}
		}
		return PicPathList;
	}

}
