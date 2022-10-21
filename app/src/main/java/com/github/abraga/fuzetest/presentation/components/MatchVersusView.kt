package com.github.abraga.fuzetest.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.abraga.fuzetest.databinding.ViewMatchVersusBinding
import com.github.abraga.fuzetest.utils.loadUrl

class MatchVersusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewMatchVersusBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setHomeTeam(name: String?, logoUrl: String?) = with(binding) {
        tvMatchTeamHomeName.text = name
        ivMatchTeamHomeLogo.loadUrl(logoUrl)
    }

    fun setAwayTeam(name: String?, logoUrl: String?) = with(binding) {
        tvMatchTeamAwayName.text = name
        ivMatchTeamAwayLogo.loadUrl(logoUrl)
    }
}