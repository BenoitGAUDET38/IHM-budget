package com.example.fineance.controller.spendingActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class ScanningActivity extends AppCompatActivity {

    private final TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    private Bitmap imageToScanBitmap;
    private ImageView imageView;
    private TextView scannedText;
    private Button takePictureButton;
    private final ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    this.imageToScanBitmap = (Bitmap) result.getData().getExtras().get("data");
                    this.imageView.setImageBitmap(this.imageToScanBitmap);
                    this.detectTextFromImage();
                    this.takePictureButton.setText("Re-Scan Ticket");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        this.imageView = this.findViewById(R.id.takenPicture);
        this.scannedText = this.findViewById(R.id.scannedText);
        this.takePictureButton = this.findViewById(R.id.takePicture);

        takePictureButton.setOnClickListener(view -> this.takePictureIntent());
    }

    private void takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.activityResultLaunch.launch(takePictureIntent);
    }

    private void detectTextFromImage() {
        InputImage image = InputImage.fromBitmap(this.imageToScanBitmap, 0);
        recognizer.process(image)
                .addOnSuccessListener(visionText -> {
                    String amount = "0";
                    String block;
                    for (Text.TextBlock textBlock : visionText.getTextBlocks()) {
                        block = textBlock.getText().replaceAll("\\D", "");
                        if (!block.isEmpty()) {
                            amount = block;
                        }
                    }
                    this.scannedText.setText(amount);
                    Toast.makeText(this.getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(
                        e -> {
                            this.scannedText.setText("No Text Was Found");
                            Toast.makeText(this.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        });
    }


}