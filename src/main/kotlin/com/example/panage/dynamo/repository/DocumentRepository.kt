package com.example.panage.dynamo.repository

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

/**
 * @author fu-taku
 */
@EnableScan
interface DocumentRepository:CrudRepository<Document, String>
