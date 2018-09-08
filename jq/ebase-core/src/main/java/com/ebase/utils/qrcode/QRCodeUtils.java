package com.ebase.utils.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码生成工具
 */
public class QRCodeUtils {

	// 二维码写码器
	private static MultiFormatWriter mutiWriter = new MultiFormatWriter();

	/**
	 * 生成二维码 并存盘到指定文件
	 * 
	 * @param code
	 *            封装二维码信息 QrCode
	 * @param targetFile
	 *            生成二维码文件（文件格式 jpg）
	 */
	public static void encode(QrCodeDTO code, String targetFile) {
		try {
			ImageIO.write(genBarcode(code), "jpg", new File(targetFile));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成二维码 返回字节数组
	 * 
	 * @param code
	 *            封装QrCode对象
	 * @return 返回图片字节数组
	 */
	public static byte[] getQRCodeByteArray(QrCodeDTO code) {

		ByteArrayOutputStream os = null;

		try {

			os = new ByteArrayOutputStream();
			ImageIO.write(genBarcode(code), "jpg", os);
			return os.toByteArray();
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		} finally {

			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 生成二维码 返回内存流
	 * 
	 * @param code
	 *            封装QrCode对象
	 * @return 返回图片流
	 */
	public static OutputStream encode(QrCodeDTO code) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(genBarcode(code), "jpg", os);
			return os;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据QrCode 生成二维码图片
	 * 
	 * @param code
	 * @return
	 */
	private static BufferedImage genBarcode(QrCodeDTO code) {
		try {
			// 二维码内容是必须的
			if (StringUtils.isBlank(code.getContent())) {
				throw new Exception("二维码内容不能为空！");
			}

			if (code.getInnerImageStream() != null && (code.getInnerImageHeight() > code.getImageHeight()
					|| code.getInnerImageWidth() > code.getImageWidth() || code.getInnerImageWidth() == 0
					|| code.getInnerImageHeight() == 0)) {
				throw new Exception("内嵌图片长宽（必须设置）不能大于二维码图片长宽");
			}

			if (code.getBackImageStream() != null && (code.getOuterImageHeight() < code.getImageHeight()
					|| code.getOuterImageWidth() < code.getImageWidth() || code.getOuterImageHeight() == 0
					|| code.getOuterImageWidth() == 0)) {
				throw new Exception("背景图长宽（必须设置）不能小于二维码图片长宽");
			}

			if ((float) code.getInnerImageWidth() / code.getImageWidth() > 0.3
					|| (float) code.getInnerImageHeight() / code.getImageHeight() > 0.3) {
				throw new Exception("内嵌图大小不能超过二维码图片的30%,否则生成的二维码不容易识别");
			}

			// 声明二维码内容编码及错误修正级别
			Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
			hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			hint.put(EncodeHintType.MARGIN, 1);

			// 获取缩放后 背景图的像素点集合
			int[][] outerPixels = null;
			if (code.getBackImageStream() != null) {
				// 定比例缩放或放大背景图
				BufferedImage outerImage = scale(code.getBackImageStream(), code.getOuterImageHeight(),
						code.getOuterImageWidth(), true);
				outerPixels = new int[code.getOuterImageWidth()][code.getOuterImageHeight()];
				for (int i = 0; i < outerImage.getWidth(); i++) {
					for (int j = 0; j < outerImage.getHeight(); j++) {
						outerPixels[i][j] = outerImage.getRGB(i, j);
					}
				}
			}

			// 获取缩放后 内嵌图的像素点集合
			int[][] innerPixels = null;
			if (code.getInnerImageStream() != null) {
				// 定比例缩放或放大背景图
				BufferedImage innerImage = scale(code.getInnerImageStream(), code.getInnerImageHeight(),
						code.getInnerImageWidth(), true);
				innerPixels = new int[code.getInnerImageWidth()][code.getInnerImageHeight()];
				for (int i = 0; i < innerImage.getWidth(); i++) {
					for (int j = 0; j < innerImage.getHeight(); j++) {
						innerPixels[i][j] = innerImage.getRGB(i, j);
					}
				}
			}

			// 生成二维码
			BitMatrix matrix = mutiWriter.encode(code.getContent(), BarcodeFormat.QR_CODE, code.getImageWidth(),
					code.getImageHeight(), hint);

			if (innerPixels != null || outerPixels != null) {
				if (innerPixels != null && outerPixels == null) {
					// 二维矩阵转为一维像素数组
					int halfW = matrix.getWidth() / 2;
					int halfH = matrix.getHeight() / 2;
					int[] pixels = new int[code.getImageWidth() * code.getImageHeight()];
					int frameWidth = code.getFrameWidth();
					int innerHalfW = code.getInnerImageWidth() / 2;

					for (int y = 0; y < matrix.getHeight(); y++) {
						for (int x = 0; x < matrix.getWidth(); x++) {
							// 读取图片
							if (x > halfW - innerHalfW && x < halfW + innerHalfW && y > halfH - innerHalfW
									&& y < halfH + innerHalfW) {
								pixels[y * code.getImageWidth() + x] = innerPixels[x - halfW + innerHalfW][y - halfH
										+ innerHalfW];
							}

							// 在图片四周形成边框
							else if ((x > halfW - innerHalfW - frameWidth && x < halfW - innerHalfW + frameWidth
									&& y > halfH - innerHalfW - frameWidth && y < halfH + innerHalfW + frameWidth)
									|| (x > halfW + innerHalfW - frameWidth && x < halfW + innerHalfW + frameWidth
											&& y > halfH - innerHalfW - frameWidth
											&& y < halfH + innerHalfW + frameWidth)
									|| (x > halfW - innerHalfW - frameWidth && x < halfW + innerHalfW + frameWidth
											&& y > halfH - innerHalfW - frameWidth
											&& y < halfH - innerHalfW + frameWidth)
									|| (x > halfW - innerHalfW - frameWidth && x < halfW + innerHalfW + frameWidth
											&& y > halfH + innerHalfW - frameWidth
											&& y < halfH + innerHalfW + frameWidth)) {
								pixels[y * code.getImageWidth() + x] = 0xfffffff;
							} else {
								// 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
								pixels[y * code.getImageWidth() + x] = matrix.get(x, y) ? code.getArgb() : 0xfffffff;
							}
						}
					}
					BufferedImage image = new BufferedImage(code.getImageWidth(), code.getImageHeight(),
							BufferedImage.TYPE_INT_RGB);
					image.getRaster().setDataElements(0, 0, code.getImageWidth(), code.getImageHeight(), pixels);
					return image;
				} else if (outerPixels != null) {
					// 二维矩阵转为一维像素数组
					int halfW = code.getOuterImageWidth() / 2;
					int halfH = code.getOuterImageHeight() / 2;
					int[] pixels = new int[code.getOuterImageWidth() * code.getOuterImageHeight()];
					int frameWidth = code.getFrameWidth();
					int innerHalfW = matrix.getWidth() / 2;
					int iconHalfW = code.getInnerImageWidth() / 2;

					for (int y = 0; y < code.getOuterImageHeight(); y++) {
						for (int x = 0; x < code.getOuterImageWidth(); x++) {
							// 绘制二维码
							if (x > halfW - innerHalfW && x < halfW + innerHalfW && y > halfH - innerHalfW
									&& y < halfH + innerHalfW) {
								pixels[y * code.getOuterImageWidth() + x] = matrix.get((x - halfW + innerHalfW),
										(y - halfH + innerHalfW)) ? (outerPixels[x][y]) : 0xfffffff;
								if (innerPixels != null) {
									if (x > halfW - iconHalfW && x < halfW + iconHalfW && y > halfH - iconHalfW
											&& y < halfH + iconHalfW) {
										pixels[y * code.getOuterImageWidth() + x] = innerPixels[x - halfW + iconHalfW][y
												- halfH + iconHalfW];
									}
								}
							}
							// 在图片四周形成边框
							else if ((x > halfW - innerHalfW - frameWidth && x < halfW - innerHalfW + frameWidth
									&& y > halfH - innerHalfW - frameWidth && y < halfH + innerHalfW + frameWidth)
									|| (x > halfW + innerHalfW - frameWidth && x < halfW + innerHalfW + frameWidth
											&& y > halfH - innerHalfW - frameWidth
											&& y < halfH + innerHalfW + frameWidth)
									|| (x > halfW - innerHalfW - frameWidth && x < halfW + innerHalfW + frameWidth
											&& y > halfH - innerHalfW - frameWidth
											&& y < halfH - innerHalfW + frameWidth)
									|| (x > halfW - innerHalfW - frameWidth && x < halfW + innerHalfW + frameWidth
											&& y > halfH + innerHalfW - frameWidth
											&& y < halfH + innerHalfW + frameWidth)) {
								pixels[y * code.getOuterImageWidth() + x] = outerPixels[x][y];// 0xfffffff;
							} else {
								// 背景图绘制
								pixels[y * code.getOuterImageWidth() + x] = outerPixels[x][y];
							}
						}
					}
					BufferedImage image = new BufferedImage(code.getOuterImageWidth(), code.getOuterImageHeight(),
							BufferedImage.TYPE_INT_RGB);
					image.getRaster().setDataElements(0, 0, code.getOuterImageWidth(), code.getOuterImageHeight(),
							pixels);
					return image;
				}
			} else {
				int[] pixels = new int[code.getImageWidth() * code.getImageHeight()];
				for (int y = 0; y < matrix.getHeight(); y++) {
					for (int x = 0; x < matrix.getWidth(); x++) {
						pixels[y * code.getImageWidth() + x] = matrix.get(x, y) ? code.getArgb() : 0xfffffff;
					}
				}
				BufferedImage image = new BufferedImage(code.getImageWidth(), code.getImageHeight(),
						BufferedImage.TYPE_INT_RGB);
				image.getRaster().setDataElements(0, 0, code.getImageWidth(), code.getImageHeight(), pixels);
				return image;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
	 *
	 * @param inputStream
	 *            图片流
	 * @param height
	 *            目标高度
	 * @param width
	 *            目标宽度
	 * @param hasFiller
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 * @throws IOException
	 */
	private static BufferedImage scale(InputStream inputStream, int height, int width, boolean hasFiller)
			throws IOException {
		double ratio = 0.0; // 缩放比例
		BufferedImage srcImage = ImageIO.read(inputStream);
		if (srcImage.getWidth() != width || srcImage.getHeight() != height) {
			if (width == 0)
				width = srcImage.getWidth();
			if (height == 0)
				height = srcImage.getHeight();
			Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			// 计算比例
			if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
				if (srcImage.getHeight() > srcImage.getWidth()) {
					ratio = (new Integer(height)).doubleValue() / srcImage.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / srcImage.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				destImage = op.filter(srcImage, null);
			}
			if (hasFiller) {// 补白
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphic = image.createGraphics();
				graphic.setColor(Color.white);
				graphic.fillRect(0, 0, width, height);
				if (width == destImage.getWidth(null))
					graphic.drawImage(destImage, 0, (height - destImage.getHeight(null)) / 2, destImage.getWidth(null),
							destImage.getHeight(null), Color.white, null);
				else
					graphic.drawImage(destImage, (width - destImage.getWidth(null)) / 2, 0, destImage.getWidth(null),
							destImage.getHeight(null), Color.white, null);
				graphic.dispose();
				destImage = image;
			}
			return (BufferedImage) destImage;
		} else {
			return srcImage;
		}
	}
}
