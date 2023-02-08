package com.flexcode.wedate.matches.data.repository

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.matches.data.model.Matches
import com.flexcode.wedate.matches.domain.repository.MatchesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : MatchesRepository{
    private val dbRef = FirebaseDatabase.getInstance().reference

    override suspend fun getAllUserMatches(): Flow<Resource<List<Matches>>> {
        return flow<Resource<List<Matches>>> {
            emit(Resource.Loading())
            try {
                val currentUserId = auth.uid!!
                val allUserMatches = ArrayList<Matches>()
                val allMatches = dbRef.child(MATCHES).child(currentUserId).get().await()

                for (i in allMatches.children){
                    val result = i.getValue(Matches::class.java)
                    allUserMatches.add(result!!)
                }
                emit(Resource.Success(allUserMatches))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        const val MATCHES = "Matches"
    }
}