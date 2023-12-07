package com.example.frontend.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.frontend.R
import com.example.frontend.model.MeetUpResponse
import com.example.frontend.model.MissionModel
import com.example.frontend.model.PlaceModel
import com.example.frontend.model.UserWithLocationModel
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime

interface data {

}

val predefinedImages = listOf(
    R.drawable.cat,
    R.drawable.cat_sunglass,
    R.drawable.dog_sunglass,
    R.drawable.hamster
)
val defaultMissions = listOf(
    MissionModel("예상치 못한 장소에서 친구와 마주쳐 보세요!", "친구와 우연히 만나기", false, 0)
)

val defaultfriendIdsList: List<Long> = listOf(1L, 2L, 3L, 4L, 5L)

val defaultfriendList = listOf(
    UserWithLocationModel(1, "Alice", "Sha@snu.ac.kr", 125.9, 110.2),
    UserWithLocationModel(2, "Bob", "sha@snu.ac.kr", 11.3, 119.2),
)

val defaultSearchedList = listOf(
    UserWithLocationModel(1, "Hi", "Sha@snu.ac.kr", 125.9, 110.2),
    UserWithLocationModel(2, "HOOHOO", "sha@snu.ac.kr", 11.3, 119.2),
)


val defaultPlaces = listOf(
    PlaceModel(LatLng(11.1, 123.4), "자하연",1),
    PlaceModel(LatLng(121.1, 103.4), "중도",2)
)

@RequiresApi(Build.VERSION_CODES.O)
val defaultMeetups = listOf(
    MeetUpResponse("카페", "팀 미팅", LocalDateTime.now(), emptyList(), PlaceModel(LatLng(0.0, 0.0), "",3))
)
