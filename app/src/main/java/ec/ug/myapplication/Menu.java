package ec.ug.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ec.ug.myapplication.dao.BaseDatos;
import ec.ug.myapplication.entidad.Usuario;

public class Menu extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtTelefono;
    private EditText txtEdad;
    private EditText txtCedula;
    private EditText txtCorreo;
    private EditText txtusuario;
    private EditText txtContrasenia;
    private TextView txtFechaNacmienito;
    private RadioButton rbtnMasculino;
    private RadioButton rbtnFemenino;

    private Button btnRegistrar;
    private String lblSpinner;

    private String nombre= "";
    private String cedula= "";
    private String edad= "";
    private String telefono= "";
    private String correo= "";
    private String usuario= "";
    private String contrasenia= "";
    private String fechaNacmienito= "";
    private String id= "";
    private String genero= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtNombre = findViewById(R.id.txtNombre2);
        txtCedula = findViewById(R.id.txtCedula2);
        txtEdad = findViewById(R.id.txtEdad2);
        txtTelefono = findViewById(R.id.txtTelefono2);
        txtCorreo = findViewById(R.id.txtCorreo2);
        txtusuario = findViewById(R.id.txtUsuario);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        txtFechaNacmienito = findViewById(R.id.txtFechaNacimiento2);
        rbtnMasculino = findViewById(R.id.rbtnMasculino2);
        rbtnFemenino = findViewById(R.id.rbtnFemenino2);

        btnRegistrar = findViewById(R.id.btnRegistrar2);

        llenarSpiner();
    }

    public void actualizar(View v) {
        //actualizar de datos ne la bd creada
        Usuario objUsu = new Usuario();
        objUsu.setNombres(txtNombre.getText().toString());

        BaseDatos sqlite = new BaseDatos(this);
        final SQLiteDatabase bd = sqlite.getWritableDatabase();

        if (bd != null) {
            System.out.println("CREACION DE BD+=====================================================");
            ContentValues cv = new ContentValues();
            cv.put("nombre", txtNombre.getText().toString());
            cv.put("edad", txtEdad.getText().toString());
            cv.put("telefono", txtTelefono.getText().toString());
            cv.put("correo", txtCorreo.getText().toString());
            cv.put("usuario", txtusuario.getText().toString());
            cv.put("clave", txtContrasenia.getText().toString());
            cv.put("fecha_nacimiento", txtFechaNacmienito.getText().toString());
            if (rbtnMasculino.isChecked()) {
                cv.put("genero", "masculino");
            } else if (rbtnFemenino.isChecked()) {
                cv.put("genero", "femenino");
            }

            bd.insert("usuario", null, cv);
            Toast.makeText(this, "Se ha registrado con exito", Toast.LENGTH_SHORT).show();
            bd.close();
            irAtras(v);
        } else {
            Toast.makeText(this, "No se ha establecido conexion con la BD", Toast.LENGTH_SHORT).show();
        }
        limpiarComponentes();
    }

    public void irAtras(View view) {
        Intent atras = new Intent(this, MainActivity.class);
        startActivity(atras);
        limpiarComponentes();
        //Toast.makeText(this, "No Registro ningun usuario.", Toast.LENGTH_SHORT).show();
    }

    private void limpiarComponentes() {
        txtNombre.setText(" ");
        txtCedula.setText(" ");
        txtEdad.setText(" ");
        txtTelefono.setText(" ");
        txtCorreo.setText(" ");
        txtusuario.setText(" ");
        txtContrasenia.setText(" ");
        txtFechaNacmienito.setText(" ");
        rbtnMasculino.setText(" ");
        rbtnFemenino.setText(" ");
        rbtnMasculino.setSelected(false);
        rbtnFemenino.setSelected(false);
    }

    private void llenarSpiner() {
        Spinner ciudades = (Spinner) findViewById(R.id.spCiudad);
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.spiner_ciudades, android.R.layout.simple_spinner_item);
        //diseño que utilizara el spinnercuando aparezca la lista de opciones
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ciudades.setAdapter(adaptador);///establecer el adaptador al spinner

        //evento interno
        ciudades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                lblSpinner = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void abrirDialogoFecha(View v) {
        final TextView vFechaNac = (TextView) findViewById(R.id.txtFechaNacimiento2);

        fragment_date_picker nuevoFragment = fragment_date_picker.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                final String fechaSeleccionada = dia + "/" + (mes + 1) + "/" + anio; //el mes comienza desde cero por eso se le suma 1
                vFechaNac.setText(fechaSeleccionada);
            }
        });

        nuevoFragment.show(getSupportFragmentManager(), "Calendario");
    }

    public void buscarUsuario(View view) {
        System.out.println("Buscandooooo=========================");
        BaseDatos sqlite = new BaseDatos(this);
        final SQLiteDatabase bd = sqlite.getWritableDatabase();
        Cursor cr = bd.rawQuery
//                ("SELECT * FROM USUARIO where cedula ='" + txtCedula.getText().toString() + "'", null);
                ("SELECT * FROM USUARIO ", null);
        if (cr != null) {

            if (cr.moveToFirst()) {
                try {
                    //System.out.println("datos  SQLITE");
                    System.out.println("sql  " + cr.getString(cr.getColumnIndex("usuario")).toString());
                    System.out.println("sql  " + cr.getString(cr.getColumnIndex("clave")).toString());

                    id= cr.getString(cr.getColumnIndex("ID")).toString();
                    nombre= cr.getString(cr.getColumnIndex("nombre")).toString();
                    cedula= cr.getString(cr.getColumnIndex("cedula")).toString();
                    edad= cr.getString(cr.getColumnIndex("edad")).toString();
                    telefono= cr.getString(cr.getColumnIndex("telefono")).toString();
                    correo= cr.getString(cr.getColumnIndex("correo")).toString();
                    usuario= cr.getString(cr.getColumnIndex("usuario")).toString();
                    contrasenia= cr.getString(cr.getColumnIndex("clave")).toString();
                    fechaNacmienito= cr.getString(cr.getColumnIndex("fecha_nacimiento")).toString();
                    genero = cr.getString(cr.getColumnIndex("genero")).toString();

                    txtNombre.setText(nombre);
                    txtTelefono.setText(telefono);
                    txtEdad.setText(edad);
                    txtCedula.setText(cedula);
                    txtCorreo.setText(correo);
                    txtusuario.setText(usuario);
                    txtContrasenia.setText(contrasenia);
                    txtFechaNacmienito.setText(fechaNacmienito);
                    if(genero.equalsIgnoreCase("femenino")){
                        rbtnFemenino.setSelected(true);
                        rbtnMasculino.setSelected(false);
                    }else
                        if(genero.equalsIgnoreCase("masculino")){
                            rbtnMasculino.setSelected(true);
                            rbtnFemenino.setSelected(false);
                    }
                    bd.close();
                } catch (IllegalStateException e) {
                    System.out.println("===============Error en bd " + e);
                }


            }
            else{
                Toast.makeText(this, "Usuario No Existe", Toast.LENGTH_SHORT).show();
            }
        }
    }

}