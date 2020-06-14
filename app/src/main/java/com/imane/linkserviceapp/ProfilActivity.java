package com.imane.linkserviceapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class ProfilActivity extends AppCompatActivity {

    Button  btnDeco , btnPoints, btnNewsletter, btnNotif ;
    ImageView imageProfil ;
    TextView modifProfil, addJustif ;

    private static final int PICK_IMAGE = 1 ;
    Uri imageUri ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        btnDeco = findViewById(R.id.btn_deco) ;
        btnPoints = findViewById(R.id.btn_points);
        btnNewsletter= findViewById(R.id.btn_newsletter);
        btnNotif = findViewById(R.id.btn_notif);
        imageProfil = findViewById(R.id.imageProfil);
        modifProfil = findViewById(R.id.tv_profil);
        addJustif = findViewById(R.id.tv_justif);


        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent() ;
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery,"Select picture"), PICK_IMAGE);

            }
        });


        btnDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogPoints();

            }
        });

        btnNewsletter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogNewsletter();

            }
        });

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogNotif();

            }
        });

        modifProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogModifProfil();

            }
        });

        addJustif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilActivity.this,JustificatifsActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    public void openDialogPoints() {

        PopUpPoints popUpPoints = new PopUpPoints();
        popUpPoints.show(getSupportFragmentManager(), "Vos points");

    }

    public void openDialogNewsletter() {

        PopUpNewsletter popUpNewsletter = new PopUpNewsletter();
        popUpNewsletter.show(getSupportFragmentManager(), "Newsletter");

    }

    public void openDialogNotif() {

        PopUpNotifications popUpNotifications = new PopUpNotifications();
        popUpNotifications.show(getSupportFragmentManager(), "Notification");

    }

    public void openDialogModifProfil() {

        PopUpModifProfil popUpModifProfil = new PopUpModifProfil();
        popUpModifProfil.show(getSupportFragmentManager(), "Modification");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK) {

            imageUri = data.getData();
            try  {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageProfil.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
