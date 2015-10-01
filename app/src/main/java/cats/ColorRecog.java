package cats;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class ColorRecog {

	HashMap<Double, Integer> concentrations = new HashMap<Double, Integer>();


	public ColorRecog(HashMap<Double, Integer> concentrations ){
		this.concentrations = concentrations;
	}

	private Map<Double, Double> estimateFromImage(int image) {
		Map<Double, Double> distances = new HashMap<Double, Double>();
		for(Map.Entry<Double, Integer> nameColours : concentrations.entrySet()){
			Double name = nameColours.getKey();
			int rgbColor = nameColours.getValue();
			int rDiff = Color.red(image) - Color.red(rgbColor);
			int gDiff = Color.green(image) - Color.green(rgbColor);
			int bDiff = Color.blue(image) - Color.blue(rgbColor);
			double dist = Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
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
