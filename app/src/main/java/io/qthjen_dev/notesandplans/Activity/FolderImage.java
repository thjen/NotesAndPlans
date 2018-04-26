package io.qthjen_dev.notesandplans.Activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.etsy.android.grid.StaggeredGridView;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.qthjen_dev.notesandplans.Adapter.ListImageAdapter;
import io.qthjen_dev.notesandplans.Model.ImageModel;
import io.qthjen_dev.notesandplans.Model.NoteImagesModel;
import io.qthjen_dev.notesandplans.R;
import io.qthjen_dev.notesandplans.Utils.NotesDatabaseManager;

public class FolderImage extends AppCompatActivity {

    private Toolbar mTbarFolderImage;
    private ExpandableRelativeLayout mMenuAddImage;
    private FloatingActionButton mFabClear, mFabAdd, mFabChooseImage, mFabTakeImage;

    private NotesDatabaseManager mDatabaseManager;
    private List<ImageModel> list = new ArrayList<>();
    private ListImageAdapter listImageAdapter;

    private StaggeredGridView mGridView;

    private static final int GALLERY_PICKER = 1;
    private static final int TAKE_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_image);

        InitView();

        listImageAdapter = new ListImageAdapter(list, FolderImage.this);
        mGridView.setAdapter(listImageAdapter);

        EventClick();

    }

    private void EventClick() {

        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFabAdd.setVisibility(View.INVISIBLE);
                mFabClear.setVisibility(View.VISIBLE);
                mMenuAddImage.setVisibility(View.VISIBLE);
                mFabTakeImage.setVisibility(View.VISIBLE);
                mFabChooseImage.setVisibility(View.VISIBLE);

                mMenuAddImage.setDuration(100);
                mMenuAddImage.toggle();



            }
        });

        mFabClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFabAdd.setVisibility(View.VISIBLE);
                mFabClear.setVisibility(View.INVISIBLE);
                mMenuAddImage.setVisibility(View.INVISIBLE);
                mFabTakeImage.setVisibility(View.INVISIBLE);
                mFabChooseImage.setVisibility(View.INVISIBLE);

                mMenuAddImage.collapse();

            }
        });

        mFabChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "SELECT IMAGE"), GALLERY_PICKER);

            }
        });

        mFabTakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_IMAGE);

            }
        });

    }

    private void InitView() {

        mDatabaseManager = new NotesDatabaseManager(this);
        mDatabaseManager.openDatabase();

        mGridView        = findViewById(R.id.grid_view);
        mTbarFolderImage = findViewById(R.id.tbar_folderimage);
        mMenuAddImage    = findViewById(R.id.expandable_menuImage);
        mFabClear        = findViewById(R.id.fab_clear);
        mFabAdd          = findViewById(R.id.fab_addimage);
        mFabChooseImage  = findViewById(R.id.fab_chooseImage);
        mFabTakeImage    = findViewById(R.id.fab_takeImage);

        setSupportActionBar(mTbarFolderImage);
        getSupportActionBar().setTitle(getResources().getString(R.string.folderIma));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch ( requestCode ) {

            case GALLERY_PICKER:
                if ( resultCode == RESULT_OK && data != null ) {
                    Uri uri = data.getData();
                    list.add(new ImageModel(uri));
                    listImageAdapter.notifyDataSetChanged();

                    try {

                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        byte[] imagebyte = getBytes(inputStream);

                        NoteImagesModel noteImagesModel = new NoteImagesModel();
                        noteImagesModel.setImage(imagebyte);

                            boolean checkInsertDatabase = mDatabaseManager.insertNoteImages(noteImagesModel);

                            if (checkInsertDatabase) {

                                /** to do something **/

                                //TastyToast.makeText(FolderImage.this, getResources().getString(R.string.addsucc), TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            } else {
                                //TastyToast.makeText(FolderImage.this, getResources().getString(R.string.addfailed), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                            }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case TAKE_IMAGE:
                if ( resultCode == RESULT_OK && data != null ) {
                    Uri uri = data.getData();
                    list.add(new ImageModel(uri));
                    listImageAdapter.notifyDataSetChanged();
                }
                break;

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
