package com.spring.codeblog.controller;

import com.spring.codeblog.model.Post;
import com.spring.codeblog.service.CodeblogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CodeblogController {
    @Autowired
    CodeblogService codeblogService;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ModelAndView getPosts() {
        ModelAndView mv = new ModelAndView("posts");
        //ModelAndView mv = new ModelAndView(viewName:"posts");//CHECK HERE
        List<Post> posts = codeblogService.findAll();
        //mv.addObject(attributeName:"posts", posts);//CHECK HERE
        mv.addObject("posts", posts);
        return mv;

    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public ModelAndView getPostDetails(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("postDetails");
        //ModelAndView mv = new ModelAndView(viewName:"posts");//CHECK HERE
        Post posts = codeblogService.findById(id);
        //mv.addObject(attributeName:"posts", posts);//CHECK HERE
        mv.addObject("posts", posts);
        return mv;

    }

    @RequestMapping(value = "/newpost", method = RequestMethod.GET)
    public String getPostForm() {
        return "postForm";
    }

    @RequestMapping(value = "/newpost", method = RequestMethod.POST)
    public String savePost(@Validated Post post, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            //attributes.addFlashAttribute(s:"mensagem", o:"verifique se os campos foram preenchidos!");
            attributes.addFlashAttribute("verifique se os campos foram preenchidos!");
            return "redirect:/newpost";
        }

        post.setData(LocalDate.now());

        codeblogService.save(post);
        return "redirect:/posts";
    }


}
