package com.pokemon.shared.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.pokemon.shared.Inflate2

class AlertDialog private constructor() {
    class Builder<VB : ViewBinding>(
        ctx: Context, inflate: Inflate2<VB>
    ) : AlertDialog.Builder(ctx) {

        private var customAlertDialog: AlertDialog? = null
        private var isCancelable = true
        private var isCancelableOnTouchOutside = true
        private var mBinding: VB? = null

        val binding get() = mBinding!!

        init {
            mBinding = inflate.invoke(LayoutInflater.from(ctx))
            this.setView(binding.root)
        }

        fun setIsCancelable(isCancelable: Boolean): Builder<VB> {
            this.isCancelable = isCancelable
            return this
        }

        fun setCanceledOnTouchOutside(isCancelable: Boolean): Builder<VB> {
            this.isCancelableOnTouchOutside = isCancelable
            return this
        }

        fun build(): Builder<VB> {
            customAlertDialog = this.create()
            customAlertDialog?.setCancelable(isCancelable)
            customAlertDialog?.setCanceledOnTouchOutside(isCancelableOnTouchOutside)
            return this
        }

        /*
        *  this is for showing alert dialog with default height & width
        * */
        override fun show(): AlertDialog {
            if ((context is Activity)) {
                if (!(context as Activity).isFinishing) {
                    doShow()
                }
            } else {
                doShow()
            }
            return customAlertDialog ?: super.show()
        }

        private fun doShow() {
            customAlertDialog?.show()
            customAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        fun dismiss() {
            customAlertDialog?.dismiss()
        }
    }
}