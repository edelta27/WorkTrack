package com.worktrack.data.local

import com.worktrack.data.model.Company
import com.worktrack.data.model.Job
import com.worktrack.data.model.WorkEntry

object FakeDatabase {
    val companies = mutableListOf<Company>()
    val jobs = mutableListOf<Job>()
    val entries = mutableListOf<WorkEntry>()
}