/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.SurveyQuestion
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a survey question in the RSP (Receipt Survey Processor) module.
 *
 * @param surveyQuestion The [SurveyQuestion] object from the Microblink SDK.
 */
class RspSurveyQuestion(surveyQuestion: SurveyQuestion) : Rsp {
    private val myIndex: Int
    private val lastQuestion: Boolean
    private val nextQuestionIndex: Int
    private val serverId: Int
    private val text: String?
    private val type: String?
    private val answers: List<RspSurveyAnswer>
    private val multipleAnswers: Boolean
    private val totalNumberOfQuestions: Int

    init {
        myIndex = surveyQuestion.myIndex()
        lastQuestion = surveyQuestion.lastQuestion()
        nextQuestionIndex = surveyQuestion.nextQuestionIndex()
        serverId = surveyQuestion.serverId()
        text = surveyQuestion.text()
        type = surveyQuestion.type()?.a
        answers = surveyQuestion.answers()?.map { answer -> RspSurveyAnswer(answer) } ?: emptyList()
        multipleAnswers = surveyQuestion.multipleAnswers()
        totalNumberOfQuestions = surveyQuestion.totalNumberOfQuestions()
    }

    /**
     * Converts the RspSurveyQuestion object to a JSON representation.
     *
     * @return A [JSONObject] representing the RspSurveyQuestion.
     */
    override fun toJson(): JSONObject =
        JSONObject()
            .put("myIndex", myIndex)
            .put("lastQuestion", lastQuestion)
            .put("nextQuestionIndex", nextQuestionIndex)
            .put("serverId", serverId)
            .put("text", text)
            .put("type", type)
            .put("answers", JSONArray(answers.map { answer -> answer.toJson() }))
            .put("multipleAnswers", multipleAnswers)
            .put("totalNumberOfQuestions", totalNumberOfQuestions)

    companion object {
        /**
         * Creates an RspSurveyQuestion instance from a [SurveyQuestion] object.
         *
         * @param surveyQuestion The [SurveyQuestion] object to convert.
         * @return An [RspSurveyQuestion] instance or null if the input is null.
         */
        fun opt(surveyQuestion: SurveyQuestion?): RspSurveyQuestion? =
            if (surveyQuestion != null) RspSurveyQuestion(surveyQuestion) else null
    }
}
