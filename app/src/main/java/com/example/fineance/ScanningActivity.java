package com.example.fineance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class ScanningActivity extends AppCompatActivity {

    private final TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    private ImageView imageView;
    private TextView scannedText;
    private Button takePictureButton;
    private Button scanImageButton;
    private final ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                    this.imageView.setImageBitmap(imageBitmap);
                    this.scannedText.setText("Picture was taken");
                    this.scanImageButton.setEnabled(true);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        this.imageView = this.findViewById(R.id.takenPicture);
        this.scannedText = this.findViewById(R.id.scannedText);
        this.takePictureButton = this.findViewById(R.id.takePicture);
        this.scanImageButton = this.findViewById(R.id.scanImageButton);

        this.scanImageButton.setEnabled(false);
        this.takePictureButton.setOnClickListener(view -> this.takePictureIntent());
        this.scanImageButton.setOnClickListener(view -> this.detectTextFromImage());
    }

    private void takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.activityResultLaunch.launch(takePictureIntent);
    }

    private void detectTextFromImage() {

    }
}