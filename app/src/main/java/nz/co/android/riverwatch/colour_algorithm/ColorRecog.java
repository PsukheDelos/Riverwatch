package nz.co.android.riverwatch.colour_algorithm;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

public class ColorRecog {

	HashMap<Double, Integer> concentrations = new HashMap<Double, Integer>();


	public ColorRecog(HashMap<Double, Integer> concentrations ){
		this.concentrations = concentrations;
	}

	private Map<Double, Double> estimateFromImage(HSBColor image) {
		System.err.println("estimateFromImage: " + image.toString());
		Map<Double, Double> distances = new HashMap<Double, Double>();
		for(Map.Entry<Double, Integer> nameColours : concentrations.entrySet()){
			Double name = nameColours.getKey();
			int rgbColor = nameColours.getValue();
			System.err.println("rgbColor " + name + ": " + Integer.toHexString(rgbColor));
			HSBColor hsbColor = new HSBColor(rgbColor);
			System.err.println(name + ": " + hsbColor.toString());
//			HSBColor diff = image.differenceFrom(hsbColor);
//			double dist = Math.sqrt(diff.h * diff.h + diff.s * diff.s + diff.b * diff.b);
			double hDist = hsbColor.h - image.h;
			double sDist = hsbColor.s - image.s;
			double bDist = hsbColor.b - image.b;
			double dist = Math.sqrt(hDist * hDist + sDist * sDist + bDist * bDist);
			distances.put(name, dist);
		}
		return distances;
	}

	public Map<Double, Double> processImage(Bitmap img){

		HSBImage hsbSubImage = new HSBImage(img);
		HSBColor median = hsbSubImage.medianColor();
		System.err.println("median: " + median.h + " " + median.s + " " + median.b);
		return estimateFromImage(median);
	}
}
