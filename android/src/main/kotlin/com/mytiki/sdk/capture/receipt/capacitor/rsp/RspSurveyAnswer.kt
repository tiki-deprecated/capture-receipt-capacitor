/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.getcapacitor.JSObject
import com.microblink.core.SurveyAnswer

/**
 * Represents a response (RSP) for a survey answer.
 *
 * @property surveyAnswer The underlying survey answer to be represented.
 */
class RspSurveyAnswer(surveyAnswer: SurveyAnswer) : Rsp {
    private val id: Int
    private val text: String?
    private val nextQuestionIndex: Int?

    init {
        id = surveyAnswer.serverId()
        text = surveyAnswer.text()
        nextQuestionIndex = surveyAnswer.nextQuestionIndex()
    }

    /**
     * Converts the RSP survey answer object to a JSON object.
     *
     * @return The JSON representation of the RSP survey answer.
     */
    override fun toJS(): JSObject =
        JSObject()
            .put("id", id)
            .put("text", text)
            .put("nextQuestionIndex", nextQuestionIndex)

    companion object {
        /**
         * Creates an optional RSP survey answer from a given survey answer.
         *
         * @param surveyAnswer The survey answer to create an RSP survey answer from.
         * @return An optional RSP survey answer, or null if the input is null.
         */
        fun opt(surveyAnswer: SurveyAnswer?): RspSurveyAnswer? =
            if (surveyAnswer != null) RspSurveyAnswer(surveyAnswer) else null
    }
}
