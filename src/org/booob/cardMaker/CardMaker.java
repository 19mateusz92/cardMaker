package org.booob.cardMaker;

import static org.booob.cardMaker.utils.Statics.FONT_SIZE;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;

import org.booob.cardMaker.dataLoader.CsvLoader;
import org.booob.cardMaker.dataLoader.GraphicsIO;
import org.booob.cardMaker.model.Card;
import org.booob.cardMaker.processor.CsvDataProcessor;
import org.booob.cardMaker.processor.ImageProcessor;
import org.booob.cardMaker.utils.CreateOutputName;
import org.booob.cardMaker.utils.ImageUtils;

public class CardMaker {

	private final String csvPath;
	private final String imagePath;

	private static int imageId = 0;

	private ImageProcessor imageProcessor;
	private List<Point> labels;

	public CardMaker(String csvPath, String imagePath, Set<JLabel> labels) {
		this.csvPath = csvPath;
		this.imagePath = imagePath;
		this.labels = ImageUtils.extractPoint(labels);
	}

	public void makeCards() {
		List<String[]> data = CsvLoader.loadCsvFile(csvPath);
		Graphics2D graphics;
		BufferedImage renderedImage;

		List<Card> cards = CsvDataProcessor.processData(data);

		for (Card card : cards) {

			renderedImage = GraphicsIO.loadImage(imagePath);
			graphics = (Graphics2D) renderedImage.getGraphics();

			imageProcessor = new ImageProcessor(graphics);

			imageProcessor.setTextInImage(card.getWydzial(), labels.get(0), FONT_SIZE);
			imageProcessor.setTextInImage(card.getKierunek(), labels.get(1), FONT_SIZE);
			imageProcessor.setTextInImage(card.getRok(), labels.get(2), FONT_SIZE);
			imageProcessor.setTextInImage(card.getStopien(), labels.get(3), FONT_SIZE);
			imageProcessor.setTextInImage(card.getGrupa(), labels.get(4), FONT_SIZE);
			imageProcessor.setTextInImage(card.getRokRozpoczecia(), labels.get(5), FONT_SIZE);

			imageProcessor.disposeImage();

			GraphicsIO.writeImage(renderedImage, CreateOutputName.getOutputName(imageId));

			imageId++;
		}
	}

}
