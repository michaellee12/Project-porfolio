package com.example.android.inventoryapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.Data.Contract;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static com.example.android.inventoryapp.R.id.price;

public class EditActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private boolean mHasChanged = false;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mHasChanged = true;
            return false;
        }
    };
    private final int PICK_IMAGE_REQUEST = 0;

    private EditText mName;
    private EditText mPrice;
    private EditText mSupplier;
    private Button mQuantityAdd;
    private Button mQuantityTake;
    private TextView mQuantity;

    private ImageView mImage;
    private String imageUri = null;
    private int nQuantity = 0;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        uri = getIntent().getData();
        if (uri == null) {
            setTitle("Add Inventory");
            invalidateOptionsMenu();

            mImage = (ImageView) findViewById(R.id.Image);
            mImage.setVisibility(View.GONE);
            findViewById(R.id.change_image).setVisibility(View.GONE);

            View EmptyView = findViewById(R.id.empty_image);
            EmptyView.setVisibility(View.VISIBLE);
            EmptyView.setOnTouchListener(mTouchListener);
            EmptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
                }
            });
        } else {
            getLoaderManager().initLoader(0, null, this);
            findViewById(R.id.Image).setVisibility(View.VISIBLE);
            findViewById(R.id.change_image).setVisibility(View.VISIBLE);
            findViewById(R.id.empty_image).setVisibility(View.GONE);
            setTitle("Edit Inventory");

        }


        mName = (EditText) findViewById(R.id.name);
        mSupplier = (EditText) findViewById(R.id.supplier);
        mPrice = (EditText) findViewById(price);
        mQuantityAdd = (Button) findViewById(R.id.add_quantity);
        mQuantityTake = (Button) findViewById(R.id.take_quantity);
        mQuantity = (TextView) findViewById(R.id.quantity);

        mSupplier.setOnTouchListener(mTouchListener);
        mName.setOnTouchListener(mTouchListener);
        mPrice.setOnTouchListener(mTouchListener);
        mQuantityTake.setOnTouchListener(mTouchListener);
        mQuantityAdd.setOnTouchListener(mTouchListener);

        Button change = (Button) findViewById(R.id.changeButton);
        change.setOnTouchListener(mTouchListener);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
            }
        });


        mQuantityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nQuantity += 1;
                mQuantity.setText(String.valueOf(nQuantity));
            }
        });

        mQuantityTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nQuantity > 0) {
                    nQuantity -= 1;
                    mQuantity.setText(String.valueOf(nQuantity));
                }
            }
        });
        findViewById(R.id.order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", mSupplier.getText().toString(), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order for " + mName.getText().toString());
                emailIntent.putExtra(Intent.EXTRA_TEXT, createOrder());
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }

    private String createOrder() {
        String message = "To whom it may concern,\n" +
                "I would like to order product - " + mName.getText().toString() +
                "\nThank you!";
        return message;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK & resultData != null) {

            Uri resultUri = resultData.getData();
            imageUri = resultUri.toString();

            ImageView image = (ImageView) findViewById(R.id.Image);
            image.setImageURI(resultUri);


            mImage.setVisibility(View.VISIBLE);
            findViewById(R.id.change_image).setVisibility(View.VISIBLE);
            findViewById(R.id.empty_image).setVisibility(View.GONE);


        }
    }

    private void Dialog(String message, String positive, String negative, DialogInterface.OnClickListener ClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(positive, ClickListener);
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });

        builder.create().show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (uri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!mHasChanged) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener ClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        };
        Dialog("Discharge your changes and quit editing",
                "Discharge",
                "Keep Editing",
                ClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    private void SaveProduct() {
        String text_name = mName.getText().toString().trim();
        String text_supplier = mSupplier.getText().toString().trim();
        String text_price = mPrice.getText().toString().trim();
        if (!TextUtils.isEmpty(text_name) && !TextUtils.isEmpty(text_supplier) &&
                !TextUtils.isEmpty(text_price) && imageUri != null) {

            float float_price = Float.parseFloat(text_price);
            ContentValues values = new ContentValues();
            values.put(Contract.Entry.COLUMN_SUPPLIER, text_supplier);
            values.put(Contract.Entry.COLUMN_NAME, text_name);
            values.put(Contract.Entry.COLUMN_PRICE, float_price);
            values.put(Contract.Entry.COLUMN_QUANtITY, nQuantity);
            values.put(Contract.Entry.COLUMN_IMAGE_PATH, imageUri);
            if (uri == null) {
                Uri newUri = getContentResolver().insert(Contract.Entry.CONTENT_URI, values);
                if (newUri == null) {
                    Toast.makeText(this, "Error with saving inventory",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Inventory saved",
                            Toast.LENGTH_SHORT).show();
                    finish();

                }
            } else {
                int rowUpdated = getContentResolver().update(uri, values, null, null);
                if (rowUpdated == 0) {
                    Toast.makeText(this, "Error with saving inventory",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Inventory saved",
                            Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        } else {
            Toast.makeText(this, "Missing input",
                    Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void finish() {
        getLoaderManager().destroyLoader(0);
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                SaveProduct();
                return true;
            case R.id.action_delete:
                DialogInterface.OnClickListener ClickListener1 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int rowDeleted = getContentResolver().delete(uri, null, null);
                        if (rowDeleted == 0) {
                            Toast.makeText(EditActivity.this, "Error with deletion", Toast.LENGTH_SHORT).show();
                        } else if (rowDeleted == 1) {
                            Toast.makeText(EditActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                };
                Dialog("Delete Pet",
                        "Delete",
                        "Cancel",
                        ClickListener1);
                return true;
            case android.R.id.home:
                if (!mHasChanged) {
                    finish();
                    return true;
                }
                DialogInterface.OnClickListener ClickListener2 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };
                Dialog("Discharge your changes and quit editing",
                        "Discharge",
                        "Keep Editing",
                        ClickListener2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                Contract.Entry._ID,
                Contract.Entry.COLUMN_NAME,
                Contract.Entry.COLUMN_SUPPLIER,
                Contract.Entry.COLUMN_PRICE,
                Contract.Entry.COLUMN_QUANtITY,
                Contract.Entry.COLUMN_IMAGE_PATH
        };
        return new CursorLoader(this,
                uri,
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            String supplier = data.getString(data.getColumnIndex(Contract.Entry.COLUMN_SUPPLIER));
            String name = data.getString(data.getColumnIndex(Contract.Entry.COLUMN_NAME));
            int quantity = data.getInt(data.getColumnIndex(Contract.Entry.COLUMN_QUANtITY));
            float price = data.getFloat(data.getColumnIndex(Contract.Entry.COLUMN_PRICE));

            mName.setText(name);
            mSupplier.setText(supplier);
            mQuantity.setText(String.valueOf(quantity));
            mPrice.setText(String.valueOf(price));
            nQuantity = quantity;

            String ImagePath = data.getString(data.getColumnIndex(Contract.Entry.COLUMN_IMAGE_PATH));
            imageUri = ImagePath;

            try {
                grantUriPermission("com.android.providers.media.MediaProvider", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                grantUriPermission("com.android.providers.media.MediaProvider", uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                grantUriPermission("com.android.providers.media.MediaProvider", uri, Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                ImageView image = (ImageView) findViewById(R.id.Image);
                image.setImageURI(Uri.parse(ImagePath));
            } catch (Exception e) {
                Log.e(ProductAdapter.class.getSimpleName(), e + ImagePath);
            }
            data.close();


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSupplier.setText("");

        mName.setText("");
        mQuantity.setText("");
        mPrice.setText("");

        findViewById(R.id.empty_image).setVisibility(View.VISIBLE);
        findViewById(R.id.Image).setVisibility(View.GONE);
        findViewById(R.id.change_image).setVisibility(View.GONE);
    }
}
