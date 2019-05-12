package com.mbaleczny.userspreview.data.remote.service

/**
 * @author Mariusz Baleczny
 * @date 10/05/19
 */
class ApiFactory(private val retrofitFactory: RetrofitFactory) {

    companion object {
        private const val DAILYMOTION_API = "https://api.dailymotion.com"
        private const val GITHUB_API = "https://api.github.com"
    }

    fun github(): GitHubApi = retrofitFactory.retrofit(GITHUB_API).create(GitHubApi::class.java)

    fun dailymotion(): DailymotionApi = retrofitFactory.retrofit(DAILYMOTION_API).create(DailymotionApi::class.java)
}