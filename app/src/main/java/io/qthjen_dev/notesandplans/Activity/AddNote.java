package io.qthjen_dev.notesandplans.Activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import io.qthjen_dev.notesandplans.Adapter.ListImageAdapter;
import io.qthjen_dev.notesandplans.Model.ImageModel;
import io.qthjen_dev.notesandplans.Model.NoteModel;
import io.qthjen_dev.notesandplans.R;
import io.qthjen_dev.notesandplans.Utils.NotesDatabaseManager;

public class AddNote extends AppCompatActivity {

    private Toolbar mTbarAddNote;
    private FrameLayout mMenuBox;
    private TextView mTvTime;
    private LinearLayout mBtDelete, mBtCopy;
    private ExpandableRelativeLayout mExpandableMenuMore;
    private EditText mDescription;
    private RelativeLayout mBtAddColor, mFolderImage;

    /** custom toolbar **/
    private ImageView mAddAlert, mClear, mArchive, mFavoriteBoder, mFavorited;

    private Dialog dialog; // dialog choose color ;

    private boolean MENU_MORE_IS_CLICK = false;

    private List<Integer> mListColor = new ArrayList<>();

    private NotesDatabaseManager mNotesDatabaseManager;

    private String mNoteDescription, mTime;
    private int mColor;
    private byte[] mImageByteArray;

