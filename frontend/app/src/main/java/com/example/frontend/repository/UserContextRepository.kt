package com.example.frontend.repository

import android.content.Context
import com.example.frontend.R
import com.example.frontend.model.MissionModel

val predefinedImages = listOf(
    R.drawable.cat,
    R.drawable.cat_sunglass,
    R.drawable.dog_sunglass,
    R.drawable.hamster
)
val defaultMissions = listOf(
    MissionModel("미션1", "친구와 우연히 만나기", false, "예상치 못한 장소에서 친구와 마주쳐 보세요!"),
    MissionModel("미션2", "친구와 약속 잡기", false, "친구와 약속을 잡아 보세요!"),
    MissionModel("미션3", "친구와 약속 장소 정하기", false, "3명 이상의 친구와 약속을 잡아 보세요!"),
    MissionModel("미션4", "자하연 근처에서 친구 마주치기", false, "자하연에서 친구와 마주쳐 보세요!"),
    MissionModel("미션5", "관악산 등산하기", false, "관악산에 올라가 보세요!"),
    MissionModel("미션6", "도서관에 한 시간 머물기", false, "도서관에 머물며 책을 읽는 시간을 가져 보세요!"),
    MissionModel("미션7", "친구 세 명과 만나기", false, "친구 세 명과 약속을 잡아 보세요!"),
    MissionModel("미션8", "친구 스무 명 추가하기", false, "친구 20 명을 추가해 보세요!")
)

class UserContextRepository(
    private val context: Context
) {
    private val appPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getUserName(): String {
        val defaultValue = "User0"
        return appPrefs.getString("USERNAME", defaultValue) ?: defaultValue
    }
    fun saveUserName(newName: String?) {
        val editor = appPrefs.edit()
        editor.putString("USERNAME", newName)
        editor.apply()
    }

    fun getUserMail(): String {
        val defaultValue = "sha@snu.ac.kr"
        return appPrefs.getString("USER_MAIL", defaultValue) ?: defaultValue
    }

    fun getAuthToken(): String {
        return appPrefs.getString("AUTH_TOKEN", "") ?: ""
    }
    fun saveSelectedPredefinedImage(imageId: Int?) {
        val editor = appPrefs.edit()
        if(imageId != null && imageId != -1) {
            editor.putInt("SELECTED_PREDEFINED_IMAGE", imageId)
            editor.apply()
        }

    }

    fun getSelectedPredefinedImage(): Int? {
        val imageId = appPrefs.getInt("SELECTED_PREDEFINED_IMAGE", -1)
        return if (imageId != -1) imageId else 0
    }


}