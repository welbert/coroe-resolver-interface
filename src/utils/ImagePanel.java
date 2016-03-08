package utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

	private BufferedImage image;
	private boolean keepImageSize = true;
	private int imageSizeFactor = 1;
	

	public void setKeepImageSize(boolean keepImageSize) {
		this.keepImageSize = keepImageSize;
	}

	public void setImageSizeFactor(int imageSizeFactor) {
		this.imageSizeFactor = imageSizeFactor;
	}

	public ImagePanel(String path) throws IOException {
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException ex) {
			throw ex;
		}
	}

	public ImagePanel() {
		image = null;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(String pathImage) throws IOException {
		try {
			image = ImageIO.read(new File(pathImage));
		} catch (IOException ex) {
			image = null;
			throw ex;
		} finally {
			repaint();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null)
			if(keepImageSize)
				g.drawImage(image, 0, 0, image.getWidth()*imageSizeFactor, image.getHeight()*imageSizeFactor, null);
			else
				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
	}

}