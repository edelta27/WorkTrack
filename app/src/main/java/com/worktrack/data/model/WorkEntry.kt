package com.worktrack.data.model

data class WorkEntry(val id: Long,
                     val jobId: Long,
                     var hours: Double,
                     var date: String)
