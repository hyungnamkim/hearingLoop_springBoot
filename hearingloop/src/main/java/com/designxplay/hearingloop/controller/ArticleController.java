package com.designxplay.hearingloop.controller;
import com.designxplay.hearingloop.dto.CommentDto;
import com.designxplay.hearingloop.service.CommentService;
import com.designxplay.hearingloop.service.FileService;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.designxplay.hearingloop.dto.ArticleForm;
import com.designxplay.hearingloop.entity.Article;
import com.designxplay.hearingloop.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@ToString
@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/articles/new")
    public String newArticleForm()
    {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        Article article = form.toEntity();
        // 파일 저장 로직을 FileService를 통해 처리하고 파일 이름을 받음
        List<String> fileNames = fileService.saveFiles(form.getImageFiles());
        // 파일 이름을 Article 엔티티에 설정
        article.setFileNames(fileNames);
        Article saved = articleRepository.save(article);


        return "redirect:/articles/"+saved.getId();
    }

//    @GetMapping("/articles/{id}")
//    public String show(@PathVariable Long id, Model model){
//        Article articleEntity = articleRepository.findById(id).orElse(null);
//        model.addAttribute("article", articleEntity);
//        return "articles/show";
//    }
@GetMapping("/articles/{id}")
public String show(@PathVariable Long id, Model model) {
        //1. id를 조회해 데이터 가져오기
    Article articleEntity = articleRepository.findById(id).orElse(null);
    List<CommentDto> commentsDtos = commentService.comments(id);


    // 이미지 파일이 있는 경우에 해당 이미지 파일들을 model에 입력시켜주기
    if (articleEntity != null && articleEntity.getFileNames() != null) {
        List<String> fileUrls = articleEntity.getFileNames().stream()
                .map(fileName -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
                        "serveFile", fileName).build().toUri().toString())
                .collect(Collectors.toList());
        model.addAttribute("fileUrls", fileUrls);
    }
    //2. 모델에 데이터 등록하기
    model.addAttribute("article", articleEntity);
    model.addAttribute("commentDtos", commentsDtos);
    //3. 뷰 페이지 설정하기
    return "articles/show";
}


    @GetMapping("/articles")
    public String index(Model model)
    {
        List<Article> articleEntityList = articleRepository.findAll();
        model.addAttribute("articleList", articleEntityList);
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }

    @PostMapping("articles/update")
    public String update(ArticleForm form)
    {
        Article articleEntity = form.toEntity();
        Article target =articleRepository.findById(articleEntity.getId()).orElse(null);

        if(target != null)
        {
            articleRepository.save(articleEntity);
        }
        return "redirect:/articles/"+articleEntity.getId();
    }
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        Article target = articleRepository.findById(id).orElse(null);
        if(target != null)
        {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }

        return "redirect:/articles";
    }
}
