package com.example.car;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static DatabaseAccess instance;
    private static Cursor cursor;

    private DatabaseAccess(Context context) {
        this.openHelper = new MyDatabase(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = this.openHelper.getWritableDatabase();
    }

    public void close() {
        if (this.database != null) {
            this.database.close();
        }
    }

    // دالة الاضافة
    public boolean insert_Car(Car car) {
        ContentValues values = new ContentValues();
        values.put(MyDatabase.CAR_TB_MODEL, car.getModel());
        values.put(MyDatabase.CAR_TB_COLOR, car.getColor());
        values.put(MyDatabase.CAR_TB_DPL, car.getDbl());
        values.put(MyDatabase.CAR_TB_IMAGE, car.getImage());
        values.put(MyDatabase.CAR_TB_DESCRIPTION, car.getDescription());
        long result = database.insert(MyDatabase.CAR_TB_NAME, null, values);
        return result != -1;
    }

    // دالة التعديل
    public boolean Updat_Car(Car car) {
        ContentValues values = new ContentValues();
        values.put(MyDatabase.CAR_TB_MODEL, car.getModel());
        values.put(MyDatabase.CAR_TB_COLOR, car.getColor());
        values.put(MyDatabase.CAR_TB_DPL, car.getDbl());
        values.put(MyDatabase.CAR_TB_IMAGE, car.getImage());
        values.put(MyDatabase.CAR_TB_DESCRIPTION, car.getDescription());
        values.put(MyDatabase.CAR_TB_ID , car.getId());

        //String args[] = {car.getId() + ""}; // or
        String args[] = {String.valueOf(car.getId())};
        int result = database.update(MyDatabase.CAR_TB_NAME, values, "id=?" , args); // or --> "id?"
        return result > 1;
    }

    //ارجاع عدد الصفوف في جدول معين
    public long getCarsCount() {
        return DatabaseUtils.queryNumEntries(database, MyDatabase.CAR_TB_NAME);
    }

    // دالة الحذف
    public boolean deleteCar(Car car) {
//        String args[] = {car.getId() + ""}; // or
        String args[] = {String.valueOf(car.getId())};
        int result = database.delete(MyDatabase.CAR_TB_NAME, "id=?", args); // or --> "id?"
        return result > 0;
    }

    //     دالة الاسترجاع
    public ArrayList<Car> GET_ALL_CARS() {
        ArrayList<Car> AllCars = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MyDatabase.CAR_TB_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String model = cursor.getString(1);
                String color = cursor.getString(2);
                String description = cursor.getString(3);
                String image = cursor.getString(4);
                double dpl = cursor.getDouble(5);

                Car c = new Car(id, model, color, dpl, image, description);
                AllCars.add(c);
            }
            while (cursor.moveToNext());
            cursor.close();

        }
        return AllCars;
    }

    //    public ArrayList<Car> getAllCar() {
//        cursor = database.rawQuery("SELECT * FROM " + MyDatabase.CAR_TB_NAME, null);
//        cursor.moveToFirst();
//        ArrayList<Car> cars = new ArrayList<Car>();
//        while (!cursor.isAfterLast()) {
//            Car car = new Car();
//            int id = cursor.getColumnIndex(MyDatabase.CAR_TB_ID);
//            car.setId(cursor.getInt(id));
//            int model = cursor.getColumnIndex(MyDatabase.CAR_TB_MODEL);
//            car.setModel(cursor.getString(model));
//            int color = cursor.getColumnIndex(MyDatabase.CAR_TB_COLOR);
//            car.setColor(cursor.getString(color));
//            double dbl = cursor.getColumnIndex(MyDatabase.CAR_TB_DPL);
//            car.setDbl(cursor.getDouble((int) dbl));
//            int image = cursor.getColumnIndex(MyDatabase.CAR_TB_IMAGE);
//            car.setImage(cursor.getString(image));
//            int description = cursor.getColumnIndex(MyDatabase.CAR_TB_DESCRIPTION);
//            car.setDescription(cursor.getString(description));
//            cars.add(car);
//            cursor.moveToNext();
//            cursor.close();
//        }
//        return cars;
//    }


    //     دالة الاسترجاع
