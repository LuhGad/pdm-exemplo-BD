package com.example.luciana.premiumbancodedados;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    EditText editNome, editEndereco, editTipo;

    SQLiteDatabase db;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = (EditText) findViewById(R.id.editNome);
        editEndereco = (EditText) findViewById(R.id.editEndereco);
        editTipo = (EditText) findViewById(R.id.editTipo);

        db=openOrCreateDatabase("ContatosBD", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS contatos (Nome VARCHAR, Endereco VARCHAR, Tipo VARCHAR);");


    }

    public void adicionar(View view){
        if(editNome.getText().toString().trim().length()==0 || editEndereco.getText().toString().trim().length()==0 || editTipo.getText().toString().trim().length()==0){
            showMessage("Erro"," Preencha os campos corretamente!");
                return;
        }
        db.execSQL("INSERT INTO contatos VALUES('"+editNome.getText()+"','"+editEndereco.getText()+"','"+editTipo.getText()+"');");
        showMessage("OK!"," Salvo!");
        clearText();
    }


    public void deletar(View view){
        if(editNome.getText().toString().trim().length()==0){
            showMessage("Erro!" , "Informe um nome");
            return;
        }

        Cursor cursor = db.rawQuery("SELECT * FROM contatos WHERE Nome='"+editNome.getText()+"'", null);
    if (cursor.moveToFirst()){
        db.execSQL("DELETE FROM contatos WHERE Nome='" +editNome.getText()+"'");
        showMessage("Ok","Dados deletados");
    }
        else{
        showMessage("Erro!","Inválido!");

    }
        clearText();
    }

    public void salvarEdicoes(View view){
        if(editNome.getText().toString().trim().length()==0){
            showMessage("Erro!" , "Informe um nome");
            return;

        }
        Cursor cursor = db.rawQuery("SELECT * FROM contatos WHERE Nome='"+editNome.getText()+"'", null);
        if (cursor.moveToFirst()){
            db.execSQL("UPDATE contatos SET Nome='" + editNome.getText() + "', Endereço='" + editEndereco.getText() + "',Tipo='"+ editTipo.getText() + "' WHERE Nome='"+editNome.getText()+"'");
            showMessage("Correto!","Dados atualizados");
        }
        else{
            showMessage("Erro!"," Faça uma busca primeiro");
            clearText();
        }


        }

    public void buscarContato(View view){
        if(editNome.getText().toString().trim().length()==0){
            showMessage("Erro!" , "Informe um nome");
            return;

        }

        Cursor cursor = db.rawQuery("SELECT * FROM contatos WHERE Nome='"+editNome.getText()+"'", null);
        if (cursor.moveToFirst()){
            editNome.setText(cursor.getString(0));
            editEndereco.setText(cursor.getString(1));
            editTipo.setText(cursor.getString(2));
        }
        else{
            showMessage("Erro!"," Faça uma busca primeiro");
            clearText();
        }



        }




    public void listarContatos(View view){
        Cursor cursor = db.rawQuery("SELECT * FROM contatos", null);
        if(cursor.getCount()==0){
            showMessage("Erro!","Nada encontrado");
            return;
        }
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext()){
            buffer.append("Nome" + cursor.getString(0) +"\n");
        }
        showMessage("Contato: ", buffer.toString());
    }


    public  void showMessage(String title,String message ){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void clearText(){
        editNome.setText("");
        editEndereco.setText("");
        editTipo.setText("");
    }


//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.me);
//    }


}
