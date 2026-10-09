package cn.edu.sicnu.cs.tsy.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cn.edu.sicnu.cs.tsy.activity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val returnLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data?.getStringExtra(SecondActivity2.EXTRA_RESULT)
                if (!data.isNullOrEmpty()) {
                    binding.tvReturnResult.text =
                        getString(R.string.return_received_prefix) + data
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnActivity1.setOnClickListener {
            val intent = Intent(this, SecondActivity1::class.java)
            intent.putExtra(SecondActivity1.EXTRA_MESSAGE, getString(R.string.pass_value_content))
            startActivity(intent)
        }

        binding.btnActivity2.setOnClickListener {
            returnLauncher.launch(Intent(this, SecondActivity2::class.java))
        }

        binding.btnActivity3.setOnClickListener {
            startActivity(Intent(this, SecondActivity3::class.java))
        }

        binding.btnActivity4.setOnClickListener {
            startActivity(Intent(this, SecondActivity4::class.java))
        }
    }
}
