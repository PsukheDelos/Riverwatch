package nz.co.android.riverwatch.colour_algorithm;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Arrays;

/**
 * An Image in HSB space
 *
 */
public class HSBImage {
	private HSBColor[] data;
	public HSBImage(Bitmap img){
		int[] colours = new int[img.getWidth() * img.getHeight()];
		img.getPixels(colours, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
		data = new HSBColor[colours.length];
		for(int i = 0;i < colours.length;i ++){
			float[] vals = new float[3];
			Color.colorToHSV(colours[i], vals);
			data[i] = new HSBColor(vals[0], vals[1], vals[2]);
		}
	}

	public HSBColor medianColor(){
		float[] hArr =	new float[data.length];
		float[] sArr =	new float[data.length];
		float[] bArr =	new float[data.length];
		for(int i = 0;i < data.length;i ++){
			hArr[i] = data[i].h;
			sArr[i] = data[i].s;
			bArr[i] = data[i].b;
		}
		Arrays.sort(hArr);
		Arrays.sort(sArr);
		Arrays.sort(bArr);
		float hMed = hArr[data.length / 2];
		float sMed = sArr[data.length / 2];
		float bMed = bArr[data.length / 2];
		return new HSBColor(hMed, sMed, bMed);
	}
}
