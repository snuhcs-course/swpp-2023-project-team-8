package com.example.frontend.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.frontend.R
import com.example.frontend.model.MeetupModel
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
    MissionModel("미션1", "친구와 우연히 만나기", false, "예상치 못한 장소에서 친구와 마주쳐 보세요!"),
    MissionModel("미션2", "친구와 약속 잡기", false, "친구와 약속을 잡아 보세요!"),
    MissionModel("미션3", "친구와 약속 장소 정하기", false, "3명 이상의 친구와 약속을 잡아 보세요!"),
    MissionModel("미션4", "자하연 근처에서 친구 마주치기", false, "자하연에서 친구와 마주쳐 보세요!"),
    MissionModel("미션5", "관악산 등산하기", false, "관악산에 올라가 보세요!"),
    MissionModel("미션6", "도서관에 한 시간 머물기", false, "도서관에 머물며 책을 읽는 시간을 가져 보세요!"),
    MissionModel("미션7", "친구 세 명과 만나기", false, "친구 세 명과 약속을 잡아 보세요!"),
    MissionModel("미션8", "친구 스무 명 추가하기", false, "친구 20 명을 추가해 보세요!")
)

val defaultfriendIdsList: List<Long> = listOf(1L, 2L, 3L, 4L, 5L)

val defaultfriendList = listOf(
    UserWithLocationModel(1, "Alice", "Sha@snu.ac.kr", 125.9, 110.2),
    UserWithLocationModel(2, "Bob", "sha@snu.ac.kr", 11.3, 119.2),
)

val defaultLocationMarkers = listOf(
    LatLng(37.4671, 126.9476),
    LatLng(37.4653, 126.9483),
    LatLng(37.4644, 126.9482),
    LatLng(37.4636, 126.9483),
    LatLng(37.4628, 126.9487),
    LatLng(37.4615,126.9491 ),
    LatLng(37.4605, 126.9486),
    LatLng(37.45997,126.9478 ),
    LatLng(37.4593,126.9473 ),
    LatLng(37.4582, 126.9475),
    LatLng(37.4570, 126.9478),
    LatLng(37.4562, 126.948),
    LatLng(37.45460, 126.9492),
    LatLng(37.4528, 126.9495),
    LatLng(37.4517, 126.9492),
    LatLng(37.45031, 126.9492),
    LatLng(37.4477, 126.9486),
    LatLng(37.4460, 126.9492),
    LatLng(37.446,126.9516 ),
    LatLng(37.4475, 126.9533),
    LatLng(37.4495, 126.9532),
    LatLng(37.453, 126.95489),
    LatLng(37.4535, 126.9558),
    LatLng(37.4582,126.9579 ),
    LatLng(37.459, 126.958),
    LatLng(37.4604, 126.9594),
    LatLng(37.4622, 126.9595),
    LatLng(37.463, 126.9601),
    LatLng(37.4657, 126.9613),
    LatLng(37.4673, 126.9609),
    LatLng(37.4681, 126.9603),
    LatLng(37.4686, 126.9579),
    LatLng(37.4688, 126.953),
    LatLng(37.4685, 126.951)

)

val defaultSearchedList = listOf(
    UserWithLocationModel(1, "Hi", "Sha@snu.ac.kr", 125.9, 110.2),
    UserWithLocationModel(2, "HOOHOO", "sha@snu.ac.kr", 11.3, 119.2),
)


val defaultPlaces = listOf(
    PlaceModel(LatLng(11.1,123.4), "자하연",1),
            PlaceModel(LatLng(121.1,103.4), "중도",3)
)
@RequiresApi(Build.VERSION_CODES.O)
val defaultMeetups = listOf(
    MeetupModel("카페", "카페 약속입니다.", listOf(1L, 2L, 3L, 4L, 5L), LocalDateTime.MIN, true, 22)
)
