package com.huehn.initword.ui.view.viewpager;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.huehn.initword.R;
import com.huehn.initword.core.utils.Log.LogManager;
import com.taobao.weex.ui.view.WXVideoView;

import java.io.IOException;

import tv.danmaku.ijk.media.exo.IjkExoMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PageStreamLayout extends LinearLayout implements IItemStreamView {

    private FrameLayout frameLayout;
    private TextureView textureView;
//    private MediaPlayer mediaPlayer;
    private Surface surface;
//    private PlayerVideoThread playerVideoThread;

    private IMediaPlayer mediaPlayer;

    private int position;

    public PageStreamLayout(Context context) {
        super(context);
        initView();
    }

    public PageStreamLayout(Context context, int position) {
        super(context);
        this.position = position;
        initView();
    }

    public PageStreamLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PageStreamLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View.inflate(getContext(), R.layout.adpater_page_item_stream_layout, this);
        frameLayout = findViewById(R.id.texture_layout);
        textureView = new TextureView(getContext());
        frameLayout.addView(textureView);
        prepareTexture();
    }

    private void prepareTexture(){
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                LogManager.d("huehn texture onSurfaceTextureAvailable");
                PageStreamLayout.this.surface = new Surface(surface);
//                //开启一个线程去播放视频
//                new PlayerVideoThread().start();
//                playerVideoThread = new PlayerVideoThread();
//                load();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            /**
             * surface即将被销毁时调用
             * @param surface
             * @return
             */
            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                LogManager.d("huehn texture onSurfaceTextureDestroyed");
                PageStreamLayout.this.surface = null;
                mediaPlayer.stop();
                mediaPlayer.release();
//                mediaPlayer = null;
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }

    private void prepareMediaPlayer(MediaPlayer mediaPlayer){
        if (mediaPlayer == null){
            return;
        }

    }
    /**
     * 加载视频
     */
    private void load(){
        //每次都要重新创建IMediaPlayer
        createPlayer();
        try {
            mediaPlayer.setDataSource(getStreamUrl());
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0f,0f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //给mediaPlayer设置视图
        mediaPlayer.setSurface(surface);
        mediaPlayer.prepareAsync();
    }

    /**
     * 创建一个新的player
     */
    private void createPlayer(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.setDisplay(null);
            mediaPlayer.release();
        }
        IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);

        //开启硬解码
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);

        mediaPlayer = ijkMediaPlayer;



//        if (playListener != null) {
//            mediaPlayer.setOnPreparedListener(listener);
//            mediaPlayer.setOnInfoListener(listener);
//            mediaPlayer.setOnSeekCompleteListener(listener);
//            mediaPlayer.setOnBufferingUpdateListener(listener);
//            mediaPlayer.setOnErrorListener(listener);
//        }
    }

//    private class PlayerVideoThread extends Thread{
//        @Override
//        public void run(){
//            try {
//                LogManager.d("huehn texture PlayerVideoThread run");
//                mediaPlayer= new MediaPlayer();
//                //把res/raw的资源转化为Uri形式访问(android.resource://)
////                Uri uri = Uri.parse("android.resource://com.github.davidji80.videoplayer/"+R.raw.ansen);
//                //设置播放资源(可以是应用的资源文件／url／sdcard路径)
//                mediaPlayer.reset();
//                mediaPlayer.setDataSource(getStreamUrl());
//                //设置渲染画板
//                mediaPlayer.setSurface(surface);
//                //设置播放类型
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                //播放完成监听
////                mediaPlayer.setOnCompletionListener(onCompletionListener);
//                //预加载监听
////                mediaPlayer.setOnPreparedListener(onPreparedListener);
//                prepareMediaPlayer(mediaPlayer);
////                //设置是否保持屏幕常亮
////                mediaPlayer.setScreenOnWhilePlaying(true);
//                //同步的方式装载流媒体文件
//                mediaPlayer.prepare();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
    @Override
    public int getCardType() {
        return IType.IMAGE_TYPE;
    }

    @Override
    public String getStreamUrl() {
        return "http://vjs.zencdn.net/v/oceans.mp4";
//        return "https://media.w3.org/2010/05/sintel/trailer.mp4";
//        return "https://ws.flv.nimo.tv/live/su1599514834494r1251b2ae8f9dfeab1aa9a8b5028b48af_mid.flv";
    }

    private boolean hasPlay = false;
    @Override
    public void play() {
        LogManager.d("huehn texture play");
//        if(surface != null && playerVideoThread != null && !playerVideoThread.isAlive() && !hasPlay){
//            LogManager.d("huehn texture play");
//            //开启一个线程去播放视频
//            playerVideoThread.start();
//            hasPlay = true;
//        }else if (mediaPlayer != null && !mediaPlayer.isPlaying()){
//            LogManager.d("huehn texture is not Playing");
//            mediaPlayer.start();
//        }
        LogManager.d("huehn texture hasplay : " + hasPlay);
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && hasPlay){
            LogManager.d("huehn texture play 1");
            mediaPlayer.start();
        }else if(!hasPlay){
            hasPlay = true;
            LogManager.d("huehn texture play 2");
            load();
        }
    }

    @Override
    public void pause() {
        LogManager.d("huehn texture pause position : " + position);
//        if (surface != null && mediaPlayer != null && mediaPlayer.isPlaying()){
//            LogManager.d("huehn texture pause in");
//            mediaPlayer.pause();
//        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void realse() {

    }


    @Override
    public void setData(ItemData itemData) {

    }

    @Override
    public View getView() {
        return this;
    }
}
