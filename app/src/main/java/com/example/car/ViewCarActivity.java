package com.example.car;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ViewCarActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQ_CODE = 1;
    public static final int ADD_CAR_RESULT_CODE = 2;
    public static final int EDIT_CAR_RESUL_TCODE = 3;
    Toolbar toolbar;
    private TextInputEditText et_model, et_color, et_dpl, et_description;
    private ImageView imageView;
    private int car_id;  //// or car = 0 or -1
    private DatabaseAccess databaseAccess;
    private Uri ImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);

        toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.details_iv);
        et_model = findViewById(R.id.et_detalls_model);
        et_color = findViewById(R.id.et_detalls_color);
        et_dpl = findViewById(R.id.et_detalls_dpl);
        et_description = findViewById(R.id.et_detalls_description);

        databaseAccess = DatabaseAccess.getInstance(this);

        Intent intent = getIntent(); // هاد يعني انه روح جيبه الي
        car_id = intent.getIntExtra(MainActivity.CAR_KEY, -1); ////// or == 0

        if (car_id == -1) { // or car_id >= 0
            // عملية الاضافة
            enabledFields();
            clearFields();
        } else {
            // عملية عرض
            disableFields();
            databaseAccess.open();
            Car c = databaseAccess.getCar(car_id);
            databaseAccess.close();
            if (c != null) {
                fillCarToFields(c);
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, PICK_IMAGE_REQ_CODE);
            }
        });


    }

    private void fillCarToFields(Car car) {
        if (car.getImage() != null && !car.getImage().equals(""))
            imageView.setImageURI(Uri.parse(car.getImage()));
        et_model.setText(car.getModel());
        et_color.setText(car.getColor());
        et_description.setText(car.getDescription());
        et_dpl.setText(car.getDbl() + "");
    }

    private void disableFields() {
        imageView.setEnabled(false);
        et_model.setEnabled(false);
        et_color.setEnabled(false);
        et_description.setEnabled(false);
        et_dpl.setEnabled(false);
    }

    private void enabledFields() {
        imageView.setEnabled(true);
        et_model.setEnabled(true);
        et_color.setEnabled(true);
        et_description.setEnabled(true);
        et_dpl.setEnabled(true);
    }

    private void clearFields() {
        imageView.setImageURI(null);
        et_model.setText("");
        et_color.setText("");
        et_description.setText("");
        et_dpl.setText("");
    }

    @Override
    // الشاشة الخاصة بالتفاصيل
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        MenuItem Save = menu.findItem(R.id.details_menu_save);
        MenuItem Edit = menu.findItem(R.id.details_menu_edit);
        MenuItem Delete = menu.findItem(R.id.details_menu_delete);
        if (car_id == -1) {  // or 0
            // عملية اضافة
            Save.setVisible(true); // يعني ما رح يظهر غير هاد الزر
            Edit.setVisible(false);
            Delete.setVisible(false);
        } else {
            // عملية عرض
            Save.setVisible(false);
            Edit.setVisible(true);
            Delete.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String model, color, image = "", description;
        double dpl;
        databaseAccess.open();
        switch (item.getItemId()) {

            case R.id.details_menu_save:
                model = et_model.getText().toString();
                color = et_color.getText().toString();
                description = et_description.getText().toString();
                dpl = Double.parseDouble(et_dpl.getText().toString());
                if (ImageUri != null)
                    image = ImageUri.toString();
                boolean res;
                Car c = new Car(car_id, model, color, dpl, image, description);
                if (car_id == -1) {
                    res = databaseAccess.insert_Car(c);
                    if (res) {
                        Toast.makeText(this, "Car added successfully", Toast.LENGTH_LONG).show();
                        setResult(ADD_CAR_RESULT_CODE, null);
                        finish();
                    }
                } else {
                    res = databaseAccess.Updat_Car(c);
                    if (res) {
                        Toast.makeText(this, "Car modified successfully", Toast.LENGTH_LONG).show();
                        setResult(ADD_CAR_RESULT_CODE, null);
                        finish();
                    }
                }
                return true;
            case R.id.details_menu_edit:
                enabledFields();
                MenuItem Save = toolbar.getMenu().findItem(R.id.details_menu_save);
                MenuItem Edit = toolbar.getMenu().findItem(R.id.details_menu_edit);
                MenuItem Delete = toolbar.getMenu().findItem(R.id.details_menu_delete);
                Delete.setVisible(false);
                Edit.setVisible(false);
                Save.setVisible(true);

                return true;
            case R.id.details_menu_delete:
                //  boolean res;
                c = new Car(car_id, null, null, 0, null, null);
                databaseAccess.open();
                res = databaseAccess.insert_Car(c);
                databaseAccess.deleteCar(c);
                if (res) {
                    Toast.makeText(this, "Car deleteed successfully", Toast.LENGTH_LONG).show();
                    setResult(EDIT_CAR_RESUL_TCODE, null);
                    finish();
                }
                return true;
        }
        databaseAccess.close();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQ_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                ImageUri = data.getData();
                imageView.setImageURI(ImageUri);
            }
        }
    }
}