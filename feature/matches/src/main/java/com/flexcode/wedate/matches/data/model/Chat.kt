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
package com.flexcode.wedate.matches.data.model

data class Chat(
    val name: String,
    val image: String,
    val message: String
)
val chats = listOf(
    Chat(
        name = "Mary",
        image = "https://www.pngfind.com/pngs/m/38-386087_png-image-indian-girl-transparent-png.png",
        message = "Lorem Ipsium"
    ),
    Chat(
        name = "Sharon",
        image = "https://www.pngmart.com/files/1/Girl-PNG.png",
        message = "Lorem Ipsium"
    ),
    Chat(
        name = "Dee",
        image = "https://www.pngall.com/wp-content/uploads/2016/04/Happy-Girl-Free-PNG-Image.png",
        message = "Lorem Ipsium"
    ),
    Chat(
        name = "Sandra",
        image = "https://www.pngall.com/wp-content/uploads/2016/04/Happy-Girl-PNG-HD.png",
        message = "Lorem Ipsium"
    ),
    Chat(
        name = "Faith",
        image = "https://www.pngall.com/wp-content/uploads/2016/04/Happy-Girl-PNG-Picture.png",
        message = "Lorem Ipsium"
    )
)
