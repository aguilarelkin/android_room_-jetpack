package com.room.shoppe.ui.product

import com.room.shoppe.domain.usecase.product.DeleteProduct
import com.room.shoppe.domain.usecase.product.GetProduct
import com.room.shoppe.motherobject.ProductMotherObject
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class ProductViewModelTest {

    private val dispacher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var productViewModel: ProductViewModel

    @MockK
    private lateinit var getAProduct: GetProduct

    @MockK
    private lateinit var deleteProduct: DeleteProduct

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispacher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when response is not emptyList getProducts should stateList have list of products`() {
        productViewModel = ProductViewModel(getAProduct, deleteProduct)
        coEvery { getAProduct() } returns listOf(ProductMotherObject.anyResponse)
        productViewModel.getProducts()

        runBlocking {
            coVerify { getAProduct() }
            assertTrue(productViewModel.stateList.value.isNotEmpty())
            assertEquals(productViewModel.stateList.value, listOf(ProductMotherObject.anyResponse))
        }
    }

    @Test
    fun `when response is emptyList getProducts should stateList have emptyList`() {
        productViewModel = ProductViewModel(getAProduct, deleteProduct)
        runBlocking {
            coEvery { getAProduct() } returns null
        }
        productViewModel.getProducts()
        runBlocking {
            coVerify { getAProduct() }
            assertTrue(productViewModel.stateList.value.isEmpty())
            assertNotEquals(
                productViewModel.stateList.value,
                listOf(ProductMotherObject.anyResponse)
            )
        }
    }

    @Test
    fun `when response is 200 deleteProductId should stateDelete have boolean true`() {
        productViewModel = ProductViewModel(getAProduct, deleteProduct)
        val id: Int = 1

        assertEquals(productViewModel.stateDelete.value, false)

        runBlocking {
            coEvery { deleteProduct(id) } returns "200"
        }
        productViewModel.deleteProductId(id)
        runBlocking {
            coVerify { deleteProduct(id) }
            assertEquals(productViewModel.stateDelete.value, true)
        }
    }
}