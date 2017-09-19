package home.pam.notepad;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText txt_anotacao;
    private ImageView btn_salvar;
    private static final String ARQUIVO = "arquivo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_anotacao = (EditText) findViewById(R.id.txt_anotacao);
        btn_salvar = (ImageView) findViewById(R.id.btn_salvar);

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Captura o texto digitado pelo usuário*/
                String msg = txt_anotacao.getText().toString();

                /*Verifica se o usuário está tentando salvar um arquivo em branco */
                if(ARQUIVO == null && msg.equals("")){
                    Toast.makeText(MainActivity.this, "Por favor insira um texto para salvar", Toast.LENGTH_SHORT).show();
                }else {
                    gravarArquivo(msg);
                    Toast.makeText(MainActivity.this, "Texto salvo com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*Recupera o texto */
        txt_anotacao.setText(lerArquivo());
    }

    private void gravarArquivo(String texto){
        try{
             /* OutputStreamWriter = grava o arquivo
             * openFileOutput = abre/cria o arquivo (nome do arquivo, permissão de leitura)
             */
            OutputStreamWriter gravar = new OutputStreamWriter(openFileOutput(ARQUIVO, Context.MODE_PRIVATE));

            /*Escreve no arquivo*/
            gravar.write(texto);

            /*Fecha o arquivo*/
            gravar.close();

        }catch (Exception e){
            /*Cria um log e transforma o erro em String*/
            Log.v("Principal", e.toString());
        }
    }

    private String lerArquivo() {
        String resultado = "";
        String linhaArquivo = "";

        try {
            /* Abre o arquivo*/
            InputStream arquivo = openFileInput(ARQUIVO);

            /*Verifica se o arquivo existe*/
            if(arquivo !=null){
                /* Lê o arquivo*/
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

                /* Gera um buffer(bloco de bytes) do arquivo lido */
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                /*Recupera o texto e insere na variável o processo só termina quando todo o arquivo é lido*/
                while((linhaArquivo = bufferedReader.readLine()) != null){

                    resultado +=linhaArquivo;
                }
                arquivo.close();
            }

        }catch (Exception e){
            Log.v("Principal", e.toString());
        }

        return resultado;
    }
}
