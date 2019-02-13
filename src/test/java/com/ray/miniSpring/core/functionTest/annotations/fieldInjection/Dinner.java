package com.ray.miniSpring.core.functionTest.annotations.fieldInjection;

import com.ray.miniSpring.core.annotations.Component;
import com.ray.miniSpring.core.annotations.Autowired;
import com.ray.miniSpring.core.annotations.Qualifier;

@Component
public class Dinner {
    @Autowired
    @Qualifier("pingAn")
    private Fruit apple;

    @Autowired
    private Fruit banana;

    @Autowired
    private Cookie cookie1;

    private Cookie cookie2;

    private Fruit orange;

    @Override
    public String toString() {
        return "Dinner{" +
                "apple=" + apple +
                ", banana=" + banana +
                '}';
    }

    public Fruit getApple() {
        return apple;
    }

    public void setApple(Fruit apple) {
        this.apple = apple;
    }

    public Fruit getBanana() {
        return banana;
    }

    public void setBanana(Fruit banana) {
        this.banana = banana;
    }

    public Cookie getCookie1() {
        return cookie1;
    }

    public void setCookie1(Cookie cookie1) {
        this.cookie1 = cookie1;
    }

    public Cookie getCookie2() {
        return cookie2;
    }

    public void setCookie2(Cookie cookie2) {
        this.cookie2 = cookie2;
    }

    public Fruit getOrange() {
        return orange;
    }

    public void setOrange(Fruit orange) {
        this.orange = orange;
    }
}
