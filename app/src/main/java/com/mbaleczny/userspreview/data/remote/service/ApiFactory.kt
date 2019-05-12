package com.mbaleczny.userspreview.data.remote.service

/**
 * @author Mariusz Baleczny
 * @date 10/05/19
 */
object ApiFactory {

    private const val DAILYMOTION_API = "https://api.dailymotion.com"
    private const val GITHUB_API = "https://api.github.com"

    val github: GitHubApi = RetrofitFactory.retrofit(GITHUB_API).create(GitHubApi::class.java)

    val dailymotion: DailymotionApi = RetrofitFactory.retrofit(DAILYMOTION_API).create(DailymotionApi::class.java)
}