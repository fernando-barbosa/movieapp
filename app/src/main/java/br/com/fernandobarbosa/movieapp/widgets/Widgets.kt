package br.com.fernandobarbosa.movieapp.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fernandobarbosa.movieapp.model.Movie
import br.com.fernandobarbosa.movieapp.model.getMovies
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

@ExperimentalAnimationApi
@Preview
@Composable
fun MovieRow(movie: Movie = getMovies()[0],
             onClickItem: (String) -> Unit = {}) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            //.height(130.dp)
            .clickable { onClickItem(movie.id) },
        shape = RoundedCornerShape(
            corner = CornerSize(16.dp)
        ),
        elevation = 6.dp,
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp),
                shape = RectangleShape
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = movie.images[0],
                        builder = {
                            crossfade(true)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = "Movie poster"
                )
            }
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "Release: ${movie.year}",
                    style = MaterialTheme.typography.caption
                )

                AnimatedVisibility(visible = expanded) {
                    Column {
                        Text(buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Light)) {
                                append(movie.plot)
                            }
                        },
                        modifier = Modifier.padding(6.dp))

                        Divider(modifier = Modifier.padding(3.dp))

                        Text(
                            text = "Director: ${movie.director}",
                            style = MaterialTheme.typography.caption
                        )

                        Text(
                            text = "Actors: ${movie.actors}",
                            style = MaterialTheme.typography.caption
                        )

                        Text(
                            text = "Rating: ${movie.rating}",
                            style = MaterialTheme.typography.caption
                        )
                    }
                }

                Icon(
                    imageVector =
                        if (expanded) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand arrow",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable { expanded = !expanded },
                    tint = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun HorizontalScrollableImageView(newMovieList: List<Movie>) {
    LazyRow {
        items(newMovieList.first().images) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                elevation = 6.dp
            ) {
                Image(
                    painter = rememberImagePainter(data = image),
                    contentDescription = "Movie image"
                )
            }
        }
    }
}