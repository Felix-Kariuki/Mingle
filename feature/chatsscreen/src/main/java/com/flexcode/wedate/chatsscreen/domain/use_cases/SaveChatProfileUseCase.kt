package com.flexcode.wedate.chatsscreen.domain.use_cases

import com.flexcode.wedate.chatsscreen.domain.repository.SaveChatRepository
import com.flexcode.wedate.common.utils.Resource
import kotlinx.coroutines.flow.Flow

class SaveChatProfileToCurrentUserUseCase(
    private val repository: SaveChatRepository
) {
    suspend operator fun invoke(
        crushUserId: String,
        firstName: String,
        profileImage: String,
        lastMsgTime: Long,
        lastMsg: String,
    ): Flow<Resource<Any>> {
        return repository.saveChatProfileToCurrentUser(
            crushUserId, firstName, profileImage, lastMsgTime, lastMsg
        )
    }
}

class SaveChatProfileToCrushUseCase(
    private val repository: SaveChatRepository
){
    suspend operator fun invoke(
        crushUserId:String,
        firstName: String,
        profileImage: String,
        lastMsgTime:Long,
        lastMsg:String,
    ):Flow<Resource<Any>>{
        return repository.saveChatProfileToCrush(
            crushUserId, firstName, profileImage, lastMsgTime, lastMsg
        )
    }
}