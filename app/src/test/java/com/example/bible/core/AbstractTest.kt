package com.example.bible.core

import junit.framework.Assert.assertTrue
import org.junit.Test
import java.io.IOException
import java.lang.Exception

class AbstractTest{
    @Test
    fun test_success() {
        val dataObjekt = TestDataObjekt.Success("a", "b")
        val domainObjekt = dataObjekt.map(DataMapper.Base())
        assertTrue(domainObjekt is DomainObject.Success)
    }@Test
    fun test_fail() {
        val dataObjekt = TestDataObjekt.Fail(IOException())
        val domainObjekt = dataObjekt.map(DataMapper.Base())
        assertTrue(domainObjekt is DomainObject.Fail)
    }
    private sealed class TestDataObjekt : Abstract.Object<DomainObject, DataMapper>(){
        abstract override fun map(mapper: DataMapper): DomainObject

        class Success(
            private val textOne:String,
            private val textTwo:String
            ) : TestDataObjekt(){
            override fun map(mapper: DataMapper): DomainObject {
               return mapper.map(textOne, textTwo)
            }

        }
        class Fail(private val exception: Exception) : TestDataObjekt() {
            override fun map(mapper: DataMapper): DomainObject {
                return mapper.map(exception)
            }

        }
    }
    private interface DataMapper : Abstract.Mapper{
        fun map(textOne: String, textTwo: String) : DomainObject

        fun map(exception: Exception) : DomainObject

        class Base : DataMapper{
            override fun map(textOne: String, textTwo: String): DomainObject {
                return DomainObject.Success("$textOne $textTwo")
            }

            override fun map(exception: Exception): DomainObject {
                return DomainObject.Fail()
            }

        }
    }

    private sealed class DomainObject : Abstract.Object<UiObject, DomainToUiMapper>() {
        class Success(private  val textCombined: String) : DomainObject() {
            override fun map(mapper: DomainToUiMapper): UiObject {
                TODO("not don yet")
            }

        }

        class Fail: DomainObject(){
            override fun map(mapper: DomainToUiMapper): UiObject {
                TODO("not don yet")
            }
        }
    }
    private interface DomainToUiMapper : Abstract.Mapper

    private sealed class UiObject : Abstract.Object<Unit, Abstract.Mapper.Empty>(){

    }
}