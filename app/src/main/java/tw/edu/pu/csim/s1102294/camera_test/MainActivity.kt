package tw.edu.pu.csim.s1102294.camera_test

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    lateinit var txv: TextView
    lateinit var btn: Button
    lateinit var img: ImageView

    private val takePictureResult =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            img.setImageBitmap(bitmap)
        }

    private val permissionsResultCallback = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
        when (it) {
            true -> {
                txv.text = "您允許拍照權限，歡迎使用拍照功能！"
                takePictureResult.launch(null)
            }
            false -> {
                txv.text = "抱歉，您尚未允許拍照權限，無法使用相機功能。"
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txv = findViewById(R.id.txv)
        btn = findViewById(R.id.btn)
        img = findViewById(R.id.img)
        btn.setOnClickListener({
            checkPermission()
        })


    }

    private fun checkPermission() {
        val permission = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(android.Manifest.permission.CAMERA)
        } else {
            txv.text = "您先前已允許拍照權限，歡迎使用拍照功能！"
            takePictureResult.launch(null)
        }
    }
}