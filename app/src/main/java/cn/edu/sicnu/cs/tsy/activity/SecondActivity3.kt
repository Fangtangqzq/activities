package cn.edu.sicnu.cs.tsy.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import cn.edu.sicnu.cs.tsy.activity.databinding.ActivitySecond3Binding
import java.io.File

class SecondActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivitySecond3Binding
    private lateinit var photoUri: Uri

    private val takePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                binding.ivPhoto.setImageURI(photoUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecond3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTakePhoto.setOnClickListener {
            val file = File(cacheDir, "photo.jpg")
            photoUri = FileProvider.getUriForFile(
                this,
                "$packageName.fileprovider",
                file
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            }
            if (intent.resolveActivity(packageManager) != null) {
                takePhotoLauncher.launch(intent)
            } else {
                Toast.makeText(this, R.string.camera_no_app, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
