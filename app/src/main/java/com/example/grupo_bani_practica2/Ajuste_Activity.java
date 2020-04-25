package com.example.grupo_bani_practica2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class Ajuste_Activity extends AppCompatActivity {

    int idestudiante;
    String v;
    Inf_Registrar_Activity r = new Inf_Registrar_Activity();

    public void modificar(){

        Bundle parametro = this.getIntent().getExtras();
        v= parametro.getString("valor");
        //Toast.makeText(this, "v: " + v, Toast.LENGTH_SHORT).show();

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
                //Toast.makeText(this, "v: " + v+"  r.nombre:  "+r.nombre[i], Toast.LENGTH_SHORT).show();
                if(v.equals(r.nombre[i])){
                    //Toast.makeText(this, "v2: " + v+"  r.nombre2:  "+r.nombre[i], Toast.LENGTH_SHORT).show();
                    imageView.setImageResource(Integer.parseInt(r.foto[i]));
                    editnombre.setText(r.nombre[i]);
                    editmatricula.setText(r.matricula[i]);
                    editnacimiento.setText(r.nacimiento[i]);
                    editexpresion.setText(r.expresion[i]);
                }

            }
            //Toast.makeText(this, "sueldofecha: " + sueldo.sueldofec2[i] + " posicion: " + i, Toast.LENGTH_LONG).show();
            //Toast.makeText(this, "sueldo: " + sueldo.sueldosuel2[i] + " posicion: " + i, Toast.LENGTH_LONG).show();
        }
        bd.close();
    }

    ImageView imageView;
    EditText editnombre,editmatricula,editnacimiento,editexpresion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuste_);

        imageView=(ImageView)findViewById(R.id.imagenId2);
        editnombre=(EditText)findViewById(R.id.idnombre2);
        editmatricula=(EditText)findViewById(R.id.idmatricula2);
        editnacimiento=(EditText)findViewById(R.id.idnacimiento2);
        editexpresion=(EditText)findViewById(R.id.idexpresion_creativa2);
        modificar();


    }




    public void inicio2(View v){
        Intent i2 = new Intent(this, MainActivity.class);

        startActivity(i2);
    }
}
