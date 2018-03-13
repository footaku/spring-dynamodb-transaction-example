package com.example.panage.dynamo.repository

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import org.springframework.data.annotation.Id

/**
 * @author fu-taku
 */
@DynamoDBTable(tableName = TABLE_NAME_EXAMPLE)
data class Document(
        @Id
        @get:DynamoDBHashKey
        var id: String = "",

        @get:DynamoDBAttribute
        var name: String = ""
)

const val TABLE_NAME_EXAMPLE = "document"

