package jp.wasabeef.sample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

import jp.wasabeef.richeditor.EditorToolbar;
import jp.wasabeef.richeditor.RichEditor;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements EditorToolbar.OnImagePickListener {

    private static int RESULT_LOAD_IMAGE = 1;
    private RichEditor mEditor;
    private TextView mPreview;
    private EditorToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditor = (RichEditor) findViewById(R.id.editor);
//        mEditor.setEditorHeight(200);
//        mEditor.setEditorFontSize(22);
//        mEditor.setEditorFontColor(Color.RED);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPlaceholder("Type here...");
        //mEditor.setInputEnabled(false);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setUpWithEditor(mEditor);
        toolbar.setImagePickListener(this);
        toolbar.setColorTint(ContextCompat.getColor(this, R.color.colorPrimary));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, HtmlActivity.class);
        i.putExtra("data", mEditor.getHtml());
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Log.d("onActivityResult: ", picturePath);
            String encoded = null;
            try {
                Bitmap bm = BitmapFactory.decodeFile(picturePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encoded = Base64.encodeToString(b, Base64.NO_PADDING);
                Log.i("Image", encoded);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //mEditor.loadDataWithBaseURL(picturePath);
            // mEditor.insertImage("http://img3.wikia.nocookie.net/__cb20150103173556/mario/images/c/cf/Legend-of-zelda-spirit-tracks-link-spirit-flute.jpg",
            /*mEditor.insertImage("data:image/jpeg;base64," + img,
                    "Image");*/
            mEditor.insertImage(picturePath, "Image");
            //ImageView imageView = (ImageView) findViewById(R.id.imgView);
            //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }

    @Override
    public void onChoosePick() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
            }
        }
    }
}
