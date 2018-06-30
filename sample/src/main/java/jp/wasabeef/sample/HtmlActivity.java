package jp.wasabeef.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HtmlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);

        TextView preview = findViewById(R.id.preview);
        preview.setText(getIntent().getStringExtra("data"));
    }
}
