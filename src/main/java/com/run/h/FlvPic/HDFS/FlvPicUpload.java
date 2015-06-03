package com.run.h.FlvPic.HDFS;

import java.util.List;

/**
 * 
 * @author Guo Chen
 *
 */
public interface FlvPicUpload {
	public void upload(String PicPath);
	public List<String> videoPathGet();
	public List<String> picPathGet(List<String> VideoPathList);
	
}
