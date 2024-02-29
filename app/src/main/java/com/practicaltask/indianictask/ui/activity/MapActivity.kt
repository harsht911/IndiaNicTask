package com.practicaltask.indianictask.ui.activity

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.practicaltask.indianictask.R
import com.practicaltask.indianictask.databinding.ActivityMapBinding
import com.practicaltask.indianictask.utils.RuntimeAppPermission
import java.util.ArrayList

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityMapBinding

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var supportMapFragment: SupportMapFragment? = null
    private val REQUEST_CHECK_SETTINGS = 2
    private var mapView: View? = null

    private var latitudeFinal = 0.0
    private var longitudeFinal = 0.0
    private var mMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        setContentView(binding.root)
        initializeViews()
    }

    private fun initializeViews() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapView = supportMapFragment!!.view
        supportMapFragment!!.getMapAsync(this)
        binding.btnSaveLocation.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
                    .putExtra("latitude", latitudeFinal).putExtra("longitude", longitudeFinal)
            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        askPermissionForMap()
    }

    private fun setupGoogleMap() {
        mMap!!.uiSettings.isZoomControlsEnabled = true
        mMap!!.uiSettings.isMyLocationButtonEnabled = true
        mMap!!.uiSettings.setAllGesturesEnabled(true)
        mMap!!.uiSettings.isCompassEnabled = false
        mMap!!.isMyLocationEnabled = true

        if (mapView != null) {
            val locationButton =
                (mapView!!.findViewById<View>("1".toInt()).parent as View).findViewById<View>("2".toInt())
            val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0)
            layoutParams.setMargins(0, 160, 40, 0)
        }

        mFusedLocationClient!!.lastLocation.addOnCompleteListener(this) { task ->
            if (task.isSuccessful && task.result != null) {
                mMap!!.clear()
                val mLastLocation = task.result
                var latLng: LatLng? = null
                if (mLastLocation != null && mLastLocation.latitude != null && mLastLocation.longitude != null) {
                    latLng = LatLng(mLastLocation.latitude, mLastLocation.longitude)
                    latitudeFinal = latLng.latitude
                    longitudeFinal = latLng.longitude
                }
                if (latLng != null) {
                    val cameraPosition =
                        CameraPosition.Builder().target(latLng).zoom(15f).build()
                    mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }
            }
        }

        mMap!!.setOnCameraChangeListener {
            latitudeFinal = it.target.latitude
            longitudeFinal = it.target.longitude
        }

        mMap!!.setOnMyLocationButtonClickListener {
            createLocationRequest()
            false
        }
    }

    private fun askPermissionForMap() {
        if (
            !RuntimeAppPermission.checkGrantedPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            || !RuntimeAppPermission.checkGrantedPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )


        ) {
            var permissionsList: ArrayList<String> = ArrayList()
            permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION)
            RuntimeAppPermission.getPermissions(this, permissionsList)
        } else {
            createLocationRequest()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()) {
            var rejectedPemissionCount = 0
            var success = true
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    success = false
                    rejectedPemissionCount++
                }
            }
            if (success) {
                createLocationRequest()
            } else {
                var permissionsList: ArrayList<String> = ArrayList()
                permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
                permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION)

                RuntimeAppPermission.getRuntimePermissionSnackBar(
                    binding.root,
                    rejectedPemissionCount,
                    permissionsList,
                    this
                )
            }
        }
    }

    private fun createLocationRequest() {
        var locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            setupGoogleMap()
        }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(this@MapActivity, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            when (resultCode) {
                RESULT_OK -> {
                    setupGoogleMap()
                }

                RESULT_CANCELED -> {
                    Log.e("location Request", "Cancel By User")
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Please enable GPS")
                    builder.setPositiveButton(resources.getString(R.string.str_enable)) { _, _ ->
                        createLocationRequest()
                    }

                    builder.setNegativeButton(resources.getString(R.string.str_cancel)) { _, _ ->
                        finish()
                    }
                    builder.show()
                }
            }
        }
    }
}