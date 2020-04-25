package com.example.grupo_bani_practica2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int idestudiante;
    ListView myListView;
    ArrayAdapter<String> adapter;

    Inf_Registrar_Activity r = new Inf_Registrar_Activity();

    public void modificar(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor cursor = bd.rawQuery("SELECT * FROM estudiante", null);

        if (cursor.moveToLast()){
            idestudiante = cursor.getInt(0);
            //Toast.makeText(this, "id: " + idestudiante, Toast.LENGTH_SHORT).show();
        }
        if(idestudiante>=0){

        } else {
            idestudiante=0;
        }

        for (int i = 1; i <= idestudiante; i++) {
            Cursor fila = bd.rawQuery("select nombre,matricula,nacimiento,expresion,image from estudiante where codigo=" + i, null);
            //Toast.makeText(this, "for: " + idestudiante, Toast.LENGTH_SHORT).show();
            if (fila.moveToFirst()) {
                r.nombre[i] = fila.getString(0);
                r.matricula[i] = fila.getString(1);
                r.nacimiento[i] = fila.getString(2);
                r.expresion[i] = fila.getString(3);
                r.foto[i] = fila.getString(4);

            }
            //Toast.makeText(this, "sueldofecha: " + sueldo.sueldofec2[i] + " posicion: " + i, Toast.LENGTH_LONG).show();
            //Toast.makeText(this, "sueldo: " + sueldo.sueldosuel2[i] + " posicion: " + i, Toast.LENGTH_LONG).show();
        }
        bd.close();
    }

    private ArrayList<String> getEstudiante() {
        ArrayList<String> data = new ArrayList<>();
        data.clear();

        int ind = idestudiante;
        //Toast.makeText(this,"idEstudiante: "+idestudiante,Toast.LENGTH_LONG).show();
        for (int i = 1; i <= ind; i++) {
            //Toast.makeText(this, "fordata: " + idestudiante, Toast.LENGTH_SHORT).show();
            if(r.nombre[i]==null||r.matricula[i]==null){
                //Toast.makeText(this,"getEstudiantenulo: "+idestudiante,Toast.LENGTH_LONG).show();
            }else {
                //Toast.makeText(this,"0 no es nulo   i="+i,Toast.LENGTH_SHORT).show();
                data.add(r.nombre[i]);

            }

        }
        return data;
    }

    private void bindDate(){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getEstudiante());
        myListView.setAdapter(adapter);
    }
    String p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        modificar();
        myListView= (ListView) findViewById(R.id.myListViews);
        bindDate();
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,getEstudiante().get(position),Toast.LENGTH_SHORT).show();
                p=getEstudiante().get(position);

                Intent i2 = new Intent(MainActivity.this, Ajuste_Activity.class);
                i2.putExtra("valor",p);
                startActivity(i2);
            }

        });


    }



    public void registro(View view){
        Intent intent = new Intent(this, Inf_Registrar_Activity.class);
        startActivity(intent);
        Toast.makeText(this, "Informacion de registro", Toast.LENGTH_LONG).show();
    }
}
