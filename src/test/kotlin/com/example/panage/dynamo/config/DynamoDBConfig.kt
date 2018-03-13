package com.example.panage.dynamo.config

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.transactions.TransactionManager
import com.amazonaws.services.dynamodbv2.util.TableHelper
import com.example.panage.dynamo.repository.Document
import com.example.panage.dynamo.repository.TABLE_NAME_EXAMPLE
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


/**
 * @author fu-taku
 */
@Component
@EnableDynamoDBRepositories(basePackages = ["com.example.panage.dynamo.repository"])
class DynamoDBConfig {

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        System.setProperty("sqlite4java.library.path", "target/dependencies")
        val server = DynamoDBEmbedded.create()
        val amazonDynamoDB = server.amazonDynamoDB()

        amazonDynamoDB.createTable(
                DynamoDBMapper(amazonDynamoDB).generateCreateTableRequest(Document::class.java)
                        .withProvisionedThroughput(ProvisionedThroughput(2L, 2L))
        )

        // Transactionsテーブルの作成
        TransactionManager.verifyOrCreateTransactionTable(amazonDynamoDB, TABLE_NAME_TRANSACTIONS, 1, 1, null)

        // TransactionImagesテーブルの作成
        TransactionManager.verifyOrCreateTransactionImagesTable(amazonDynamoDB, TABLE_NAME_TRANSACTION_IMAGES, 1, 1, null)

        val tableHelper = TableHelper(amazonDynamoDB)
        tableHelper.waitForTableActive(TABLE_NAME_EXAMPLE, 10L)
        tableHelper.waitForTableActive(TABLE_NAME_TRANSACTIONS, 10L)
        tableHelper.waitForTableActive(TABLE_NAME_TRANSACTION_IMAGES, 10L)

        return amazonDynamoDB
    }
}

const val TABLE_NAME_TRANSACTIONS = "Transactions"
const val TABLE_NAME_TRANSACTION_IMAGES = "TransactionImages"
