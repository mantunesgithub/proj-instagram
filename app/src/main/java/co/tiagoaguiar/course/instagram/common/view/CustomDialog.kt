package co.tiagoaguiar.course.instagram.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.databinding.DialogCustomBinding

class CustomDialog(context: Context) : Dialog(context) {

  private lateinit var binding: DialogCustomBinding
  private lateinit var dialogLinearLayout: LinearLayout
  private lateinit var txtTitle: TextView
  private lateinit var txtButtons: Array<TextView>

  private var titleId: Int? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DialogCustomBinding.inflate(layoutInflater)
    setContentView(binding.root)

    dialogLinearLayout = findViewById(R.id.dialog_container)
    txtTitle = findViewById(R.id.dialog_title)
  }

  override fun setTitle(titleId: Int) {
    this.titleId = titleId
  }

  fun addButton(vararg texts: Int, listener: View.OnClickListener) {
    txtButtons = Array(texts.size) {
      TextView(context)
    }

    texts.forEachIndexed { index, txtId ->
      txtButtons[index].id = txtId
      txtButtons[index].setText(txtId)
      txtButtons[index].setOnClickListener {
        listener.onClick(it)
        dismiss()
      }
    }

  }

  override fun show() {
    //* acertos para rodar API 21 *
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    super.show()

    titleId?.let {
      binding.dialogTitle.setText(it)
    }

    for(textView in txtButtons) {
      val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
      layoutParams.setMargins(30, 50, 30, 50)

      binding.dialogContainer.addView(textView, layoutParams)
    }
  }
}