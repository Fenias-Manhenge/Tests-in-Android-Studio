package com.example.tests.fundamentos.everything

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tests.R
import com.example.tests.databinding.SpeechToTextDemoBinding
import java.util.Locale

class SpeechToTextDemo : AppCompatActivity(), TextToSpeech.OnInitListener {

    private val binding by lazy { SpeechToTextDemoBinding.inflate(layoutInflater) }

    private val tts by lazy { TextToSpeech(this, this) }

    private var mediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnSpeech.setOnClickListener{
            if (binding.etText.text!!.isEmpty())
                Toast.makeText(this, "Add Text in the Box", Toast.LENGTH_LONG).show()
            else
                speech(binding.etText.text.toString())
        }

        binding.btnMediaPlayer.setOnClickListener {
            try {
                val soundUri = Uri.parse(
                    "android.resource://com.example.tests/" + R.raw.i_follow_rivers)
                mediaPlayer = MediaPlayer.create(applicationContext, soundUri)
                mediaPlayer.isLooping = false
                mediaPlayer.start()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
             val result = tts.setLanguage(Locale("pt", "PT"))

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_LONG).show()
                showTtsSettings()
            }
        }else
            Toast.makeText(this, "Initialization failed", Toast.LENGTH_LONG).show()
    }

    private fun speech(stringToSpeech: String){
        tts.speak(stringToSpeech, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun showTtsSettings(){
        startActivity(Intent(Settings.ACTION_VOICE_INPUT_SETTINGS))
    }

    override fun onDestroy() {
        if(tts.isSpeaking)
            tts.stop()

        if (mediaPlayer.isPlaying)
            mediaPlayer.stop()

        tts.shutdown()
        super.onDestroy()
    }
}