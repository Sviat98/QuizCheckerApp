package com.bashkevich.quizchecker.model

//Статус REGISTERED нужен только для отображения в расписании на QuizListScreen(QuizScheduleScreen)
enum class Status {
    NOT_STARTED,REGISTERED, IN_PROGRESS, COMPLETED
}