package com.moutamid.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.android.iplayer.controller.VideoController;
import com.android.iplayer.interfaces.IVideoController;
import com.android.iplayer.widget.VideoPlayer;
import com.android.iplayer.widget.WidgetFactory;
import com.android.iplayer.widget.controls.ControWindowView;
import com.android.iplayer.widget.controls.ControlCompletionView;
import com.android.iplayer.widget.controls.ControlFunctionBarView;
import com.android.iplayer.widget.controls.ControlGestureView;
import com.android.iplayer.widget.controls.ControlLoadingView;
import com.android.iplayer.widget.controls.ControlStatusView;
import com.android.iplayer.widget.controls.ControlToolBarView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    VideoPlayer videoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoPlayer = findViewById(R.id.video_player);

        videoPlayer.setAutoChangeOrientation(true);

        VideoController controller = new VideoController(videoPlayer.getContext());
        videoPlayer.setController(controller);
        WidgetFactory.bindDefaultControls(controller);
        controller.setTitle("Video");
        // binding.videoPlayer.createController();

        ControlToolBarView toolBarView=new ControlToolBarView(this);
        toolBarView.setTarget(IVideoController.TARGET_CONTROL_TOOL);
        toolBarView.showBack(true);

        toolBarView.showMenus(false,false,false);
        toolBarView.setOnToolBarActionListener(new ControlToolBarView.OnToolBarActionListener() {
            @Override
            public void onBack() {
                //Logger.d(TAG,"onBack");
                onBackPressed();
            }

            @Override
            public void onTv() {
                //Logger.d(TAG,"onTv");
                // startActivity(new Intent("android.settings.CAST_SETTINGS"));
            }

            @Override
            public void onWindow() {
                //Logger.d(TAG,"onWindow");
                //startGoableWindow(null);
            }

            @Override
            public void onMenu() {
                //Logger.d(TAG,"onMenu");
                //showMenuDialog();
            }
        });

        ControlFunctionBarView functionBarView=new ControlFunctionBarView(this);
        functionBarView.showSoundMute(false,false);
        ControlGestureView gestureView=new ControlGestureView(this);
        ControlCompletionView completionView=new ControlCompletionView(this);
        ControlStatusView statusView=new ControlStatusView(this);
        ControlLoadingView loadingView=new ControlLoadingView(this);
        ControWindowView windowView=new ControWindowView(this);

        controller.addControllerWidget(toolBarView,functionBarView,gestureView,completionView,statusView,loadingView,windowView);

        videoPlayer.setDataSource("https://stream.dxbh.net/AWR360Iloilo");
        // binding.videoPlayer.setDataSource("https://upload.dongfeng-nissan.com.cn/nissan/video/202204/4cfde6f0-bf80-11ec-95c3-214c38efbbc8.mp4");
        videoPlayer.prepareAsync();

    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onPause();
    }

    @Override
    public void onBackPressed() {
        if(videoPlayer.isBackPressed()){
//            startActivity(new Intent(VideoPlayerActivity.this, MainActivity.class));
            finish();
        }
//        startActivity(new Intent(VideoPlayerActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayer.onDestroy();
    }
}