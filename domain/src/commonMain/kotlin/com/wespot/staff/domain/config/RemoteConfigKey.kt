package com.wespot.staff.domain.config

enum class RemoteConfigKey {
    MIN_VERSION,
    BASE_URL,
    MOCK_BASE_URL,
    VOTE_QUESTION_GOOGLE_FORM_URL,
    WESPOT_KAKAO_CHANNEL_URL,
    WESPOT_INSTAGRAM_URL,
    USER_OPINION_GOOGLE_FORM_URL,
    ANDROID_LATEST_VERSION,
    RESEARCH_PARTICIPATION_GOOGLE_FORM_URL,
    WESPOT_MAKERS_URL,
    PROFILE_CHANGE_GOOGLE_FORM_URL,
    PRIVACY_POLICY_URL,
    PLAY_STORE_URL,
    TERMS_OF_SERVICE_URL,
    SCHOOL_FORM,
    MARKETING_SERVICE_TERM,
    MESSAGE_RECEIVE_TIME,
    MESSAGE_START_TIME;

    fun toDescription(): String =
        when (this) {
            MIN_VERSION -> "안드로이드 최소 지원 버전"
            BASE_URL -> "API 기본 URL"
            ANDROID_LATEST_VERSION -> "안드로이드 최신 지원 버전"
            MOCK_BASE_URL -> "모의 API를 위한 기본 URL"
            VOTE_QUESTION_GOOGLE_FORM_URL -> "투표 질문 Google 폼 URL"
            WESPOT_KAKAO_CHANNEL_URL -> "WeSpot 카카오 채널 URL"
            WESPOT_INSTAGRAM_URL -> "WeSpot Instagram URL"
            USER_OPINION_GOOGLE_FORM_URL -> "사용자 의견 수집 Google 폼 URL"
            RESEARCH_PARTICIPATION_GOOGLE_FORM_URL -> "리서치 참여 Google 폼 URL"
            WESPOT_MAKERS_URL -> "WeSpot Makers URL"
            PROFILE_CHANGE_GOOGLE_FORM_URL -> "프로필 변경 요청 Google 폼 URL"
            PRIVACY_POLICY_URL -> "개인정보 처리방침 URL"
            PLAY_STORE_URL -> "Google Play 스토어 URL"
            TERMS_OF_SERVICE_URL -> "서비스 약관 URL"
            SCHOOL_FORM -> "학교 정보를 입력하는 Google 폼 URL"
            MARKETING_SERVICE_TERM -> "마케팅 서비스 이용 약관"
            MESSAGE_RECEIVE_TIME -> "메시지 수신 시간"
            MESSAGE_START_TIME -> "메시지 시작 시간"
        }
}
