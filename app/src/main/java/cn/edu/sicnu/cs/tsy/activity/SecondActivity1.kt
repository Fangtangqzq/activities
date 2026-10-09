package cn.edu.sicnu.cs.tsy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sicnu.cs.tsy.activity.databinding.ActivitySecond1Binding

class SecondActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivitySecond1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecond1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra(EXTRA_MESSAGE)
        binding.tvMessage.text = message ?: getString(R.string.pass_value_empty)
    }

    companion object {
        const val EXTRA_MESSAGE = "extra_message"
    }
}
