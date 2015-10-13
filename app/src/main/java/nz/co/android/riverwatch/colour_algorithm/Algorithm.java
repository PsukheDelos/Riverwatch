package nz.co.android.riverwatch.colour_algorithm;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import nz.co.android.cowseye2.R;

/**
 * The main class indicating how to use the algorithm properly
 *
 */
public class Algorithm {

	public static Analysis processImages(Bitmap left, Bitmap middle, Bitmap right, Context c){

		Analysis analysis  = new Analysis();
        WhiteBalance balancer = new WhiteBalance();
		Bitmap balancedLeft = balancer.balance(left, middle);
		Bitmap balancedRight = balancer.balance(right, middle);

//		System.err.println("left: " + new HSBImage(balancedLeft).medianColor().toString());
//		System.err.println("right: " + new HSBImage(balancedRight).medianColor().toString());


		HashMap<Double, Integer> nitrateColours = new HashMap<>();
		HashMap<Double, Integer> nitriteColours = new HashMap<>();

		nitriteColours.put(1., c.getResources().getColor(R.color.Nitrite1));
		nitriteColours.put(3., c.getResources().getColor(R.color.Nitrite3));
		nitriteColours.put(1.5, c.getResources().getColor(R.color.Nitrite1_5));
		nitriteColours.put(0.3, c.getResources().getColor(R.color.Nitrite0_3));
		nitriteColours.put(0., c.getResources().getColor(R.color.Nitrite0));
		nitriteColours.put(0.15, c.getResources().getColor(R.color.Nitrite0_15));

        nitrateColours.put(10., c.getResources().getColor(R.color.Nitrate10));
        nitrateColours.put(0., c.getResources().getColor(R.color.Nitrate0));
        nitrateColours.put(1., c.getResources().getColor(R.color.Nitrate1));
        nitrateColours.put(2., c.getResources().getColor(R.color.Nitrate2));
        nitrateColours.put(50., c.getResources().getColor(R.color.Nitrate50));
        nitrateColours.put(20., c.getResources().getColor(R.color.Nitrate20));
        nitrateColours.put(5., c.getResources().getColor(R.color.Nitrate5));

        ColorRecog nitrateRecog = new ColorRecog(nitrateColours);
        ColorRecog nitriteRecog = new ColorRecog(nitriteColours);

		//Do the analysis
		Map<Double, Double> nitrateAnalysis = nitrateRecog.processImage(balancedRight);
		Map<Double, Double> nitriteAnalysis = nitriteRecog.processImage(balancedLeft);

		//Print it out nicely
		double nitrateSum = 0;
		Double bestNitrateClass = null;
		Double secondBestNitrateClass = null;
		for(Map.Entry<Double, Double> nitrateClass : nitrateAnalysis.entrySet()){
			System.err.println("nitrate: " + nitrateClass.getKey() + ": " + nitrateClass.getValue());
			//Toast.makeText(c, "Nitrate: " + nitrateClass.getKey() + " " + nitrateClass.getValue(), Toast.LENGTH_LONG).show();
			if(bestNitrateClass == null || nitrateAnalysis.get(bestNitrateClass) > nitrateClass.getValue()){
				bestNitrateClass = nitrateClass.getKey();
			}
			else if(secondBestNitrateClass == null || nitrateAnalysis.get(secondBestNitrateClass) > nitrateClass.getValue()){
				secondBestNitrateClass = nitrateClass.getKey();
			}
		}

		Double bestNitriteClass = null;
		Double secondBestNitriteClass = null;
		for(Map.Entry<Double, Double> nitriteClass : nitriteAnalysis.entrySet()) {
			System.err.println("nitrite: " + nitriteClass.getKey() + ": " + nitriteClass.getValue());
			//Toast.makeText(c, "Nitrite" + nitriteClass.getKey() + " " + nitriteClass.getValue(), Toast.LENGTH_LONG).show();
			if (bestNitriteClass == null || nitriteAnalysis.get(bestNitriteClass) > nitriteClass.getValue()) {
				bestNitriteClass = nitriteClass.getKey();
			}
			else if (secondBestNitriteClass == null || nitriteAnalysis.get(secondBestNitriteClass) > nitriteClass.getValue()) {
				secondBestNitriteClass = nitriteClass.getKey();
			}
		}

		double invNitrate1 = 1 / nitrateAnalysis.get(bestNitrateClass);
		double invNitrate2 = 1 / nitrateAnalysis.get(secondBestNitrateClass);
		double nitrateProb = invNitrate1 / (invNitrate1 + invNitrate2);

		double invNitrite1 = 1 / nitriteAnalysis.get(bestNitriteClass);
		double invNitrite2 = 1 / nitriteAnalysis.get(secondBestNitriteClass);
		double nitriteProb = invNitrite1 / (invNitrite1 + invNitrite2);


		// UNCOMMENT THESE TO SHOW NITRITE/NITRATE LEVELS AS A TOAST
		//Toast.makeText(c, "Nitrate Class: " + (bestNitrateClass * nitrateProb + secondBestNitrateClass * (1 - nitrateProb)), Toast.LENGTH_LONG).show();
		//Toast.makeText(c, "Nitrite Class: " + (bestNitriteClass * nitriteProb + secondBestNitriteClass * (1 - nitriteProb)), Toast.LENGTH_LONG).show();

		analysis.nitrite = (bestNitriteClass * nitriteProb + secondBestNitriteClass * (1 - nitriteProb));
		analysis.nitrate = (bestNitrateClass * nitrateProb + secondBestNitrateClass * (1 - nitrateProb));
		analysis.nitrateColor = new HSBImage(balancedRight).medianColor();
		analysis.nitriteColor = new HSBImage(balancedLeft).medianColor();

		return analysis;
	}
}
