package com.example.qrcodescanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button scanbtn;
    EditText name,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;});

        scanbtn=findViewById(R.id.buttonScan);
        name=findViewById(R.id.etName);
        address=findViewById(R.id.etAddress);
        address.setOnClickListener(v -> {
            String url = address.getText().toString().trim();

            // Validate input
            if (!url.isEmpty()) {
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url; // Add http:// if missing
                }

                // Open the website in a browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } else {
                // Show a message if no URL is entered
                Toast.makeText(MainActivity.this, "Please enter a URL first", Toast.LENGTH_SHORT).show();
            }
        });
        scanbtn.setOnClickListener(v->{
            CodeScan();
        });

    }
    private void CodeScan()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
barLauncher.launch(options);

    }
    ActivityResultLauncher<ScanOptions>  barLauncher=registerForActivityResult(new ScanContract(), result->{
if(result.getContents()!=null){
    try {
        JSONObject jsonResult = new JSONObject(result.getContents());

        // Extract "title" and "website"
        String title = jsonResult.optString("title", "No Title Found");
        String website = jsonResult.optString("website", "No Website Found");
  name.setText(title);
  address.setText(website);
    }
    catch (Exception e){}
    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    builder.setTitle("Result");
    builder.setMessage((result.getContents()));
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int i) {
            dialog.dismiss();
        }
    }).show();
}
    });
}