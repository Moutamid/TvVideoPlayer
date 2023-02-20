package com.moutamid.VideoPlayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.downloader.Status;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    VideoView videoView;
    String videoUri;
    ProgressDialog progressDialog;
    TextInputLayout edittext;
    Button enterbtn;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog =new ProgressDialog(MainActivity.this);
        videoView = findViewById(R.id.videoView);
        edittext = findViewById(R.id.identerurltext);
        enterbtn = findViewById(R.id.identerbtn);

        PRDownloader.initialize(getApplicationContext());
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        progressDialog.setMessage("Downloading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        enterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String userurl = edittext.getEditText().getText().toString();


                int length = userurl.length();
                String middlelink = userurl.substring(11,(length-5));
                String first = "https://dl";
//
                String finalstring = first+middlelink.trim();
                Toast.makeText(MainActivity.this, ""+finalstring, Toast.LENGTH_SHORT).show();

                videoUri = finalstring;
                Uri uri = Uri.parse(videoUri);
                videoView.setVideoURI(uri);
                progressDialog.dismiss();
                MediaController mediaController = new MediaController(MainActivity.this);
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);

                downloading();
            }
        });

    }

    public void downloading(){
        int downloadId = PRDownloader.download(videoUri,file.getPath(), URLUtil.guessFileName(videoUri,null,null))
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long per = progress.currentBytes*100/progress.totalBytes;

                        progressDialog.setMessage("Downloading : "+per+"%");
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(MainActivity.this, "complete", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(MainActivity.this, ""+error.getServerErrorMessage(), Toast.LENGTH_SHORT).show();
                    }

                });


    }
}