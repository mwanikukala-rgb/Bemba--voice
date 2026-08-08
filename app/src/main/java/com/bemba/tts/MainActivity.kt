
package com.bemba.tts

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var audioTrack: AudioTrack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val title = TextView(this).apply {
            text = "Bemba TTS"
            textSize = 28f
        }

        val description = TextView(this).apply {
            text = "Offline Bemba Text-to-Speech"
            textSize = 16f
        }

        val input = EditText(this).apply {
            hint = "Lembani ici mu Bemba..."
            minLines = 5
            gravity = android.view.Gravity.TOP
        }

        val speakButton = Button(this).apply {
            text = "SPEAK"
        }

        val stopButton = Button(this).apply {
            text = "STOP"
        }

        speakButton.setOnClickListener {
            val text = input.text.toString().trim()

            if (text.isNotEmpty()) {
                speakButton.isEnabled = false
                speakButton.text = "Processing..."

                Thread {
                    try {
                        BembaTTS.speak(text, this)

                        runOnUiThread {
                            speakButton.isEnabled = true
                            speakButton.text = "SPEAK"
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            speakButton.isEnabled = true
                            speakButton.text = "SPEAK"
                            description.text =
                                "Error: ${e.message ?: "Unable to generate speech"}"
                        }
                    }
                }.start()
            }
        }

        stopButton.setOnClickListener {
            BembaTTS.stop()
        }

        layout.addView(title)
        layout.addView(description)
        layout.addView(input)
        layout.addView(speakButton)
        layout.addView(stopButton)

        setContentView(layout)
    }

    override fun onDestroy() {
        BembaTTS.stop()
        super.onDestroy()
    }
}
