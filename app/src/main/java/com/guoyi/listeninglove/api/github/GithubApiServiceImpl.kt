package com.guoyi.listeninglove.api.github

import com.guoyi.listeninglove.api.net.ApiManager
import com.guoyi.listeninglove.common.Constants
import io.reactivex.Observable
import retrofit2.Response

/**
 * Created by master on 2018/4/5.
 */

object GithubApiServiceImpl {
    private val TAG = "PlaylistApiServiceImpl"
    val githubApiService by lazy { ApiManager.getInstance().create(GithubApiService::class.java, Constants.GITHUB_BASE_URL) }

    fun getAccessToken(code: String, state: String): Observable<Response<OauthToken>> {
        return githubApiService.getAccessToken(
                Constants.GITHUB_CLIENT_ID,
                Constants.GITHUB_CLIENT_SECRET,
                code,
                state)
    }
}
