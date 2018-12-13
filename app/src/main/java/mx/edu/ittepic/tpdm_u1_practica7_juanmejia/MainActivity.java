package mx.edu.ittepic.tpdm_u1_practica7_juanmejia;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText edad,archivo;
    CheckBox mexico,america,europa,asia;
    RadioButton casado,soltero;
    Switch selec;
    Button aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selec=(Switch)findViewById(R.id.selec);

        edad=(EditText)findViewById(R.id.edad);

        mexico=(CheckBox)findViewById(R.id.mexico);
        america=(CheckBox)findViewById(R.id.america);
        europa=(CheckBox)findViewById(R.id.europa);
        asia=(CheckBox)findViewById(R.id.asia);

        casado=(RadioButton)findViewById(R.id.casado);
        soltero=(RadioButton)findViewById(R.id.soltero);

        aceptar=(Button)findViewById(R.id.aceptar);

        permisos();

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ciudad="";
                String activo="";

                if(mexico.isChecked()){
                    ciudad += "Mexico\n";
                }

                if(america.isChecked()){
                    ciudad += "America\n";
                }

                if(europa.isChecked()){
                    ciudad += "Europa\n";
                }

                if(asia.isChecked()){
                    ciudad += "Asia\n";
                }

                if(casado.isChecked()){
                    ciudad += "Casado\n";
                }

                if(soltero.isChecked()){
                    ciudad += "Soltero\n";
                }

                String cad="";
                cad = "Edad: "+edad.getText().toString();
                cad +="\nLugares visitados: \n"+ciudad;
                cad +="\nEstado Civil: \n"+activo;

                if(selec.isChecked()){
                    archivo = new EditText(MainActivity.this);
                    AlertDialog.Builder alerta=new AlertDialog.Builder(MainActivity.this);
                    final String finalCad = cad;
                    alerta.setTitle("Guardar Archivo").setView(archivo)
                            .setPositiveButton("guardar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    guardar(archivo.getText().toString(), finalCad);
                                }
                            }).setMessage("Nombre del archivo?").show();

                }else{
                    AlertDialog.Builder alerta=new AlertDialog.Builder(MainActivity.this);
                    alerta.setTitle("Alerta").setMessage("Sin permisos")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }

            }//onClick
        });

    }//onCreate

    public void guardar(String nombre, String cad2) {
        FileOutputStream flujo=null;
        OutputStreamWriter escritor = null;

        try{
            File ruta = Environment.getExternalStorageDirectory();
            File fichero = new File(ruta.getAbsolutePath(), nombre+".txt");
            flujo = new FileOutputStream(fichero);
            escritor= new OutputStreamWriter(flujo);
            escritor.write(cad2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(getBaseContext(), "Archivo guardado", Toast.LENGTH_SHORT).show();
    }//guardar

    private void permisos() {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
    }//permisos

}//class
