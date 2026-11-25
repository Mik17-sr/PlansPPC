package com.example.plansppc.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.plansppc.data.model.MiembroEntity
import com.example.plansppc.data.model.MiembroSummary
import com.example.plansppc.data.model.PlanDetail
import com.example.plansppc.data.model.PlanSummary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ahorro_familiar.db"
        private const val DATABASE_VERSION = 1

        // Table Planes
        private const val TABLE_PLANES = "planes"
        private const val COLUMN_PLAN_ID = "_id"
        private const val COLUMN_PLAN_MOTIVO = "motivo"
        private const val COLUMN_PLAN_META = "meta"
        private const val COLUMN_PLAN_MESES = "meses"

        // Table Miembros
        private const val TABLE_MIEMBROS = "miembros"
        private const val COLUMN_MIEMBRO_ID = "_id"
        private const val COLUMN_MIEMBRO_PLAN_ID = "plan_id"
        private const val COLUMN_MIEMBRO_NOMBRE = "nombre"

        // Table Pagos
        private const val TABLE_PAGOS = "pagos"
        private const val COLUMN_PAGO_ID = "_id"
        private const val COLUMN_PAGO_PLAN_ID = "plan_id"
        private const val COLUMN_PAGO_MIEMBRO_ID = "miembro_id"
        private const val COLUMN_PAGO_MONTO = "monto"
        private const val COLUMN_PAGO_FECHA = "fecha"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createPlanesTable = """
            CREATE TABLE $TABLE_PLANES (
                $COLUMN_PLAN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PLAN_MOTIVO TEXT NOT NULL,
                $COLUMN_PLAN_META REAL NOT NULL,
                $COLUMN_PLAN_MESES INTEGER NOT NULL
            );
        """.trimIndent()

        val createMiembrosTable = """
            CREATE TABLE $TABLE_MIEMBROS (
                $COLUMN_MIEMBRO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MIEMBRO_PLAN_ID INTEGER NOT NULL,
                $COLUMN_MIEMBRO_NOMBRE TEXT NOT NULL,
                FOREIGN KEY ($COLUMN_MIEMBRO_PLAN_ID) REFERENCES $TABLE_PLANES($COLUMN_PLAN_ID) ON DELETE CASCADE
            );
        """.trimIndent()

        val createPagosTable = """
            CREATE TABLE $TABLE_PAGOS (
                $COLUMN_PAGO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PAGO_PLAN_ID INTEGER NOT NULL,
                $COLUMN_PAGO_MIEMBRO_ID INTEGER NOT NULL,
                $COLUMN_PAGO_MONTO REAL NOT NULL,
                $COLUMN_PAGO_FECHA TEXT NOT NULL,
                FOREIGN KEY ($COLUMN_PAGO_PLAN_ID) REFERENCES $TABLE_PLANES($COLUMN_PLAN_ID) ON DELETE CASCADE,
                FOREIGN KEY ($COLUMN_PAGO_MIEMBRO_ID) REFERENCES $TABLE_MIEMBROS($COLUMN_MIEMBRO_ID) ON DELETE CASCADE
            );
        """.trimIndent()

        db.execSQL(createPlanesTable)
        db.execSQL(createMiembrosTable)
        db.execSQL(createPagosTable)
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PAGOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MIEMBROS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PLANES")
        onCreate(db)
    }

    fun crearPlanConMiembros(motivo: String, meta: Double, meses: Int, miembros: List<String>): Long {
        val db = writableDatabase
        db.beginTransaction()
        var planId: Long = -1
        try {
            val valuesPlan = ContentValues().apply {
                put(COLUMN_PLAN_MOTIVO, motivo)
                put(COLUMN_PLAN_META, meta)
                put(COLUMN_PLAN_MESES, meses)
            }
            planId = db.insert(TABLE_PLANES, null, valuesPlan)

            if (planId != -1L) {
                for (nombre in miembros) {
                    val nombreLimpio = nombre.trim()
                    if (nombreLimpio.isNotEmpty()) {
                        val valuesMiembro = ContentValues().apply {
                            put(COLUMN_MIEMBRO_PLAN_ID, planId)
                            put(COLUMN_MIEMBRO_NOMBRE, nombreLimpio)
                        }
                        db.insert(TABLE_MIEMBROS, null, valuesMiembro)
                    }
                }
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        return planId
    }

    fun getPlanesSummary(): List<PlanSummary> {
        val list = mutableListOf<PlanSummary>()
        val db = readableDatabase

        val query = """
            SELECT p.$COLUMN_PLAN_ID, p.$COLUMN_PLAN_MOTIVO, p.$COLUMN_PLAN_META, p.$COLUMN_PLAN_MESES,
                   COALESCE(SUM(pg.$COLUMN_PAGO_MONTO), 0.0) AS total_ahorro,
                   (SELECT COUNT(*) FROM $TABLE_MIEMBROS m WHERE m.$COLUMN_MIEMBRO_PLAN_ID = p.$COLUMN_PLAN_ID) AS num_miembros
            FROM $TABLE_PLANES p
            LEFT JOIN $TABLE_PAGOS pg ON p.$COLUMN_PLAN_ID = pg.$COLUMN_PAGO_PLAN_ID
            GROUP BY p.$COLUMN_PLAN_ID, p.$COLUMN_PLAN_MOTIVO, p.$COLUMN_PLAN_META, p.$COLUMN_PLAN_MESES
            ORDER BY p.$COLUMN_PLAN_ID DESC
        """.trimIndent()

        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(0)
                val motivo = cursor.getString(1)
                val meta = cursor.getDouble(2)
                val meses = cursor.getInt(3)
                val totalAhorrado = cursor.getDouble(4)
                val numMiembros = cursor.getInt(5)

                list.add(PlanSummary(id, motivo, meta, meses, totalAhorrado, numMiembros))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun getPlanDetail(planId: Long): PlanDetail? {
        val db = readableDatabase

        val queryPlan = """
            SELECT $COLUMN_PLAN_MOTIVO, $COLUMN_PLAN_META, $COLUMN_PLAN_MESES
            FROM $TABLE_PLANES
            WHERE $COLUMN_PLAN_ID = ?
        """.trimIndent()

        val cursorPlan = db.rawQuery(queryPlan, arrayOf(planId.toString()))
        if (!cursorPlan.moveToFirst()) {
            cursorPlan.close()
            return null
        }

        val motivo = cursorPlan.getString(0)
        val meta = cursorPlan.getDouble(1)
        val meses = cursorPlan.getInt(2)
        cursorPlan.close()

        val queryMiembros = """
            SELECT m.$COLUMN_MIEMBRO_ID, m.$COLUMN_MIEMBRO_NOMBRE,
                   COALESCE(SUM(pg.$COLUMN_PAGO_MONTO), 0.0) AS total_pagado
            FROM $TABLE_MIEMBROS m
            LEFT JOIN $TABLE_PAGOS pg ON m.$COLUMN_MIEMBRO_ID = pg.$COLUMN_PAGO_MIEMBRO_ID
            WHERE m.$COLUMN_MIEMBRO_PLAN_ID = ?
            GROUP BY m.$COLUMN_MIEMBRO_ID, m.$COLUMN_MIEMBRO_NOMBRE
            ORDER BY m.$COLUMN_MIEMBRO_ID ASC
        """.trimIndent()

        val miembrosList = mutableListOf<MiembroSummary>()
        var totalPlanAhorrado = 0.0

        val cursorMiembros = db.rawQuery(queryMiembros, arrayOf(planId.toString()))
        if (cursorMiembros.moveToFirst()) {
            do {
                val mId = cursorMiembros.getLong(0)
                val mNombre = cursorMiembros.getString(1)
                val mTotal = cursorMiembros.getDouble(2)

                totalPlanAhorrado += mTotal
                miembrosList.add(MiembroSummary(mId, mNombre, mTotal))
            } while (cursorMiembros.moveToNext())
        }
        cursorMiembros.close()

        return PlanDetail(
            id = planId,
            motivo = motivo,
            meta = meta,
            meses = meses,
            totalAhorrado = totalPlanAhorrado,
            miembros = miembrosList
        )
    }

    fun getMiembrosForPlan(planId: Long): List<MiembroEntity> {
        val list = mutableListOf<MiembroEntity>()
        val db = readableDatabase
        val query = "SELECT $COLUMN_MIEMBRO_ID, $COLUMN_MIEMBRO_PLAN_ID, $COLUMN_MIEMBRO_NOMBRE FROM $TABLE_MIEMBROS WHERE $COLUMN_MIEMBRO_PLAN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(planId.toString()))
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(0)
                val pId = cursor.getLong(1)
                val nombre = cursor.getString(2)
                list.add(MiembroEntity(id, pId, nombre))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun registrarPago(planId: Long, miembroId: Long, monto: Double): Boolean {
        val db = writableDatabase
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = sdf.format(Date())

        val values = ContentValues().apply {
            put(COLUMN_PAGO_PLAN_ID, planId)
            put(COLUMN_PAGO_MIEMBRO_ID, miembroId)
            put(COLUMN_PAGO_MONTO, monto)
            put(COLUMN_PAGO_FECHA, currentDate)
        }

        val result = db.insert(TABLE_PAGOS, null, values)
        return result != -1L
    }
}
