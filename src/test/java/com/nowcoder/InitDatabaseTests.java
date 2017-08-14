package com.nowcoder;

import com.nowcoder.dao.NewsDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.News;
import com.nowcoder.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    NewsDAO newsDAO;

    @Test
    public void inDate() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setName(String.format("USER%d", i));
            user.setHeadUrl(String.format("http://image.nowcoder.com/head/%dt.png", random.nextInt(1000)));

            user.setPassword("");
            user.setSalt("");

            userDAO.addUser(user);

            //
            News news = new News();
            news.setCommentCount(i);
            //
            Date date=new Date();
            date.setTime(date.getTime()+1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://image.nowcoder.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(i+1);
            news.setUserId(i+1);
            news.setTitle(String.format("TITLE{%d}",i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html",i));
            newsDAO.addNews(news);

            user.setPassword("newpassword");
            userDAO.updatePassword(user);
        }

        Assert.assertEquals("newpassword", userDAO.selectById(2).getPassword());
        userDAO.deleteById(2);
        Assert.assertNull(userDAO.selectById(2));
    }

}
