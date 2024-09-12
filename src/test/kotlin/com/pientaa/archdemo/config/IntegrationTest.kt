package com.pientaa.archdemo.config

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import kotlin.annotation.AnnotationTarget.CLASS

@Target(CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@Import(TestcontainersConfiguration::class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
annotation class IntegrationTest
