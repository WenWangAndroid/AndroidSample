package cn.xhuww.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.xhuww.sample.popup.PopupWindowActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startPopupWindowActivity(view: View) {
        startActivity(Intent(this, PopupWindowActivity::class.java))
    }
}
