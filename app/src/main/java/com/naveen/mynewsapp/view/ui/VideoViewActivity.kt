package com.naveen.mynewsapp.view.ui

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.naveen.mynewsapp.R


class VideoViewActivity : AppCompatActivity() {
    private var url_list: ArrayList<String> ?= ArrayList()
    private var position:Int = 0
    var mVideoView: VideoView? = null
    private var mBufferingTextView: TextView? = null

    var mediacontroller:MediaController?=null
    private val PLAYBACK_TIME = "play_time"
    private var mCurrentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)

        url_list=intent.getStringArrayListExtra("urls")
        position=intent.getIntExtra("position", 0)
        Log.e("pos", position.toString())

        mVideoView = findViewById(R.id.videoview);
        mBufferingTextView = findViewById(R.id.buffering_textview);

        mediacontroller= MediaController(this)

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        mVideoView!!.setMediaController(mediacontroller);
        mediacontroller!!.setMediaPlayer(mVideoView);


    }
    override fun onStart() {
        super.onStart()

        // Load the media each time onStart() is called.
        initializePlayer()
    }

    override fun onPause() {
        super.onPause()
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
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, mVideoView!!.currentPosition)
    }
    private fun initializePlayer() {
        // Show the "Buffering..." message while the video loads.
        mBufferingTextView!!.visibility = VideoView.VISIBLE

        // Buffer and decode the video sample.
        val videoUri: Uri = Uri.parse("http://uds.ak.o.brightcove.com/5384493731001/5384493731001_5552873278001_5552856451001.mp4")
        mVideoView!!.setVideoURI(videoUri)

        // Listener for onPrepared() event (runs after the media is prepared).
        mVideoView!!.setOnPreparedListener { // Hide buffering message.
            mBufferingTextView!!.visibility = VideoView.INVISIBLE
            mediacontroller!!.setAnchorView(mVideoView);

            mVideoView!!.setMediaController(mediacontroller);

            // Restore saved position, if available.
            if (mCurrentPosition > 0) {
                mVideoView!!.seekTo(mCurrentPosition)
            } else {
                // Skipping to 1 shows the first frame of the video.
                mVideoView!!.seekTo(1)
            }

            // Start playing!
            mVideoView!!.start()
        }

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        mVideoView!!.setOnCompletionListener {
            Toast.makeText(
                this@VideoViewActivity,
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

}