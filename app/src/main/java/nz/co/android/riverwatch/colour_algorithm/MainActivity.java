package nz.co.android.riverwatch.colour_algorithm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nz.co.android.cowseye2.R;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

    private Camera camera = null;
    private SurfaceView cameraSurfaceView = null;
    private SurfaceHolder cameraSurfaceHolder = null;
    private CameraOverlay stripOverlay = null;
    private boolean previewing = false;
    RelativeLayout relativeLayout;

    private Button btnCapture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.camera_activity);

        relativeLayout=(RelativeLayout) findViewById(R.id.containerImg);
        relativeLayout.setDrawingCacheEnabled(true);
        cameraSurfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        stripOverlay = (CameraOverlay) findViewById(R.id.stripOverlay);
        cameraSurfaceHolder = cameraSurfaceView.getHolder();
        cameraSurfaceHolder.addCallback(this);

        btnCapture = (Button)findViewById(R.id.button1);
        btnCapture.setOnClickListener(new OnClickListener()
        {
        @Override
        public void onClick(View v)
        {
            View b = findViewById(R.id.button1);
            b.setVisibility(View.INVISIBLE);
            View p = findViewById(R.id.progressBar);
            p.setVisibility(View.VISIBLE);
            camera.takePicture(null, null, cameraPictureCallbackJpeg);
        }
        });

        final RelativeLayout layout = (RelativeLayout)findViewById(R.id.containerImg);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
        //				  Camera.Parameters parameters = camera.getParameters();
        //				  		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        //		ArrayList<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
        //		focusAreas.add(new Camera.Area(stripOverlay.getStripRectangle(), 1000));
        //		parameters.setFocusAreas(focusAreas);
        //		camera.setParameters(parameters);
        //				  this.layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        //				  int width = layout.getMeasuredWidth();
        //				  int height = layout.getMeasuredHeight();

        }
        });
        }

        PictureCallback cameraPictureCallbackJpeg = new PictureCallback()
        {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
        HashMap<String, Rect> captureRectangles = stripOverlay.getCaptureRectangles();

        Bitmap cameraBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        cameraBitmap = RotateBitmap(cameraBitmap,90f);

        int wid = cameraBitmap.getWidth();
        int hgt = cameraBitmap.getHeight();

        Bitmap newImage = Bitmap.createBitmap(wid, hgt, Bitmap.Config.ARGB_8888);

        Rect leftR = captureRectangles.get("leftCaptureRectangle");
        Rect midR = captureRectangles.get("middleCaptureRectangle");
        Rect rightR = captureRectangles.get("rightCaptureRectangle");

//        System.err.println("Left: " + leftR.left);
//        System.err.println("Top: " + leftR.top);
//        System.err.println("Width: " + leftR.width());
//        System.err.println("Height: " + leftR.height());
//
//        System.err.println("Bitmap Height: " + cameraBitmap.getHeight());
//        System.err.println("Bitmap Width: " + cameraBitmap.getWidth());

        Bitmap left = Bitmap.createBitmap(cameraBitmap, leftR.left, leftR.top, leftR.width(), leftR.height());
        Bitmap middle = Bitmap.createBitmap(cameraBitmap, midR.left, midR.top, midR.width(), midR.height());
        Bitmap right = Bitmap.createBitmap(cameraBitmap, rightR.left, rightR.top, rightR.width(), rightR.height());

        Analysis analysis = Algorithm.processImages(left,middle,right, getApplicationContext());

//        Canvas leftcanvas = new Canvas(left);
//        Canvas midcanvas = new Canvas(middle);
//        Canvas rightcanvas = new Canvas(right);
//        leftcanvas.drawBitmap(left, 0f, 0f, null);
//        rightcanvas.drawBitmap(right, 0f, 0f, null);
//        midcanvas.drawBitmap(middle, 0f, 0f, null);

        Canvas canvas = new Canvas(newImage);
        canvas.drawBitmap(cameraBitmap, 0f, 0f, null);

        File storagePath = new File(Environment.getExternalStorageDirectory() + "/PhotoAR/");
        storagePath.mkdirs();

//        File leftImage = new File(storagePath, Long.toString(System.currentTimeMillis()) + "-left.jpg");
//        File midImage = new File(storagePath, Long.toString(System.currentTimeMillis()) + "-mid.jpg");
//        File rightImage = new File(storagePath, Long.toString(System.currentTimeMillis()) + "-right.jpg");

        File myImage = new File(storagePath, Long.toString(System.currentTimeMillis()) + ".jpg");

        analysis.path = Uri.fromFile(myImage).toString();

        try {
//            FileOutputStream out = new FileOutputStream(leftImage);
//            left.compress(Bitmap.CompressFormat.JPEG, 80, out);
//            out.flush();
//            out.close();
//
//            out = new FileOutputStream(rightImage);
//            right.compress(Bitmap.CompressFormat.JPEG, 80, out);
//            out.flush();
//            out.close();
//
//            out = new FileOutputStream(midImage);
//            middle.compress(Bitmap.CompressFormat.JPEG, 80, out);
//            out.flush();
//            out.close();

            FileOutputStream out = new FileOutputStream(myImage);
            newImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        }
        catch(IOException e) {
            Log.d("In Saving File", e + "");
        }

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(myImage.getCanonicalPath());
//                exif.setAttribute("UserComment", "Nitrate: " + String.valueOf(analysis.nitrate) + " Nitrite: " + String.valueOf(analysis.nitrite));
                String message = " - Nitrate: " + String.valueOf(Math.round(analysis.nitrate)) + " Nitrite: " + String.valueOf(Math.round(analysis.nitrite));
                exif.setAttribute("UserComment", message);
                exif.saveAttributes();
                System.err.println("EXIF SUCCESS???" + String.valueOf(analysis.nitrate));
            } catch (IOException e) {
                System.err.println("EXIF SAVE FAIL");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        camera.startPreview();

//        left.recycle();
//        right.recycle();
//        middle.recycle();
        newImage.recycle();

        //clean this up
        View p = findViewById(R.id.progressBar);
        p.setVisibility(View.INVISIBLE);
        View b = findViewById(R.id.button1);
        b.setVisibility(View.VISIBLE);

        Intent results = new Intent();
            results.putExtra("analysis", analysis);
            setResult(RESULT_OK, results);
            finish();

    }
    };

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (previewing) {
            camera.stopPreview();
        }

        Camera.Parameters parameters = camera.getParameters();
        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        //We always assume the Rotation is 0
        System.err.println("surfaceChanged Height: " + height);
        System.err.println("surfaceChanged Width: " + width);

        parameters.setPreviewSize(height, width);
        parameters.setPictureSize(height, width);
        camera.setDisplayOrientation(90);

        /**
         * Below is code that was to deal with the focus issues.  While our algorithm does not require that the strip
         * image be in focus, it would be ideal from a user stand point as users may feel uneasy when confronted with a
         * blurry image.  Also, on the far end, having a nice clear, crisp image of the test strip would be nice from
         * a human readability perspective.  However, at this time, we have had not been able to fully debug this issue.
         */
//        camera.cancelAutoFocus();
        //Toast.makeText(getApplicationContext(), "" + stripOverlay.getStripRectangle(), Toast.LENGTH_LONG).show();
//        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//        Camera.Area focusArea = new Camera.Area(stripOverlay.getStripRectangle(), 1000);
//        List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
//        focusAreas.add(focusArea);
        //		parameters.setFocusAreas(focusAreas);

        camera.setParameters(parameters);
        previewCamera();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
        }
        catch(RuntimeException e) {
            Toast.makeText(getApplicationContext(), "Device camera is not working properly.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }

    public void previewCamera() {
        try {
            camera.setPreviewDisplay(cameraSurfaceHolder);
            camera.startPreview();
            previewing = true;
        }
        catch(Exception e) {
            //Log.d(APP_CLASS, "Cannot start preview", e);
        }
    }
    public void getScreenDimensions(){

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_width = size.x;
        int screen_height = size.y;
        System.out.println("Real Width: " + screen_width);
        System.out.println("Real Height: " + screen_height);
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
