package com.guoyi.listeninglove.api

import com.guoyi.musicapi.BaseApiImpl
import com.guoyi.musicapi.bean.*
import org.junit.Test

/**
 * Des    :
 * Author : master.
 * Date   : 2018/9/9 .
 */
class MusicApiServiceImplTest {

    val string = "{\"status\":true,\"data\":{\"hotComments\":[],\"comments\":[{\"user\":{\"locationInfo\":null,\"userId\":517105625,\"vipType\":0,\"nickname\":\"雾隐暗杖\",\"vipRights\":null,\"userType\":0,\"expertTags\":null,\"remarkName\":null,\"avatarUrl\":\"http:\\/\\/p1.music.126.net\\/asEVm19ysOrdUCnS_YURSA==\\/18621328929985606.jpg\",\"experts\":null,\"authStatus\":0},\"beReplied\":[],\"pendantData\":null,\"expressionUrl\":null,\"liked\":false,\"likedCount\":0,\"commentId\":1240683866,\"time\":1536413938319,\"content\":\"蝙蝠侠，老爷！\",\"isRemoveHotComment\":false},{\"user\":{\"locationInfo\":null,\"userId\":116497548,\"vipType\":0,\"nickname\":\"Ollie2077\",\"vipRights\":null,\"userType\":0,\"expertTags\":null,\"remarkName\":null,\"avatarUrl\":\"http:\\/\\/p1.music.126.net\\/IJVGPVEVCUWc3g7BLKmTHg==\\/109951163452867085.jpg\",\"experts\":null,\"authStatus\":0},\"beReplied\":[],\"pendantData\":null,\"expressionUrl\":null,\"liked\":false,\"likedCount\":1,\"commentId\":1240605026,\"time\":1536409583105,\"content\":\"老爷！\",\"isRemoveHotComment\":false},{\"user\":{\"locationInfo\":null,\"userId\":1560905893,\"vipType\":0,\"nickname\":\"不拆沙发的伍兹小姐姐\",\"vipRights\":null,\"userType\":0,\"expertTags\":null,\"remarkName\":null,\"avatarUrl\":\"http:\\/\\/p1.music.126.net\\/g5fg1JAtUIyagImeczhggQ==\\/109951163539995845.jpg\",\"experts\":null,\"authStatus\":0},\"beReplied\":[],\"pendantData\":null,\"expressionUrl\":null,\"liked\":false,\"likedCount\":1,\"commentId\":1240577279,\"time\":1536408573689,\"content\":\"@宅博士\",\"isRemoveHotComment\":false},{\"user\":{\"locationInfo\":null,\"userId\":76415447,\"vipType\":0,\"nickname\":\"SZ阮妹纸\",\"vipRights\":null,\"userType\":0,\"expertTags\":null,\"remarkName\":null,\"avatarUrl\":\"http:\\/\\/p1.music.126.net\\/OxXBh-lqGenwwNc63bCVfA==\\/1367792468009700.jpg\",\"experts\":null,\"authStatus\":0},\"beReplied\":[],\"pendantData\":null,\"expressionUrl\":null,\"liked\":false,\"likedCount\":0,\"commentId\":1240547325,\"time\":1536406506936,\"content\":\"想起高中的时候我的网名还叫 黑羽骑士[大哭]\",\"isRemoveHotComment\":false},{\"user\":{\"locationInfo\":null,\"userId\":95790487,\"vipType\":10,\"nickname\":\"林染LR\",\"vipRights\":{\"musicPackage\":{\"vipCode\":200,\"rights\":true}},\"userType\":0,\"expertTags\":null,\"remarkName\":null,\"avatarUrl\":\"http:\\/\\/p1.music.126.net\\/OSScdhTPUIVCk9CgcGA0EQ==\\/109951163235445671.jpg\",\"experts\":null,\"authStatus\":0},\"beReplied\":[],\"pendantData\":null,\"expressionUrl\":null,\"liked\":false,\"likedCount\":1,\"commentId\":1240534774,\"time\":1536404068205,\"content\":\"铁刘海和铁鬓角[大哭]\",\"isRemoveHotComment\":false},{\"user\":{\"locationInfo\":null,\"userId\":114008624,\"vipType\":0,\"nickname\":\"人群像羊群\",\"vipRights\":null,\"userType\":0,\"expertTags\":null,\"remarkName\":null,\"avatarUrl\":\"http:\\/\\/p1.music.126.net\\/5Am4xoC4EoUEkd3KVqpFiA==\\/109951163290252944.jpg\",\"experts\":null,\"authStatus\":0},\"beReplied\":[],\"pendantData\":null,\"expressionUrl\":null,\"liked\":false,\"likedCount\":0,\"commentId\":1240462323,\"time\":1536399788069,\"content\":\"突然发现虾米音乐没有这首歌的版权了\",\"isRemoveHotComment\":false},{\"user\":{\"locationInfo\":null,\"userId\":531373934,\"vipType\":0,\"nickname\":\"半觞映寒\",\"vipRights\":null,\"userType\":0,\"expertTags\":null,\"remarkName\":null,\"avatarUrl\":\"http:\\/\\/p1.music.126.net\\/jlQQ_JQP2sdkpgQWFyJFNA==\\/109951163433285857.jpg\",\"experts\":null,\"authStatus\":0},\"beReplied\":[],\"pendantData\":null,\"expressionUrl\":null,\"liked\":false,\"likedCount\":0,\"commentId\":1240457371,\"time\":1536399690183,\"content\":\"世界在等我出现\",\"isRemoveHotComment\":false},{\"user\":{\"locationInfo\":null,\"userId\":318236746,\"vipType\":0,\"nickname\":\"Dont-look-back-in-anger\",\"vipRights\":null,\"userType\":0,\"expertTags\":null,\"remarkName\":null,\"avatarUrl\":\"http:\\/\\/p1.music.126.net\\/uIYfDipGjL79hVamaBM7Eg==\\/109951163172521165.jpg\",\"experts\":null,\"authStatus\":0},\"beReplied\":[],\"pendantData\":null,\"expressionUrl\":null,\"liked\":false,\"likedCount\":0,\"commentId\":1240168966,\"time\":1536374773122,\"content\":\"The dark knight\",\"isRemoveHotComment\":false},{\"user\":{\"locationInfo\":null,\"userId\":1322316949,\"vipType\":0,\"nickname\":\"重归第一名\",\"vipRights\":null,\"userType\":0,\"expertTags\":null,\"remarkName\":null,\"avatarUrl\":\"http:\\/\\/p1.music.126.net\\/UAhs5QUO67FJDfI_ZGl98w==\\/109951163216332483.jpg\",\"experts\":null,\"authStatus\":0},\"beReplied\":[],\"pendantData\":null,\"expressionUrl\":null,\"liked\":false,\"likedCount\":0,\"commentId\":1240128888,\"time\":1536370938296,\"content\":\"好动感\",\"isRemoveHotComment\":false},{\"user\":{\"locationInfo\":null,\"userId\":377433194,\"vipType\":0,\"nickname\":\"你若安然便是晴天123\",\"vipRights\":null,\"userType\":0,\"expertTags\":null,\"remarkName\":null,\"avatarUrl\":\"http:\\/\\/p1.music.126.net\\/nmVdUVWmEt_B-Bzm8iY7nQ==\\/18743374720327626.jpg\",\"experts\":null,\"authStatus\":0},\"beReplied\":[],\"pendantData\":null,\"expressionUrl\":null,\"liked\":false,\"likedCount\":1,\"commentId\":1240020861,\"time\":1536345990356,\"content\":\"这些精彩评论大多都是博眼球 毫无深刻而言\",\"isRemoveHotComment\":false}],\"total\":10902}}"

    //25794008、黑暗骑士、netease
    @Test
    fun getMusicComment() {

    }
}