//    public ArrayList<Car> getAllCar() {
//        cursor = database.rawQuery("SELECT * FROM " + MyDatabase.CAR_TB_NAME, null);
//        cursor.moveToFirst();
//        ArrayList<Car> cars = new ArrayList<Car>();
//        while (!cursor.isAfterLast()) {
//            Car car = new Car();
//            int id = cursor.getColumnIndex(MyDatabase.CAR_TB_ID);
//            car.setId(cursor.getInt(id));
//            int model = cursor.getColumnIndex(MyDatabase.CAR_TB_MODEL);
//            car.setModel(cursor.getString(model));
//            int color = cursor.getColumnIndex(MyDatabase.CAR_TB_COLOR);
//            car.setColor(cursor.getString(color));
//            double dbl = cursor.getColumnIndex(MyDatabase.CAR_TB_DPL);
//            car.setDbl(cursor.getDouble((int) dbl));
//            int image = cursor.getColumnIndex(MyDatabase.CAR_TB_IMAGE);
//            car.setImage(cursor.getString(image));
//            int description = cursor.getColumnIndex(MyDatabase.CAR_TB_DESCRIPTION);
//            car.setDescription(cursor.getString(description));
//            cars.add(car);
//            cursor.moveToNext();
//            cursor.close();
//        }
//        return cars;
//    }

//     //دالة الاسترجاع
//    public ArrayList<Car> getAllCar1() {
//        ArrayList<Car> cars = new ArrayList<Car>();
//        Cursor cursor = database.rawQuery("SELECT * FROM " + MyDatabase.CAR_TB_NAME, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                int id = (int) cursor.getInt((int)cursor.getColumnIndex(MyDatabase.CAR_TB_ID));
//                String model = (String) cursor.getString((int) cursor.getColumnIndex(MyDatabase.CAR_TB_MODEL));
//                String color = (String)cursor.getString((int)cursor.getColumnIndex(MyDatabase.CAR_TB_COLOR));
//                double dbl =(double) cursor.getInt((int)cursor.getColumnIndex(MyDatabase.CAR_TB_DPL));
//                String image =(String) cursor.getString((int)cursor.getColumnIndex(MyDatabase.CAR_TB_IMAGE));
//                String description = cursor.getString((int)cursor.getColumnIndex(MyDatabase.CAR_TB_DESCRIPTION));
//                Car c = new Car(id, model, color, dbl, image, description);
//                cars.add(c);
//            }
//            while (cursor.moveToNext());
//            cursor.close();
//        }
//        return cars;
//    }


    //دالة البحث
//    public ArrayList<Car> getCar(String modelSearch) {
//        cursor = database.rawQuery("SELECT * FROM " + MyDatabase.CAR_TB_NAME, null);
//        cursor.moveToFirst();
//        ArrayList<Car> cars = new ArrayList<Car>();
//        while (!cursor.isAfterLast()) {
//            Car car = new Car();
//            int model = cursor.getColumnIndex(MyDatabase.CAR_TB_MODEL);
//            car.setModel(cursor.getString(model));
//            int color = cursor.getColumnIndex(MyDatabase.CAR_TB_COLOR);
//            car.setColor(cursor.getString(color));
//            int dbl = cursor.getColumnIndex(MyDatabase.CAR_TB_DPL);
//            car.setDbl(cursor.getInt(dbl));
//            int image = cursor.getColumnIndex(MyDatabase.CAR_TB_IMAGE);
//            car.setImage(cursor.getString(image));
//            int description = cursor.getColumnIndex(MyDatabase.CAR_TB_DESCRIPTION);
//            car.setDescription(cursor.getString(description));
//            cars.add(car);
//            cursor.moveToNext();
//            cursor.close();
//        }
//        return cars;
//    }


    public Car getCar(int car_id) {
        cursor = database.rawQuery("SELECT * FROM " + MyDatabase.CAR_TB_NAME + " where " + MyDatabase.CAR_TB_ID + "=?"
                , new String[]{String.valueOf(car_id)});
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String model = cursor.getString(1);
            String color = cursor.getString(2);
            String description = cursor.getString(3);
            String image = cursor.getString(4);
            double dpl = cursor.getDouble(5);

            Car c = new Car(id,model, color, dpl, image, description);
            cursor.close();
           return c;
        }
        return null;
    }



//    //دالة البحث
    public ArrayList<Car> getCar(String modelSearch) {
        ArrayList<Car> cars = new ArrayList<>();
         cursor = database.rawQuery("SELECT * FROM " + MyDatabase.CAR_TB_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                //int id = cursor.getInt(0);
                String model = cursor.getString(1);
                String color = cursor.getString(2);
                String description = cursor.getString(3);
                String image = cursor.getString(4);
                double dpl3 = cursor.getDouble(5);
                Car c = new Car(model, color, dpl3, image, description);
                cars.add(c);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }
}
