package com.githubuser.moviecatalogue.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.githubuser.moviecatalogue.R
import com.githubuser.moviecatalogue.utils.EspressoIdlingResource
import org.junit.*
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class HomeActivityTest {


    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)

    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)

    }

    @Test
    fun t01_loadMovie() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )

    }


    @Test
    fun t02_loadDetailMovie() {

        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.movieTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.Mtagline)).check(matches(isDisplayed()))
        onView(withId(R.id.date_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.Score)).check(matches(isDisplayed()))
        onView(withId(R.id.score)).check(matches(isDisplayed()))
        onView(withId(R.id.title2)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_favorite)).check(matches(isDisplayed()))



    }

    @Test
    fun t03_loadTvShow() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_Tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_Tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
    }

    @Test
    fun t04_loadDetailTvShow(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))

        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_Tv)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_Tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.TvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.date_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.Score)).check(matches(isDisplayed()))
        onView(withId(R.id.score)).check(matches(isDisplayed()))
        onView(withId(R.id.title2)).check(matches(isDisplayed()))
        onView(withId(R.id.title2)).check(matches(withText(R.string.title2)))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_favorite)).check(matches(isDisplayed()))



    }

    @Test
    fun t04a_addFavoriteTvShow(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))

        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_Tv)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_Tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.TvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.date_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.Score)).check(matches(isDisplayed()))
        onView(withId(R.id.score)).check(matches(isDisplayed()))
        onView(withId(R.id.title2)).check(matches(isDisplayed()))
        onView(withId(R.id.title2)).check(matches(withText(R.string.title2)))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_favorite)).perform(click())



    }

    @Test
    fun t05_loadFavorite(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(withId(R.id.fav_tabs)).check(matches(isDisplayed()))
    }

    @Test
    fun t06_loadDetailMovieFavorite(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(withId(R.id.fav_tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.tv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.movieTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.Mtagline)).check(matches(isDisplayed()))
        onView(withId(R.id.date_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.Score)).check(matches(isDisplayed()))
        onView(withId(R.id.score)).check(matches(isDisplayed()))
        onView(withId(R.id.title2)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_favorite)).check(matches(isDisplayed()))

    }


    @Test
    fun t06a_addMovieFavorite(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )
        onView(withId(R.id.tv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.movieTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.Mtagline)).check(matches(isDisplayed()))
        onView(withId(R.id.date_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.Score)).check(matches(isDisplayed()))
        onView(withId(R.id.score)).check(matches(isDisplayed()))
        onView(withId(R.id.title2)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_favorite)).perform(click())

    }

    @Test
    fun t07_loadDetailTvFavorite(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(withId(R.id.fav_tabs)).check(matches(isDisplayed()))
        onView(withText(R.string.tv_show)).perform(click())
        onView(withId(R.id.rv_fav_Tv)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_fav_Tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.TvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.date_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.Score)).check(matches(isDisplayed()))
        onView(withId(R.id.score)).check(matches(isDisplayed()))
        onView(withId(R.id.title2)).check(matches(isDisplayed()))
        onView(withId(R.id.title2)).check(matches(withText(R.string.title2)))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_favorite)).check(matches(isDisplayed()))


    }

    @Test
    fun t07a_deleteFavoriteTvShow(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))

        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_Tv)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_Tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.TvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.date_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.Score)).check(matches(isDisplayed()))
        onView(withId(R.id.score)).check(matches(isDisplayed()))
        onView(withId(R.id.title2)).check(matches(isDisplayed()))
        onView(withId(R.id.title2)).check(matches(withText(R.string.title2)))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_favorite)).perform(click())

    }

    @Test
    fun t08_deleteMovieFavorite(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )
        onView(withId(R.id.tv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.movieTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.Mtagline)).check(matches(isDisplayed()))
        onView(withId(R.id.date_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.Score)).check(matches(isDisplayed()))
        onView(withId(R.id.score)).check(matches(isDisplayed()))
        onView(withId(R.id.title2)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_favorite)).perform(click())

    }


}