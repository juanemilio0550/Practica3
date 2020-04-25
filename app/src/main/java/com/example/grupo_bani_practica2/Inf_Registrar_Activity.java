package com.example.grupo_bani_practica2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class Inf_Registrar_Activity extends AppCompatActivity {

    private EditText editnombre,editmatricula,editnacimiento,editexpresion;
    private ImageView imagen;
    Uri path;
    int ima;
    String nombre[]=new String[10];
    String matricula[]=new String[10];
    String nacimiento[]=new String[10];
    String expresion[]=new String[10];
    String foto[]=new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf__registrar_);

        imagen=(ImageView)findViewById(R.id.imagenId);

        //editcodigo=(EditText)findViewById(R.id.idcodigo);
        editnombre=(EditText)findViewById(R.id.idnombre);
        editmatricula=(EditText)findViewById(R.id.idmatricula);
        editnacimiento=(EditText)findViewById(R.id.idnacimiento);
        editexpresion=(EditText)findViewById(R.id.idexpresion_creativa);
    }

    public void cargarfoto(View v){
        cargarImagen();
    }

    private void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        path=data.getData();
        imagen.setImageURI(path);
        Toast.makeText(this, "Se cargó la foto.", Toast.LENGTH_LONG).show();
    }



    public void alta(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        //String cod = editcodigo.getText().toString();
        String nom = editnombre.getText().toString();
        String mat = editmatricula.getText().toString();
        String nac = editnacimiento.getText().toString();
        String desc = editexpresion.getText().toString();
        ima = imagen.getImageAlpha();
        //String im = imagen.toString();

        if(!nom.isEmpty()&&!mat.isEmpty()&&!nac.isEmpty()&&!desc.isEmpty()&&!(ima==0)) {

            ContentValues registro = new ContentValues();
            //registro.put("codigo", cod);
            registro.put("nombre", nom);
            registro.put("matricula", mat);
            registro.put("nacimiento", nac);
            registro.put("expresion", desc);
            registro.put("image", ima);

            bd.insert("estudiante", null, registro);

            //editcodigo.setText("");
            editnombre.setText("");
            editmatricula.setText("");
            editnacimiento.setText("");
            editexpresion.setText("");
            imagen.setImageResource(R.drawable.camara);


            bd.close();
            Intent i2 = new Intent(this, MainActivity.class);

            startActivity(i2);

            Toast.makeText(this, "Se cargaron los datos.", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Todos los camppos tiene que estar llenos",Toast.LENGTH_LONG).show();
        }
    }

    public void consultapormatricula(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String mat = editmatricula.getText().toString();

        if(!mat.isEmpty()) {
            Cursor fila = bd.rawQuery("select nombre,nacimiento,expresion,image from estudiante where matricula=" + mat, null);

            if (fila.moveToFirst()) {
                editnombre.setText(fila.getString(0));
                //editmatricula.setText(fila.getString(1));
                editnacimiento.setText(fila.getString(1));
                editexpresion.setText(fila.getString(2));
                int ICO = Integer.parseInt(fila.getString(3));
                imagen.setImageResource(ICO);

            } else {
                Toast.makeText(this, "No exite un registro con dicho codigo", Toast.LENGTH_LONG).show();
            }
            bd.close();
        }else {
            Toast.makeText(this,"Debes introducir el codigo del registro", Toast.LENGTH_LONG).show();
        }
    }

    public void modificar(View v){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        //String cod = editcodigo.getText().toString();
        String nom = editnombre.getText().toString();
        String mat = editmatricula.getText().toString();
        String nac = editnacimiento.getText().toString();
        String desc = editexpresion.getText().toString();
        ima = imagen.getImageAlpha();

        if(!nom.isEmpty()&&!mat.isEmpty()&&!nac.isEmpty()&&!desc.isEmpty()) {

            ContentValues registro = new ContentValues();
            //registro.put("codigo", cod);
            registro.put("nombre", nom);
            registro.put("matricula", mat);
            registro.put("nacimiento", nac);
            registro.put("expresion", desc);
            registro.put("image", ima);

            int cant = bd.update("estudiante", registro, "matricula=" + mat, null);
            bd.close();

            if (cant == 1) {
                Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No existe un registro con el codigo ingresado", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"Debes llenar todos los campos",Toast.LENGTH_LONG);
        }
        //editcodigo.setText("");
        editnombre.setText("");
        editmatricula.setText("");
        editnacimiento.setText("");
        editexpresion.setText("");
        imagen.setImageResource(R.drawable.camara);

    }


    public void bajaporcodigo(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String mat = editmatricula.getText().toString();

        if(!mat.isEmpty()) {
            int cant = bd.delete("estudiante", "matricula=" + mat, null);
            bd.close();

            if (cant == 1) {
                Toast.makeText(this, "Se borro el registro con dicha matricula", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No exite un registro con dicha matricula", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"Debes de introducir la matricula del registro",Toast.LENGTH_LONG).show();
        }
        //editcodigo.setText("");
        editnombre.setText("");
        editmatricula.setText("");
        editnacimiento.setText("");
        editexpresion.setText("");
        imagen.setImageResource(R.drawable.camara);
    }

    public void inicio(View v){
        Intent i2 = new Intent(this, MainActivity.class);

        startActivity(i2);
    }

}
