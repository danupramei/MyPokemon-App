package com.pokemon.shared.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.pokemon.shared.R
import de.hdodenhof.circleimageview.CircleImageView

class GeneralToolbar constructor(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : this(context, attrs)

    private var _attributes: TypedArray
    private var _toolbar: AppBarLayout
    private var _tvTitle: TextView
    private var _ivTitleIcon: CircleImageView
    private var _imgBack: ImageView
    private var _tbMenu: Toolbar

    val toolbar get() = _toolbar
    val title get() = _tvTitle
    val titleIcon get() = _ivTitleIcon
    val imgBack get() = _imgBack
    val toolbarMenu get() = _tbMenu


    init {
        inflate(context, R.layout.layout_general_toolbar, this)
        _attributes = context.obtainStyledAttributes(attrs, R.styleable.GeneralToolbar)

        _toolbar = findViewById(R.id.app_bar)
        _tvTitle = findViewById(R.id.tv_title_start)
        _ivTitleIcon = findViewById(R.id.civ_photo_patient_chat)
        _imgBack = findViewById(R.id.iv_btn_back)
        _tbMenu = findViewById(R.id.toolbar_telemedicine)

        val viewCard = findViewById<CardView>(R.id.root_view)
        viewCard.radius = 0.dpToPixels(context)
        setupView()

        _attributes.recycle()

    }

    fun setElevation(dp: Int) {
        val viewCard = findViewById<CardView>(R.id.root_view)
        viewCard.cardElevation = dp.dpToPixels(context)
    }

    private fun setupView() {

        val toolbarBackground = _attributes.getResourceId(
            R.styleable.GeneralToolbar_toolbarBackground,
            R.color.white
        )


        val title = _attributes.getString(R.styleable.GeneralToolbar_toolbarTitle)
        val titleColor =
            _attributes.getColor(R.styleable.GeneralToolbar_toolbarTitleColor, ContextCompat.getColor(context, R.color.white))
        val titleGravity = _attributes.getInt(
            R.styleable.GeneralToolbar_toolbarTitleGravity,
            Gravity.CENTER or Gravity.CENTER_HORIZONTAL
        )

        val isTitleIcon = _attributes.getBoolean(
            R.styleable.GeneralToolbar_toolbarShowTitleIcon,
            false
        )
        val titleIconDrawable = _attributes.getResourceId(
            R.styleable.GeneralToolbar_toolbarTitleIcon,
            R.color.transparent
        )

        val toolbarBackIcon = _attributes.getResourceId(
            R.styleable.GeneralToolbar_toolbarBackIcon,
            R.drawable.ic_arrow_left_white
        )

        val toolbarBackIconTint = _attributes.getColor(
            R.styleable.GeneralToolbar_toolbarBackIconTint,
            ContextCompat.getColor(context, R.color.black)
        )

        val toolbarAddMenu = _attributes.getResourceId(
            R.styleable.GeneralToolbar_toolbarMenu,
            R.menu.menu_null
        )

        _toolbar.setBackgroundResource(toolbarBackground)
        _tvTitle.text = title
        _tvTitle.setTextColor(titleColor)
        _ivTitleIcon.visibility = if (isTitleIcon) VISIBLE else GONE
        _ivTitleIcon.setImageResource(titleIconDrawable)
        _tvTitle.gravity = titleGravity
        _imgBack.setImageResource(toolbarBackIcon)
        _imgBack.setColorFilter(toolbarBackIconTint)
        _tbMenu.inflateMenu(toolbarAddMenu)
    }

    private fun Int.dpToPixels(context: Context):Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),context.resources.displayMetrics
    )
}