package com.naveen.mynewsapp.view.ui

import android.app.PictureInPictureParams
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.view.WindowManager
import android.widget.Toast
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.naveen.mynewsapp.R
import com.naveen.mynewsapp.service.utils.AppConstant
import com.naveen.mynewsapp.service.utils.FullScreenMediaController


class FullScreenVideoActivity : AppCompatActivity() {
    private var mVideoView: VideoView? = null
    private var mediaController: FullScreenMediaController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_video)

        mVideoView = findViewById(R.id.videoview)

        val fullScreen = intent.getStringExtra("fullScreenInd")
        if ("y" == fullScreen) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            supportActionBar!!.hide()
        }

        val videoUri: Uri = Uri.parse(AppConstant.VIDEO_URL)

        mVideoView!!.setVideoURI(videoUri)

        mediaController = FullScreenMediaController(this)

    }

    override fun onStart() {
        super.onStart()

        // Load the media each time onStart() is called.
        initializePlayer()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPause() {
        super.onPause()
        enterPipMode()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView!!.pause()
        }
    }
    override fun onStop() {
        super.onStop()

        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
        releasePlayer()
    }
    private fun initializePlayer() {
        // Show the "Buffering..." message while the video loads.

        // Buffer and decode the video sample.
        val videoUri: Uri = Uri.parse(AppConstant.VIDEO_URL)
        mVideoView!!.setVideoURI(videoUri)

        // Listener for onPrepared() event (runs after the media is prepared).
        mVideoView!!.setOnPreparedListener { // Hide buffering message.
            mediaController!!.setAnchorView(mVideoView);

            mVideoView!!.setMediaController(mediaController);

            // Restore saved position, if available.
            if (AppConstant.SEEK_POSITION > 0) {
                mVideoView!!.seekTo(AppConstant.SEEK_POSITION)
            } else {
                mVideoView!!.seekTo(1)
            }

            // Start playing!
            mVideoView!!.start()
        }

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        mVideoView!!.setOnCompletionListener {
            Toast.makeText(
                this@FullScreenVideoActivity,
                R.string.toast_message,
                Toast.LENGTH_SHORT
            ).show()

            // Return the video position to the start.
            mVideoView!!.seekTo(0)
        }
    }


    // Release all media-related resources. In a more complicated app this
    // might involve unregistering listeners or releasing audio focus.
    private fun releasePlayer() {
        mVideoView!!.stopPlayback()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun enterPipMode() {
        val rational = Rational(
            mVideoView!!.getWidth(),
            mVideoView!!.getHeight()
        )
        val params = PictureInPictureParams.Builder()
            .setAspectRatio(rational)
            .build()
        mVideoView!!.setMediaController(null)
        enterPictureInPictureMode(params)
    }
}