package com.multithread.editor.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.multithread.editor.R
import com.multithread.editor.utils.EditTestTag

/**
 * @author annah
 * Created 7/28/2023 at 6:19 PM
 */

@Composable
fun Toolbar(title: String, onNavigationUp: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .height(56.dp)
            .padding(end = 8.dp)
            .testTag(EditTestTag.TAG_TOOLBAR),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // NavigateUp Icon
        if (onNavigationUp != null) {
            IconButton(
                onClick = { onNavigationUp() },
                modifier = Modifier
                    .size(40.dp)
                    .testTag(EditTestTag.TAG_TOOLBAR_NAVIGATION_UP)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_left_arrow),
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.wrapContentSize()
                )
            }
        }

        Spacer(modifier = Modifier.width(4.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.headlineSmall) {
                CompositionLocalProvider(
                    content = {
                        Text(
                            title,
                            modifier = Modifier.testTag(EditTestTag.TAG_TOOLBAR_TITLE)
                        )
                    }
                )
            }
        }
    }
}