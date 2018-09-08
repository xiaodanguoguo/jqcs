package com.ebase.utils.qrcode;

import java.io.InputStream;
import java.io.Serializable;

/**
 * 封装生成二维码数据传输对象
 *
 */
public class QrCodeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 背景图流
	 */
	private InputStream backImageStream;
	/**
	 * 内嵌图流
	 */
	private InputStream innerImageStream;
	/**
	 * 二维码内容
	 */
	private String content;
	/**
	 * 生成二维码宽度
	 */
	private int imageWidth = 300;
	/**
	 * 生成二维码高度
	 */
	private int imageHeight = 300;
	/**
	 * 生成图片宽度
	 */
	private int outerImageWidth;
	/**
	 * 生成图片高度
	 */
	private int outerImageHeight;
	/**
	 * 生成二维码边框宽度（默认边框：2）
	 */
	private int frameWidth = 2;
	/**
	 * 内嵌图片宽度
	 */
	private int innerImageWidth;
	/**
	 * 内嵌图片高度
	 */
	private int innerImageHeight;
	/**
	 * 二维码颜色（ARGB）(默认值：0xff000000)
	 */
	private int argb = 0xff000000;

	public QrCodeDTO() {
	}

	/**
	 * 构建二维码数据传输对象
	 * 
	 * @param content
	 *            内容
	 * @param imageWidth
	 *            生成宽度
	 * @param imageHeight
	 *            生成高度
	 */
	public QrCodeDTO(String content, int imageWidth, int imageHeight) {
		this.content = content;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	/**
	 * 构建带有内嵌图片的二维码数据传输对象
	 * 
	 * @param innerImageStream
	 *            内嵌图片流
	 * @param content
	 *            二维码内容
	 * @param imageWidth
	 *            生成图片宽度
	 * @param imageHeight
	 *            生成图片高度
	 * @param innerImageWidth
	 *            内嵌图片的宽度
	 * @param innerImageHeight
	 *            内嵌图片的高度
	 */
	public QrCodeDTO(InputStream innerImageStream, String content, int imageWidth, int imageHeight, int innerImageWidth,
			int innerImageHeight) {
		this.innerImageStream = innerImageStream;
		this.content = content;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.innerImageWidth = innerImageWidth;
		this.innerImageHeight = innerImageHeight;
	}

	/**
	 * 构建带有背景图 内嵌图的 二维码数据传输对象
	 * 
	 * @param backImageStream
	 * @param innerImageStream
	 * @param content
	 * @param imageWidth
	 * @param imageHeight
	 * @param outterImageWidth
	 * @param outterImageHeight
	 * @param frameWidth
	 * @param innerImageWidth
	 * @param innerImageHeight
	 * @param argb
	 */
	public QrCodeDTO(InputStream backImageStream, InputStream innerImageStream, String content, int imageWidth,
			int imageHeight, int outterImageWidth, int outterImageHeight, int frameWidth, int innerImageWidth,
			int innerImageHeight, int argb) {
		this.backImageStream = backImageStream;
		this.innerImageStream = innerImageStream;
		this.content = content;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.outerImageWidth = outterImageWidth;
		this.outerImageHeight = outterImageHeight;
		this.frameWidth = frameWidth;
		this.innerImageWidth = innerImageWidth;
		this.innerImageHeight = innerImageHeight;
		this.argb = argb;
	}

	public InputStream getBackImageStream() {
		return backImageStream;
	}

	public void setBackImageStream(InputStream backImageStream) {
		this.backImageStream = backImageStream;
	}

	public InputStream getInnerImageStream() {
		return innerImageStream;
	}

	public void setInnerImageStream(InputStream innerImageStream) {
		this.innerImageStream = innerImageStream;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getOuterImageWidth() {
		return outerImageWidth;
	}

	public void setOuterImageWidth(int outerImageWidth) {
		this.outerImageWidth = outerImageWidth;
	}

	public int getOuterImageHeight() {
		return outerImageHeight;
	}

	public void setOuterImageHeight(int outerImageHeight) {
		this.outerImageHeight = outerImageHeight;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	public int getInnerImageWidth() {
		return innerImageWidth;
	}

	public void setInnerImageWidth(int innerImageWidth) {
		this.innerImageWidth = innerImageWidth;
	}

	public int getInnerImageHeight() {
		return innerImageHeight;
	}

	public void setInnerImageHeight(int innerImageHeight) {
		this.innerImageHeight = innerImageHeight;
	}

	public int getArgb() {
		return argb;
	}

	public void setArgb(int argb) {
		this.argb = argb;
	}
}
