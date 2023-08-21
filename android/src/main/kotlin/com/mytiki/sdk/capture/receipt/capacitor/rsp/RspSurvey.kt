/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.Survey
import org.json.JSONArray
import org.json.JSONObject

class RspSurvey(survey: Survey) : Rsp {
    private val clientUserId: String?
    private val serverId: Int
    private val slug: String?
    private val rewardValue: String?
    private val startDate: String?
    private val endDate: String?
    private val questions: List<RspSurveyQuestion>

    init {
        clientUserId = survey.clientUserId()
        serverId = survey.serverId()
        slug = survey.slug()
        rewardValue = survey.rewardValue()
        startDate = survey.startDate()
        endDate = survey.endDate()
        questions =
            survey.questions()?.map { question -> RspSurveyQuestion(question) } ?: emptyList()
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("clientUserId", clientUserId)
            .put("serverId", serverId)
            .put("slug", slug)
            .put("rewardValue", rewardValue)
            .put("startDate", startDate)
            .put("endDate", endDate)
            .put("questions", JSONArray(questions.map { question -> question.toJson() }))

    companion object {
        fun opt(survey: Survey?): RspSurvey? =
            if (survey != null) RspSurvey(survey) else null
    }
}