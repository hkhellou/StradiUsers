package com.example.stradiusers.viewmodels

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import com.example.domain.operations.UserParams
import com.example.stradiusers.R
import com.example.stradiusers.utils.OptionsList
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(private val context: Context) : ViewModel() {
    fun createOptionsList(user: UserParams?): List<OptionsList> {
        val imageProfile = context.getDrawable(R.drawable.ic_user_profile)
        val imageEmail = context.getDrawable(R.drawable.ic_mail)
        val imageGender = getGender(user)
        val imageDate = context.getDrawable(R.drawable.ic_calendar)
        val imagePhone = context.getDrawable(R.drawable.ic_phone)
        val userName = "${user?.name?.first} ${user?.name?.last}"
        val email = user?.email
        val gender = user?.gender
        val date = user?.registered?.date?.formatDate()
        val phoneNumber = user?.phone
        return listOf(
            OptionsList(imageProfile, context.getString(R.string.options_fullname_title), userName),
            OptionsList(imageEmail, context.getString(R.string.options_email_title), email),
            OptionsList(imageGender, context.getString(R.string.options_gender_title), gender),
            OptionsList(imageDate, context.getString(R.string.options_date_title), date),
            OptionsList(imagePhone, context.getString(R.string.options_phone_title), phoneNumber)
        )
    }

    private fun getGender(user: UserParams?): Drawable? {

        if (user?.gender.equals("male")) {
            return context.getDrawable(R.drawable.ic_gender_male)
        } else {
            return context.getDrawable(R.drawable.ic_gender_female)
        }
    }

    private fun String.formatDate(): String {
        val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val fecha = formatoEntrada.parse(this)

        val formatoSalida = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatoSalida.format(fecha!!)
    }

}