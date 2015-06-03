package properties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import org.apache.log4j.Logger;


public class propertiesReader {
	//读取jar包外配置文档的方法
	private static Logger log = Logger.getLogger(propertiesReader.class);
	public  String getProperty(String key) {
		String filePath = System.getProperty("user.dir") + "/FlvPic.properties"; 
		String PropertyCotent = null;
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			 PropertyResourceBundle rb = new PropertyResourceBundle(in); 
			 PropertyCotent=rb.getString(key);
		} catch (Exception e) {
			log.error("Properties get error "+e.getMessage());
		} 
		return PropertyCotent;
		
	}
	
}
