package com.exchangerate.core.data.usecase

import io.reactivex.Scheduler

class ExecutionScheduler(val scheduler: Scheduler)

class PostExecutionScheduler(val scheduler: Scheduler)

class ExecutionConfiguration(val execution: ExecutionScheduler, val postExecution: PostExecutionScheduler)
