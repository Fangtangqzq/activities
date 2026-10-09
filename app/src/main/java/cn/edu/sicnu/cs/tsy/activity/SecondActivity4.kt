package cn.edu.sicnu.cs.tsy.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sicnu.cs.tsy.activity.databinding.ActivitySecond4Binding
import java.util.Locale

class SecondActivity4 : AppCompatActivity() {

    private lateinit var binding: ActivitySecond4Binding
    private val handler = Handler(Looper.getMainLooper())

    private var running = false
    private var accumulatedMs = 0L
    private var startRealtime = 0L

    private val ticker = object : Runnable {
        override fun run() {
            if (running) {
                updateDisplay()
                handler.postDelayed(this, TICK_INTERVAL_MS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecond4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        restoreState(savedInstanceState)

        binding.btnStartPause.setOnClickListener {
            if (running) {
                accumulatedMs += SystemClock.elapsedRealtime() - startRealtime
                running = false
                handler.removeCallbacks(ticker)
                binding.btnStartPause.setText(R.string.stopwatch_start)
            } else {
                startRealtime = SystemClock.elapsedRealtime()
                running = true
                binding.btnStartPause.setText(R.string.stopwatch_pause)
                handler.postDelayed(ticker, TICK_INTERVAL_MS)
            }
        }

        binding.btnReset.setOnClickListener {
            running = false
            handler.removeCallbacks(ticker)
            accumulatedMs = 0L
            binding.btnStartPause.setText(R.string.stopwatch_start)
            updateDisplay()
        }

        updateDisplay()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (running) {
            accumulatedMs += SystemClock.elapsedRealtime() - startRealtime
            startRealtime = SystemClock.elapsedRealtime()
        }
        outState.putBoolean(KEY_RUNNING, running)
        outState.putLong(KEY_ACCUMULATED, accumulatedMs)
        outState.putLong(KEY_START_REALTIME, startRealtime)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(ticker)
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            accumulatedMs = savedInstanceState.getLong(KEY_ACCUMULATED)
            startRealtime = savedInstanceState.getLong(KEY_START_REALTIME)
            running = savedInstanceState.getBoolean(KEY_RUNNING)
            if (running) {
                binding.btnStartPause.setText(R.string.stopwatch_pause)
                handler.postDelayed(ticker, TICK_INTERVAL_MS)
            }
        }
    }

    private fun updateDisplay() {
        val elapsed = if (running) {
            accumulatedMs + (SystemClock.elapsedRealtime() - startRealtime)
        } else {
            accumulatedMs
        }
        binding.tvTime.text = format(elapsed)
    }

    private fun format(ms: Long): String {
        val totalTenths = ms / 100
        val tenths = totalTenths % 10
        val totalSeconds = totalTenths / 10
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60
        return String.format(Locale.getDefault(), "%02d:%02d.%d", minutes, seconds, tenths)
    }

    companion object {
        private const val TICK_INTERVAL_MS = 100L
        private const val KEY_RUNNING = "key_running"
        private const val KEY_ACCUMULATED = "key_accumulated"
        private const val KEY_START_REALTIME = "key_start_realtime"
    }
}
