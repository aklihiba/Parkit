package com.example.parkit.Parking

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.engine.Resource
import com.example.parkit.Adapter
import com.example.parkit.R
import com.example.parkit.databinding.FragmentParkingListBinding
import com.example.parkit.retrofit.Endpoint
import com.example.parkit.utils.LocationPermissionHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.image
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.linear
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

class ParkingList : Fragment() {
    private  val BLUE_ICON_ID = "blue"
    private  val SOURCE_ID = "source_id"
    private  val LAYER_ID = "layer_id"
    private  val LATITUDE = 55.665957
    private val LONGITUDE = 12.550343
    private lateinit var mapView: MapView
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }
    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }
    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }
        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    val viewModel:ParkingViewModel by navGraphViewModels(R.id.fragmentParkings)
    private lateinit var binding: FragmentParkingListBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = MapView(requireActivity())
        requireActivity().setContentView(mapView)
        locationPermissionHelper = LocationPermissionHelper(WeakReference(requireActivity()))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        val annotationApi = mapView.annotations
        val circleAnnotationManager = annotationApi?.createCircleAnnotationManager(mapView)
// Set options for the resulting circle layer.
        val circleAnnotationOptions: CircleAnnotationOptions = CircleAnnotationOptions()
            // Define a geographic coordinate.
            .withPoint(Point.fromLngLat(36.7529126,3.1875092))
            // Style the circle that will be added to the map.
            .withCircleRadius(8.0)
            .withCircleColor("#F08080")
            .withCircleStrokeWidth(2.0)
            .withCircleStrokeColor("#ffffff")
// Add the resulting circle to the map.
        circleAnnotationManager?.create(circleAnnotationOptions)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View ?{
        binding = FragmentParkingListBinding.inflate(layoutInflater)
        val view = binding.root
        binding.progressB.visibility = View.INVISIBLE
        mapView = binding.mapView
        mapView.getMapboxMap()
            .loadStyleUri("mapbox://styles/hiba-akli/cl37sgdw800e214p6u9lne2k8")

        return view


    }

    fun loadParkings() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                binding.progressB.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
            }

        }
        binding.progressB.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getAllParkings()
            withContext(Dispatchers.Main) {
                binding.progressB.visibility = View.INVISIBLE
                if (response.isSuccessful && response.body() != null) {
                    viewModel.list = response.body()!!.toMutableList()
                    binding.recyclerView.adapter = Adapter(requireActivity(), viewModel.list)
                } else {
                    Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
                }
            }
        }




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = Adapter(requireActivity(),viewModel.getData())
        // Sheet
        if (binding.sheet != null) {
            BottomSheetBehavior.from(binding.sheet).apply{
                peekHeight = 150
                this.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        if(viewModel.list.isEmpty()) {
            binding.recyclerView.adapter = Adapter(requireActivity(), viewModel.list)
            loadParkings()

        }
        else {
            binding.recyclerView.adapter = Adapter(requireActivity(),viewModel.list)
        }


    }




    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            "mapbox://styles/hiba-akli/cl37sgdw800e214p6u9lne2k8"
        ) {
            initLocationComponent()
            setupGesturesListener()
        }
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    requireActivity(),
                    R.drawable.ic_near_me,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }

    private fun onCameraTrackingDismissed() {
        Toast.makeText(requireActivity(), "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }





}