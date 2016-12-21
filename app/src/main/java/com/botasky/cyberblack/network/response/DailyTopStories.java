package com.botasky.cyberblack.network.response;

/**
 * Created by Botasky on 21/12/2016.
 */

public class DailyTopStories {
    /**
     * image : http://pic4.zhimg.com/e57f3dd7c6cb6c2a6fa0b1b6e728e473.jpg
     * type : 0
     * id : 9082912
     * ga_prefix : 122107
     * title : 机场跑道突然塌陷，吓得我赶紧蒸了一锅海鲜
     */

    private String image;
    private int type;
    private int id;
    private String ga_prefix;
    private String title;

    public void setImage(String image) {
        this.image = image;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public String getTitle() {
        return title;
    }
}
