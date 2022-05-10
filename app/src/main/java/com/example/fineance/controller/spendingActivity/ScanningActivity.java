package com.example.fineance.controller.spendingActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseIntArray;
import android.view.Surface;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class ScanningActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 1;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    private final TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    private final Intent intentResult = new Intent();
    private final ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    if (result.getData().getExtras().containsKey("data")) {
                        new Thread(() -> detectTextFromImage((Bitmap) result.getData().getExtras().get("data"))).start();
                        new Thread(() -> {
                            synchronized (this.intentResult) {
                                while (this.intentResult.getExtras() == null || this.intentResult.getExtras().isEmpty()) {
                                    try {
                                        this.intentResult.wait(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            this.setResult(Activity.RESULT_OK, this.intentResult);
                            this.finish();
                        }).start();
                    }
                }
            });

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.takePictureIntent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ScanningActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ScanningActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void takePictureIntent() {
        if (this.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            this.activityResultLaunch.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        } else {
            Toast.makeText(this.getApplicationContext(), "Need Camera Permission for this feature", Toast.LENGTH_LONG).show();
        }
    }

    private void detectTextFromImage(Bitmap bitmapImage) {
        InputImage image = InputImage.fromBitmap(bitmapImage, 0);
        recognizer.process(image)
                .addOnSuccessListener(this::analyseDetectedText)
                .addOnFailureListener(
                        e -> Toast.makeText(this.getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void analyseDetectedText(Text result) {
        double montantTmp = 0;
        String resultText = result.getText();
        for (Text.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements()) {
                    String elementText = element.getText();
                    try {
                        montantTmp = Math.max(montantTmp, Double.parseDouble(elementText.replaceAll("[^0-9]", "")));
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        synchronized (this.intentResult) {
            this.intentResult.putExtra("montant", montantTmp);
            this.intentResult.putExtra("commentaire", "Text détecté dans la Photo:\n" + resultText);
            this.intentResult.notify();
        }
    }
}