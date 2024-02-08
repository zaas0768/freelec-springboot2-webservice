package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor // 어노테이션 추가로 하단 주석되어 있는 IndexController를 사용하지 않음
@Controller // 기존 @Controller 에서 @RestController로 변경 -> 변경하니깐 return에 문자열을 머스테치로 읽지 않고 단순 문자열로 읽음 그래서 다시 원복
public class IndexController {

    // service
    private final PostsService postsService;

//    public IndexController(PostsService postsService) {
//        this.postsService = postsService;
//    }


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