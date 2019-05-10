package cn.xhuww.sample.popup

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.xhuww.sample.R

class PopupWindowActivity : AppCompatActivity() {

    private lateinit var popupWindow: TextPopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)

        popupWindow = TextPopupWindow(this)
        popupWindow.actionViewOnClickListener = {
            if (it is TextView) {
                Toast.makeText(this, getString(R.string.click_tip, it.text), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun showPopupInTop(view: View) {
        popupWindow.showInTop(view)
    }

    fun showPopupInBottom(view: View) {
        popupWindow.showInBottom(view)
    }

    fun showPopupInLeft(view: View) {
        popupWindow.showInLeft(view)
    }

    fun showPopupInRight(view: View) {
        popupWindow.showInRight(view)
    }
}
