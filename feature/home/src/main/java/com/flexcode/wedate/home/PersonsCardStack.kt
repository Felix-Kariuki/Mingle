package com.flexcode.wedate.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.flexcode.wedate.common.composables.BasicText
import com.flexcode.wedate.common.composables.ResultText
import com.flexcode.wedate.common.ext.moveTo
import com.flexcode.wedate.common.ext.visible
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.onlineGreen
import com.flexcode.wedate.home.data.Person
import com.flexcode.wedate.common.R.string as AppText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonsCardStack(
    modifier: Modifier = Modifier,
    items: MutableList<Person>,
    thresholdConfig: (Float, Float) -> ThresholdConfig = { _, _ -> FractionalThreshold(0.2f) },
    velocityThreshold: Dp = 125.dp,
    onSwipeLeft: (person: Person) -> Unit = {},
    onSwipeRight: (person: Person) -> Unit = {},
    onEmptyStack: (lastItem: Person) -> Unit = {}
) {

    var i by remember {
        mutableStateOf(items.size - 1)
    }

    if (i == -1) {
        onEmptyStack(items.last())
    }

    val cardStackController = rememberCardStackController()

    cardStackController.onSwipeLeft = {
        onSwipeLeft(items[i])
        i--
    }
    cardStackController.onSwipeRight = {
        onSwipeRight(items[i])
        i--
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 60.dp, top = 10.dp, start = 10.dp, end = 10.dp)
            .clip(
                shape = RoundedCornerShape(
                    topEnd = 130.dp, bottomEnd = 25.dp,
                    topStart = 25.dp, bottomStart = 25.dp
                )
            )
    ) {
        val stack = createRef()

        Box(
            modifier = modifier
                .constrainAs(stack) {
                    top.linkTo(parent.top)
                }
                .draggableStack(
                    controller = cardStackController,
                    thresholdConfig = thresholdConfig,
                    velocityThreshold = velocityThreshold
                )
                .fillMaxHeight()
        ) {
            items.asReversed().forEachIndexed { index, item ->
                PersonCard(
                    modifier = Modifier
                        .moveTo(
                            x = if (index == i) cardStackController.offsetX.value else 0f,
                            y = if (index == i) cardStackController.offsetY.value else 0f
                        )
                        .visible(visible = index == i || index == i - 1)
                        .graphicsLayer(
                            rotationZ = if (index == i) cardStackController.rotation.value else 0f,
                            scaleX = if (index < i) cardStackController.scale.value else 1f,
                            scaleY = if (index < i) cardStackController.scale.value else 1f
                        ),
                    item,
                    cardStackController,
                )
            }
        }
    }


}

@Composable
fun PersonCard(
    modifier: Modifier = Modifier,
    person: Person,
    cardStackController: CardStackController
) {
    Box(modifier = modifier) {
        if (person.url != null) {
            AsyncImage(
                model = person.url,
                contentDescription = person.name,
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        }

        Row(modifier = modifier
            .wrapContentWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start) {
            StatusItem(
                status = if (person.isOnline) AppText.offline else AppText.online,
                backgroundColor = if (person.isOnline) deepBrown else onlineGreen
            )
        }

        Column(
            modifier = modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            ResultText(
                text = "${person.name},${person.age}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = modifier.offset(x = (-16).dp, y = (30).dp)
            )

            ResultText(
                text = person.location,
                color = Color.White,
                fontSize = 20.sp,
                modifier = modifier.offset(x = ((-16).dp), y = 16.dp)
            )

            Row {
                IconButton(
                    modifier = modifier.padding(50.dp, 0.dp, 0.dp, 0.dp),
                    onClick = { cardStackController.swipeLeft() },
                ) {
                    Icon(
                        Icons.Default.Close, contentDescription = "", tint = Color.White, modifier =
                        modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    modifier = modifier.padding(0.dp, 0.dp, 50.dp, 0.dp),
                    onClick = { cardStackController.swipeRight() }
                ) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = "",
                        tint = Color.White,
                        modifier =
                        modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                }
            }
        }


    }
}

@Composable
fun StatusItem(
    modifier: Modifier = Modifier,
    status: Int,
    backgroundColor: Color
) {
    Card(
        modifier = modifier
            .width(100.dp)
            .height(35.dp),
        shape =  RoundedCornerShape(50.dp),
        elevation = 10.dp,
        backgroundColor = backgroundColor
    ) {
        BasicText(text = status, color = Color.White, fontWeight = FontWeight.SemiBold)
    }
}