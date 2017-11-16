Barcode Reader - Google Mobile Vision
===================
Android Barcode Reader library using **Google Mobile Vision.** This library is built on top of google mobile vision sample adding improvements and fixing few bugs.

[ ![Download](https://api.bintray.com/packages/androidhive-info/maven/barcode-reader/images/download.svg) ](https://bintray.com/androidhive-info/maven/barcode-reader/_latestVersion)
[![Example](https://img.shields.io/badge/Example-Movie%20Tickets-green.svg)](https://www.androidhive.info/2017/07/android-implementing-preferences-settings-screen/)

![Demo](https://user-images.githubusercontent.com/497670/29021175-41e613c4-7b82-11e7-8887-a6d8186aeca3.gif)

How to Use
-------------
1. Include the barcode reader dependency in app's **build.gradle**
```gradle
dependencies {
    // google mobile vision
    implementation 'com.google.android.gms:play-services-vision:11.0.2'

    // barcode reader
    implementation 'info.androidhive:barcode-reader:1.1.5'
}
```

2. Add the barcode reader fragment to your activity
```xml
<fragment
        android:id="@+id/barcode_fragment"
        android:name="info.androidhive.barcode.BarcodeReader"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:auto_focus="true"
        app:use_flash="false" />
```

3. Implement your activity from <code>BarcodeReader.BarcodeReaderListener</code> and override the necessary methods.
```java
public class MainActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    private BarcodeReader barcodeReader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);
    }


    @Override
    public void onScanned(Barcode barcode) {
        // play beep sound
        barcodeReader.playBeep();
    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String s) {

    }
    
    @Override
    public void onCameraPermissionDenied() {
            Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }
}
```

Adding Barcode Reader in Fragment
----
In fragment the barcode reader can be added easily but the scanner listener <code>barcodeReader.setListener()</code> has to 
be set manually.

Check the example fragment code in <code>BarcodeFragment.java</code> and <code>BarcodeFragmentTestActivity.java</code>

https://github.com/ravi8x/Barcode-Reader/tree/master/example/src/main/java/info/androidhive/barcodereader

Adding Scanner Overlay Scanning Indicator
----
The overlay animation indicator displays a horizontal line animating from top to bottom. This will be useful to  to show some cool animation to indicate scanning progress.

To use it, add the <code>info.androidhive.barcode.ScannerOverlay</code> on top of barcode reader fragment using Relative or Frame layout.
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout ...>

    <fragment
        android:id="@+id/barcode_fragment"
        android:name="info.androidhive.barcode.BarcodeReader"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:auto_focus="true"
        app:use_flash="false" />

    <info.androidhive.barcode.ScannerOverlay
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#44000000"
        app:line_color="#7323DC"
        app:line_speed="6"
        app:line_width="4"
        app:square_height="200"
        app:square_width="200"/>

</RelativeLayout>

```


Additional Options
-------------
XML attribute for **Barcode Reader**

<code>auto_focus</code> - boolean, turn on/off auto focus. Default is <code>true</code>

<code>use_flash</code> - boolean, turn on/off flash. Default is <code>false</code>


XML attribute for **Scanner Overlay** Indicator

<code>square_width</code> - Width of transparent square

<code>square_height</code> - Height of transparent square

<code>line_color</code> - Horizontal line color

<code>line_speed</code> - Horizontal line animation speed

----

JAVA Methods

- **Play beep sound**

You can play the **beep** sound when the barcode is scanned. This code is usually called in <code>onScanned()</code> callback.
```java
@Override
    public void onScanned(final Barcode barcode) {
        Log.e(TAG, "onScanned: " + barcode.displayValue);
        barcodeReader.playBeep();
        });
    }
```

- **Change beep sound**

You can change the default **beep** sound by passing the file name. You beep file should be in project's **assets** folder.
```java
barcodeReader.setBeepSoundFile("shutter.mp3");
```

- **Pause scanning**

The scanning can be paused by calling <code>pauseScanning()</code> method.
```java
barcodeReader.pauseScanning();
```

- **Resume Scanning**

The scanning can be resumed by calling <code>resumeScanning()</code> method.
```java
barcodeReader.resumeScanning();
```

## Know Issues

- Camera stream is not smooth. It's because of camera resolution.
- Sometimes screen turns black after Camera permission is granted.
