package com.mendelu.xmoric.burgermaster.ui.screens

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.BaseArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.mendelu.xmoric.burgermaster.databinding.ActivityAractivityBinding
import com.mendelu.xmoric.burgermaster.navigation.INavigationRouter
import kotlinx.coroutines.launch
import com.google.ar.core.*
import com.gorisse.thomas.sceneform.scene.await
import com.mendelu.xmoric.burgermaster.R
import com.mendelu.xmoric.burgermaster.utils.ARUtils

//@Composable
//fun ARScreen(navigation: INavigationRouter) {
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(colorResource(id = R.color.vector_tint_theme_color))
//            .wrapContentSize(Alignment.Center)
//    ) {
//        Text(
//            text = "AR Screen",
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            textAlign = TextAlign.Center,
//            fontSize = 20.sp
//        )
//    }
//}

class ARScreen : AppCompatActivity(), BaseArFragment.OnTapArPlaneListener,
    Scene.OnUpdateListener {

    private lateinit var binding: ActivityAractivityBinding

    private lateinit var arFragment: ARMainFragment
    private var model: Renderable? = null
    private var anchorNode: AnchorNode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        arFragment = ARMainFragment()
        supportFragmentManager.commit {
            add(R.id.containerFragment, arFragment)
        }

        arFragment.setOnTapPlaneListener(this)
        arFragment.setOnSceneChangeListener(this)

        lifecycleScope.launch {
            loadModel("models/halloween.glb") //todo fix na burger
        }

    }

    private suspend fun loadModel(path: String){
        model = ModelRenderable.builder().setSource(this, Uri.parse(path)).setIsFilamentGltf(true).await()
    }

    override fun onTapPlane(hitResult: HitResult?, plane: Plane?, motionEvent: MotionEvent?) {
        if (model == null) {
            return
        }

        if (anchorNode == null) {
            val node = createNode(hitResult)
            node?.let {
                arFragment.addModel(it)
            }
        }

    }

    private fun createNode(hitResult: HitResult?): Node?{
        hitResult?.let {
            val anchor = it.createAnchor() //it.createAnchor() == hitResult.createAnchor()
            anchorNode = AnchorNode(anchor)
            val transformableNode = TransformableNode(arFragment.getTransformationSystem())
            transformableNode.renderable = model
            transformableNode.renderableInstance.animate(true)

            //transformableNode.localScale = Vector3(0.1f, 0.1f,0.1f)
            transformableNode.localPosition = Vector3(0f, 0.6f, 0f)
            anchorNode!!.localScale = Vector3(0.1f, 0.1f,0.1f)

            anchorNode!!.addChild(transformableNode)
            return anchorNode
        }
        return null
    }

    override fun onUpdate(frameTime: FrameTime?) {
        val frame = arFragment.getFrame()
        frame?.let {
            val camera = frame.camera
            if (camera.trackingState == TrackingState.TRACKING && anchorNode != null){
                val cameraPosition = camera.displayOrientedPose
                val distance = ARUtils.getDistanceToObject(cameraPosition, anchorNode!!.anchor!!.pose)
                anchorNode!!.localScale = Vector3(0.1f*(distance*3), 0.1f*(distance*3),0.1f*(distance*3))
                Log.i("Distance", distance.toString())
            }

        }

    }


}