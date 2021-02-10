package ec.ug.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.ug.myapplication.dao.BaseDatos;

public class MainActivity extends AppCompatActivity {

    private String user;
    private Button btnNuevoUsuario;
    private String usuario = "";
    private String clave  = "";
    private EditText txtUsuario;
    private EditText txtContra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContra = findViewById(R.id.txtContrasenia);
        btnNuevoUsuario = (Button) findViewById(R.id.btnFRegistrar);

    }

    public void irNuevoFormulario(View view){
        Intent obj = new Intent(this, FrmNuevoUsuario.class);
        //putExtras(key, valor);
        startActivity(obj);
        //startActivityForResult(obj, 0);
    }

    public void selectTable(View view){
        BaseDatos sqlite = new BaseDatos(this);
        final SQLiteDatabase bd = sqlite.getWritableDatabase();
        //Cursor cr = bd.rawQuery("SELECT * FROM usuario", null);
        Cursor cr = bd.rawQuery
                ("SELECT usuario, clave, cedula FROM usuario where usuario ='"+txtUsuario.getText().toString()+"'", null);
        System.out.println("sql lite======================>");
        if(cr != null){

           if(cr.moveToFirst()){
               try{
                   System.out.println("datos  SQLITE");
                   System.out.println("1  "+cr.getString(cr.getColumnIndex("usuario")).toString());
                   System.out.println("2  "+cr.getString(cr.getColumnIndex("clave")).toString());
                   //System.out.println("  "+cr.getString(cr.getColumnIndex("cedula")).toString());

                   usuario = cr.getString(cr.getColumnIndex("usuario")).toString();
                   clave = cr.getString(cr.getColumnIndex("clave")).toString();
                   bd.close();
               }catch (IllegalStateException e){
                   System.out.println("===============Error en bd "+e);
               }

           }
//            do{
//
//            }while(cr.moveToNext());
        }
        if(usuario.equals(txtUsuario.getText().toString()) && clave.equals(txtContra.getText().toString())){
            Intent obj = new Intent(this, Menu.class);
            startActivity(obj);
            Toast.makeText(this, "Acceso exitoso", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Acceso denegado", Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }

    private void limpiar(){
        txtUsuario.setText("");
        txtContra.setText("");
    }
}