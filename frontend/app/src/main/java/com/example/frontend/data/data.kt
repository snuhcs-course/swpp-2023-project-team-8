package com.example.frontend.data

import com.example.frontend.R
import com.example.frontend.model.MeetupModel
import com.example.frontend.model.MissionModel
import com.example.frontend.model.PlaceModel
import com.example.frontend.model.UserWithLocationModel
import com.google.android.gms.maps.model.LatLng

interface data {

}
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
val defaultfriendList = listOf(
    UserWithLocationModel(1, "Alice", "Sha@snu.ac.kr", 125.9, 110.2),
    UserWithLocationModel(2, "Bob", "sha@snu.ac.kr", 11.3, 119.2),
)

val defaultPlaces = listOf(
    PlaceModel(LatLng(11.1,123.4), "자하연"),
            PlaceModel(LatLng(121.1,103.4), "중도")
)
val defaultMeetups = listOf(
    MeetupModel("카페", listOf("친구1", "친구2", "친구3"), "14:00", "2023-12-01", LatLng(13.1, 23.1))
)
