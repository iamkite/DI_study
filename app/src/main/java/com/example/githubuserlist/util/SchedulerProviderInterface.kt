package com.example.githubuserlist.util

import io.reactivex.Scheduler

interface SchedulerProviderInterface {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun main(): Scheduler
}
