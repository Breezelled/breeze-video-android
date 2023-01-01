package com.example.cby2112114536.VO;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author breeze
 */
public class UserVO extends BmobUser {
    private BmobFile avatar;
    private String nickname;
    private boolean sex;
    private String intro;

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "avatar=" + avatar +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", intro='" + intro + '\'' +
                '}';
    }
}
