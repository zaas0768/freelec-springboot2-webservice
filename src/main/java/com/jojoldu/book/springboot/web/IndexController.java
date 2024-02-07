package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    // service
    private final PostsService postsService;

    // service 주입
    @Autowired
    public IndexController(PostsService postsService) {
        this.postsService = postsService;
    }

    /**
     * 메인화면 및 목록 조회
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        // Model
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    /**
     * 등록하면으로 이동
     * @return
     */
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    /**
     * 수정화면으로 이동
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts", dto);
        return "posts-update";
    }
}


// Model
// 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장
// ex) postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달