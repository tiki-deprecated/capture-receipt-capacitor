/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.SurveyAnswer
import org.json.JSONObject

class RspSurveyAnswer(surveyAnswer: SurveyAnswer) : Rsp {
    private val id: Int
    private val text: String?
    private val nextQuestionIndex: Int?

    init {
        id = surveyAnswer.serverId()
        text = surveyAnswer.text()
        nextQuestionIndex = surveyAnswer.nextQuestionIndex()
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("id", id)
            .put("text", text)
            .put("nextQuestionIndex", nextQuestionIndex)

    companion object {
        fun opt(surveyAnswer: SurveyAnswer?): RspSurveyAnswer? =
            if (surveyAnswer != null) RspSurveyAnswer(surveyAnswer) else null
    }
}