package com.pokemon.ui.view.input_nick_dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import com.pokemon.ui.databinding.DialogInputNicknameBinding

class InputNicknameDialog(context: Context, nick: String? = null, onSave: (String) -> Unit) : AppCompatDialog(context) {

    private var binding: DialogInputNicknameBinding

    init {
        binding = DialogInputNicknameBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        val width = (context.resources.displayMetrics.widthPixels * 0.90)
        this.window?.setLayout(width.toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        this.window?.decorView?.setBackgroundColor(context.getColor(android.R.color.transparent))

        nick?.let {
            val firstNickname = nick.split("-").first()
            binding.nicknameEditText.setText(firstNickname)
        }?: run {
            binding.nicknameEditText.setText("")
        }
        binding.saveButton.setOnClickListener {
            onSave.invoke(binding.nicknameEditText.text.toString())
            dismiss()
        }
    }
}