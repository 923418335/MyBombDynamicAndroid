package madan.com.test.project.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by 山东娃 on 2016/3/5.
 */
public class User extends BmobUser {
    private Boolean sex;

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Boolean getSex() {
        return sex;
    }
}
