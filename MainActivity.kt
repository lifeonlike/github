package kr.co.telecons.searchsample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var permissionLauncher : ActivityResultLauncher<Array<String>>

    private var isAccessCoarseLocationPermissionGranted = false
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private var isLocationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            isAccessCoarseLocationPermissionGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: isAccessCoarseLocationPermissionGranted
            isReadPermissionGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: isReadPermissionGranted
            isWritePermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: isWritePermissionGranted
            isLocationPermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isLocationPermissionGranted

            Log.d("lsm","!!!!!!!!!!!!!!!!!!!")
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            )) { Toast.makeText(this, "권한", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "메시지 텍스트", Toast.LENGTH_SHORT).show()

            }
        }

        Log.d("lsm","!!!!!!!!!!!!!!!!!!!")
        selfCheckPermission()
        requestPermission()
    }


    //권한 없는것 요청
    private fun requestPermission() {
        val permissionRequest : MutableList<String> = ArrayList()
        if(!isAccessCoarseLocationPermissionGranted) {
            permissionRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if(!isLocationPermissionGranted) {
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if(!isReadPermissionGranted) {
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(!isWritePermissionGranted) {
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        //없는 권한 ArrayList로 만들어 Launch로 MultiPermission열기
        if(permissionRequest.isNotEmpty()){
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }
    }

    //권한 등록되어있는지 체크
    private fun selfCheckPermission() {
        Log.e("lsm","selfCheckPermission()")
        //위치 기반 권한 체크
        isAccessCoarseLocationPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        isLocationPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        //저장소 일기 권한 체크
        isReadPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        //저장소 쓰기 권한 체크
        val isWritePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val minSdkLevel = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q //쓰기 권한은 28이후에 나왔으므로
        isWritePermissionGranted = isWritePermission || minSdkLevel
        Log.d("lsm","!!!!!!!!!!!!!!!!!!!")
    }

}