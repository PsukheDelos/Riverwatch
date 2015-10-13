package nz.co.android.riverwatch.colour_algorithm;

import android.graphics.Bitmap;
import android.net.Uri;
import java.io.Serializable;

/**
 * Created by glenpeek on 4/10/15.
 */
public class Analysis implements Serializable{

    public HSBColor nitrateColor, nitriteColor;
    public double nitrate, nitrite;
    public String path;

    public Analysis(){

    }

}
