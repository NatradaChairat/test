package com.example.diversitiontest.data

import com.example.diversitiontest.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()
            return when {
                uri.contains("product/detail?&product_id=p-01") -> proceedWithMock(chain, mockProductDetailP1)
                uri.contains("product/detail?&product_id=p-02") -> proceedWithMock(chain, mockProductDetailP2)
                uri.contains("products") -> proceedWithMock(chain, mockProducts)
                uri.contains("purchase") -> proceed(chain)
                else -> chain.proceed(chain.request())
            }
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

    private fun proceedWithMock(chain: Interceptor.Chain, responseString: String) =
        chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    responseString.toByteArray()
                )
            )
            .addHeader("content-type", "application/json")
            .build()

    private fun proceed(chain: Interceptor.Chain) =
        chain.proceed(chain.request())
            .newBuilder()
            .code(204)
            .protocol(Protocol.HTTP_2)
            .addHeader("content-type", "application/json")
            .build()

}


const val mockProducts = """
[
    {
        "productId": "p-01",
        "productName": "Camera",
        "coverImageUrl": "https://picsum.photos/id/454/800/400"
    },
    {
        "productId": "p-02",
        "productName": "Notebook",
        "coverImageUrl": "https://picsum.photos/seed/picsum/800/400"
    }
]
"""

const val mockProductDetailP1 = """
{
    "productId": "p-01",
    "productName": "Camera",
    "imageUrl": "https://picsum.photos/seed/picsum/600/300",
    "price": 35900,
    "stock": 10,
    "createdAt": "2020-10-19T19:23:35.963Z",
    "updatedAt": "2020-10-19T23:23:23.223Z"
}
"""

const val mockProductDetailP2 = """
{
    "productId": "p-02",
    "productName": "Notebook",
    "imageUrl": "https://picsum.photos/seed/picsum/600/300",
    "price": 29900,
    "stock": 5,
    "createdAt": "2020-10-19T19:23:35.963Z",
    "updatedAt": "2020-10-19T23:23:23.223Z"
}
"""
