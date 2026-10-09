package cn.edu.sicnu.cs.tsy.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sicnu.cs.tsy.activity.databinding.ActivitySecond2Binding

class SecondActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivitySecond2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecond2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener {
            val input = binding.etInput.text.toString().trim()
            if (input.isEmpty()) {
                Toast.makeText(this, R.string.return_empty_hint, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val data = Intent().putExtra(EXTRA_RESULT, input)
            setResult(RESULT_OK, data)
            finish()
        }
    }

    companion object {
        const val EXTRA_RESULT = "extra_result"
    }
}
