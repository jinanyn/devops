package cn.gwssi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class Md5Util {
	/**
	 * 获取字符串MD5码
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5Code(String data)  {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
		md.update(data.getBytes());
		StringBuffer buf = new StringBuffer();
		byte[] bits = md.digest();
		for (int i = 0; i < bits.length; i++) {
			int a = bits[i];
			if (a < 0)
				a += 256;
			if (a < 16)
				buf.append("0");
			buf.append(Integer.toHexString(a));
		}
		return buf.toString();
	}
	
	/**
     * 对一个文件获取md5值
     * @return md5串
	 * @throws NoSuchAlgorithmException 
     */
    public static String getMD5(String filePath)  {
    	File file = new File(filePath);
    	if(file == null || !file.exists()){
    		return "";
    	}
        FileInputStream fileInputStream = null;
        try {
        	fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            MessageDigest newMd5 = null;
			try
			{
				newMd5 = MessageDigest.getInstance("MD5");
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}
			
            while ((length = fileInputStream.read(buffer)) != -1) {
            	newMd5.update(buffer, 0, length);
            }

            return new String(Hex.encodeHex(newMd5.digest()));
            
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
            return null;
        } catch (IOException e) {
        	e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null){
                	fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	public static void main1(String[] args) {
		System.out.println(Md5Util.md5Code("370826"));
	}
}
