package com.poly.labshop.controller;

import com.poly.labshop.model.Category;
import com.poly.labshop.repository.CategoryRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("categories")
public class CategoryController {
    /* Sử dụng Autowired để tiêm thể hiện CategoryRepository vào CategoryController*/
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    private String list(ModelMap model,@RequestParam Optional<String> message) {
        Iterable<Category> list = categoryRepository.findAll();
        if(message.isPresent()) /* Nếu tồn tại nội dung của message */ {
            model.addAttribute("message",message.get()) /* Hiện thị nội dung của message trong view */;
        }
        model.addAttribute("categories",list);
        return "/list";
    }

    @GetMapping("sort")
    private String sort(ModelMap model,@RequestParam Optional<String> message,
                            @SortDefault(sort = "name",direction = Sort.Direction.ASC) Sort sort) {
        Iterable<Category> list = categoryRepository.findAll(sort);
        if(message.isPresent()) /* Nếu tồn tại nội dung của message */ {
            model.addAttribute("message",message.get()) /* Hiện thị nội dung của message trong view */;
        }
        model.addAttribute("categories",list);
        return "/sort";
    }

//  Tạo chức năng hiện thị DS Category có phân trang và sắp xếp
    @GetMapping("paginate")
    private String paginate(ModelMap model,@RequestParam Optional<String> message,
                        @PageableDefault(size =5,sort = "name",direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Category> pages = categoryRepository.findAll(pageable);

        if(message.isPresent()) /* Nếu tồn tại nội dung của message */ {
            model.addAttribute("message",message.get()) /* Hiện thị nội dung của message trong view */;
        }

        List<Sort.Order> sortOrders = pages.getSort().stream().collect(Collectors.toList());
        if(sortOrders.size() > 0 ) {
            Sort.Order order = sortOrders.get(0);
            model.addAttribute("sort",order.getProperty() + "," +
                    (order.getDirection() == Sort.Direction.ASC ? "acs":"desc"));
        } else {
            model.addAttribute("sort","name");
        }
        model.addAttribute("pages",pages);
        return "/paginate";
    }


    @GetMapping(value = {"newOrEdit"})
    public String newOrEdit(ModelMap model){
        Category category = new Category();
        model.addAttribute("category",category);
        return "/newOrEdit";
    }
    @PostMapping("saveOrUpdate")
    public String saveOrUpdate(RedirectAttributes attributes, Category item){
        /* Phương thức Save */
        categoryRepository.save(item);
        attributes.addAttribute("message","New category is saved !");
        return "redirect:/categories";
    }
}
