package com.example.panage.dynamo.config

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.transactions.TransactionManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

/**
 * @author fu-taku
 */
@Component
class TransactionManagerConfig {
    @Autowired lateinit var amazonDynamoDB: AmazonDynamoDB

    @Bean
    fun transactionManager(): TransactionManager =
            TransactionManager(amazonDynamoDB, TABLE_NAME_TRANSACTIONS, TABLE_NAME_TRANSACTION_IMAGES)
}