    private static final int FOLER_IMAGE = 3;
    private static final int GALLERY_PICKER = 1;
    private static final int TAKE_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        InitView();
        ViewEventClick();

    }

    private void ViewEventClick() {

                    /*mBtTakeImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(AddNote.this, "Take Image", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, TAKE_IMAGE);
                        }
                    });

                    mBtChooseImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //Toast.makeText(AddNote.this, "Choose image", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "SELECT IMAGE"), GALLERY_PICKER);


                            /*mListImage.clear();
                            FilePickerBuilder.getInstance().setMaxCount(5)
                                    .setSelectedFiles(mListImage)
                                    .setActivityTheme(R.style.AppTheme)
                                    .pickPhoto(AddNote.this);*/
/*
                        }
                    });
*/

        mFolderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddNote.this, FolderImage.class);
                startActivityForResult(intent, FOLER_IMAGE);

            }
        });

        mBtAddColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListColor.clear();
                 dialog = new Dialog(AddNote.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.dialog_add_color);

                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewAddColor);
                ImageView ivClear = dialog.findViewById(R.id.clear_dialogColor);


                ColorAdapter adapter = new ColorAdapter(mListColor);
                LinearLayoutManager layoutManager = new LinearLayoutManager(AddNote.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setAdapter(adapter);

                mListColor.add(R.drawable.icon_color);
                mListColor.add(R.drawable.icon_color1);
                mListColor.add(R.drawable.icon_color2);
                mListColor.add(R.drawable.icon_color3);
                mListColor.add(R.drawable.icon_color4);

                ivClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        mMenuBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( !MENU_MORE_IS_CLICK ) {

                    mExpandableMenuMore.setVisibility(View.VISIBLE);
                    mBtCopy.setVisibility(View.VISIBLE);
                    mBtDelete.setVisibility(View.VISIBLE);

                    mBtCopy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(AddNote.this, "Copy", Toast.LENGTH_SHORT).show();
                        }
                    });

                    mBtDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(AddNote.this, "Delete", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {

                    mExpandableMenuMore.setVisibility(View.VISIBLE);
                    mBtCopy.setVisibility(View.INVISIBLE);
                    mBtDelete.setVisibility(View.INVISIBLE);

                }

                mExpandableMenuMore.setDuration(150);
                mExpandableMenuMore.toggle();

            }
        });

        mAddAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNote.this, "Add Aler", Toast.LENGTH_SHORT).show();
            }
        });

        mArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNote.this, "Archive", Toast.LENGTH_SHORT).show();
            }
        });

        mFavoriteBoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFavoriteBoder.setVisibility(View.INVISIBLE);
                mFavorited.setVisibility(View.VISIBLE);

                TastyToast.makeText(AddNote.this, getResources().getString(R.string.addfavorite), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            }
        });

        mFavorited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFavoriteBoder.setVisibility(View.VISIBLE);
                mFavorited.setVisibility(View.INVISIBLE);

                TastyToast.makeText(AddNote.this, getResources().getString(R.string.removefavorite), TastyToast.LENGTH_LONG, TastyToast.INFO);

            }
        });

        mDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDescription.setCursorVisible(true);

            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNoteDescription = mDescription.getText().toString().trim();
                Calendar calendar1 = Calendar.getInstance();
                mTime = calendar1.get(Calendar.DAY_OF_MONTH) + "/" + calendar1.get(Calendar.MONTH) + "/"
                        + calendar1.get(Calendar.YEAR);

                NoteModel noteModel = new NoteModel();
                noteModel.setDescription(mNoteDescription);
                noteModel.setTime(mTime);
                noteModel.setColor(mColor);
                noteModel.setImage(mImageByteArray);

                /** TODO: insert database **/
                boolean checkInsertDatabase = mNotesDatabaseManager.insertNote(noteModel);

                if ( !mDescription.getText().toString().equals("")) {

                    if ( checkInsertDatabase )
                        TastyToast.makeText(AddNote.this, getResources().getString(R.string.addsucc), TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    else {
                        TastyToast.makeText(AddNote.this, getResources().getString(R.string.addfailed), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }

                }

                finish();
                overridePendingTransition(R.anim.activity_stay, R.anim.activity_top_to_bottom);
            }
        });

    }

    private void InitView() {

        mNotesDatabaseManager = new NotesDatabaseManager(this);
        mNotesDatabaseManager.openDatabase();

        mMenuBox = findViewById(R.id.menu_box);
        mTvTime = findViewById(R.id.tv_time);
        mBtAddColor = findViewById(R.id.bt_addcolor);
        mExpandableMenuMore = findViewById(R.id.expandable_menuMore);
        mBtDelete = findViewById(R.id.bt_delete);
        mBtCopy = findViewById(R.id.bt_copy);
        mDescription = findViewById(R.id.et_description);
        mFolderImage = findViewById(R.id.bt_folderImage);
        //mAddImage = findViewById(R.id.iv_addImage);
        //mGridImage = findViewById(R.id.list_image);

        Calendar calendar = Calendar.getInstance();
        mTvTime.setText(getResources().getString(R.string.added) + " "
                + calendar.get(Calendar.DAY_OF_MONTH) + "/"
                + calendar.get(Calendar.MONTH) + "/"
                + calendar.get(Calendar.YEAR));

        mTbarAddNote = findViewById(R.id.tbar_add);
        setSupportActionBar(mTbarAddNote);
        ActionBar actionBar = getSupportActionBar();
        //getSupportActionBar().setTitle(getResources().getString(R.string.addnote));
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_toolbar_addnote, null);

        mAddAlert = view.findViewById(R.id.iv_addAlert);
        mClear = view.findViewById(R.id.iv_clear);
        mArchive = view.findViewById(R.id.iv_archive);
        mFavoriteBoder = view.findViewById(R.id.iv_favoriteBoder);
        mFavorited = view.findViewById(R.id.iv_favorite);

        actionBar.setCustomView(view);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_stay, R.anim.activity_top_to_bottom);
    }

    /** adapter add color **/
    class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

        private List<Integer> mList;

        public ColorAdapter(List<Integer> mList) {

            this.mList = mList;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.layout_color, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            //holder.view.setBackgroundColor(mList.get(position));
            holder.view.setBackgroundDrawable(getResources().getDrawable(mList.get(position)));
            holder.myItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch ( position ) {

                        case 0:mColor = R.color.colorOne;
                            dialog.dismiss();break;
                        case 1:mColor = R.color.colorTwo;
                            dialog.dismiss();break;
                        case 2:mColor = R.color.colorThree;
                            dialog.dismiss();break;
                        case 3:mColor = R.color.colorFour;
                            dialog.dismiss();break;
                        case 4:mColor = R.color.colorFive;
                            dialog.dismiss();break;

                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            View view;
            View myItemView;

            public ViewHolder(View itemView) {
                super(itemView);

                view = itemView.findViewById(R.id.v_chooseColor);
                myItemView = itemView;
            }

        }

    }

}
