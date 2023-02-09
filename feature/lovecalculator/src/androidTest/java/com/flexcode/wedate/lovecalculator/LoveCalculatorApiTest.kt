/*
 * Copyright 2023 Felix Kariuki.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flexcode.wedate.lovecalculator

class LoveCalculatorApiTest {

    /*private var context: Context? = null
    private var mockWebServer = MockWebServer()
    private lateinit var api: CalculatorApiService

    @Before
    fun setUp(){
        mockWebServer.start()

        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        context = InstrumentationRegistry.getInstrumentation().targetContext

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val contentType = "application/json".toMediaType()
        val converterFactory = Json.asConverterFactory(contentType)

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
            .create(CalculatorApiService::class.java)

        val jsonStream: InputStream = context!!.resources.assets.open("loveresponse.json")
        val jsonBytes: ByteArray = jsonStream.readBytes()

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(String(jsonBytes))
        mockWebServer.enqueue(response)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testLoveCalculatorApi(): Unit = runBlocking {
        val data = api.getLovePercentage(sname = "Alice", fname = "David")
        ViewMatchers.assertThat(data.sname , CoreMatchers.equalTo("Alice"))
    }*/
}
