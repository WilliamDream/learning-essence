package com.william.redis.controller;

import com.william.redis.service.BookTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booktag")
public class BookTagController {

    @Autowired
    private BookTagService bookTagService;


    /**
     * 新增书籍标签
     * @param tagKey
     * @param tagName
     */
    @RequestMapping("/addBookTag")
    public void addBookTag(@RequestParam("tagKey") String tagKey, @RequestParam("tagName") String tagName){
        bookTagService.addBookTag(tagKey,tagName);
    }

    /**
     * 根据key获取图书标签
     * @param tagKey
     * @return
     */
    @RequestMapping("/getBookTagByKey")
    public String getBookTagByKey(@RequestParam("tagKey")String tagKey){
        return bookTagService.getBookTagByKey(tagKey);
    }

    /**
     *
     * @param tagKey
     * @param tagName
     * @return
     */
    @RequestMapping("/getAndSetBooktag")
    public String getAndSetBooktag(@RequestParam("tagKey") String tagKey, @RequestParam("tagName") String tagName){
        return bookTagService.getAndSetBooktag(tagKey,tagName);
    }

    /**
     * 根据key模糊查询
     * @param tagKey
     * @return
     */
    @RequestMapping("/fingByKeyLike")
    public String fingByKeyLike(@RequestParam("tagKey") String tagKey){
        return bookTagService.fingByKeyLike(tagKey);
    }

    /**
     * 多个key获取
     * @param tagKeys
     * @return
     */
    @RequestMapping("/fingByKeys")
    public List<String> fingByKeys(@RequestParam("tagKeys") List<String> tagKeys){
        return bookTagService.fingByKeys(tagKeys);
    }


}
