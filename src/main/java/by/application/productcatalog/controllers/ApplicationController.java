package by.application.productcatalog.controllers;

import by.application.productcatalog.model.dto.ProductDto;
import by.application.productcatalog.model.entity.User;
import by.application.productcatalog.model.enums.MessageStatus;
import by.application.productcatalog.model.enums.UserType;
import by.application.productcatalog.service.ProductService;
import by.application.productcatalog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ApplicationController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/")
    public String mainPage(Model model) {
        setAuth(model);
        return "mainpage";
    }

    @GetMapping("/message")
    public String messagePage(Model model) {
        model.addAttribute("message1", productService.getQuestionsByStatus(MessageStatus.CREATE.getValue()));
        model.addAttribute("message2", productService.getQuestionsByStatus(MessageStatus.DONE.getValue()));
        setAuth(model);
        return "messagepage";
    }

    @PostMapping("/answer")
    public String answer(
            @RequestParam String id,
            @RequestParam String answer,
            Model model) {
        productService.saveMessage(id, answer);

        model.addAttribute("message1", productService.getQuestionsByStatus(MessageStatus.CREATE.getValue()));
        model.addAttribute("message2", productService.getQuestionsByStatus(MessageStatus.DONE.getValue()));
        setAuth(model);
        return "messagepage";
    }

    @GetMapping("/catalog")
    public String catalogPage(Model model) {
        Integer categoryId = 1;
        try {
            List<ProductDto> productDtoList = productService.getListProductByCategory(categoryId);
            model.addAttribute("products", productDtoList);
        } catch(Exception e) {
            model.addAttribute("server_error", "Ошибка на сервере");
        }
        setAuth(model);
        return "catalogpage";
    }

    @GetMapping("/catalog2")
    public String catalogPage2(Model model) {
        Integer categoryId = 2;
        try {
            List<ProductDto> productDtoList = productService.getListProductByCategory(categoryId);
            model.addAttribute("products", productDtoList);
        } catch(Exception e) {
            model.addAttribute("server_error", "Ошибка на сервере");
        }
        setAuth(model);
        return "catalog2page";
    }

    @GetMapping("/catalog3")
    public String catalogPage3(Model model) {
        Integer categoryId = 3;
        try {
            List<ProductDto> productDtoList = productService.getListProductByCategory(categoryId);
            model.addAttribute("products", productDtoList);
        } catch(Exception e) {
            model.addAttribute("server_error", "Ошибка на сервере");
        }
        setAuth(model);
        return "catalog3page";
    }

    @GetMapping("/partners")
    public String partnersPage(Model model) {
        setAuth(model);
        return "partnerspage";
    }

    @GetMapping("/contacts")
    public String contactsPage(Model model) {
        setAuth(model);
        return "contactspage";
    }

    @GetMapping("/cabinet")
    public String cabinetPage(Model model) {
        setAuth(model);
        return "cabinetpage";
    }

    @GetMapping("/basket")
    public String basketPage(Model model) {
        setAuth(model);
        return "basketpage";
    }

    @GetMapping("/searchresult")
    public String searchresultPage(Model model) {
        setAuth(model);
        return "searchresultpage";
    }

    @GetMapping("/support")
    public String supportPage(Model model) {
        try {
            if(userService.findAuthorizationUser()){
                Optional<User> optional = userService.getAuthUser();
                model.addAttribute("questions", productService.getQuestionsByUserId(optional.get().getId()));
            }
        } catch(Exception e) {
            model.addAttribute("server_error", "Ошибка на сервере");
        }
        setAuth(model);
        return "supportpage";
    }

    @GetMapping("/cabinetu")
    public String cabinetuPage(Model model) {
        try {
            if(userService.findAuthorizationUser() && userService.getAuthUser().isPresent()){
                User user = userService.getAuthUser().get();
                model.addAttribute("name", user.getName());
                model.addAttribute("login", user.getLogin());
                model.addAttribute("password",  user.getPassword());
            }
        } catch(Exception e) {
            model.addAttribute("server_error", "Ошибка на сервере");
        }
        setAuth(model);
        return "cabinetupage";
    }

    @GetMapping("/cabineta")
    public String cabinetaPage(Model model) {
        try {
            //admin
            if(userService.findAuthorizationUser() && userService.getAuthUser().isPresent()){
                User user = userService.getAuthUser().get();
                model.addAttribute("name", user.getName());
                model.addAttribute("login", user.getLogin());
                model.addAttribute("password",  user.getPassword());
            }
        } catch(Exception e) {
            model.addAttribute("server_error", "Ошибка на сервере");
        }
        setAuth(model);
        return "cabinetapage";
    }


    @GetMapping("/info")
    public String infoPage(Model model) {
        setAuth(model);
        return "infopage";
    }

    @GetMapping("/services")
    public String servicesPage(Model model) {
        setAuth(model);
        return "servicespage";
    }


    private void setAuth(Model model){
        if(userService.findAuthorizationUser()){
            Optional<User> optional = userService.getAuthUser();
            model.addAttribute("auth", "");
            if(optional.isPresent()){
                if(optional.get().getTypeId().equals(UserType.USER.getValue())){
                    model.addAttribute("user", "");
                } else {
                    model.addAttribute("admin", "");
                }
            }
        }
    }
}
