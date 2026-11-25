package com.example.plansppc.data.remote.dto

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

object JsonParsers {

    fun parsePlansResponse(jsonStr: String, gson: Gson): List<PlanDTO> {
        if (jsonStr.isBlank()) return emptyList()
        return try {
            val element = JsonParser.parseString(jsonStr)
            when {
                element.isJsonArray -> {
                    val type = object : TypeToken<List<PlanDTO>>() {}.type
                    gson.fromJson<List<PlanDTO>>(element, type) ?: emptyList()
                }
                element.isJsonObject -> {
                    val obj = element.asJsonObject
                    val arrayElement = obj.get("plans")
                        ?: obj.get("data")
                        ?: obj.get("items")
                        ?: obj.get("results")

                    if (arrayElement != null && arrayElement.isJsonArray) {
                        val type = object : TypeToken<List<PlanDTO>>() {}.type
                        gson.fromJson<List<PlanDTO>>(arrayElement, type) ?: emptyList()
                    } else if (obj.has("_id") || obj.has("id") || obj.has("name") || obj.has("motive") || obj.has("motivo")) {
                        val singlePlan: PlanDTO = gson.fromJson(obj, PlanDTO::class.java)
                        listOf(singlePlan)
                    } else {
                        emptyList()
                    }
                }
                else -> emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun parseMembersResponse(jsonStr: String, gson: Gson): List<MiembroDTO> {
        if (jsonStr.isBlank()) return emptyList()
        return try {
            val element = JsonParser.parseString(jsonStr)
            when {
                element.isJsonArray -> {
                    val type = object : TypeToken<List<MiembroDTO>>() {}.type
                    gson.fromJson<List<MiembroDTO>>(element, type) ?: emptyList()
                }
                element.isJsonObject -> {
                    val obj = element.asJsonObject
                    val arrayElement = obj.get("members")
                        ?: obj.get("miembros")
                        ?: obj.get("data")
                        ?: obj.get("items")

                    if (arrayElement != null && arrayElement.isJsonArray) {
                        val type = object : TypeToken<List<MiembroDTO>>() {}.type
                        gson.fromJson<List<MiembroDTO>>(arrayElement, type) ?: emptyList()
                    } else if (obj.has("_id") || obj.has("id") || obj.has("name")) {
                        val singleMember: MiembroDTO = gson.fromJson(obj, MiembroDTO::class.java)
                        listOf(singleMember)
                    } else {
                        emptyList()
                    }
                }
                else -> emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun parsePaymentsResponse(jsonStr: String, gson: Gson): List<PagoDTO> {
        if (jsonStr.isBlank()) return emptyList()
        return try {
            val element = JsonParser.parseString(jsonStr)
            when {
                element.isJsonArray -> {
                    val type = object : TypeToken<List<PagoDTO>>() {}.type
                    gson.fromJson<List<PagoDTO>>(element, type) ?: emptyList()
                }
                element.isJsonObject -> {
                    val obj = element.asJsonObject
                    val arrayElement = obj.get("payments")
                        ?: obj.get("pagos")
                        ?: obj.get("data")
                        ?: obj.get("items")

                    if (arrayElement != null && arrayElement.isJsonArray) {
                        val type = object : TypeToken<List<PagoDTO>>() {}.type
                        gson.fromJson<List<PagoDTO>>(arrayElement, type) ?: emptyList()
                    } else if (obj.has("_id") || obj.has("amount") || obj.has("planId")) {
                        val singlePayment: PagoDTO = gson.fromJson(obj, PagoDTO::class.java)
                        listOf(singlePayment)
                    } else {
                        emptyList()
                    }
                }
                else -> emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun parseSinglePlanResponse(jsonStr: String, gson: Gson): PlanDTO {
        if (jsonStr.isBlank()) return PlanDTO()
        return try {
            val element = JsonParser.parseString(jsonStr)
            if (element.isJsonObject) {
                val obj = element.asJsonObject
                val dataObj = if (obj.has("data") && obj.get("data").isJsonObject) {
                    obj.getAsJsonObject("data")
                } else if (obj.has("plan") && obj.get("plan").isJsonObject) {
                    obj.getAsJsonObject("plan")
                } else {
                    obj
                }
                gson.fromJson(dataObj, PlanDTO::class.java) ?: PlanDTO()
            } else if (element.isJsonArray) {
                val arr = element.asJsonArray
                if (arr.size() > 0) gson.fromJson(arr.get(0), PlanDTO::class.java) else PlanDTO()
            } else {
                PlanDTO()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            PlanDTO()
        }
    }

    fun parseSingleMemberResponse(jsonStr: String, gson: Gson): MiembroDTO {
        if (jsonStr.isBlank()) return MiembroDTO()
        return try {
            val element = JsonParser.parseString(jsonStr)
            if (element.isJsonObject) {
                val obj = element.asJsonObject
                val dataObj = if (obj.has("data") && obj.get("data").isJsonObject) {
                    obj.getAsJsonObject("data")
                } else if (obj.has("member") && obj.get("member").isJsonObject) {
                    obj.getAsJsonObject("member")
                } else {
                    obj
                }
                gson.fromJson(dataObj, MiembroDTO::class.java) ?: MiembroDTO()
            } else if (element.isJsonArray) {
                val arr = element.asJsonArray
                if (arr.size() > 0) gson.fromJson(arr.get(0), MiembroDTO::class.java) else MiembroDTO()
            } else {
                MiembroDTO()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            MiembroDTO()
        }
    }
}
