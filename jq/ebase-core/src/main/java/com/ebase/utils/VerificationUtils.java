package com.ebase.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public final class VerificationUtils {
	static final Random RANDOM = new Random();
	static final int WIDTH = 90;
	static final int HEIGHT = 25;

	static final int CODE_X = 15;
	static final int CODE_Y = 20;

	public static byte[] generateCode(String randomCode) {
		try {
			// 定义图像buffer
			BufferedImage buffImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D gd = buffImg.createGraphics();

			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// 创建一个随机数生成器类
			Random random = new Random();
			gd.fillRect(0, 0, WIDTH, HEIGHT);

			Color c = getRandColor(200, 250);
			gd.setColor(c);// 设置背景色
			gd.fillRect(0, 2, WIDTH, HEIGHT - 4);

			// 画边框。
			gd.setColor(Color.BLACK);
			gd.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

			// 绘制干扰线
			Random rand = new Random();
			gd.setColor(getRandColor(160, 200));// 设置线条的颜色
			for (int i = 0; i < 20; i++) {
				int x = rand.nextInt(WIDTH - 1);
				int y = rand.nextInt(HEIGHT - 1);
				int xl = rand.nextInt(6) + 1;
				int yl = rand.nextInt(12) + 1;
				gd.drawLine(x, y, x + xl + 40, y + yl + 20);
			}

			// 添加噪点
			float yawpRate = 0.05f;// 噪声率
			int area = (int) (yawpRate * WIDTH * HEIGHT);
			for (int i = 0; i < area; i++) {
				int x = random.nextInt(WIDTH);
				int y = random.nextInt(HEIGHT);
				int rgb = getRandomIntColor();
				buffImg.setRGB(x, y, rgb);
			}

			shear(gd, WIDTH, HEIGHT, c);// 使图片扭曲

			gd.setColor(getRandColor(100, 160));
			int fontSize = HEIGHT - 4;
			Font font = new Font("Algerian", Font.ITALIC, fontSize);
			gd.setFont(font);

			// 随机产生codeCount数字的验证码。
			for (int i = 0; i < randomCode.length(); i++) {
				// 得到随机产生的验证码数字。
				String code = String.valueOf(randomCode.charAt(i));
				// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
				gd.drawString(code, (i + 1) * CODE_X, CODE_Y);
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(buffImg, "jpeg", baos);
			baos.close();

			return baos.toByteArray();
		} catch (Exception ex) {
			throw new RuntimeException("生成校验码出错!");
		}
	}

	private static Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + RANDOM.nextInt(bc - fc);
		int g = fc + RANDOM.nextInt(bc - fc);
		int b = fc + RANDOM.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	private static int getRandomIntColor() {
		int[] rgb = getRandomRgb();
		int color = 0;
		for (int c : rgb) {
			color = color << 8;
			color = color | c;
		}
		return color;
	}

	private static int[] getRandomRgb() {
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			rgb[i] = RANDOM.nextInt(255);
		}
		return rgb;
	}

	private static void shear(Graphics g, int w1, int h1, Color color) {
		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	private static void shearX(Graphics g, int w1, int h1, Color color) {

		int period = RANDOM.nextInt(2);

		boolean borderGap = true;
		int frames = 1;
		int phase = RANDOM.nextInt(2);

		for (int i = 0; i < h1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}

	}

	private static void shearY(Graphics g, int w1, int h1, Color color) {

		int period = RANDOM.nextInt(40) + 10; // 50;

		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}
		}

	}

}
