package com.wespot.staff.data.vote.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.wespot.staff.data.vote.model.VoteQuestionResponse
import com.wespot.staff.data.vote.model.toVoteQuestionResponse
import com.wespot.staff.domain.vote.VoteQuestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

public interface VoteDataStore {
    public suspend fun save(voteQuestions: List<VoteQuestion>)
    public fun getVoteQuestionsStream(): Flow<List<VoteQuestion>>
}

public class DefaultVoteDataStore(
    private val dataStore: DataStore<Preferences>,
) : VoteDataStore {
    override suspend fun save(voteQuestions: List<VoteQuestion>) {
        dataStore.edit { preferences ->
            val voteQuestionsDto = voteQuestions.map { it.toVoteQuestionResponse() }
            preferences[KEY_VOTE_QUESTION] = Json.encodeToString(voteQuestionsDto)
        }
    }

    override fun getVoteQuestionsStream(): Flow<List<VoteQuestion>> {
        return dataStore.data.map { preferences ->
            val cache = preferences[KEY_VOTE_QUESTION] ?: return@map null
            Json.decodeFromString<List<VoteQuestionResponse>>(cache)
        }.map {
            it?.map { list -> list.toVoteQuestion() } ?: listOf()
        }
    }

    private companion object {
        private val KEY_VOTE_QUESTION = stringPreferencesKey("KEY_VOTE_QUESTION")
    }
}
