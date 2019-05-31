package com.app.leafgarden;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView entrarComGoogle;
    TextView novaConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrarComGoogle = (TextView) findViewById(R.id.textViewGoogle);
        novaConta = (TextView) findViewById(R.id.textViewNovaConta);

        /*Link para criar conta com o Google*/
        SpannableString spannableString = new SpannableString("Entrar com o Google");
        spannableString.setSpan(new customOnclickGoogle(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        entrarComGoogle.setText(spannableString);
        entrarComGoogle.setMovementMethod(LinkMovementMethod.getInstance());

        /*Link para criar conta normal*/

        spannableString = new SpannableString("Criar Conta");
        spannableString.setSpan(new custonOnclickNovaConta(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        novaConta.setText(spannableString);
        novaConta.setMovementMethod(LinkMovementMethod.getInstance());


    }

    class customOnclickGoogle extends ClickableSpan {
        @Override
        public void onClick(View texView) {

        }

        public void updateDrawState(TextPaint textPaint) {
            textPaint.setColor(Color.BLUE);
            textPaint.setUnderlineText(true);
        }
    }

    class custonOnclickNovaConta extends ClickableSpan {
        @Override
        public void onClick(View texView) {

        }

        public void updateDrawState(TextPaint textPaint) {
            textPaint.setColor(Color.BLUE);
            textPaint.setUnderlineText(true);
        }
    }
}