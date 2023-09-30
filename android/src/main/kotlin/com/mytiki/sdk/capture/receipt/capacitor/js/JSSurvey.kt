/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.js

import com.getcapacitor.JSObject
import com.microblink.core.Survey
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a survey response for the TIKI SDK.
 *
 * @param survey The [Survey] object containing survey data.
 */
class JSSurvey(survey: Survey)  {

    /**
     * The client's user ID for the survey.
     */
    private val clientUserId: String?

    /**
     * The server ID of the survey.
     */
    private val serverId: Int

    /**
     * The slug associated with the survey.
     */
    private val slug: String?

    /**
     * The reward value associated with the survey.
     */
    private val rewardValue: String?

    /**
     * The start date of the survey.
     */
    private val startDate: String?

    /**
     * The end date of the survey.
     */
    private val endDate: String?

    /**
     * The list of survey questions.
     */
    private val questions: List<JSSurveyQuestion>

    init {
        clientUserId = survey.clientUserId()
        serverId = survey.serverId()
        slug = survey.slug()
        rewardValue = survey.rewardValue()
        startDate = survey.startDate()
        endDate = survey.endDate()
        questions =
            survey.questions()?.map { question -> JSSurveyQuestion(question) } ?: emptyList()
    }

    /**
     * Converts the survey response to a JSON representation.
     *
     * @return A [JSONObject] containing the survey response data.
     */
    fun toJS(): JSObject =
        JSObject()
            .put("clientUserId", clientUserId)
            .put("serverId", serverId)
            .put("slug", slug)
            .put("rewardValue", rewardValue)
            .put("startDate", startDate)
            .put("endDate", endDate)
            .put("questions", JSONArray(questions.map { question -> question.toJS() }))

    companion object {

        /**
         * Creates an instance of [JSSurvey] from a [Survey] object.
         *
         * @param survey The [Survey] object to convert.
         * @return An [JSSurvey] object representing the survey response, or `null` if the input is `null`.
         */
        fun opt(survey: Survey?): JSSurvey? =
            if (survey != null) JSSurvey(survey) else null
    }
}
