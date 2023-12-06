package com.example.frontend.utilities

/*
 * @snu.ac.kr 이메일 형식인지 확인하는 함수
 */
fun isValidSnuMail(email: String): Boolean {
    return email.endsWith("@snu.ac.kr")
}