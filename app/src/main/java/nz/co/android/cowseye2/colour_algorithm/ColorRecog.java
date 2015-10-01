package nz.co.android.cowseye2.colour_algorithm;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

public class ColorRecog {

	HashMap<Double, Integer> concentrations = new HashMap<Double, Integer>();


	public ColorRecog(HashMap<Double, Integer> concentrations ){
		this.concentrations = concentrations;
	}

	private Map<Double, Double> estimateFromImage(HSBColor image) {
		Map<Double, Double> distances = new HashMap<Double, Double>();
		for(Map.Entry<Double, Integer> nameColours : concentrations.entrySet()){
			Double name = nameColours.getKey();
			int rgbColor = nameColours.getValue();
			HSBColor hsbColor = new HSBColor(rgbColor);
			HSBColor diff = image.differenceFrom(hsbColor);
			double dist = Math.sqrt(diff.h * diff.h + diff.s * diff.s + diff.b * diff.b);
			distances.put(name, dist);
		}
		return distances;
	}

	public Map<Double, Double> processImage(Bitmap img){

		HSBImage hsbSubImage = new HSBImage(img);
		HSBColor median = hsbSubImage.medianColor();
		
		return estimateFromImage(median);
	}
}
