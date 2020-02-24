package com.guoyi.listeninglove.socket

import com.guoyi.listeninglove.bean.MessageInfoBean
import com.guoyi.listeninglove.bean.UserInfoBean

/**
 * Des    :
 * Author : master.
 * Date   : 2018/9/26 .
 */
interface SocketListener {
    /**
     * 用户下线
     */
    fun onLeaveEvent(user: UserInfoBean)

    /**
     * 用户上线
     */
    fun onJoinEvent(user: UserInfoBean)

    /**
     * 在线用户
     */
    fun onOnlineUsers(users: MutableList<UserInfoBean>)

    /**
     * 消息
     */
    fun onMessage(msgInfo: MessageInfoBean)

    /**
     * 错误
     */
    fun onError(msg: String)
}