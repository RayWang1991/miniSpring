package indi.ray.miniSpring.core.functionTest.annotations.constructorInjection.baseConstructorInjectin;

import indi.ray.miniSpring.core.annotations.Autowired;
import indi.ray.miniSpring.core.annotations.Component;
import indi.ray.miniSpring.core.annotations.Qualifier;

@Component
public class Dinner {
    private Fruit apple;

    private Fruit banana;

    private Cookie cookie1;

    private Cookie cookie2;


    @Autowired
    public Dinner(@Qualifier("pingAn") Fruit apple, Banana banana, Cookie cookie) {
        this.apple = apple;
        this.banana = banana;
        this.cookie1 = cookie;
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
}
