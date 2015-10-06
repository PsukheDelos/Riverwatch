package nz.co.android.riverwatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class gallery extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        TableLayout tableLayout = new TableLayout(getApplicationContext());
        TableRow tableRow;
        TextView textView;

        for (int i = 0; i < 4; i++) {
            tableRow = new TableRow(getApplicationContext());
            for (int j = 0; j < 3; j++) {
                textView = new TextView(getApplicationContext());
                textView.setText("test");
                textView.setPadding(20, 20, 20, 20);
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }
        setContentView(tableLayout);
    }
}
