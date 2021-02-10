package ec.ug.myapplication;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import ec.ug.myapplication.dao.BaseDatos;
import ec.ug.myapplication.entidad.Usuario;

public class FrmNuevoUsuario extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtEdad;
    private EditText txtTelefono;
    private EditText txtCorreo;
    private EditText txtusuario;
    private EditText txtContrasenia;
    private TextView txtFechaNacmienito;
    private RadioButton rbtnMasculino;
    private RadioButton rbtnFemenino;
    private Button btnRegistrar;
    private String lblSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_nuevo_usuario);

        txtNombre = findViewById(R.id.txtFNombre);
        txtEdad = findViewById(R.id.txtFEdad);
        txtTelefono = findViewById(R.id.txtFTelefono);
        txtCorreo = findViewById(R.id.txtFCorreo);
        txtusuario = findViewById(R.id.txtFUsuario);
        txtContrasenia = findViewById(R.id.txtFContrasenia);
        txtFechaNacmienito = findViewById(R.id.txtFFechaNacimiento);
        rbtnMasculino = findViewById(R.id.rbtnFMasculino);
        rbtnFemenino = findViewById(R.id.rbtnFFemenino);
        btnRegistrar = findViewById(R.id.btnFRegistrar);

        llenarSpiner();
    }

    public void registrar(View v) {
        /*Ingreso de datos ne la bd creada*/
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

    private void llenarSpiner(){
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
        final TextView vFechaNac = (TextView) findViewById(R.id.txtFFechaNacimiento);

        fragment_date_picker nuevoFragment = fragment_date_picker.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                final String fechaSeleccionada = dia + "/" + (mes + 1) + "/" + anio; //el mes comienza desde cero por eso se le suma 1
                vFechaNac.setText(fechaSeleccionada);
            }
        });

        nuevoFragment.show(getSupportFragmentManager(), "Calendario");
    }
}
