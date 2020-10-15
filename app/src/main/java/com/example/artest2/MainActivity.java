package com.example.artest2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
//    Anchor anchor = hitResult.createAnchor();
//    AnchorNode anchorNode = new AnchorNode(anchor);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            final Anchor anchor = hitResult.createAnchor();


            ModelRenderable.builder()
                    .setSource(this, Uri.parse("arcticfox_posed.sfb"))
                    .build()
                    .thenAccept(modelRenderable -> addModelToScence(anchor, modelRenderable))
                    .exceptionally(throwable ->{
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(throwable.getMessage()).show();
                        return null;
                    });
        });

        Button butTest = findViewById(R.id.buttonTest);
        butTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AnchorNode anchorNode = new AnchorNode(anchor);
                //Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
                //arFragment.getArSceneView().getScene().removeChild(anchorNode);
                Intent intent = new Intent(MainActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addModelToScence(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }
}
