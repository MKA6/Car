package com.example.car;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_CAR_REQ_CODE = 1;
    private static final int EDIT_CAR_REQ_CODE = 1;
    public static final String CAR_KEY = "car_key";

    private FloatingActionButton fab;
    private RecyclerView rv;
    private Toolbar toolbar;
    private MyCar_Adapter adapter;
    private DatabaseAccess databaseAccess;
    private static final int PERMISSION_REQ_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // عشان يطلب صلاحية الوصول للصور
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQ_CODE);
        }
/////////////////////////////////////////

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.main_fab);
        rv = findViewById(R.id.main_rv);

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        ArrayList<Car> cars = databaseAccess.GET_ALL_CARS();
        databaseAccess.close();

        adapter = new MyCar_Adapter(cars, new OnReclerViewItemClickListener() {
            @Override
            public void onItemClick(int CarId) {
                Intent intent = new Intent(getBaseContext(), ViewCarActivity.class);
                intent.putExtra(CAR_KEY, CarId);
                startActivityForResult(intent, EDIT_CAR_REQ_CODE);
            }
        });

        rv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2); // بعمل على كيفية عرض الشكل
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ViewCarActivity.class);
                startActivityForResult(intent, ADD_CAR_REQ_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setSubmitButtonEnabled(true); // هاد بعمل على اضافة زر بجانب للضغط عليه للقيام بعملة البحث
        // هاد يعني انه بدك تعمل كوري على النص الي بدك تعمل اله استعلام
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            // يتم تنفيذ الدالة هاي لما المستخدم يضغط على زر البحث الموجود بالجانب
            public boolean onQueryTextSubmit(String query) {
                databaseAccess.open();
                ArrayList<Car> cars = databaseAccess.getCar(query);
                databaseAccess.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                //Toast.makeText(MainActivity.this, "Submit clicked", Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            // تستدعى كل ما يغير المستخم النص الموجود في الاستعلام
            //خلال عملية الكتابة
            public boolean onQueryTextChange(String newText) {
                databaseAccess.open();
                ArrayList<Car> cars = databaseAccess.getCar(newText);
                databaseAccess.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
            //    Toast.makeText(MainActivity.this, "text changed", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                databaseAccess.open();
                ArrayList<Car> cars = databaseAccess.GET_ALL_CARS();
                databaseAccess.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
          //      Toast.makeText(MainActivity.this, "srarch finished", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CAR_REQ_CODE) {
            databaseAccess.open();
            ArrayList<Car> cars = databaseAccess.GET_ALL_CARS();
            databaseAccess.close();
            adapter.setCars(cars);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQ_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // تم الحصول على الصلاحية
                }
        }

    }
}