package cn.com.gwssi.patent.xml.util;

import com.sun.media.jai.codec.*;
import lombok.extern.slf4j.Slf4j;

import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.*;
@Slf4j
public class Tiff2JpgUtil {

    public static void main(String[] args) throws Exception {
    	String source ="D:/99temp/wushu/2008801254544/100005/EPA00001185963400011.tif";
    	String denpend ="D:/99temp/wushu/2008801254544/100005/EPA00001185963400011.jpg";
    	tiffToJpg(source, denpend);

	}
	/*
	 * lzw tiff转png
	 */
	public static String tiffToJpg(String source, String denpend) throws Exception {
		log.debug("原文件---"+source+"---目标文件---"+denpend);
		
		File inFile = new File(source);
		byte[] imgData = getBytesFromFile(inFile);

		int TAG_COMPRESSION = 259;
		int TAG_JPEG_INTERCHANGE_FORMAT = 513;
		int COMP_JPEG_OLD = 6;

		SeekableStream stream = new ByteArraySeekableStream(imgData);
		TIFFDirectory tdir = new TIFFDirectory(stream, 0);
		int compression = tdir.getField(TAG_COMPRESSION).getAsInt(0);

		// Decoder name
		String decoder2use = "tiff";
		boolean needResize = false;
		if (COMP_JPEG_OLD == compression) {
			// Special handling for old/unsupported JPEG-in-TIFF format:
			// {@link:
			// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4929147 }
			stream.seek(tdir.getField(TAG_JPEG_INTERCHANGE_FORMAT).getAsLong(0));
			decoder2use = "jpeg";
			needResize = true;
		}

		// Decode image
		ImageDecoder dec = ImageCodec.createImageDecoder(decoder2use, stream, null);
		RenderedImage img = dec.decodeAsRenderedImage();

		if (needResize) {
			ParameterBlock params = new ParameterBlock();
			params.addSource(img);
			params.add(0.35F); // x scale factor
			params.add(0.35F); // y scale factor
			params.add(0.0F); // x translate
			params.add(0.0F); // y translate
			params.add(new InterpolationNearest());
			img = JAI.create("scale", params, null);
		}
		
		FileOutputStream fileOut = new FileOutputStream(denpend);
		ImageEncoder pngEncoder = ImageCodec.createImageEncoder("png", fileOut, null);
		pngEncoder.encode(img);
		stream.close();
		fileOut.close();
		return denpend;
	}
	
	
	/**
	 * File转byte[]
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		// 获取文件大小
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			is.close();
			// 文件太大，无法读取
			throw new IOException("File is to large " + file.getName());
		}
		// 创建一个数据来保存文件数据
		byte[] bytes = new byte[(int) length];
		// 读取数据到byte数组中
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		// 确保所有数据均被读取
		if (offset < bytes.length) {
			is.close();
			throw new IOException("Could not completely read file " + file.getName());
		}
		// Close the input stream and return bytes
		is.close();
		return bytes;
	}
	
}
