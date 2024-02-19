package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.LoginUser;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor // 어노테이션 추가로 하단 주석되어 있는 IndexController를 사용하지 않음
@Controller // 기존 @Controller 에서 @RestController로 변경 -> 변경하니깐 return에 문자열을 머스테치로 읽지 않고 단순 문자열로 읽음 그래서 다시 원복
public class IndexController {

    // service
    private final PostsService postsService;
    private final HttpSession httpSession;

//    public IndexController(PostsService postsService) {
//        this.postsService = postsService;
//    }


    /**
     * 메인화면 및 목록 조회
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {     // @LoginUser SessionUser user
        // Model
        model.addAttribute("posts", postsService.findAllDesc());

        // login
        //SessionUser user = (SessionUser) httpSession.getAttribute("user"); // (SessionUser) httpSession.getAttribute("user")
        // @LoginUser 로 인해 더 이상 사용하지 않음 주석처리, 재사용성을 위해 OAuth를 받는 코드를 따로 구현함
        if (user != null) {     // if (user != null)
            model.addAttribute("userName", user.getName());
            System.out.println("userEmail ##### " + user.getEmail());
            System.out.println("userPicture ##### " + user.getPicture());
        }

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

// (SessionUser) httpSession.getAttribute("user")
// 앞서 작성된 CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성했다.
// 즉, 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있다.

// if (user != null)
// 세션에 저장된 겂이 있을 때만 model에 userName으로 등록한다.
// 세션에 저장된 값이 없으면 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보이지 않데 된다.

// @LoingUser SessionUser user
// 기존에 (User) httpSession.getAttribute("user")로 가져오던 세션 정보값이 개선 되었다.
// 이제는 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있게 되었다.