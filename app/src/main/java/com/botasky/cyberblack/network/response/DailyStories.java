package com.botasky.cyberblack.network.response;

import java.util.List;

/**
 * Created by Botasky on 21/12/2016.
 */

public class DailyStories {
    /**
     * title : 站在诺奖颁奖典礼现场，全世界看着我唱歌，我忘词了
     * ga_prefix : 122114
     * images : ["http://pic3.zhimg.com/64f8f804a876f9df7198a8d750e5184a.jpg"]
     * multipic : true
     * type : 0
     * id : 9084349
     */

    private String title;
    private String ga_prefix;
    private boolean multipic;
    private int type;
    private int id;
    private List<String> images;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public boolean getMultipic() {
        return multipic;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public List<String> getImages() {
        return images;
    }
}
