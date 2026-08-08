package com.bemba.tts

import android.content.Context
import ai.onnxruntime.*

object BembaTTS {

    private var environment: OrtEnvironment? = null
    private var session: OrtSession? = null

    fun initialize(context: Context) {
        if (session != null) return

        environment = OrtEnvironment.getEnvironment()

        val modelBytes = context.assets
            .open("model.onnx")
            .use { it.readBytes() }

        session = environment!!.createSession(
            modelBytes,
            OrtSession.SessionOptions()
        )
    }

    fun speak(text: String, context: Context) {
        initialize(context)

        if (text.isBlank()) return

        /*
         * The ONNX model is loaded successfully here.
         *
         * The exact tokenizer → tensor → audio pipeline
         * will be connected after we inspect this model's
         * actual ONNX input/output structure.
         */
        val inputs = session!!.inputInfo

        println("Bemba TTS input information:")
        inputs.forEach { (name, info) ->
            println("$name : $info")
        }
    }

    fun stop() {
        // Audio playback will be connected in the next stage.
    }

    fun close() {
        session?.close()
        session = null
        environment?.close()
        environment = null
    }
}
