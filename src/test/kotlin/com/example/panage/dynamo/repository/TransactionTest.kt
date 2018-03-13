package com.example.panage.dynamo.repository

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.amazonaws.services.dynamodbv2.transactions.TransactionManager
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.util.*

/**
 * @author fu-taku
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
class TransactionTest {
    @Autowired
    lateinit var documentRepository: DocumentRepository
    @Autowired
    lateinit var transactionManager: TransactionManager

    @Test
    fun コミットするとデータは保存される() {
        val tx1 = transactionManager.newTransaction()
        val uuid = generateUUID()

        try {
            tx1.putItem(
                    PutItemRequest().withTableName(TABLE_NAME_EXAMPLE).withItem(mapOf(
                            Pair("id", AttributeValue(uuid)),
                            Pair("name", AttributeValue("hoge"))
                    ))
            )
        } finally {
            tx1.commit()
            tx1.delete()
        }

        Assert.assertEquals("hoge", documentRepository.findById(uuid).get().name)
    }

    private fun generateUUID(): String = UUID.randomUUID().toString()
}
