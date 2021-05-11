package com.example.runningapp.presentation.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.runningapp.databinding.FragmentRunBinding
import com.example.runningapp.presentation.services.RunService
import com.example.runningapp.presentation.utils.Constants
import com.example.runningapp.presentation.utils.TrackingPermissions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class RunFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private var _binding: FragmentRunBinding? = null
    private val binding: FragmentRunBinding get() = _binding!!
    private var service: RunService? = null

    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MYTAG","RunFragment onCreate() Fragment started")
        startService()
        bindService()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRunBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        requestPermissions()
        getMap()
    }

    private fun getMap() {
        binding.mapView.getMapAsync {
            map = it
        }
    }

    private fun requestPermissions() {
        if (TrackingPermissions.isPermitted(requireContext()))
            return
        TrackingPermissions.requestPermissions(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("MYTAG", "onsaveinstancestate")
        _binding?.mapView?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            btnStart.setOnClickListener {
                startRun()
            }
        }
    }

    private fun startRun() {
        service?.start()
        service?.isTracking()?.observe(viewLifecycleOwner){
            //TODO
        }
        service?.path()?.observe(viewLifecycleOwner){
            moveMapToRunner(it)
            addLines(it)
        }
    }

    private fun moveMapToRunner(list: List<LatLng>) {
        if(list.isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    list.last(),
                    Constants.MAP_ZOOM
                )
            )
        }
    }

    private fun addLines(list: List<LatLng>){
        val polylineOptions = PolylineOptions()
            .color(Constants.COLOR)
            .width(Constants.WIDTH)
            .addAll(list)
        map?.addPolyline(polylineOptions)
    }

    private fun startService() {
        Log.d("MYTAG", "RunFragment startService() : Service started")
        val intent = Intent(requireContext(), RunService::class.java)
        activity?.startService(intent)
    }

    private fun bindService() {
        Log.d("MYTAG", "RunFragment bindService() : Service bound")
        val myIntent = Intent(requireContext(), RunService::class.java)
        activity?.bindService(myIntent, myConnection, Context.BIND_AUTO_CREATE)
    }

    private val myConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.d("MYTAG", "RunFragment onServiceConnected() : Service connected")
            service = (p1 as? RunService.LocalBinder)?.getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
           service = null
        }

    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroyView() {
        Log.d("MYTAG", "ondestroy")
        _binding?.mapView?.onDestroy()
        super.onDestroyView()
        _binding = null
        activity?.unbindService(myConnection)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


}