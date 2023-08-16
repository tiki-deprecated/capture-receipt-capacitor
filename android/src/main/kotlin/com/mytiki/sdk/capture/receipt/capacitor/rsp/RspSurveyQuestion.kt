/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.getcapacitor.JSArray
import com.microblink.core.SurveyQuestion
import org.json.JSONObject

class RspSurveyQuestion(surveyQuestion: SurveyQuestion) : Rsp {
    private val myIndex: Int
    private val lastQuestion: Boolean
    private val nextQuestionIndex: Int
    private val serverId: Int
    private val text: String?
    private val type: String?
    private val answers: List<RspSurveyAnswer>?
    private val multipleAnswers: Boolean
    private val totalNumberOfQuestions: Int

    init {
        myIndex = surveyQuestion.myIndex()
        lastQuestion = surveyQuestion.lastQuestion()
        nextQuestionIndex = surveyQuestion.nextQuestionIndex()
        serverId = surveyQuestion.serverId()
        text = surveyQuestion.text()
        type = surveyQuestion.type()?.a
        answers = surveyQuestion.answers()?.map { answer -> RspSurveyAnswer(answer) }
        multipleAnswers = surveyQuestion.multipleAnswers()
        totalNumberOfQuestions = surveyQuestion.totalNumberOfQuestions()
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("myIndex", myIndex)
            .put("lastQuestion", lastQuestion)
            .put("nextQuestionIndex", nextQuestionIndex)
            .put("serverId", serverId)
            .put("text", text)
            .put("type", type)
            .put("answers", JSArray.from(answers?.map { answer -> answer.toJson() }))
            .put("multipleAnswers", multipleAnswers)
            .put("totalNumberOfQuestions", totalNumberOfQuestions)

    companion object {
        fun opt(surveyQuestion: SurveyQuestion?): RspSurveyQuestion? =
            if (surveyQuestion != null) RspSurveyQuestion(surveyQuestion) else null
    }
}