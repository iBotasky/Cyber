package com.botasky.cyberblack.network.response;

import java.util.List;

/**
 * Created by Botasky on 13/12/2016.
 */

public class SubjectsBean {
    /**
     * rating : {"max":10,"average":8.8,"stars":"45","min":0}
     * genres : ["剧情","爱情","动画"]
     * title : 你的名字。
     * casts : [{"alt":"https://movie.douban.com/celebrity/1185637/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/13768.jpg","large":"https://img1.doubanio.com/img/celebrity/large/13768.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/13768.jpg"},"name":"神木隆之介","id":"1185637"},{"alt":"https://movie.douban.com/celebrity/1316660/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/1445093052.07.jpg","large":"https://img1.doubanio.com/img/celebrity/large/1445093052.07.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/1445093052.07.jpg"},"name":"上白石萌音","id":"1316660"},{"alt":"https://movie.douban.com/celebrity/1018667/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/36735.jpg","large":"https://img3.doubanio.com/img/celebrity/large/36735.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/36735.jpg"},"name":"长泽雅美","id":"1018667"}]
     * collect_count : 144563
     * original_title : 君の名は。
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1005177/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/1118.jpg","large":"https://img1.doubanio.com/img/celebrity/large/1118.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/1118.jpg"},"name":"新海诚","id":"1005177"}]
     * year : 2016
     * images : {"small":"https://img1.doubanio.com/view/movie_poster_cover/ipst/public/p2395733377.jpg","large":"https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p2395733377.jpg","medium":"https://img1.doubanio.com/view/movie_poster_cover/spst/public/p2395733377.jpg"}
     * alt : https://movie.douban.com/subject/26683290/
     * id : 26683290
     */

    private RatingBean rating;
    private String title;
    private int collect_count;
    private String original_title;
    private String subtype;
    private String year;
    private ImagesBean images;
    private String alt;
    private String id;
    private List<String> genres;
    private List<CastsBean> casts;
    private List<DirectorsBean> directors;

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setCasts(List<CastsBean> casts) {
        this.casts = casts;
    }

    public void setDirectors(List<DirectorsBean> directors) {
        this.directors = directors;
    }

    public RatingBean getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getYear() {
        return year;
    }

    public ImagesBean getImages() {
        return images;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<CastsBean> getCasts() {
        return casts;
    }

    public List<DirectorsBean> getDirectors() {
        return directors;
    }

    public static class RatingBean {
        /**
         * max : 10
         * average : 8.8
         * stars : 45
         * min : 0
         */

        private int max;
        private double average;
        private String stars;
        private int min;

        public void setMax(int max) {
            this.max = max;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public double getAverage() {
            return average;
        }

        public String getStars() {
            return stars;
        }

        public int getMin() {
            return min;
        }
    }

    public static class ImagesBean {
        /**
         * small : https://img1.doubanio.com/view/movie_poster_cover/ipst/public/p2395733377.jpg
         * large : https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p2395733377.jpg
         * medium : https://img1.doubanio.com/view/movie_poster_cover/spst/public/p2395733377.jpg
         */

        private String small;
        private String large;
        private String medium;

        public void setSmall(String small) {
            this.small = small;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getSmall() {
            return small;
        }

        public String getLarge() {
            return large;
        }

        public String getMedium() {
            return medium;
        }
    }

    public static class CastsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1185637/
         * avatars : {"small":"https://img1.doubanio.com/img/celebrity/small/13768.jpg","large":"https://img1.doubanio.com/img/celebrity/large/13768.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/13768.jpg"}
         * name : 神木隆之介
         * id : 1185637
         */

        private String alt;
        private AvatarsBean avatars;
        private String name;
        private String id;

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAlt() {
            return alt;
        }

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public static class AvatarsBean {
            /**
             * small : https://img1.doubanio.com/img/celebrity/small/13768.jpg
             * large : https://img1.doubanio.com/img/celebrity/large/13768.jpg
             * medium : https://img1.doubanio.com/img/celebrity/medium/13768.jpg
             */

            private String small;
            private String large;
            private String medium;

            public void setSmall(String small) {
                this.small = small;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getSmall() {
                return small;
            }

            public String getLarge() {
                return large;
            }

            public String getMedium() {
                return medium;
            }
        }
    }

    public static class DirectorsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1005177/
         * avatars : {"small":"https://img1.doubanio.com/img/celebrity/small/1118.jpg","large":"https://img1.doubanio.com/img/celebrity/large/1118.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/1118.jpg"}
         * name : 新海诚
         * id : 1005177
         */

        private String alt;
        private AvatarsBean avatars;
        private String name;
        private String id;

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAlt() {
            return alt;
        }

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public static class AvatarsBean {
            /**
             * small : https://img1.doubanio.com/img/celebrity/small/1118.jpg
             * large : https://img1.doubanio.com/img/celebrity/large/1118.jpg
             * medium : https://img1.doubanio.com/img/celebrity/medium/1118.jpg
             */

            private String small;
            private String large;
            private String medium;

            public void setSmall(String small) {
                this.small = small;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getSmall() {
                return small;
            }

            public String getLarge() {
                return large;
            }

            public String getMedium() {
                return medium;
            }
        }
    }
}
