package com.example.thierrycouilleault.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

//pour l'image voir dans le build gradle le compile pour l'image view circle


public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    private TextView mDisplayName, mStatus;
    private CircleImageView mDisplayImage;

    private Button mChangeImageBtn, mChangeStatusBtn;

    private static final int GALLERY_PICK = 1;

    private StorageReference mStorageRef;

    private ProgressDialog imageProgress;

    private Bitmap thumb_bitmap;

    private Byte [] thumb_byte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDisplayName = findViewById(R.id.display_name_settings);
        mStatus = findViewById(R.id.status_settings);
        mDisplayImage = findViewById(R.id.settings_image);

        //Storage

        mStorageRef = FirebaseStorage.getInstance().getReference();


        //gestion des boutons
        mChangeImageBtn = findViewById(R.id.image_settings_btn);
        mChangeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent ();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT_IMAGE"),GALLERY_PICK);

            }
        });


        mChangeStatusBtn = findViewById(R.id.status_settings_btn);
        mChangeStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status_value = mStatus.getText().toString();

                Intent statusIntent = new Intent(SettingsActivity.this, StatusActivity.class);

                statusIntent.putExtra("status_change", status_value);

                startActivity(statusIntent);
            }
        });


        //gestion de l'évolution de la database
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String currentUid = mCurrentUser.getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid);
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                final String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mDisplayName.setText(name);
                mStatus.setText(status);

                if(!image.equals("default")) {

                    //utilisation de Picasso

                    Picasso.with(SettingsActivity.this).load(image).networkPolicy(NetworkPolicy.OFFLINE).
                            placeholder(R.drawable.default_avatar).into(mDisplayImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Picasso.with(SettingsActivity.this).load(image).placeholder(R.drawable.default_avatar).into(mDisplayImage);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode==RESULT_OK) {

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {

                    imageProgress = new ProgressDialog(SettingsActivity.this);
                    imageProgress.setTitle("Uploading Image...");
                    imageProgress.setMessage("Please wait the upload !");
                    imageProgress.setCanceledOnTouchOutside(false);
                    imageProgress.show();

                    Uri resultUri = result.getUri();


                    //compression des images pour les thumbnails
                    File thumb_file = new File(resultUri.getPath());

                    try {
                        thumb_bitmap = new Compressor(this)
                                .setMaxWidth(200)
                                .setMaxHeight(200)
                                .setQuality(75)
                                .compressToBitmap(thumb_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    final byte[] thumb_byte = baos.toByteArray();


                    //storage avec un générateur de string aléatoire cf plus bas

                    String current_user_id = mCurrentUser.getUid();

                    StorageReference filepath = mStorageRef.child("profile_images").child(current_user_id+".jpg");
                    final StorageReference thumb_filepath = mStorageRef.child("profile_images").child("thumbs").child(current_user_id+".jpg");

                    filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()){

                                final String download_url = task.getResult().getDownloadUrl().toString();

                                UploadTask uploadTask =thumb_filepath.putBytes(thumb_byte);
                                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {

                                        String thumb_downloadUrl = thumb_task.getResult().getDownloadUrl().toString();

                                        if (thumb_task.isSuccessful()) {

                                            Map update_hashmap = new HashMap<>();
                                            update_hashmap.put("image", download_url);
                                            update_hashmap.put("thumb_image",thumb_downloadUrl);


                                            mUserDatabase.updateChildren(update_hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()){

                                                        imageProgress.dismiss();
                                                        Toast.makeText(SettingsActivity.this, "Succcess Uploading", Toast.LENGTH_SHORT).show();


                                                    }else{

                                                    }

                                                }
                                            });

                                        }else{
                                            imageProgress.dismiss();
                                            Toast.makeText(SettingsActivity.this, "Error in uploading thumbnail...", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });


                            }else{
                                imageProgress.dismiss();
                                Toast.makeText(SettingsActivity.this, "Error in uploading...", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();


        if (mCurrentUser==null){

            Toast.makeText(this, "This person doesn't exist !", Toast.LENGTH_SHORT).show();

        } else {


            mUserDatabase.child("online").setValue(true);

        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (mCurrentUser==null){

            Toast.makeText(this, "This person doesn't exist !", Toast.LENGTH_SHORT).show();

        } else {

            mUserDatabase.child("online").setValue(false);

        }

    }

    //Code inutile mais permet de générer une String aléatoire
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
