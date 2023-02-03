package com.flexcode.wedate.matches.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.composables.BasicText
import com.flexcode.wedate.common.composables.SearchTextField
import com.flexcode.wedate.common.ext.fieldModifier
import com.flexcode.wedate.matches.composables.ChatItem
import com.flexcode.wedate.matches.composables.MatchesItem
import com.flexcode.wedate.matches.data.Chat
import com.flexcode.wedate.matches.data.Match
import com.flexcode.wedate.matches.data.chats
import com.flexcode.wedate.matches.data.matches
import com.flexcode.wedate.common.R.drawable as AppIcon
import com.flexcode.wedate.common.R.string as AppText


@Composable
fun MatchesScreen(
    modifier: Modifier = Modifier,
    viewModel: MatchesViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current

    val state by viewModel.state

    Column {
        SearchTextField(
            modifier = modifier.fieldModifier(),
            value = state.searchValue,
            leadingIcon = AppIcon.ic_search,
            placeholder = AppText.search,
            onNewValue = viewModel::onSearchValueChange,
            onSearch = {
                focusManager.clearFocus()
            }
        )

        BasicText(text = AppText.matches, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        MatchesComposable(matches = matches)

        BasicText(text = AppText.chats, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        ChatsComposable(chats = chats)

    }

}

@Composable
fun ChatsComposable(chats:List<Chat>) {
    LazyColumn(
        Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ){
        items(chats){ chat ->
            ChatItem(name = chat.name, image = chat.image , message = chat.message)
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
fun MatchesComposable(matches:List<Match>) {
    LazyRow(
        Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 6.dp)
    ){
        items(matches){match->
            MatchesItem(image = match.image, name = match.name )
        }
    }
}