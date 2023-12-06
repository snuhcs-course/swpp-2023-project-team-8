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
    LatLng(37.469020, 126.952321),
    LatLng(37.467113, 126.947597),
    LatLng(37.461482, 126.948941),
    LatLng(37.459540, 126.947481),
    LatLng(37.455836, 126.948220),
    LatLng(37.450913, 126.949656),
    LatLng(37.447540, 126.949270),
    LatLng(37.447097, 126.951417),
    LatLng(37.453400, 126.953478),
    LatLng(37.457326, 126.956584),
    LatLng(37.462478, 126.959963),
    LatLng(37.467095, 126.960976),
    LatLng(37.468567, 126.957310),
    LatLng(37.4463, 126.95080),
    LatLng(37.44639, 126.9521),
    LatLng(37.4473, 126.9485),
    LatLng(37.44946, 126.9488),
    LatLng(37.45156, 126.9490),
    LatLng(37.4523, 126.94946),
    LatLng(37.4531, 126.9493),
    LatLng(37.4536, 126.9492),
    LatLng(37.45582, 126.9482),
    LatLng(37.45745, 126.9476),
    LatLng(37.4593, 126.9472),
    LatLng(37.4601, 126.9478),
    LatLng(37.4613, 126.9490),
    LatLng(37.4644,126.9481 ),
    LatLng(37.4667,126.94828 ),
    LatLng(37.46858,126.9516 ),
    LatLng(37.4687126, 126.9571),
    LatLng(37.4682, 126.9602),
    LatLng(37.4661,126.9613 ),
    LatLng(37.4630, 126.96018),
    LatLng(37.462,126.9596 ),
    LatLng(37.4608, 126.9595),
    LatLng(37.4592,126.9582 ),
    LatLng(37.4582, 126.9579),
    LatLng(37.4536, 126.9559),
    LatLng(37.4531, 126.9549),
    LatLng(37.44949, 126.9533),
    LatLng(37.4479, 126.9533),
    LatLng(37.4466,126.9526 ),
    LatLng(37.4461,126.9516 )

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
